package chic_chic.spring.web.controller.product;

import chic_chic.spring.apiPayload.ApiResponse;
import chic_chic.spring.service.product.ProductDetailService;
import chic_chic.spring.web.dto.ProductDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ProductDetail", description = "상품 상세 정보 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductDetailController {

    private final ProductDetailService productDetailService;

    @Operation(summary = "상품 상세 조회", description = "상품 ID를 기반으로 상세 정보를 조회합니다.")
    @GetMapping("/detail/{id}")
    public ApiResponse<ProductDetailResponse> getProduct(@PathVariable Long id) {
        return ApiResponse.onSuccess(productDetailService.getProductDetail(id));
    }
}