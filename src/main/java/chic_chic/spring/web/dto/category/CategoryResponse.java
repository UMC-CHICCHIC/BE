
package chic_chic.spring.web.dto.category;

import chic_chic.spring.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryResponse {

    private Long categoryId;
    private String name;
    private String type;
    private int order;

    public static CategoryResponse from(Category category) {
        return new CategoryResponse(
                category.getCategoryId(),
                category.getName(),
                category.getType().name(),
                category.getOrder()
        );
    }
}
