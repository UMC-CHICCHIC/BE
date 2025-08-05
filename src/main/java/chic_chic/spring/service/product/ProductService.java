package chic_chic.spring.service.product;

import chic_chic.spring.domain.Category;
import chic_chic.spring.domain.Product;
import chic_chic.spring.domain.repository.CategoryRepository;
import chic_chic.spring.domain.repository.ProductRepository;
import chic_chic.spring.web.dto.product.ProductListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    // 전체 상품 조회 + 정렬 + 필터링
    public Page<ProductListResponse> getProducts(Long catId, Pageable pageable) {

        if (catId == null) {
            return productRepository.findAll(pageable)
                    .map(ProductListResponse::from);
        }

        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리"));

        List<Product> filtered = productRepository.findAll().stream()
                .filter(product -> filterByCategory(product, category))
                .sorted((p1, p2) -> {
                    for (Sort.Order order : pageable.getSort()) {
                        int result = compareByField(order.getProperty(), p1, p2);
                        if (result != 0) return order.isAscending() ? result : -result;
                    }
                    return 0;
                })
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filtered.size());

        List<ProductListResponse> content = filtered.subList(start, end).stream()
                .map(ProductListResponse::from)
                .toList();

        return new PageImpl<>(content, pageable, filtered.size());
    }

    private boolean filterByCategory(Product product, Category category) {
        return switch (category.getType()) {
            // 가격 필터
            case PRICE -> switch (category.getName()) {
                case "5만 원 이하" -> product.getPrice() <= 50000;
                case "5~10만 원대" -> product.getPrice() > 50000 && product.getPrice() <= 100000;
                case "10만 원 이상" -> product.getPrice() > 100000;
                default -> false;
            };

            // 농도 필터
            case CONCENTRATION -> product.getConcentration().equalsIgnoreCase(category.getName());

            // NOTE → product.notes 안에 category.name과 동일한 note.name이 있는지 확인
            case NOTE -> product.getNotes().stream()
                    .anyMatch(note -> note.getNote().equalsIgnoreCase(category.getName()));
        };
    }

    private int compareByField(String field, Product p1, Product p2) {
        return switch (field) {
            case "price" -> Integer.compare(p1.getPrice(), p2.getPrice());
            case "itemRating" -> Double.compare(p1.getItemRating(), p2.getItemRating());
            case "numSeller" -> Integer.compare(p1.getNumSeller(), p2.getNumSeller());
            default -> 0;
        };
    }
}