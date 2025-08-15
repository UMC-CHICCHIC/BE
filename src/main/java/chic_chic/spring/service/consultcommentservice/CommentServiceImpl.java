package chic_chic.spring.service.consultcommentservice;

import chic_chic.spring.apiPayload.code.status.ErrorStatus;
import chic_chic.spring.apiPayload.exception.GeneralException;
import chic_chic.spring.apiPayload.exception.handler.MemberHandler;
import chic_chic.spring.converter.ConsultPostCommentsConverter;
import chic_chic.spring.domain.entity.ConsultPost;
import chic_chic.spring.domain.entity.Member;
import chic_chic.spring.domain.repository.ConsultPostCommentsRepository;
import chic_chic.spring.domain.repository.ConsultPostRepository;
import chic_chic.spring.domain.repository.MemberRepository;
import chic_chic.spring.web.dto.ConsultPostCommentsRequest;
import chic_chic.spring.web.dto.ConsultPostCommentsResponse;
import chic_chic.spring.web.dto.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
//@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final ConsultPostRepository consultPostRepository;
    private final ConsultPostCommentsRepository commentRepository; // ← 새 이름
    private final MemberRepository memberRepository;

    private final ConsultPostCommentsConverter converter = new ConsultPostCommentsConverter();

    @Override
    @Transactional
    public ConsultPostCommentsResponse.CommentResponseDto createParentComment(
            Long consultPostId,
            ConsultPostCommentsRequest request,
            MemberResponseDTO.MemberInfoDTO memberInfo
    ) {
        Member member = memberRepository.findByEmail(memberInfo.getEmail())
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        ConsultPost consultPost = consultPostRepository.findByConsultPostId(consultPostId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.CONSULT_POST_NOT_FOUND));

        Long maxGroup = commentRepository.findMaxGroupByPostId(consultPostId);
        Long nextGroup = (maxGroup == null) ? 1L : maxGroup + 1L;

        var parent = converter.toParentEntity(request.getContent(), member, consultPost, nextGroup);
        var saved  = commentRepository.save(parent);

        return converter.toCreateResponse(saved);
    }

    @Override
    @Transactional
    public ConsultPostCommentsResponse.CommentResponseDto createReply(
            Long consultPostId,
            Long groupId,
            ConsultPostCommentsRequest request,
            MemberResponseDTO.MemberInfoDTO memberInfo
    ) {
        Member member = memberRepository.findByEmail(memberInfo.getEmail())
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        ConsultPost consultPost = consultPostRepository.findByConsultPostId(consultPostId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.CONSULT_POST_NOT_FOUND));

        Long maxOrder  = commentRepository.findMaxConsultPostCommentsOrder(consultPostId, groupId);
        Long nextOrder = (maxOrder == null) ? 1L : maxOrder + 1L;

        var reply = converter.toReplyEntity(request.getContent(), member, consultPost, groupId, nextOrder);
        var saved = commentRepository.save(reply);

        return converter.toCreateResponse(saved);
    }

    @Override
    public ConsultPostCommentsResponse.CommentResultDto getAllComments(Long consultPostId) {
        var list = commentRepository.findAlignedConsultPostCommentsByPostId(consultPostId);
        return converter.toResultDto(list);
    }
}
