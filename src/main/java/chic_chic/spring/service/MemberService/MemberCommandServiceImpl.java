package chic_chic.spring.service.MemberService;

import chic_chic.spring.apiPayload.code.status.ErrorStatus;
import chic_chic.spring.apiPayload.exception.handler.MemberHandler;
import chic_chic.spring.config.jwt.JwtTokenProvider;
import chic_chic.spring.converter.MemberConverter;
import chic_chic.spring.domain.Member;
import chic_chic.spring.domain.repository.MemberRepository;
import chic_chic.spring.web.dto.MemberRequestDTO;
import chic_chic.spring.web.dto.MemberResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
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

    @Override
    @Transactional
    public MemberResponseDTO.JoinResultDTO signup(MemberRequestDTO.JoinDto joinDto) {
        String encodedPassword = passwordEncoder.encode(joinDto.getPassword());

        if (memberRepository.findByUsername(joinDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        if (memberRepository.findByEmail(joinDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        if(memberRepository.findByNickname(joinDto.getNickname()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }
        Member member = Member.builder()
                .username(joinDto.getUsername())
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
    public MemberResponseDTO.LoginResultDTO login(MemberRequestDTO.LoginDto loginDto) {
        Member member = memberRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 잘못되었습니다."));

        if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 잘못되었습니다.");
        }

        // JWT 토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(member.getUsername());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getUsername());

        // 리프레시 토큰 저장 로직 필요 (DB 또는 캐시)

        return MemberResponseDTO.LoginResultDTO.builder()
                .memberId(member.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)  // DTO에 리프레시 토큰 필드 추가 필요
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public MemberResponseDTO.MemberInfoDTO getMemberInfo(HttpServletRequest request){
        Authentication authentication = jwtTokenProvider.extractAuthentication(request);
        String username = authentication.getName();

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(()-> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        return MemberConverter.toMemberInfo(member);
    }

}
