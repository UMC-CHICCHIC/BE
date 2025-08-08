package chic_chic.spring.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private double itemRating;   // 평점 (기준으로 사용)
    
    @Column(name = "Image_url")
    private String ImageUrl;    // 제품이미지
}