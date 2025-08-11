package chic_chic.spring.web.dto;

import chic_chic.spring.domain.Product;

import java.util.List;

public record ProductDetailResponse(
        Long perfumeId,
        String name,
        String concentration,
        int price,
        int ml,
        String brand,
        String brandUrl,
        List<NoteDto> topNote,
        String baseNote,
        String middleNote,
        double averageRating,
        long reviewCount,
        String ImageUrl,
        String usage,
        String warnings
) {
    public static ProductDetailResponse from(Product product, String brandUrl) {
        return new ProductDetailResponse(
                product.getProductId(),
                product.getName(),
                product.getConcentration(),
                product.getPrice(),
                product.getMl(),
                product.getBrand(),
                brandUrl,
                product.getProductNotes().stream()
                        .map(pn -> new NoteDto(
                                pn.getNote().getNote_id(),
                                pn.getNote().getNote()
                        ))
                        .toList(),
                product.getBaseNote(),
                product.getMiddleNote(),
                product.getItemRating(), //null 이니 수정
                product.getReviewCount(),
                product.getImageUrl(),
                "사용 방법: 조금 떨어진 거리에서 원하는 부위에 분사해주세요.",
                "주의사항: 눈에 들어가지 않도록 주의하고, 이상이 있을 경우 사용을 중단하세요."
        );
    }

    public record NoteDto(Long noteId, String name) {}
}

