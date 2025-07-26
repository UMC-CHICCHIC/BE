package chic_chic.spring.service.ConsultPostService;

import chic_chic.spring.apiPayload.code.status.ErrorStatus;
import chic_chic.spring.apiPayload.exception.GeneralException;
import chic_chic.spring.apiPayload.exception.handler.MemberHandler;
import chic_chic.spring.converter.ConsultPostConverter;
import chic_chic.spring.domain.ConsultPost;
import chic_chic.spring.domain.Member;
import chic_chic.spring.domain.enums.PostType;
import chic_chic.spring.domain.repository.ConsultPostRepository;
import chic_chic.spring.domain.repository.MemberRepository;
import chic_chic.spring.web.dto.ConsultPostRequest;
import chic_chic.spring.web.dto.ConsultPostResponse;
import chic_chic.spring.web.dto.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultServiceImpl implements ConsultService{

    private final MemberRepository memberRepository;
    private final ConsultPostRepository consultRepository;

    @Override
    public ConsultPostResponse.EntirePostDto creatPost(ConsultPostRequest request, MemberResponseDTO.MemberInfoDTO memberInfo){
        Member member = memberRepository.findByUsername(memberInfo.getUsername())
                .orElseThrow(()-> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        ConsultPost entity = ConsultPostConverter.toEntity(request, member);
        consultRepository.save(entity);

        return ConsultPostConverter.toEntirePostDto(entity);
    }

    @Override
    public ConsultPostResponse.EntirePostDto getPost(Long consultPostId){
        ConsultPost post = consultRepository.findByConsultPost_Id(consultPostId)
                .orElseThrow(()-> new GeneralException(ErrorStatus.CONSULT_POST_NOT_FOUND));
        return ConsultPostConverter.toEntirePostDto(post);
    }

    @Override
    public ConsultPostResponse.HomeLatestDto getLatestPost(){
        var receive = consultRepository.findFirstByPostTypeOrderByCreatedAtDesc(PostType.RECEIVE).orElse(null);
        var give = consultRepository.findFirstByPostTypeOrderByCreatedAtDesc(PostType.GIVE).orElse(null);
        return ConsultPostConverter.toHomeLatestDto(receive,give);
    }

    @Override
    public ConsultPostResponse.HomeResponseDto getPostByTop2(){
        List<ConsultPost> receiveList = consultRepository.findTop2ByPostTypeOrderByCreatedAtDesc(PostType.RECEIVE);
        List<ConsultPost> giveList = consultRepository.findTop2ByPostTypeOrderByCreatedAtDesc(PostType.GIVE);

        return ConsultPostConverter.toHomeResponseDto(receiveList, giveList);
    }

    @Override
    public ConsultPostResponse.PagedResponseDto getPage(PostType postType, Pageable pageable){
        Page<ConsultPost> page = consultRepository.findAllByPostType(postType,pageable);
        return ConsultPostConverter.toPagedResponseDto(page);
    }

}
