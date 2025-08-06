package chic_chic.spring.service.product;

import chic_chic.spring.domain.Product;
import chic_chic.spring.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeProductService {

    private final ProductRepository productRepository;

    // 인기 상품 4개 조회 (메인 페이지용)
    public List<Product> getPopularProducts() {
        return productRepository.findTop4ByOrderByItemRatingDesc();
    }
}