package chic_chic.spring.service.memberservice;

import chic_chic.spring.apiPayload.code.status.ErrorStatus;
import chic_chic.spring.apiPayload.exception.handler.MemberHandler;
import chic_chic.spring.config.jwt.JwtTokenProvider;
import chic_chic.spring.converter.MemberConverter;
import chic_chic.spring.domain.entity.Member;
import chic_chic.spring.domain.entity.RefreshToken;
import chic_chic.spring.domain.repository.MemberRepository;
import chic_chic.spring.domain.repository.RefreshTokenRepository;
import chic_chic.spring.service.imageservice.S3UploaderService;
import chic_chic.spring.web.dto.MemberRequestDTO;
import chic_chic.spring.web.dto.MemberResponseDTO;
import chic_chic.spring.web.dto.S3ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final S3UploaderService s3UploaderService;


    @Override
    @Transactional
    public MemberResponseDTO.JoinResultDTO signup(MemberRequestDTO.JoinDto joinDto) {
        String encodedPassword = passwordEncoder.encode(joinDto.getPassword());


        if (memberRepository.findByEmail(joinDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        Member member = Member.builder().email(joinDto.getEmail()).password(encodedPassword).phoneNumber(joinDto.getPhoneNumber()).nickname(joinDto.getNickname()).profileImageUrl("https://aws-chicchic-bucket.s3.ap-northeast-2.amazonaws.com/default-profile.png").build();

        Member savedMember = memberRepository.save(member);

        return MemberResponseDTO.JoinResultDTO.builder().memberId(savedMember.getId()).createdAt(savedMember.getCreatedAt()).build();
    }

    @Override
    @Transactional
    public MemberResponseDTO.LoginResultDTO login(MemberRequestDTO.LoginDto loginDto) {
        Member member = memberRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("이메일 또는 비밀번호가 잘못되었습니다."));

        if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("이메일 또는 비밀번호가 잘못되었습니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(member.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getEmail());

        RefreshToken tokenEntity = RefreshToken.builder().email(member.getEmail()).token(refreshToken).build();
        refreshTokenRepository.save(tokenEntity);

        return MemberResponseDTO.LoginResultDTO.builder()
                .memberId(member.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public MemberResponseDTO.MemberInfoDTO getMemberInfo(HttpServletRequest request) {
        Authentication authentication = jwtTokenProvider.extractAuthentication(request);
        String email = authentication.getName();

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        return MemberConverter.toMemberInfo(member);
    }

    @Transactional
    public MemberResponseDTO.UpdateResultDto updateMemberInfo(MemberRequestDTO.UpdateDto dto, HttpServletRequest request) {
        String token = JwtTokenProvider.resolveToken(request);
        String email = jwtTokenProvider.getEmailFromToken(token);

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        if (member.getSocialType() != null) {
            if (dto.getNickname() != null) {
                member.updateNickname(dto.getNickname());
            }
            if (dto.getPhoneNumber() != null) {
                member.updatePhoneNumber(dto.getPhoneNumber());
            }
        } else {
            if (dto.getNickname() != null) {
                member.updateNickname(dto.getNickname());
            }
            if (dto.getPhoneNumber() != null) {
                member.updatePhoneNumber(dto.getPhoneNumber());
            }

        }


        memberRepository.save(member);

        return MemberResponseDTO.UpdateResultDto.builder().nickname(member.getNickname()).phoneNumber(member.getPhoneNumber()).build();
    }

    @Override
    @Transactional
    public void withdraw(HttpServletRequest request) {
        String token = JwtTokenProvider.resolveToken(request);
        String email = jwtTokenProvider.getEmailFromToken(token);

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        memberRepository.delete(member);
    }

    @Transactional
    public void logout(HttpServletRequest request) {
        String token = JwtTokenProvider.resolveToken(request);
        String email = jwtTokenProvider.getEmailFromToken(token);

        RefreshToken refreshToken = refreshTokenRepository.findByEmail(email).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        refreshTokenRepository.delete(refreshToken);
    }

    public String getProfileImageUrl(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        if (member.getProfileImageUrl() == null || member.getProfileImageUrl().isEmpty()) {
            return "https://aws-chicchic-bucket.s3.ap-northeast-2.amazonaws.com/default-profile.png";
        }

        return member.getProfileImageUrl();
    }

    @Transactional
    public String updateProfileImage(MultipartFile file, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        S3ResponseDto uploadResult = s3UploaderService.upload(file);

        member.setProfileImageUrl(uploadResult.getUrl());
        memberRepository.save(member);

        return uploadResult.getUrl();
    }

    public String deleteProfileImage(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        String currentImageUrl = member.getProfileImageUrl();

        String defaultImageUrl = "https://aws-chicchic-bucket.s3.ap-northeast-2.amazonaws.com/default-profile.png";
        if (currentImageUrl != null && !currentImageUrl.equals(defaultImageUrl)) {
            String fileName = s3UploaderService.extractFileNameFromUrl(currentImageUrl);
            s3UploaderService.delete(fileName);
        }

        member.setProfileImageUrl(defaultImageUrl);
        memberRepository.save(member);

        return defaultImageUrl;
    }

}
