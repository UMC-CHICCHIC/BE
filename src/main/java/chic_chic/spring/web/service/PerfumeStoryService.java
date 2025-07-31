package chic_chic.spring.web.service;

import chic_chic.spring.apiPayload.exception.GeneralException;
import chic_chic.spring.domain.entity.PerfumeStory;
import chic_chic.spring.domain.repository.PerfumeStoryRepository;
import chic_chic.spring.web.dto.PerfumeStoryDetailResponse;
import chic_chic.spring.web.dto.PerfumeStoryResponse;
import chic_chic.spring.apiPayload.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PerfumeStoryService {

    private final PerfumeStoryRepository perfumeStoryRepository;

    // 전체 조회
    public List<PerfumeStoryResponse> getAllStories() {
        return perfumeStoryRepository.findAll().stream()
                .map(PerfumeStoryResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // 상세 조회
    public PerfumeStoryDetailResponse getStoryDetail(Long id) {
        PerfumeStory story = perfumeStoryRepository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.PERFUME_STORY_NOT_FOUND));
        return PerfumeStoryDetailResponse.fromEntity(story);
    }

    // 갯수 설정하여 미리보기 가져오기
    public List<PerfumeStoryResponse> getPreviewStories(int size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by("createdAt").descending());
        return perfumeStoryRepository.findAll(pageable).stream()
                .map(PerfumeStoryResponse::fromEntity)
                .collect(Collectors.toList());
    }
}