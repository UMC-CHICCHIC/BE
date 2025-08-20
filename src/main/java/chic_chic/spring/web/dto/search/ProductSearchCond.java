package chic_chic.spring.web.dto.search;


import lombok.Data;
import java.util.List;

@Data
public class ProductSearchCond {
    private String q;                 // 키워드(name, brand에 매칭)
    private Long categoryId;          // 카테고리 필터(= NOTE로 매핑 중이면 노트명으로 처리)
    private List<String> notes;       // 노트 다중 선택 [“floral”, “woody”, …]
    private Integer minPrice;         // 최소가
    private Integer maxPrice;         // 최대가
    private String concentration;     // “edt” | “edp” | “parfum”
    private Double minRating;         // 최소 평점
    private Integer mlMin;            // 최소 용량
    private Integer mlMax;            // 최대 용량
}