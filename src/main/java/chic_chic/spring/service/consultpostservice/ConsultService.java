package chic_chic.spring.service.consultpostservice;

import chic_chic.spring.domain.enums.PostType;
import chic_chic.spring.web.dto.ConsultPostRequest;
import chic_chic.spring.web.dto.ConsultPostResponse;
import chic_chic.spring.web.dto.MemberResponseDTO;
import org.springframework.data.domain.Pageable;

public interface ConsultService {

    ConsultPostResponse.EntirePostDto creatPost(ConsultPostRequest request, MemberResponseDTO.MemberInfoDTO memberInfo);

    ConsultPostResponse.EntirePostDto getPost(Long consultPostId);

    ConsultPostResponse.HomeLatestDto getLatestPost();

    ConsultPostResponse.HomeResponseDto getPostByTop2();

    ConsultPostResponse.PagedResponseDto getPage(PostType type, Pageable pageable);

}
