package chic_chic.spring.service.ConsultCommentService;

import chic_chic.spring.web.dto.ConsultPostCommentsRequest;
import chic_chic.spring.web.dto.ConsultPostCommentsResponse;
import chic_chic.spring.web.dto.MemberResponseDTO;


public interface CommentService {

    ConsultPostCommentsResponse.CommentResponseDto createParentComment(Long consultPostId, ConsultPostCommentsRequest request, MemberResponseDTO.MemberInfoDTO memberInfo);

    ConsultPostCommentsResponse.CommentResponseDto createReply(Long consultPostId, Long groupId, ConsultPostCommentsRequest request, MemberResponseDTO.MemberInfoDTO memberInfo);

    ConsultPostCommentsResponse.CommentResultDto getAllComments(Long consultPostId);


}
