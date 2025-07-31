package chic_chic.spring.domain.enums;

import lombok.Getter;

@Getter
public enum PostType {
    RECEIVE("추천 받아요!"),
    GIVE("추천해요!");

    private final String label;

    PostType(String label) {
        this.label = label;
    }

}
