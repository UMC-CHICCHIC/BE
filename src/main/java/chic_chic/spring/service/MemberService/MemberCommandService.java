package chic_chic.spring.service.MemberService;


import chic_chic.spring.web.dto.MemberRequestDTO;
import chic_chic.spring.web.dto.MemberResponseDTO;

public interface MemberCommandService {
    MemberResponseDTO.JoinResultDTO signup(MemberRequestDTO.JoinDto joinDto);

    MemberResponseDTO.LoginResultDTO login(MemberRequestDTO.LoginDto loginDto);
}
