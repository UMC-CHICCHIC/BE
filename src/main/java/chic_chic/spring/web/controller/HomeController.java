package chic_chic.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HomeController {

    // private final HomeService homeService;

    @GetMapping("/home")
    public ResponseEntity<?> getHomeFeed() {
        return ResponseEntity.ok("메인 홈 피드 조회");
    }

    @GetMapping("/boards/story/posts")
    public ResponseEntity<?> getNewsPreview(@RequestParam(defaultValue = "3") int size) {
        return ResponseEntity.ok("뉴스 미리보기 조회");
    }

    @GetMapping("/users/me/orders")
    public ResponseEntity<?> getRecentOrders() {
        return ResponseEntity.ok("최근 주문 내역 조회");
    }
    @GetMapping("/likes")
    public ResponseEntity<?> getLikes() {
        return ResponseEntity.ok("좋아요 목록/개수 조회");
    }

    @GetMapping("/scraps")
    public ResponseEntity<?> getScraps() {
        return ResponseEntity.ok("스크랩 목록/개수 조회");
    }
}