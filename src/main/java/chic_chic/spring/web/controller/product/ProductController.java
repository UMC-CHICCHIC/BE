
package chic_chic.spring.web.controller.product;

import chic_chic.spring.apiPayload.ApiResponse;
import chic_chic.spring.service.product.ProductService;
import chic_chic.spring.web.dto.product.ProductListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
     * - 정렬: sort=price,asc 또는 itemRating,desc 등의 방식
     * - 페이징: Pageable 사용 (size=16 고정)
     */

    @Operation(
            summary = "상품 목록 조회",
            description = """
    카테고리 ID,  페이지 정보를 이용하여 상품 목록을 조회합니다.
    
    size는 개수

    정렬 조건 예시 (sort 파라미터):
    - price,asc : 가격 낮은순
    - price,desc : 가격 높은순
    - itemRating,desc : 평점 높은순
    - numSeller,desc : 판매량 많은순
    """
    )
    @GetMapping
    public ApiResponse<Page<ProductListResponse>> getProducts(
            @RequestParam(required = false) Long cat,
            @Parameter(hidden = false) @PageableDefault(size = 16) Pageable pageable
    ) {
        return ApiResponse.onSuccess(productService.getProducts(cat, pageable));
    }
}


