package chic_chic.spring.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "external_perfume")   // 실제 외부 테이블 이름
@Getter
@Setter
public class Perfume {
    @Id
    private Long id;

    private String name;
    private String topNote;
    private String middleNote;
    private String baseNote;
    private String ingredients;
    private Long brandId;
    // ... 외부에서 제공되는 컬럼들

    @Transient
    private Long reviewCount;  // 리뷰 갯수
}