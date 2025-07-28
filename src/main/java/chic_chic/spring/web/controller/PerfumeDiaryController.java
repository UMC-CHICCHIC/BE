package chic_chic.spring.web.controller;

import chic_chic.spring.Service.PerfumeDiaryService;
import chic_chic.spring.apiPayload.ApiResponse;
import chic_chic.spring.web.dto.MyDiaryResponse;
import chic_chic.spring.web.dto.PerfumeDiaryRequest;
import chic_chic.spring.web.dto.PerfumeDiaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/diary")
public class PerfumeDiaryController {

    private final PerfumeDiaryService diaryService;

    @PostMapping
    public ResponseEntity<ApiResponse<PerfumeDiaryResponse>> createDiary(
            @RequestPart("request") PerfumeDiaryRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestHeader("Authorization") String bearerToken
    ) {
        String token = bearerToken.substring(7); // "Bearer " 제거
        PerfumeDiaryResponse response = diaryService.createDiary(token, request, image);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess(response));
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<MyDiaryResponse>>> getMyDiaries(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam(defaultValue = "0") int page
    ) {
        String token = bearerToken.substring(7);
        List<MyDiaryResponse> response = diaryService.getMyDiaries(token, page);
        return ResponseEntity.ok(ApiResponse.onSuccess(response));
    }
}