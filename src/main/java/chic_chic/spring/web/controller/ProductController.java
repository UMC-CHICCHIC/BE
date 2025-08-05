package chic_chic.spring.web.controller;

import chic_chic.spring.apiPayload.ApiResponse;
import chic_chic.spring.web.dto.ProductResponse;
import chic_chic.spring.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Product", description = "상품 목록 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    /**
     * 상품 목록 조회 API
     * - 필터: 카테고리(cat)
     * - 정렬: 가격, 평점, 판매량 등
     * - 페이징: Pageable 사용 (size=16 고정)
     */

    @Operation(summary = "상품 목록 조회", description = "카테고리 ID, 정렬 조건, 페이지 정보를 이용하여 상품 목록을 조회합니다.")
    @GetMapping
    public ApiResponse<Page<ProductResponse>> getProducts(
            @RequestParam(required = false) Long cat,
            @RequestParam(required = false) String sort,
            @PageableDefault(size = 16) Pageable pageable
    ) {
        return ApiResponse.onSuccess(productService.getProducts(cat, sort, pageable));
    }
}


