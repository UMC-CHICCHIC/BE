package chic_chic.spring.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_id;

    private String name;         // 제품명
    private String baseNote;     // 베이스 노트
    private String middleNote;   // 미들 노트
    private int price;           // 가격
    private String concentration; // 농도
    private int ml;               // 용량
    private double itemRating;   // 평점 (기준으로 사용)

    private String brand;       // 브랜드
    @Column(name = "num_seller")  //  DB 컬럼명을 직접 지정 (에러남)
    private int numSeller;


    @Column(name = "Image_url")
    private String ImageUrl;    // 제품이미지

    @Column(name = "review_count", nullable = false)
    private int reviewCount;   //임시 리뷰많은순

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductNote> productNotes = new ArrayList<>();

    //제품 상세 조회 시에 필요한 메서드
    public List<Note> getNotes() {
        return productNotes.stream()
                .map(ProductNote::getNote)
                .toList();
    }
}

