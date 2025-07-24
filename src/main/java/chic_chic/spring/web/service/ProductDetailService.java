package chic_chic.spring.web.service;

import chic_chic.spring.domain.Product;
import chic_chic.spring.domain.repository.ProductRepository;
import chic_chic.spring.web.dto.ProductDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductDetailService {

    private final ProductRepository productRepository;

    public ProductDetailResponse getProductDetail(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 제품입니다."));
        return ProductDetailResponse.from(product);
    }
}
