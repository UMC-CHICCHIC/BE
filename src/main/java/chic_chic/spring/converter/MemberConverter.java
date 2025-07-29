package chic_chic.spring.converter;

import chic_chic.spring.domain.Member;
import chic_chic.spring.web.dto.MemberRequestDTO;
import chic_chic.spring.web.dto.MemberResponseDTO;

import java.time.LocalDateTime;

public class MemberConverter {

    public static MemberResponseDTO.JoinResultDTO toJoinResultDTO(Member member) {
        return MemberResponseDTO.JoinResultDTO.builder()
                .memberId(member.getId())
                .createdAt(member.getCreatedAt())
                .build();
    }

    public static Member toMember(MemberRequestDTO.JoinDto request, String encodedPassword) {
        return Member.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .nickname(request.getNickname())
                .build();
    }

    public static MemberResponseDTO.MemberInfoDTO toMemberInfo(Member member) {
        return MemberResponseDTO.MemberInfoDTO.builder()
                .username(member.getUsername())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .nickname(member.getNickname())
                .build();
    }

    public static MemberResponseDTO.LoginResultDTO toLoginResultDTO(Long memberId, String accessToken) {
        return MemberResponseDTO.LoginResultDTO.builder()
                .memberId(memberId)
                .accessToken(accessToken)
                .build();
    }
}
