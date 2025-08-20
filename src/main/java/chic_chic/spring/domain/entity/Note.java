package chic_chic.spring.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "notes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long note_id;

    private String note;    // 탑 노트

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL)
    private List<ProductNote> productNotes = new ArrayList<>();

}
