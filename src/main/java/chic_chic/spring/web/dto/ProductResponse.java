package chic_chic.spring.web.dto;

import chic_chic.spring.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductResponse {

    private Long productId;
    private String name;
    private int price;
    private double itemRating;

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getProduct_id(),
                product.getName(),
                product.getPrice(),
                product.getItemRating()
        );
    }
}