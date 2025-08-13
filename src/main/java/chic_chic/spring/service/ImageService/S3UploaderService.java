package chic_chic.spring.service.ImageService;

import chic_chic.spring.web.dto.S3ResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface S3UploaderService {

    S3ResponseDto upload(MultipartFile file);

    void delete(String key);

    String extractFileNameFromUrl(String url);

}
