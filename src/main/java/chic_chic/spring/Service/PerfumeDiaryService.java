package chic_chic.spring.Service;

import chic_chic.spring.apiPayload.code.status.ErrorStatus;
import chic_chic.spring.apiPayload.exception.GeneralException;
import chic_chic.spring.domain.Member;
import chic_chic.spring.domain.entity.PerfumeDiary;
import chic_chic.spring.domain.repository.MemberRepository;
import chic_chic.spring.domain.repository.PerfumeDiaryRepository;
import chic_chic.spring.web.dto.MyDiaryResponse;
import chic_chic.spring.web.dto.PerfumeDiaryRequest;
import chic_chic.spring.web.dto.PerfumeDiaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PerfumeDiaryService {

    private final PerfumeDiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final S3Uploader s3Uploader; // 이미지 S3 업로드
    private final JwtTokenProvider jwtTokenProvider;

    public PerfumeDiaryResponse createDiary(String token, PerfumeDiaryRequest request, MultipartFile image) {
        Long memberId = jwtTokenProvider.getUserIdFromToken(token);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            imageUrl = s3Uploader.upload(image, "diaries");
        }

        PerfumeDiary diary = new PerfumeDiary();
        diary.setUser(member);
        diary.setTitle(request.getTitle());
        diary.setContent(request.getContent());
        diary.setIsPublic(request.getIsPublic());
        diary.setImageUrl(imageUrl);

        diaryRepository.save(diary);

        return new PerfumeDiaryResponse(diary.getId());
    }

    public List<MyDiaryResponse> getMyDiaries(String token, int page) {
        Long userId = jwtTokenProvider.getUserIdFromToken(token);
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<PerfumeDiary> diaryPage = diaryRepository.findByUser_IdOrderByCreatedAtDesc(userId, pageable);

        return diaryPage.getContent().stream()
                .map(diary -> new MyDiaryResponse(
                        diary.getId(),
                        diary.getTitle(),
                        diary.getUser().getNickName(),
                        diary.getCreatedAt().toLocalDate(),
                        diary.getImageUrl()
                ))
                .collect(Collectors.toList());
    }
}