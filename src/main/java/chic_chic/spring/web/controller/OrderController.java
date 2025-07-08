package chic_chic.spring.web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

    // private final OrderService orderService;


    @PostMapping("/cart")
    public ResponseEntity<?> addToCart() {
        return ResponseEntity.ok("장바구니 담기 성공");
    }

    @PostMapping("/orders")
    public ResponseEntity<?> createOrder() {
        return ResponseEntity.ok("주문 생성 성공");
    }
}
