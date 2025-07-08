package chic_chic.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    // private final ReviewTestService reviewService;


    @PostMapping("/products/{id}/reviews")
    public ResponseEntity<?> writeReview(@PathVariable Long id) {
        return ResponseEntity.ok("리뷰 작성 성공, 상품 ID = " + id);
    }
}
