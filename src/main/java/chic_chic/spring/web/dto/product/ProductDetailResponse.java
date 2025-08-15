package chic_chic.spring.web.dto.product;

import chic_chic.spring.domain.entity.Product;

import java.util.List;
import java.util.Optional;

public record ProductDetailResponse(
        Long perfumeId,
        String name,
        String concentration,
        int price,
        int ml,
        String brand,       // 제조업자
        String brandUrl,
        List<NoteDto> topNote,
        String middleNote,
        String baseNote,
        List<String> ingredients,   // 전성분
        double averageRating,
        long reviewCount,
        String ImageUrl,
        List<String> usage,
        List<String> warnings
)
{
    private static final List<String> DEFAULT_USAGE = List.of(
            "손목, 귀 뒤, 팔 안쪽 등 맥박이 느껴지는 부위에 1~2회 분사",
            "피부에서 10~15cm 정도 떨어진 거리에서 분사",
            "문지르지 말고 자연 건조",
            "의류에 직접 분사 시 얼룩 가능성 주의"
    );

    private static final List<String> DEFAULT_WARNINGS = List.of(
            "눈에 들어가지 않도록 주의",
            "상처, 습진 등 이상이 있는 부위에는 사용 자제",
            "사용 중 붉은 반점, 가려움 등 이상 증상 시 사용 중지 및 전문의 상담",
            "직사광선 및 고온을 피하고 서늘한 곳에 보관",
            "어린이의 손이 닿지 않는 곳에 보관",
            "불꽃 주의, 인화성 물질 근처 사용 금지"
    );

    private static final List<String> DEFAULT_INGREDIENTS =
            List.of("Alcohol Denat.", "Aqua/Water", "Parfum/Fragrance");

     public static ProductDetailResponse from(Product product, String brandUrl) {
        // review count 기본값을 0으로 지정 (null 방지)
        double avg = Optional.ofNullable(product.getAverageRating()).orElse(0.0);
        long reviews = Optional.ofNullable(product.getReviewCount()).orElse(0L);

        // Note 리스트 반환
         List<NoteDto> topNotes = Optional.ofNullable(product.getProductNotes())
                 .orElseGet(List::of)
                 .stream()
                 .map(pn -> new NoteDto(
                         pn.getNote().getNote_id(),     // Note id
                         pn.getNote().getNote()         // Top Note
                 ))
                 .distinct()
                 .toList();

        return new ProductDetailResponse(


                product.getProductId(),
                product.getName(),
                product.getConcentration(),
                product.getPrice(),
                product.getMl(),
                product.getBrand(),     // 제조업자
                brandUrl,
                topNotes,   // 탑노트 + Note Id
                product.getMiddleNote(),
                product.getBaseNote(),
                buildIngredients(product, topNotes),
                avg,
                reviews,
                product.getImageUrl(),
                DEFAULT_USAGE,
                DEFAULT_WARNINGS
        );
    }

    private static List<String> buildIngredients(Product product, List<NoteDto> topNotes) {
        var merged = new java.util.LinkedHashSet<String>(); // 중복 제거 + 순서 유지

        // 탑 노트 추가
        for (NoteDto tn : topNotes) {
            if (tn == null || tn.name() == null) continue;
            String name = tn.name().trim();
            if (!name.isEmpty()) merged.add(name);
        }

        for (String s : new String[]{product.getMiddleNote(), product.getBaseNote()}) {
            if (s == null) continue;
            for (String token : s.split(",")) {
                String t = token.trim();
                if (!t.isEmpty()) merged.add(t);
            }
        }

        java.util.List<String> result = new java.util.ArrayList<>(DEFAULT_INGREDIENTS);
        result.addAll(merged);  // 고정값 뒤에 노트 추가
        return result;
    }

    public record NoteDto(Long noteId, String name) {}
}

