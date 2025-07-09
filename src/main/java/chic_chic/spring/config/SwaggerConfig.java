package chic_chic.spring.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ChicChic API 명세서")
                        .description("ChicChic 프로젝트의 Swagger 문서입니다.")
                        .version("1.0.0"));
    }
}