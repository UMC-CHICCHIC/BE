package chic_chic.spring.web.controller;
import chic_chic.spring.apiPayload.ApiResponse;
import chic_chic.spring.config.jwt.JwtTokenProvider;
import chic_chic.spring.service.ScrapService.ScrapService;
import chic_chic.spring.web.dto.product.ProductListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/scrap")
public class ScrapController {

    private final ScrapService scrapService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/{productId}")
    @Operation(summary = "향수 스크랩", security = {@SecurityRequirement(name = "JWT")})
    public ApiResponse<Void> addScrap(@PathVariable Long productId, HttpServletRequest request) {
        Authentication auth = jwtTokenProvider.extractAuthentication(request);
        Long memberId = Long.valueOf(auth.getName());

        scrapService.addScrap(memberId, productId);
        return ApiResponse.onSuccess(null);
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "향수 스크랩 해제", security = {@SecurityRequirement(name = "JWT")})
    public ApiResponse<Void> removeScrap(@PathVariable Long productId, HttpServletRequest request) {
        Authentication auth = jwtTokenProvider.extractAuthentication(request);
        Long memberId = Long.valueOf(auth.getName());

        scrapService.removeScrap(memberId, productId);
        return ApiResponse.onSuccess(null);
    }

    @GetMapping
    @Operation(summary = "내 스크랩 목록 조회", security = {@SecurityRequirement(name = "JWT")})
    public ApiResponse<List<ProductListResponse>> getScraps(HttpServletRequest request) {
        Authentication auth = jwtTokenProvider.extractAuthentication(request);
        Long memberId = Long.valueOf(auth.getName());

        return ApiResponse.onSuccess(scrapService.getScraps(memberId));
    }
}

