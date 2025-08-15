package chic_chic.spring.domain.repository;

import chic_chic.spring.domain.entity.Category;
import chic_chic.spring.domain.enums.CategoryType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByType(CategoryType type, Sort sort);
}

