package chic_chic.spring.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class S3ResponseDto {
    private String url;
    private String key;
}
