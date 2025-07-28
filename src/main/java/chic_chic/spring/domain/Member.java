package chic_chic.spring.domain;

import chic_chic.spring.domain.common.BaseEntity;
import chic_chic.spring.domain.enums.SocialType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 일반 회원가입 아이디
    @Column(nullable = false, unique = true, length = 30)
    private String username;

    // 소셜 로그인은 password가 null 가능
    @Column(nullable = true)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Column(nullable = false, unique = true, length = 20)
    private String nickname;

    // 소셜 로그인 관련 (일반 회원이면 null)
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private SocialType socialType;   // GOOGLE, KAKAO, NAVER

    @Column(length = 100)
    private String socialId;
}
