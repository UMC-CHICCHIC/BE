package chic_chic.spring.service.product;

import chic_chic.spring.domain.Product;
import chic_chic.spring.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getPopularProducts() {
        return productRepository.findTop4ByOrderByItemRatingDesc();
    }
}