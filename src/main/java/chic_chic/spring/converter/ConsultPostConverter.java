package chic_chic.spring.converter;

import chic_chic.spring.domain.entity.ConsultPost;
import chic_chic.spring.domain.entity.Member;
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


    public static ConsultPostResponse.LatestDto toLatestDto(ConsultPost consultPost){
        return ConsultPostResponse.LatestDto.builder()
                .consultPostId(consultPost.getConsultPostId())
                .postType(consultPost.getPostType())
                .title(consultPost.getTitle())
                .content(consultPost.getContent())
                .imageUrl(consultPost.getImageUrl())
                .build();
    }

    public static ConsultPostResponse.HomeLatestDto toHomeLatestDto(ConsultPost receivePost, ConsultPost givePost){
        return ConsultPostResponse.HomeLatestDto.builder()
                .receivePost(receivePost != null ? toLatestDto(receivePost) : null)
                .givePost(givePost != null ? toLatestDto(givePost) : null)
                .build();
    }


    public static ConsultPostResponse.EntirePostDto toEntirePostDto(ConsultPost consultPost){
        return ConsultPostResponse.EntirePostDto.builder()
                .memberId(consultPost.getMember().getId())
                .nickname(consultPost.getMember().getNickname())
                .profile(consultPost.getMember().getProfileImageUrl())
                .consultPostId(consultPost.getConsultPostId())
                .postType(consultPost.getPostType())
                .title(consultPost.getTitle())
                .content(consultPost.getContent())
                .imageUrl(consultPost.getImageUrl())
                .dateTime(consultPost.getCreatedAt())
                .build();
    }

    public static ConsultPostResponse.PreviewDto toPreviewDto(ConsultPost consultPost){
        return ConsultPostResponse.PreviewDto.builder()
                .memberId(consultPost.getMember().getId())
                .nickname(consultPost.getMember().getNickname())
                .profile(consultPost.getMember().getProfileImageUrl())
                .consultPostId(consultPost.getConsultPostId())
                .postType(consultPost.getPostType())
                .title(consultPost.getTitle())
                .imageUrl(consultPost.getImageUrl())
                .dateTime(consultPost.getCreatedAt())
                .build();
    }

    public static ConsultPostResponse.HomeResponseDto toHomeResponseDto(List<ConsultPost> receiveList, List<ConsultPost> giveList){
        List<ConsultPostResponse.PreviewDto> receiveDto = receiveList.stream()
                .map(ConsultPostConverter::toPreviewDto)
                .toList();
        List<ConsultPostResponse.PreviewDto> giveDto = giveList.stream()
                .map(ConsultPostConverter::toPreviewDto)
                .toList();

        return ConsultPostResponse.HomeResponseDto.builder()
                .receivePosts(receiveDto)
                .givePosts(giveDto)
                .build();
    }

    public static ConsultPostResponse.PagedResponseDto toPagedResponseDto(Page<ConsultPost> page){
        List<ConsultPostResponse.PreviewDto> contents = page.getContent().stream()
                .map(ConsultPostConverter::toPreviewDto)
                .toList();

        return ConsultPostResponse.PagedResponseDto.builder()
                .content(contents)
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

}
