package chic_chic.spring.web.controller;

import chic_chic.spring.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/actuator/health")
public class HealthCheck {
    @GetMapping
    public ApiResponse<String> healthCheck(){
        return ApiResponse.onSuccess("Health OK");
    }
}
