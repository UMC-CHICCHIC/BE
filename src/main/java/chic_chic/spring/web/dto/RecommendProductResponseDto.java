
package chic_chic.spring.web.dto;

import chic_chic.spring.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RecommendProductResponseDto {

    private Long productId;
    private String name;
    private String baseNote;
    private String middleNote;
    private int price;
    private double itemRating;

    public static RecommendProductResponseDto from(Product p) {
        return RecommendProductResponseDto.builder()
                .productId(p.getProductId())
                .name(p.getName())
                .baseNote(p.getBaseNote())
                .middleNote(p.getMiddleNote())
                .price(p.getPrice())
                .itemRating(p.getItemRating())
                .build();
    }
}