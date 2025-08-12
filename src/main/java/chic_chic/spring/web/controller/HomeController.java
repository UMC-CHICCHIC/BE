package chic_chic.spring.web.controller;

import chic_chic.spring.apiPayload.ApiResponse;
import chic_chic.spring.domain.Product;
import chic_chic.spring.service.product.HomeProductService;
import chic_chic.spring.web.dto.ProductResponse;
import chic_chic.spring.web.dto.RecommendProductResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Home", description = "메인페이지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {

    private final HomeProductService homeProductService;

    @GetMapping("/popular-products")
    public ApiResponse<List<ProductResponse>> getPopularProducts() {
        List<Product> products = homeProductService.getPopularProducts();
        return ApiResponse.onSuccess(ProductResponse.from(products));
    }

    @GetMapping("/recommend-products")
    public ApiResponse<List<RecommendProductResponseDto>> getRecommendProducts(
            @AuthenticationPrincipal UserDetails user) {

        // 인증이 안 된 경우 디버그 계정 사용
        String email = (user != null) ? user.getUsername() : "debug@example.com";

        List<Product> products = homeProductService.getRecommendProducts(email);

        if (products.isEmpty()) {
            return ApiResponse.onFailure("RECOMMEND404", "추천된 제품이 없습니다.", null);
        }

        List<RecommendProductResponseDto> result = products.stream()
                .map(RecommendProductResponseDto::from)
                .toList();

        return ApiResponse.onSuccess(result);
    }
}
