package chic_chic.spring.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductNoteId implements Serializable {

    // 복합키를 위한 entity
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "note_id")
    private Long noteId;
}
