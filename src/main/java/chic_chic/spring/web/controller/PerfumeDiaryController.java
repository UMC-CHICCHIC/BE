package chic_chic.spring.web.controller;

import chic_chic.spring.apiPayload.code.status.ErrorStatus;
import chic_chic.spring.apiPayload.exception.GeneralException;
import chic_chic.spring.service.perfumediaryservice.PerfumeDiaryService;
import chic_chic.spring.apiPayload.ApiResponse;
import chic_chic.spring.web.dto.CommentRequest;
import chic_chic.spring.web.dto.CommentResponse;
import chic_chic.spring.web.dto.MyDiaryResponse;
import chic_chic.spring.web.dto.perfumediary.PerfumeDiaryDetailResponse;
import chic_chic.spring.web.dto.perfumediary.PerfumeDiaryRequest;
import chic_chic.spring.web.dto.perfumediary.PerfumeDiaryResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class PerfumeDiaryController {

    private final PerfumeDiaryService perfumeDiaryService;
    private final ObjectMapper objectMapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "향수 일기 작성 (multipart/form-data)",
            description = "request: JSON 문자열, image: 파일. 예) request={\"title\":\"...\",\"content\":\"...\",\"isPublic\":true}",
            security = @SecurityRequirement(name = "JWT")
    )
    public ResponseEntity<ApiResponse<PerfumeDiaryResponse>> createDiary(
            @Parameter(description = "일기 데이터(JSON 문자열)",
                    schema = @Schema(example = "{\"title\":\"테스트\",\"content\":\"컨텐츠\",\"isPublic\":true}"))
            @RequestPart("request") String requestJson,
            @Parameter(description = "첨부 이미지 (선택)")
            @RequestPart(value = "image", required = false) MultipartFile image,
            @Parameter(hidden = true) HttpServletRequest httpRequest
    ) throws JsonProcessingException {
        String token = resolveToken(httpRequest);
        PerfumeDiaryRequest request = objectMapper.readValue(requestJson, PerfumeDiaryRequest.class);
        PerfumeDiaryResponse response = perfumeDiaryService.createDiary(token, request, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.onSuccess(response));
    }

    @GetMapping("/my")
    @Operation(
            summary = "내 일기 목록 조회",
            description = "로그인한 사용자 본인의 일기 리스트를 조회합니다.",
            security = @SecurityRequirement(name = "JWT")
    )
    public ResponseEntity<ApiResponse<List<MyDiaryResponse>>> getMyDiaries(
            @Parameter(hidden = true) HttpServletRequest httpRequest
    ) {
        String token = resolveToken(httpRequest);
        List<MyDiaryResponse> responses = perfumeDiaryService.getAllMy(token, 0);
        return ResponseEntity.ok(ApiResponse.onSuccess(responses));
    }

    @GetMapping("/public")
    @Operation(summary = "공개 일기 목록 조회", description = "공개로 설정된 향수 일기만 조회됩니다.")
    public ResponseEntity<ApiResponse<List<MyDiaryResponse>>> getPublicDiaries() {
        List<MyDiaryResponse> responses = perfumeDiaryService.getAllPublic(0);
        return ResponseEntity.ok(ApiResponse.onSuccess(responses));
    }

    @GetMapping("/{id}")
    @Operation(summary = "일기 상세 조회", description = "공개 일기면 누구나, 비공개면 작성자만 볼 수 있도록 service 레이어에서 검사됩니다.")
    public ResponseEntity<ApiResponse<PerfumeDiaryDetailResponse>> getDiaryDetail(
            @Parameter(description = "일기 ID", required = true) @PathVariable Long id
    ) {
        PerfumeDiaryDetailResponse response = perfumeDiaryService.getDiaryDetail(id);
        return ResponseEntity.ok(ApiResponse.onSuccess(response));
    }

    @GetMapping("/{id}/comments")
    @Operation(
            summary = "일기 댓글/대댓글 조회",
            description = "최상위 댓글 목록을 내려주며, 각 항목의 replies 배열에 대댓글이 중첩되어 포함됩니다."
    )
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getComments(
            @Parameter(description = "일기 ID", required = true) @PathVariable Long id
    ) {
        List<CommentResponse> responses = perfumeDiaryService.getComments(id);
        return ResponseEntity.ok(ApiResponse.onSuccess(responses));
    }

    @PostMapping("/{id}/comments")
    @Operation(
            summary = "댓글/대댓글 작성",
            description = "최상위 댓글이면 content만 보냅니다. 대댓글일 때만 parentCommentId를 포함합니다.",
            security = @SecurityRequirement(name = "JWT")
    )
    public ResponseEntity<ApiResponse<CommentResponse>> addComment(
            @Parameter(description = "일기 ID", required = true) @PathVariable Long id,
            @Parameter(description = "댓글 내용", required = true) @RequestBody CommentRequest request,
            @Parameter(hidden = true) HttpServletRequest httpRequest
    ) {
        String token = resolveToken(httpRequest);
        CommentResponse response = perfumeDiaryService.addComment(token, id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.onSuccess(response));
    }

    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer == null || !bearer.startsWith("Bearer ")) {
            throw new GeneralException(ErrorStatus.INVALID_TOKEN);
        }
        return bearer.substring(7).trim();
    }
}