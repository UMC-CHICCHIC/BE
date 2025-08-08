package chic_chic.spring.service.MemberService;

import chic_chic.spring.apiPayload.code.status.ErrorStatus;
import chic_chic.spring.apiPayload.exception.handler.CustomException;
import chic_chic.spring.apiPayload.exception.handler.MemberHandler;
import chic_chic.spring.config.jwt.JwtTokenProvider;
import chic_chic.spring.converter.MemberConverter;
import chic_chic.spring.domain.Member;
import chic_chic.spring.domain.RefreshToken;
import chic_chic.spring.domain.repository.MemberRepository;
import chic_chic.spring.domain.repository.RefreshTokenRepository;
import chic_chic.spring.web.dto.MemberRequestDTO;
import chic_chic.spring.web.dto.MemberResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;


    @Override
    @Transactional
    public MemberResponseDTO.JoinResultDTO signup(MemberRequestDTO.JoinDto joinDto) {
        String encodedPassword = passwordEncoder.encode(joinDto.getPassword());


        if (memberRepository.findByEmail(joinDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        Member member = Member.builder()
                .email(joinDto.getEmail())
                .password(encodedPassword)
                .phoneNumber(joinDto.getPhoneNumber())
                .nickname(joinDto.getNickname())
                .build();

        Member savedMember = memberRepository.save(member);

        return MemberResponseDTO.JoinResultDTO.builder()
                .memberId(savedMember.getId())
                .createdAt(savedMember.getCreatedAt())
                .build();
    }

    @Override
    @Transactional  // 저장이 일어나므로 트랜잭션 필요
    public MemberResponseDTO.LoginResultDTO login(MemberRequestDTO.LoginDto loginDto) {
        Member member = memberRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다."));

        if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다.");
        }

        // JWT 토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(member.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getEmail());

        // 리프레시 토큰 엔티티 생성 및 저장
        RefreshToken tokenEntity = RefreshToken.builder()
                .email(member.getEmail())
                .token(refreshToken)
                .build();

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
        String email = authentication.getName();  // 이름 대신 email이 담긴다고 가정

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        return MemberConverter.toMemberInfo(member);
    }

    @Transactional
    public MemberResponseDTO.UpdateResultDto updateMemberInfo(MemberRequestDTO.UpdateDto dto, HttpServletRequest request) {
        String token = JwtTokenProvider.resolveToken(request);
        String email = jwtTokenProvider.getEmailFromToken(token);

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        if (member.getSocialType() != null) {
            // 소셜 로그인 회원: 닉네임, 휴대전화 수정 가능
            if (dto.getNickname() != null) {
                member.updateNickname(dto.getNickname());
            }
            if (dto.getPhoneNumber() != null) {
                member.updatePhoneNumber(dto.getPhoneNumber());
            }
            // 이메일 수정 불가
        } else {
            // 일반 회원: 닉네임, 휴대전화, 이메일 수정 가능
            if (dto.getNickname() != null) {
                member.updateNickname(dto.getNickname());
            }
            if (dto.getPhoneNumber() != null) {
                member.updatePhoneNumber(dto.getPhoneNumber());
            }

        }


        memberRepository.save(member);

        return MemberResponseDTO.UpdateResultDto.builder()
                .nickname(member.getNickname())
                .phoneNumber(member.getPhoneNumber())
                .build();
    }

    @Override
    @Transactional
    public void withdraw(HttpServletRequest request) {
        String token = JwtTokenProvider.resolveToken(request);
        String email = jwtTokenProvider.getEmailFromToken(token);

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        memberRepository.delete(member);
    }

    @Transactional
    public void logout(HttpServletRequest request) {
        String token = JwtTokenProvider.resolveToken(request);
        String email = jwtTokenProvider.getEmailFromToken(token);

        RefreshToken refreshToken = refreshTokenRepository.findByEmail(email)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        refreshTokenRepository.delete(refreshToken);
    }

}
