
package chic_chic.spring.web.controller.category;

import chic_chic.spring.domain.enums.CategoryType;

import chic_chic.spring.service.category.CategoryService;
import chic_chic.spring.web.dto.category.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category", description = "카테고리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 카테고리 목록 조회 API
     * - 카테고리 타입별 필터링 가능 (NOTE, PRICE, CONCENTRATION 등)
     */
    @Operation(summary = "카테고리 목록 조회", description = "카테고리 타입(NOTE, PRICE, CONCENTRATION 등)을 기준으로 목록을 조회합니다.")
    @GetMapping
    public List<CategoryResponse> getCategories(@RequestParam(required = false) CategoryType type) {
        return categoryService.getCategories(type);
    }
}
