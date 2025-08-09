package chic_chic.spring.converter;

import chic_chic.spring.domain.ConsultPost;
import chic_chic.spring.domain.Member;
import chic_chic.spring.domain.entity.ConsultPostComments;
import chic_chic.spring.web.dto.ConsultPostCommentsResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;


public class ConsultPostCommentsConverter {

    public ConsultPostComments toParentEntity(
            String content,
            Member member,
            ConsultPost consultPost,Long groupId){
        return  ConsultPostComments.builder()
                .content(content)
                .hierarchy(0L)
                .orders(0L)
                .groups(groupId)
                .member(member)
                .consultPost(consultPost)
                .build();
    }

    public ConsultPostComments toReplyEntity(
            String content,
            Member member,
            ConsultPost consultPost, Long groupId, Long nextOrder){
        return ConsultPostComments.builder()
                .content(content)
                .hierarchy(1L)
                .orders(nextOrder)
                .groups(groupId)
                .member(member)
                .consultPost(consultPost)
                .build();
    }

    //id만 반환
    public ConsultPostCommentsResponse.CommentResponseDto toCreateResponse(ConsultPostComments comments){
        return ConsultPostCommentsResponse.CommentResponseDto.builder()
                .commentId(comments.getCommentId())
                .groupId(comments.getGroups())
                .build();
    }

    // 목록 조회
    public ConsultPostCommentsResponse.CommentListDto toListDto(ConsultPostComments comments){
        return ConsultPostCommentsResponse.CommentListDto.builder()
                .memberId(comments.getMember().getId())
                .nickname(comments.getMember().getNickname())
                .content(comments.getContent())
                .hierarchy(comments.getHierarchy())
                .order(comments.getOrders())
                .group(comments.getGroups())
                .dateTime(comments.getCreatedAt())
                .build();
    }

    public ConsultPostCommentsResponse.CommentResultDto toResultDto(List<ConsultPostComments> list) {
        // 부모 / 대댓글 분리
        List<ConsultPostComments> parents = list.stream()
                .filter(c -> c.getHierarchy() != null && c.getHierarchy() == 0L)
                .toList();

        List<ConsultPostComments> children = list.stream()
                .filter(c -> c.getHierarchy() != null && c.getHierarchy() == 1L)
                .toList();

        // groupId -> parent dto (순서 유지)
        Map<Long, ConsultPostCommentsResponse.CommentListDto> parentMap = parents.stream()
                .collect(Collectors.toMap(
                        ConsultPostComments::getGroups,
                        this::toListDto,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));

        // groupId -> replies list
        Map<Long, List<ConsultPostCommentsResponse.CommentListDto>> repliesMap = new LinkedHashMap<>();
        for (ConsultPostComments child : children) {
            repliesMap.computeIfAbsent(child.getGroups(), k -> new ArrayList<>()).add(toListDto(child));
        }


        List<ConsultPostCommentsResponse.CommentThreadDto> content = new ArrayList<>();
        for (Map.Entry<Long, ConsultPostCommentsResponse.CommentListDto> e : parentMap.entrySet()) {
            Long groupId = e.getKey();
            content.add(ConsultPostCommentsResponse.CommentThreadDto.builder()
                    .groupId(groupId)
                    .parent(e.getValue())
                    .replies(repliesMap.getOrDefault(groupId, Collections.emptyList()))
                    .build());
        }

        return ConsultPostCommentsResponse.CommentResultDto.builder()
                .content(content)
                .build();
    }




}
