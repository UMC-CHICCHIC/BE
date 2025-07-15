package chic_chic.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tests")
@RequiredArgsConstructor
public class PerfumeTestController {

    // private final PerfumeTestService perfumeTestService;

    @GetMapping("/questions")
    public ResponseEntity<?> getQuestions() {
        return ResponseEntity.ok("질문 목록 조회");
    }

    @PostMapping
    public ResponseEntity<?> submitAnswers() {
        return ResponseEntity.ok("답변 제출 완료");
    }
}

