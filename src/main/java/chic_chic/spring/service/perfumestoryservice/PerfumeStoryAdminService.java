package chic_chic.spring.service.perfumestoryservice;

import chic_chic.spring.domain.entity.PerfumeStory;
import chic_chic.spring.domain.repository.PerfumeStoryRepository;
import chic_chic.spring.web.dto.product.PerfumeStoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PerfumeStoryAdminService {
    private final PerfumeStoryRepository repo;

    @Transactional
    public Long create(String adminId, PerfumeStoryRequest req) {
        PerfumeStory story = PerfumeStory.builder()
                .title(req.title())
                .summary(req.summary())
                .content(req.content())
                .thumbnailUrl(req.thumbnailUrl())
                .userId(adminId)
                .build();
        return repo.save(story).getStoryId();
    }
}