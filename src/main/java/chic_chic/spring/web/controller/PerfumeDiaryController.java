package chic_chic.spring.web.controller;

import chic_chic.spring.service.perfumediaryservice.PerfumeDiaryService;
import chic_chic.spring.apiPayload.ApiResponse;
import chic_chic.spring.web.dto.CommentRequest;
import chic_chic.spring.web.dto.CommentResponse;
import chic_chic.spring.web.dto.MyDiaryResponse;
import chic_chic.spring.web.dto.PerfumeDiary.PerfumeDiaryDetailResponse;
import chic_chic.spring.web.dto.PerfumeDiary.PerfumeDiaryRequest;
import chic_chic.spring.web.dto.PerfumeDiary.PerfumeDiaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class PerfumeDiaryController {

    private final PerfumeDiaryService perfumeDiaryService;

    @PostMapping    // 향수 일기 작성
    public ResponseEntity<ApiResponse<PerfumeDiaryResponse>> createDiary(
            @RequestPart("request") PerfumeDiaryRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestHeader("Authorization") String bearerToken
    ) {
        String token = bearerToken.substring(7);
        PerfumeDiaryResponse response = perfumeDiaryService.createDiary(token, request, image);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess(response));
    }

    @GetMapping("/my")  // 내 일기 조회
    public ResponseEntity<ApiResponse<List<MyDiaryResponse>>> getMyDiaries(
            @RequestHeader("Authorization") String bearerToken
    ) {
        String token = bearerToken.substring(7);
        List<MyDiaryResponse> responses = perfumeDiaryService.getAllMy(token, 0);
        return ResponseEntity.ok(ApiResponse.onSuccess(responses));
    }

    @GetMapping("/public")  // 공개 일기 조회
    public ResponseEntity<ApiResponse<List<MyDiaryResponse>>> getPublicDiaries() {
        List<MyDiaryResponse> responses = perfumeDiaryService.getAllPublic(0);
        return ResponseEntity.ok(ApiResponse.onSuccess(responses));
    }

    @GetMapping("/{id}")    // 일기 상세 조회
    public ResponseEntity<ApiResponse<PerfumeDiaryDetailResponse>> getDiaryDetail(
            @PathVariable Long id
    ) {
        PerfumeDiaryDetailResponse response = perfumeDiaryService.getDiaryDetail(id);
        return ResponseEntity.ok(ApiResponse.onSuccess(response));
    }

    // 댓글 조회
    @GetMapping("/{id}/comments")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getComments(
            @PathVariable Long id
    ) {
        List<CommentResponse> responses = perfumeDiaryService.getComments(id);
        return ResponseEntity.ok(ApiResponse.onSuccess(responses));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<ApiResponse<CommentResponse>> addComment(
            @PathVariable Long id,
            @RequestBody CommentRequest request,
            @RequestHeader("Authorization") String bearerToken
    ) {
        String token = bearerToken.substring(7);
        CommentResponse response = perfumeDiaryService.addComment(token, id, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess(response));
    }
}
