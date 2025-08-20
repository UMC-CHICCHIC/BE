package chic_chic.spring.converter;

import chic_chic.spring.domain.entity.Member;
import chic_chic.spring.web.dto.MemberResponseDTO;

public class MemberConverter {



    public static MemberResponseDTO.MemberInfoDTO toMemberInfo(Member member) {
        return MemberResponseDTO.MemberInfoDTO.builder()
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .nickname(member.getNickname())
                .build();
    }

}
