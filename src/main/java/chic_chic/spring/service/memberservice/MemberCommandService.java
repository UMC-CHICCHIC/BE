package chic_chic.spring.service.memberservice;


import chic_chic.spring.web.dto.MemberRequestDTO;
import chic_chic.spring.web.dto.MemberResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface MemberCommandService {
    MemberResponseDTO.JoinResultDTO signup(MemberRequestDTO.JoinDto joinDto);

    MemberResponseDTO.LoginResultDTO login(MemberRequestDTO.LoginDto loginDto);

    @Transactional(readOnly = true)
    MemberResponseDTO.MemberInfoDTO getMemberInfo(HttpServletRequest request);

    @Transactional
    MemberResponseDTO.UpdateResultDto updateMemberInfo(MemberRequestDTO.UpdateDto updateDto, HttpServletRequest request);

    void withdraw(HttpServletRequest request);

    void logout(HttpServletRequest request);

    String getProfileImageUrl(String email);

    String updateProfileImage(MultipartFile file, String email);

    String deleteProfileImage(String email);



}
