package chic_chic.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CategoryProductController {

    // private final CategoryProductService categoryProductService;

    @GetMapping("/categories")
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.ok("카테고리 목록 조회");
    }

    @GetMapping("/products")
    public ResponseEntity<?> getProducts() {
        return ResponseEntity.ok("상품 목록 조회");
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductDetail(@PathVariable Long id) {
        return ResponseEntity.ok("상품 상세 조회, id = " + id);
    }
}
