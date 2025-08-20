package chic_chic.spring.service.oauthservice;

import chic_chic.spring.domain.entity.Member;
import chic_chic.spring.domain.enums.SocialType;

public interface Oauth2MemberService {
    Member findOrCreateOauthMember(SocialType socialType, String socialId, String email, String name);
}
