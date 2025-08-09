package chic_chic.spring.web.controller;

import chic_chic.spring.apiPayload.ApiResponse;
import chic_chic.spring.service.ConsultCommentService.CommentService;
import chic_chic.spring.service.MemberService.MemberCommandService;
import chic_chic.spring.web.dto.ConsultPostCommentsRequest;
import chic_chic.spring.web.dto.ConsultPostCommentsResponse;
import chic_chic.spring.web.dto.MemberResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/consult-posts/{consultPostId}/comments")
public class ConsultCommentController {

    private final CommentService commentService;
    private final MemberCommandService memberService;

    // 댓글 작성
    @PostMapping
    public ResponseEntity<ApiResponse<ConsultPostCommentsResponse.CommentResponseDto>> createComment
            (@PathVariable Long consultPostId,
             @RequestBody @Valid ConsultPostCommentsRequest request, HttpServletRequest httpRequest){
        MemberResponseDTO.MemberInfoDTO memberInfo = memberService.getMemberInfo(httpRequest);
        ConsultPostCommentsResponse.CommentResponseDto created = commentService.createParentComment(consultPostId, request, memberInfo);

        return ResponseEntity.ok(ApiResponse.onSuccess(created));
    }

    //대댓글 작성
    @PostMapping("/{groupId}/replies")
    public ResponseEntity<ApiResponse<ConsultPostCommentsResponse.CommentResponseDto>> createReply(
            @PathVariable Long consultPostId,
            @PathVariable Long groupId,
            @RequestBody @Valid ConsultPostCommentsRequest request,
            HttpServletRequest httpRequest){
        MemberResponseDTO.MemberInfoDTO memberInfo = memberService.getMemberInfo(httpRequest);
        ConsultPostCommentsResponse.CommentResponseDto created = commentService.createReply(consultPostId, groupId, request, memberInfo);
        return ResponseEntity.ok(ApiResponse.onSuccess(created));
    }


    @GetMapping
    public ApiResponse<ConsultPostCommentsResponse.CommentResultDto> getComment(@PathVariable Long consultPostId){
        ConsultPostCommentsResponse.CommentResultDto result = commentService.getAllComments(consultPostId);
        return ApiResponse.onSuccess(result);
    }
}
