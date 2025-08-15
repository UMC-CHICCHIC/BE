package chic_chic.spring.domain.repository;

import chic_chic.spring.domain.enums.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;
import chic_chic.spring.domain.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findBySocialTypeAndSocialId(SocialType socialType, String socialId);
}
