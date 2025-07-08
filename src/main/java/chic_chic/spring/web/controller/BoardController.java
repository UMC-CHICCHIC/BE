package chic_chic.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    // private final BrandService brandService;

    @GetMapping("/{boardType}/posts")
    public ResponseEntity<?> getPosts(@PathVariable String boardType) {
        return ResponseEntity.ok("게시글 목록 조회: " + boardType);
    }

    @PostMapping("/{boardType}/posts")
    public ResponseEntity<?> createPost(@PathVariable String boardType) {
        return ResponseEntity.ok("게시글 작성 성공: " + boardType);
    }

    @PostMapping("/{boardType}/posts/{postId}/comments")
    public ResponseEntity<?> createComment(@PathVariable String boardType, @PathVariable Long postId) {
        return ResponseEntity.ok("댓글 작성 성공: 게시글 ID = " + postId);
    }
}
