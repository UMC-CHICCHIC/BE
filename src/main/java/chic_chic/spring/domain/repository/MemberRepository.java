package chic_chic.spring.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import chic_chic.spring.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByNickname(String nickname);
}
