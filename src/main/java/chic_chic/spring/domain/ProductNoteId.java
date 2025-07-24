package chic_chic.spring.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductNoteId implements Serializable {

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "note_id")
    private Long noteId;
}
