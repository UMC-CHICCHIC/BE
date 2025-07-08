package chic_chic.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/brands")
public class BrandController {

    // private final BrandService brandService;

    @GetMapping
    public ResponseEntity<?> getBrands() {
        return ResponseEntity.ok("브랜드 목록 조회");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBrandDetail(@PathVariable Long id) {
        return ResponseEntity.ok("브랜드 상세 조회: ID = " + id);
    }
}
