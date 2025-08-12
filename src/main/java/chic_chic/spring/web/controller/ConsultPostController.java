package chic_chic.spring.web.controller;

import chic_chic.spring.apiPayload.ApiResponse;
import chic_chic.spring.domain.enums.PostType;
import chic_chic.spring.service.ConsultPostService.ConsultService;
import chic_chic.spring.service.MemberService.MemberCommandService;
import chic_chic.spring.web.dto.ConsultPostRequest;
import chic_chic.spring.web.dto.ConsultPostResponse;
import chic_chic.spring.web.dto.MemberResponseDTO;
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
    public ResponseEntity<ApiResponse<ConsultPostResponse.EntirePostDto>> createPost(
            @RequestBody @Valid ConsultPostRequest request, HttpServletRequest httpRequest){

        MemberResponseDTO.MemberInfoDTO memberInfo = memberService.getMemberInfo(httpRequest);

        ConsultPostResponse.EntirePostDto created = consultService.creatPost(request, memberInfo);

        return ResponseEntity.ok(ApiResponse.onSuccess(created));
    }

    // 게시글 보기
    @GetMapping("/{consultPostId}")
    public ApiResponse<ConsultPostResponse.EntirePostDto> getPost(@PathVariable Long consultPostId
    ){
        ConsultPostResponse.EntirePostDto result = consultService.getPost(consultPostId);

        return ApiResponse.onSuccess(result);
    }

    // 미리보기
    @GetMapping("/preview")
    public ApiResponse<ConsultPostResponse.HomeLatestDto> getLatest(){
        ConsultPostResponse.HomeLatestDto latest = consultService.getLatestPost();
        return ApiResponse.onSuccess(latest);
    }

    // 추천 상담소 홈
    @GetMapping("/home")
    public ApiResponse<ConsultPostResponse.HomeResponseDto> getPostTop2(){
        ConsultPostResponse.HomeResponseDto home = consultService.getPostByTop2();
        return ApiResponse.onSuccess(home);
    }

    // PostType 별 목록 조회
    @GetMapping
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
