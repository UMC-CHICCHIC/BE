package chic_chic.spring.service.category;


import chic_chic.spring.domain.Category;
import chic_chic.spring.domain.enums.CategoryType;
import chic_chic.spring.domain.repository.CategoryRepository;
import chic_chic.spring.web.dto.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> getCategories(CategoryType type) {
        List<Category> categories = (type == null)
                ? categoryRepository.findAll(Sort.by(Sort.Order.asc("order")))
                : categoryRepository.findByType(type, Sort.by(Sort.Order.asc("order")));
        return categories.stream()
                .map(CategoryResponse::from)
                .toList();
    }

}