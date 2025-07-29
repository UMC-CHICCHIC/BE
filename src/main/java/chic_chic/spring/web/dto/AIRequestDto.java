package chic_chic.spring.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AIRequestDto {
    private String gender;
    private String concentration;
    private String scents;
    private String base_note;
    private List<String> middle_note;
}