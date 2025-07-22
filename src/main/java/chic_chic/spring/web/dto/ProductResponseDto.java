package chic_chic.spring.web.dto;

import chic_chic.spring.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String name;
    private String baseNote;
    private String middleNote;
    private int price;
    private double itemRating;

    public static List<ProductResponseDto> from(List<Product> products) {
        return products.stream()
                .map(p -> ProductResponseDto.builder()
                        .id(p.getProduct_id())
                        .name(p.getName())
                        .baseNote(p.getBaseNote())
                        .middleNote(p.getMiddleNote())
                        .price(p.getPrice())
                        .itemRating(p.getItemRating())
                        .build())
                .toList();
    }
}