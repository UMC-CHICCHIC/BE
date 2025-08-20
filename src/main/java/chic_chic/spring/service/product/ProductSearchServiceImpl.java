package chic_chic.spring.service.product;

import chic_chic.spring.domain.entity.Product;
import chic_chic.spring.domain.repository.ProductRepository;
import chic_chic.spring.web.dto.product.ProductListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductSearchServiceImpl implements ProductSearchService {

    private final ProductRepository productRepository;

    @Override
    public ProductListResponse findOneByName(String q) {
        if (q == null || q.isBlank()) {
            throw new IllegalArgumentException("검색어를 입력하세요.");
        }

        // 1) 정확히 같은 이름이 있으면 우선 반환
        Product product = productRepository.findFirstByNameIgnoreCase(q.trim())
                // 2) 없으면 부분일치 중 첫 번째 반환
                .or(() -> productRepository.findFirstByNameContainingIgnoreCase(q.trim()))
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        return ProductListResponse.from(product);
    }
}