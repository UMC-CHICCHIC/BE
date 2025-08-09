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
        List<NoteDto> notes,
        String usageMethod,   // [추가] 사용방법
        String precautions    // [추가] 사용 시 주의사항
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
                        .toList(),
                USAGE_METHOD,   // 하드코딩 값
                PRECAUTIONS     // 하드코딩 값
        );
    }

    public record NoteDto(Long noteId, String name) {}

    // [하드코딩 상수]
    private static final String USAGE_METHOD = String.join(
            "- 손목, 귀 뒤, 팔 안쪽 등 맥박이 느껴지는 부위에 1~2회 분사",
            "- 피부에서 10~15cm 정도 떨어진 거리에서 분사",
            "- 문지르지 말고 자연 건조",
            "- 의류에 직접 분사 시 얼룩 가능성 주의"
    );

    private static final String PRECAUTIONS = String.join(
            "- 눈에 들어가지 않도록 주의",
            "- 상처, 습진 등 이상이 있는 부위에는 사용 자제",
            "- 사용 중 붉은 반점, 가려움 등 이상 증상 시 사용 중지 및 전문의 상담",
            "- 직사광선 및 고온을 피하고 서늘한 곳에 보관",
            "- 어린이의 손이 닿지 않는 곳에 보관",
            "- 불꽃 주의, 인화성 물질 근처 사용 금지"
    );
}
