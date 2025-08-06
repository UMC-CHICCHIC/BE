package chic_chic.spring.web.controller;

import chic_chic.spring.apiPayload.ApiResponse;
import chic_chic.spring.domain.Product;
import chic_chic.spring.service.product.HomeProductService;
import chic_chic.spring.web.dto.ProductResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
}
