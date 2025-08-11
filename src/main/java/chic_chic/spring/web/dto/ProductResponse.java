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
    List<ProductResponse.NoteDto> topNote;
    private String baseNote;
    private String middleNote;
    private int price;
    private double itemRating;
    private String imageUrl;

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .id(product.getProductId())
                .name(product.getName())
                .topNote(product.getProductNotes().stream()
                        .map(pn -> new ProductResponse.NoteDto(
                                pn.getNote().getNote_id(),
                                pn.getNote().getNote()
                        ))
                        .toList())
                .baseNote(product.getBaseNote())
                .middleNote(product.getMiddleNote())
                .price(product.getPrice())
                .itemRating(product.getItemRating())
                .imageUrl(product.getImageUrl())
                .build();
    }

    public record NoteDto(Long noteId, String name) {}

    public static List<ProductResponse> from(List<Product> products) {
        return products.stream()
                .map(ProductResponse::from)
                .toList();
    }
}