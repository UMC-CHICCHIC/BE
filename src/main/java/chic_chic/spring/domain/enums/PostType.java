package chic_chic.spring.domain.enums;

import lombok.Getter;

@Getter
public enum PostType {
    RECEIVE_RECOMMENDATION("추천 받아요!"),
    GIVE_RECOMMENDATION("추천해요!");

    private final String label;

    PostType(String label) {
        this.label = label;
    }

}
