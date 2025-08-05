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
    private double itemRating;   // 평점 (기준으로 사용) > DB 기준 평점

   // @Column(name = "review_count")  // 리뷰 갯수
    private Long reviewCount;

   // @Column(name = "average_rating")
    private Double averageRating;   // 리뷰 평균 평점

    private String brand;       // 브랜드
    @Column(name = "num_seller")  //  DB 컬럼명을 직접 지정 (에러남)
    private int numSeller;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductNote> productNotes = new ArrayList<>();
}