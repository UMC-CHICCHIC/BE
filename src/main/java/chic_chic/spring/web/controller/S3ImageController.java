package chic_chic.spring.web.controller;

import chic_chic.spring.apiPayload.ApiResponse;
import chic_chic.spring.service.ImageService.S3UploaderService;
import chic_chic.spring.web.dto.S3ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class S3ImageController {

    private final S3UploaderService s3UploaderService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<S3ResponseDto>> uploadImage(@RequestParam("file") MultipartFile file){
        //S3 에 업로드 -> URL + key 반환
        S3ResponseDto result = s3UploaderService.upload(file);
        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<Void> deleteImage(@PathVariable String key){
        s3UploaderService.delete(key);
        return ResponseEntity.noContent().build();
    }



}
