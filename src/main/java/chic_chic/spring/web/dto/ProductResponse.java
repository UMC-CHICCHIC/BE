package chic_chic.spring.web.dto;

import chic_chic.spring.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

//메인페이지용

@Getter
@Builder
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String baseNote;
    private String middleNote;
    private int price;
    private double itemRating;

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .id(product.getProduct_id())
                .name(product.getName())
                .baseNote(product.getBaseNote())
                .middleNote(product.getMiddleNote())
                .price(product.getPrice())
                .itemRating(product.getItemRating())
                .build();
    }

    public static List<ProductResponse> from(List<Product> products) {
        return products.stream()
                .map(ProductResponse::from)
                .toList();
    }
}