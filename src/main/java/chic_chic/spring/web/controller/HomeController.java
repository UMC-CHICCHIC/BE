package chic_chic.spring.web.controller.home;

import chic_chic.spring.apiPayload.ApiResponse;
import chic_chic.spring.domain.Product;
import chic_chic.spring.service.product.HomeProductService;
import chic_chic.spring.web.dto.ProductResponse;
import chic_chic.spring.web.dto.RecommendProductResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            @AuthenticationPrincipal User user) {

        //String email = user.getUsername();
        String email = "debug@example.com"; //디버그를위한 강제예시
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
