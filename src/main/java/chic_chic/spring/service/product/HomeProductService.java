package chic_chic.spring.service.product;

import chic_chic.spring.domain.entity.Product;
import chic_chic.spring.domain.repository.ProductRepository;
import chic_chic.spring.domain.repository.RecommendProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeProductService {

    private final ProductRepository productRepository;
    private final RecommendProductRepository recommendProductRepository;

    // 인기 상품 4개 조회 (메인 페이지용)
    public List<Product> getPopularProducts() {
        return productRepository.findTop4ByOrderByItemRatingDesc();
    }

    // 개인 맞춤 추천 향수 4개 조회 (메인 페이지용)
    public List<Product> getRecommendProducts(String memberEmail) {

        var results = recommendProductRepository
                .findTop4ByMemberEmailOrderByCreatedAtDesc(memberEmail);

        List<Product> recommendProducts = new ArrayList<>();


        for (var r : results) {
            productRepository.findById(r.getProductId()).ifPresent(recommendProducts::add);
        }



        return recommendProducts;
    }
}
