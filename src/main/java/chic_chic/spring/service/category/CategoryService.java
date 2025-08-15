package chic_chic.spring.service.category;


import chic_chic.spring.domain.entity.Category;
import chic_chic.spring.domain.enums.CategoryType;
import chic_chic.spring.domain.repository.CategoryRepository;
import chic_chic.spring.web.dto.category.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> getCategories(CategoryType type) {
        System.out.println("[Service] type 파라미터: " + type); // null or NOTE
        List<Category> categories = (type == null)
                ? categoryRepository.findAll(Sort.by(Sort.Order.asc("order")))
                : categoryRepository.findByType(type, Sort.by(Sort.Order.asc("order")));

        System.out.println("[Service] 조회된 카테고리 수: " + categories.size());
        return categories.stream().map(CategoryResponse::from).toList();
    }
}