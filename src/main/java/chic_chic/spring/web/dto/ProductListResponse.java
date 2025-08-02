package chic_chic.spring.web.dto;

import chic_chic.spring.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

//제품 페이지용
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ProductListResponse {
    private Long id;
    private String name;
    private String brand;
    private int ml;
    private String baseNote;
    private String middleNote;
    private String concentration;
    private int price;
    private double itemRating;

    public static ProductListResponse from(Product product) {
        return ProductListResponse.builder()
                .id(product.getProduct_id())
                .name(product.getName())
                .brand(product.getBrand())
                .ml(product.getMl())
                .baseNote(product.getBaseNote())
                .middleNote(product.getMiddleNote())
                .concentration(product.getConcentration())
                .price(product.getPrice())
                .itemRating(product.getItemRating())
                .build();
    }

    public static List<ProductListResponse> from(List<Product> products) {
        return products.stream()
                .map(ProductListResponse::from)
                .toList();
    }
}
