package chic_chic.spring.web.controller;


import chic_chic.spring.apiPayload.ApiResponse;
import chic_chic.spring.domain.Product;
import chic_chic.spring.service.product.ProductService;
import chic_chic.spring.web.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import chic_chic.spring.web.dto.ProductResponse;
import chic_chic.spring.web.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/popular")
    public ApiResponse<List<ProductResponseDto>> getPopularProducts() {
        List<Product> products = productService.getPopularProducts();
        System.out.println("DB 조회된 상품 = " + products);  // 엔티티 확인
        List<ProductResponseDto> dtoList = ProductResponseDto.from(products);
        System.out.println("변환된 DTO = " + dtoList);     // DTO 확인
        return ApiResponse.onSuccess(dtoList);

    @GetMapping
    public Page<ProductResponse> getProducts(
            @RequestParam(required = false) String sort,
            @PageableDefault(size = 16) Pageable pageable
    ) {
        return productService.getProducts(sort, pageable);

    }
}