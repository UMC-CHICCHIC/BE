package chic_chic.spring.web.controller;

import chic_chic.spring.apiPayload.ApiResponse;
import chic_chic.spring.service.imageservice.S3UploaderService;
import chic_chic.spring.web.dto.S3ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class S3ImageController {

    private final S3UploaderService s3UploaderService;

    @Operation(summary = "이미지 업로드")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<S3ResponseDto>> uploadImage(@Parameter(
            description = "업로드할 이미지 파일", required = true, content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
            schema = @Schema(type = "string", format = "binary")))
                                                                  @RequestPart("file") MultipartFile file){ //S3 에 업로드 -> URL + key 반환
        S3ResponseDto result = s3UploaderService.upload(file);
        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<Void> deleteImage(@PathVariable String key){
        s3UploaderService.delete(key);
        return ResponseEntity.noContent().build();
    }



}
