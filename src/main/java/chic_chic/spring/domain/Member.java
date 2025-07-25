package chic_chic.spring.domain;

import chic_chic.spring.domain.common.BaseEntity;
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

    // 아이디 (username)
    @Column(nullable = false, unique = true, length = 30)
    private String username;

    // 비밀번호
    @Column(nullable = false)
    private String password;

    // 이메일
    @Column(nullable = false, unique = true)
    private String email;

    // 휴대전화번호
    @Column(nullable = false, length = 20)
    private String phoneNumber;

    // 닉네임
    @Column(nullable = false, unique = true, length = 20)
    private String nickname;
}
