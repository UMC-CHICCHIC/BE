package chic_chic.spring.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendedPerfumeDto {
    private Integer perfumeId;
    private String perfumeName;
    private List<String> recommendedNotes;
}