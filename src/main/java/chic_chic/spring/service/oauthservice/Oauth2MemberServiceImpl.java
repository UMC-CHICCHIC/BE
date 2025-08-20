package chic_chic.spring.service.oauthservice;

import chic_chic.spring.domain.entity.Member;
import chic_chic.spring.domain.enums.SocialType;
import chic_chic.spring.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class Oauth2MemberServiceImpl implements Oauth2MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member findOrCreateOauthMember(SocialType socialType, String socialId, String email, String name) {
        return memberRepository.findBySocialTypeAndSocialId(socialType, socialId)
                .orElseGet(() -> {
                    Member member = Member.builder()
                            .email(email)
                            .nickname(name)
                            .password(null)
                            .phoneNumber("000-0000-0000")
                            .socialType(socialType)
                            .socialId(socialId)
                            .profileImageUrl("https://aws-chicchic-bucket.s3.ap-northeast-2.amazonaws.com/default-profile.png")
                            .build();
                    return memberRepository.save(member);
                });
    }

}
