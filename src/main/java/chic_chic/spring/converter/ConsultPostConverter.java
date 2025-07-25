package chic_chic.spring.converter;

import chic_chic.spring.domain.ConsultPost;
import chic_chic.spring.domain.Member;
import chic_chic.spring.web.dto.ConsultPostRequest;
import chic_chic.spring.web.dto.ConsultPostResponse;
import org.springframework.data.domain.Page;


import java.util.List;

public class ConsultPostConverter {

    public static ConsultPost toEntity(ConsultPostRequest request, Member member){
        return ConsultPost.builder()
                .postType(request.getPostType())
                .title(request.getTitle())
                .content(request.getContent())
                .imageUrl(request.getImageUrl())
                .member(member)
                .build();
    }

    public static ConsultPostResponse.PreviewDto toPreviewDto(ConsultPost consultPost){
        return ConsultPostResponse.PreviewDto.builder()
                .consultId(consultPost.getConsultPost_Id())
                .postType(consultPost.getPostType())
                .title(consultPost.getTitle())
                .content(consultPost.getContent())
                .imageUrl(consultPost.getImageUrl())
                .build();
    }

    public static ConsultPostResponse.EntirePostDto toEntirePostDto(ConsultPost consultPost){
        return ConsultPostResponse.EntirePostDto.builder()
                .memberId(consultPost.getMember().getId())
                .nickname(consultPost.getMember().getNickname())
                .consultPostId(consultPost.getConsultPost_Id())
                .postType(consultPost.getPostType())
                .title(consultPost.getTitle())
                .content(consultPost.getContent())
                .imageUrl(consultPost.getImageUrl())
                .dateTime(consultPost.getCreatedAt())
                .build();
    }

    public static ConsultPostResponse.ConsultPostSummaryDto toConsultPostSummaryDto(ConsultPost consultPost){
        return ConsultPostResponse.ConsultPostSummaryDto.builder()
                .memberId(consultPost.getMember().getId())
                .nickname(consultPost.getMember().getNickname())
                .consultPostId(consultPost.getConsultPost_Id())
                .postType(consultPost.getPostType())
                .title(consultPost.getTitle())
                .imageUrl(consultPost.getImageUrl())
                .dateTime(consultPost.getCreatedAt())
                .build();
    }

    public static Page<ConsultPostResponse.ConsultPostFilteredDto> toFilteredDtoList(Page<ConsultPost> consultPosts){

        return consultPosts.map(consultPost ->
                ConsultPostResponse.ConsultPostFilteredDto.builder()
                        .memberId(consultPost.getMember().getId())
                        .nickname(consultPost.getMember().getNickname())
                        .consultPostId(consultPost.getConsultPost_Id())
                        .title(consultPost.getTitle())
                        .imageUrl(consultPost.getImageUrl())
                        .dateTime(consultPost.getCreatedAt())
                        .build()
        );
    }
}
