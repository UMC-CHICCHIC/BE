package chic_chic.spring.service.ScrapService;


import chic_chic.spring.web.dto.ProductListResponse;

import java.util.List;

public interface ScrapService {

    void addScrap(Long memberId, Long productId);

    void removeScrap(Long memberId, Long productId);

    List<ProductListResponse> getScraps(Long memberId);

}
