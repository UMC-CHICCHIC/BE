package chic_chic.spring.service.perfumediaryservice;

import chic_chic.spring.apiPayload.code.status.ErrorStatus;
import chic_chic.spring.apiPayload.exception.GeneralException;
import chic_chic.spring.config.jwt.JwtTokenProvider;
import chic_chic.spring.domain.Member;
import chic_chic.spring.domain.entity.PerfumeDiary;
import chic_chic.spring.domain.entity.PerfumeDiaryComments;
import chic_chic.spring.domain.repository.MemberRepository;
import chic_chic.spring.domain.repository.PerfumeDiaryCommentRepository;
import chic_chic.spring.domain.repository.PerfumeDiaryRepository;
import chic_chic.spring.web.dto.*;

import chic_chic.spring.web.dto.PerfumeDiary.PerfumeDiaryDetailResponse;
import chic_chic.spring.web.dto.PerfumeDiary.PerfumeDiaryRequest;
import chic_chic.spring.web.dto.PerfumeDiary.PerfumeDiaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PerfumeDiaryServiceImpl implements PerfumeDiaryService {

    private final PerfumeDiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final chic_chic.spring.service.ImageService.S3UploaderService s3Uploader;
    private final JwtTokenProvider jwtTokenProvider;
    private final PerfumeDiaryCommentRepository commentRepository;


    @Override
    public PerfumeDiaryResponse createDiary(String token, PerfumeDiaryRequest request, MultipartFile image) {
        String email = jwtTokenProvider.getEmailFromToken(token); // 변경
        Member member = memberRepository.findByEmail(email)       // 변경
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        // 이하 생략


    String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            MultipartFile wrapped = wrapWithDirectory(image, "diaries");
            S3ResponseDto result = s3Uploader.upload(wrapped);
            imageUrl = result.getUrl();
        }

        PerfumeDiary diary = PerfumeDiary.builder()
                .user(member)
                .title(request.getTitle())
                .content(request.getContent())
                .isPublic(request.getIsPublic())
                .imageUrl(imageUrl)
                .build();

        diaryRepository.save(diary);
        return new PerfumeDiaryResponse(diary.getId());
    }

    @Override
    public List<MyDiaryResponse> getMyPreview(String token) {
        String email = jwtTokenProvider.getEmailFromToken(token); // 변경
        Member member = memberRepository.findByEmail(email)       // 변경
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
        Long memberId = member.getId();

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<PerfumeDiary> diaries = diaryRepository.findByUser_IdOrderByCreatedAtDesc(memberId, pageable).getContent();

        return diaries.stream().map(this::toMyDiaryResponse).collect(Collectors.toList());
    }


    @Override
    public List<MyDiaryResponse> getPublicPreview() {   // 미리보기
        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<PerfumeDiary> diaries = diaryRepository
                .findByIsPublicTrueOrderByCreatedAtDesc(pageable)
                .getContent();

        return diaries.stream()
                .map(this::toMyDiaryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<MyDiaryResponse> getAllPublic(int page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<PerfumeDiary> result = diaryRepository
                .findByIsPublicTrueOrderByCreatedAtDesc(pageable);

        return result.getContent().stream().map(this::toMyDiaryResponse).collect(Collectors.toList());
    }

    @Override
    public List<MyDiaryResponse> getAllMy(String token, int page) {
        String email = jwtTokenProvider.getEmailFromToken(token); // 변경
        Member member = memberRepository.findByEmail(email)       // 변경
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
        Long memberId = member.getId();

        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<PerfumeDiary> result = diaryRepository.findByUser_IdOrderByCreatedAtDesc(memberId, pageable);

        return result.getContent().stream()
                .map(this::toMyDiaryResponse)
                .collect(Collectors.toList());
    }


    @Override
    public PerfumeDiaryDetailResponse getDiaryDetail(Long diaryId) {
        PerfumeDiary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.DIARY_NOT_FOUND));

        return new PerfumeDiaryDetailResponse(
                diary.getId(),
                diary.getTitle(),
                diary.getContent(),
                diary.getUser().getNickname(),
                diary.getImageUrl(),
                diary.getCreatedAt().toLocalDate(),
                diary.getIsPublic()
        );
    }

    private MyDiaryResponse toMyDiaryResponse(PerfumeDiary diary) {
        return new MyDiaryResponse(
                diary.getId(),
                diary.getTitle(),
                diary.getUser().getNickname(),
                diary.getCreatedAt().toLocalDate(),
                diary.getImageUrl()
        );
    }

    @Override
    public CommentResponse addComment(String token, Long diaryId, CommentRequest request) {
        String email = jwtTokenProvider.getEmailFromToken(token); // 변경
        Member member = memberRepository.findByEmail(email)       // 변경
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        PerfumeDiary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.DIARY_NOT_FOUND));

        PerfumeDiaryComments parent = null;
        if (request.getParentCommentId() != null) {
            parent = commentRepository.findById(request.getParentCommentId())
                    .orElseThrow(() -> new GeneralException(ErrorStatus.COMMENT_NOT_FOUND));
        }

        PerfumeDiaryComments comment = PerfumeDiaryComments.builder()
                .diary(diary)
                .user(member)
                .content(request.getContent())
                .parentComment(parent)
                .build();
        commentRepository.save(comment);

        return new CommentResponse(
                comment.getId(),
                diary.getId(),
                comment.getContent(),
                member.getNickname(),
                comment.getCreatedAt(),
                parent != null ? parent.getId() : null
        );
    }

    @Override
    public List<CommentResponse> getComments(Long diaryId) {
        // 1) 다이어리 존재 체크
        PerfumeDiary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.DIARY_NOT_FOUND));

        // 2) 최상위 댓글만 조회 (parentComment == null), 생성일 오름차순
        List<PerfumeDiaryComments> topComments =
                commentRepository.findByDiaryAndParentCommentIsNullOrderByCreatedAt(diary);

        // 3) DTO 변환 (자식 replies 필드도 함께 매핑)
        return topComments.stream()
                .map(this::toCommentResponse)
                .collect(Collectors.toList());
    }

    private CommentResponse toCommentResponse(PerfumeDiaryComments c) {
        return new CommentResponse(
                c.getId(),
                c.getDiary().getId(),
                c.getContent(),
                c.getUser().getNickname(),
                c.getCreatedAt(),
                c.getParentComment() != null ? c.getParentComment().getId() : null
        );
    }

    // 이미지 업로드용 MultipartFile에 디렉토리 이름 붙여주는 래퍼
    private MultipartFile wrapWithDirectory(MultipartFile file, String dirName) {
        String originalName = file.getOriginalFilename();
        String newName = dirName + "/" + originalName;

        return new MultipartFile() {
            @Override public String getName() { return file.getName(); }
            @Override public String getOriginalFilename() { return newName; }
            @Override public String getContentType() { return file.getContentType(); }
            @Override public boolean isEmpty() { return file.isEmpty(); }
            @Override public long getSize() { return file.getSize(); }
            @Override public byte[] getBytes() throws IOException { return file.getBytes(); }
            @Override public InputStream getInputStream() throws IOException { return file.getInputStream(); }
            @Override public void transferTo(File dest) throws IOException, IllegalStateException { file.transferTo(dest); }
        };
    }
}
