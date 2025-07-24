package chic_chic.spring.web.controller;

import chic_chic.spring.domain.enums.CategoryType;
import chic_chic.spring.service.category.CategoryService;
import chic_chic.spring.web.dto.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponse> getCategories(@RequestParam(required = false) CategoryType type) {
        return categoryService.getCategories(type);
    }
}