package chic_chic.spring.domain.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "scraps", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"member_id", "product_id"})
})
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Scrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
