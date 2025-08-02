package chic_chic.spring.web.service;

import chic_chic.spring.domain.Product;
import chic_chic.spring.domain.repository.ProductRepository;
import chic_chic.spring.web.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Page<ProductResponse> getProducts(String sort, Pageable pageable) {
        Sort sortSpec = getSortSpec(sort);

        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                sortSpec
        );

        return productRepository.findAll(sortedPageable)
                .map(ProductResponse::from);
    }

    private Sort getSortSpec(String sort) {
        if (sort == null) return Sort.by(Sort.Direction.DESC, "numSeller");

        String[] parts = sort.split(",");
        String field = parts[0];
        Sort.Direction direction = parts.length > 1 && parts[1].equalsIgnoreCase("asc")
                ? Sort.Direction.ASC : Sort.Direction.DESC;

        return switch (field) {
            case "price" -> Sort.by(direction, "price");
            case "itemRating" -> Sort.by(direction, "itemRating");
            case "popularity", "sales" -> Sort.by(direction, "numSeller");
            default -> Sort.by(Sort.Direction.DESC, "numSeller");
        };
    }
}