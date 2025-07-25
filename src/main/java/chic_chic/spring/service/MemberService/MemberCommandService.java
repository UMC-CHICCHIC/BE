package chic_chic.spring.service.MemberService;


import chic_chic.spring.web.dto.MemberRequestDTO;
import chic_chic.spring.web.dto.MemberResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;

public interface MemberCommandService {
    MemberResponseDTO.JoinResultDTO signup(MemberRequestDTO.JoinDto joinDto);

    MemberResponseDTO.LoginResultDTO login(MemberRequestDTO.LoginDto loginDto);

    @Transactional(readOnly = true)
    MemberResponseDTO.MemberInfoDTO getMemberInfo(HttpServletRequest request);
}
