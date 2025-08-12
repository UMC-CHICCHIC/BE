package chic_chic.spring.web.controller;

import chic_chic.spring.apiPayload.ApiResponse;
import chic_chic.spring.service.ConsultCommentService.CommentService;
import chic_chic.spring.service.MemberService.MemberCommandService;
import chic_chic.spring.web.dto.ConsultPostCommentsRequest;
import chic_chic.spring.web.dto.ConsultPostCommentsResponse;
import chic_chic.spring.web.dto.MemberResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "댓글 작성 API", description = "게시글에 댓글을 작성하는 API 입니다.")
    public ResponseEntity<ApiResponse<ConsultPostCommentsResponse.CommentResponseDto>> createComment
            (@PathVariable Long consultPostId,
             @RequestBody @Valid ConsultPostCommentsRequest request, HttpServletRequest httpRequest){
        MemberResponseDTO.MemberInfoDTO memberInfo = memberService.getMemberInfo(httpRequest);
        ConsultPostCommentsResponse.CommentResponseDto created = commentService.createParentComment(consultPostId, request, memberInfo);

        return ResponseEntity.ok(ApiResponse.onSuccess(created));
    }

    //대댓글 작성
    @PostMapping("/{groupId}/replies")
    @Operation(summary = "대댓글 작성 API", description = "게시글에 대댓글을 작성하는 API 입니다.")
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
    @Operation(summary = "댓글 조회 API", description = "게시글에 작성된 댓글들을 조회하는 API 입니다.")
    public ApiResponse<ConsultPostCommentsResponse.CommentResultDto> getComment(@PathVariable Long consultPostId){
        ConsultPostCommentsResponse.CommentResultDto result = commentService.getAllComments(consultPostId);
        return ApiResponse.onSuccess(result);
    }
}
