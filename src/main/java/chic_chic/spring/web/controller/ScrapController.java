package chic_chic.spring.web.controller;

import chic_chic.spring.apiPayload.ApiResponse;
import chic_chic.spring.config.jwt.JwtTokenProvider;
import chic_chic.spring.domain.entity.Member;
import chic_chic.spring.domain.repository.MemberRepository;
import chic_chic.spring.service.scrapservice.ScrapService;
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
    private final MemberRepository memberRepository;

    @PostMapping("/{productId}")
    @Operation(summary = "향수 스크랩 API", description = "향수 스크랩 API입니다.",
            security = {@SecurityRequirement(name = "JWT")})
    public ApiResponse<Void> addScrap(@PathVariable Long productId, HttpServletRequest request) {
        Authentication auth = jwtTokenProvider.extractAuthentication(request);
        String email = auth.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("회원 정보가 없습니다."));
        Long memberId = member.getId();

        scrapService.addScrap(memberId, productId);
        return ApiResponse.onSuccess(null);
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "향수 스크랩 해제 API", description = "향수 스크랩 해제 API입니다.",
            security = {@SecurityRequirement(name = "JWT")})
    public ApiResponse<Void> removeScrap(@PathVariable Long productId, HttpServletRequest request) {
        Authentication auth = jwtTokenProvider.extractAuthentication(request);
        String email = auth.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("회원 정보가 없습니다."));
        Long memberId = member.getId();

        scrapService.removeScrap(memberId, productId);
        return ApiResponse.onSuccess(null);
    }

    @GetMapping
    @Operation(summary = "내 스크랩 목록 조회 API", description = "내 스크랩 목록 조회 API입니다.",
            security = {@SecurityRequirement(name = "JWT")})
    public ApiResponse<List<ProductListResponse>> getScraps(HttpServletRequest request) {
        Authentication auth = jwtTokenProvider.extractAuthentication(request);
        String email = auth.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("회원 정보가 없습니다."));
        Long memberId = member.getId();

        return ApiResponse.onSuccess(scrapService.getScraps(memberId));
    }
}
