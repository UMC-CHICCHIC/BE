package chic_chic.spring.service.scrapservice;

import chic_chic.spring.web.dto.product.ProductListResponse;

import java.util.List;

public interface ScrapService {

    void addScrap(Long memberId, Long productId);

    void removeScrap(Long memberId, Long productId);

    List<ProductListResponse> getScraps(Long memberId);

}
