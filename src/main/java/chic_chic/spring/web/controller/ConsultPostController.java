package chic_chic.spring.web.controller;

import chic_chic.spring.apiPayload.ApiResponse;
import chic_chic.spring.domain.enums.PostType;
import chic_chic.spring.service.consultpostservice.ConsultService;
import chic_chic.spring.service.memberservice.MemberCommandService;
import chic_chic.spring.web.dto.ConsultPostRequest;
import chic_chic.spring.web.dto.ConsultPostResponse;
import chic_chic.spring.web.dto.MemberResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/consult-posts")
public class ConsultPostController {

    private final ConsultService consultService;
    private final MemberCommandService memberService;

    //게시글 작성
    @PostMapping
    @Operation(summary = "추천 상담소 게시글 작성 API", description = "추천관련 게시글을 작성하는 API 입니다.")
    public ResponseEntity<ApiResponse<ConsultPostResponse.EntirePostDto>> createPost(
            @RequestBody @Valid ConsultPostRequest request, HttpServletRequest httpRequest){

        MemberResponseDTO.MemberInfoDTO memberInfo = memberService.getMemberInfo(httpRequest);

        ConsultPostResponse.EntirePostDto created = consultService.creatPost(request, memberInfo);

        return ResponseEntity.ok(ApiResponse.onSuccess(created));
    }

    // 게시글 보기
    @GetMapping("/{consultPostId}")
    @Operation(summary = "추천 상담소 게시글 조회 API", description = "작성된 게시글을 조회하는 API 입니다.")
    public ApiResponse<ConsultPostResponse.EntirePostDto> getPost(@PathVariable Long consultPostId
    ){
        ConsultPostResponse.EntirePostDto result = consultService.getPost(consultPostId);

        return ApiResponse.onSuccess(result);
    }

    // 미리보기
    @GetMapping("/preview")
    @Operation(summary = "전체 게시판 조회 API", description = "커뮤니티 메인 - 전체 게시판에서 향수 추천 상담소 최신글을 조회하는 API 입니다.")
    public ApiResponse<ConsultPostResponse.HomeLatestDto> getLatest(){
        ConsultPostResponse.HomeLatestDto latest = consultService.getLatestPost();
        return ApiResponse.onSuccess(latest);
    }

    // 추천 상담소 홈
    @GetMapping("/home")
    @Operation(summary = "향수 추천 상담소 홈 API", description = "상담소 홈에서 최신글 2개씩 조회하는 API 입니다.")
    public ApiResponse<ConsultPostResponse.HomeResponseDto> getPostTop2(){
        ConsultPostResponse.HomeResponseDto home = consultService.getPostByTop2();
        return ApiResponse.onSuccess(home);
    }

    // PostType 별 목록 조회
    @GetMapping
    @Operation(summary = "필터링 페이지 API", description = "필터링별로 페이지를 조회하는 API 입니다.")
    public ApiResponse<ConsultPostResponse.PagedResponseDto> getPagedPosts(
            @RequestParam PostType type,
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "5") int size
            ){
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt"));
        ConsultPostResponse.PagedResponseDto paged = consultService.getPage(type,pageable);

        return ApiResponse.onSuccess(paged);
    }

}
