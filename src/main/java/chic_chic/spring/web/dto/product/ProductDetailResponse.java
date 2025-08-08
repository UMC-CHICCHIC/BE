package chic_chic.spring.web.dto.product;

import chic_chic.spring.domain.Product;

import java.util.List;

public record ProductDetailResponse(
        Long perfumeId,
        String name,
        String concentration,
        int price,
        int ml,
        String brand,
        String baseNote,
        String middleNote,
        String imageUrl,
        List<NoteDto> notes
) {
    public static ProductDetailResponse from(Product product) {
        return new ProductDetailResponse(
                product.getProduct_id(),
                product.getName(),
                product.getConcentration(),
                product.getPrice(),
                product.getMl(),
                product.getBrand(),
                product.getBaseNote(),
                product.getMiddleNote(),
                product.getImageUrl(),
                product.getProductNotes().stream()
                        .map(pn -> new NoteDto(
                                pn.getNote().getNote_id(),
                                pn.getNote().getNote()
                        ))
                        .toList()
        );
    }

    public record NoteDto(Long noteId, String name) {}
}
