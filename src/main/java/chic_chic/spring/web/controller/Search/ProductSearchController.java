package chic_chic.spring.web.controller.Search;

import chic_chic.spring.apiPayload.ApiResponse;
import chic_chic.spring.service.product.ProductSearchService;
import chic_chic.spring.web.dto.product.ProductListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Product Search", description = "상품 이름 단일 검색 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductSearchController {

    private final ProductSearchService productSearchService;

    // GET /api/v1/products/search?q=olympea
    @Operation(summary = "상품 단일 검색", description = "이름(부분일치/대소문자 무시)로 단일 상품을 반환합니다.")
    @GetMapping("/search")
    public ApiResponse<ProductListResponse> searchByName(@RequestParam String q) {
        return ApiResponse.onSuccess(productSearchService.findOneByName(q));
    }
}