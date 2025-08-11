
package chic_chic.spring.web.dto;

import chic_chic.spring.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class RecommendProductResponseDto {

    private Long productId;
    private String name;
    List<RecommendProductResponseDto.NoteDto> topNote;
    private String baseNote;
    private String middleNote;
    private int price;
    private double itemRating;
    private String imageUrl;

    public static RecommendProductResponseDto from(Product p) {
        return RecommendProductResponseDto.builder()
                .productId(p.getProductId())
                .name(p.getName())
                .topNote(p.getProductNotes().stream()
                        .map(pn -> new RecommendProductResponseDto.NoteDto(
                                pn.getNote().getNote_id(),
                                pn.getNote().getNote()
                        ))
                        .toList()
                )
                .baseNote(p.getBaseNote())
                .middleNote(p.getMiddleNote())
                .price(p.getPrice())
                .itemRating(p.getItemRating())
                .imageUrl(p.getImageUrl())
                .build();
    }
    public record NoteDto(Long noteId, String name) {}
}