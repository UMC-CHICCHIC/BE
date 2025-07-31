package chic_chic.spring.service.perfumediaryservice;

import chic_chic.spring.web.dto.*;
import chic_chic.spring.web.dto.PerfumeDiary.PerfumeDiaryDetailResponse;
import chic_chic.spring.web.dto.PerfumeDiary.PerfumeDiaryRequest;
import chic_chic.spring.web.dto.PerfumeDiary.PerfumeDiaryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PerfumeDiaryService {

    PerfumeDiaryResponse createDiary(String token, PerfumeDiaryRequest request, MultipartFile image);

    List<MyDiaryResponse> getMyPreview(String token);

    List<MyDiaryResponse> getPublicPreview();

    List<MyDiaryResponse> getAllPublic(int page);

    List<MyDiaryResponse> getAllMy(String token, int page);

    PerfumeDiaryDetailResponse getDiaryDetail(Long diaryId);

    CommentResponse addComment(String token, Long diaryId, CommentRequest request);

    // 향수 일기장 댓글 반환
    List<CommentResponse> getComments(Long diaryId);

}