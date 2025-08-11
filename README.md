<img width="825" alt="Image" src="https://github.com/user-attachments/assets/e35bf514-0866-470d-adcb-96df9a1e24fe" />


# 📌 프로젝트 소개
> "CHIC-CHIC"
- 향수 전문 쇼핑과 커뮤니티 서비스를 제공하는 웹 플랫폼

---

# 🔑 주요 기능

- **회원가입 및 인증**  
  - JWT 기반 로그인/회원가입  
  - 소셜 로그인 (Google, Kakao, Naver 등) 지원  
  - 토큰 발급 및 갱신  

- **향수 상품 관리**  
  - 상품 리스트 조회 및 상세 정보 제공  
  - 카테고리별 상품 필터링  

- **커뮤니티 기능**  
  - 추천 상담소 게시글 CRUD  
  - 댓글 작성 및 관리  

- **이미지 업로드 및 관리**  
  - AWS S3 연동 이미지 업로드/삭제  
  - 프로필 및 상품 이미지 저장  

- **데이터 영속화**  
  - MySQL 기반 데이터 저장  
  - JPA/Hibernate ORM 사용  


- **추천 시스템 연동**  
  - AI 추천 클라이언트 연동 (Fast API)  
  - 개인화 향수 추천 제공  


---

## 🌿 브랜치 전략
> "Git Flow" 전략

- **main** : 배포 브랜치
- **develop** : 개발 통합 브랜치
- **feature/** : 기능별 브랜치 (`feature/auth`, `feature/perfume`)
- **refactor/** : 기존 코드 리팩토링 전용 브랜치
- **fix/** : 개발 중 발견된 일반 버그 수정 브랜치



## 🛠️ 기술 스택 

| 구분        | 기술 및 라이브러리                      |
|-------------|---------------------------------------|
| **언어**    | Java 17                               |
| **프레임워크** | Spring Boot 3.2.5                      |
| **빌드 도구** | Gradle                               |
| **데이터베이스** | MySQL 8                              |
| **ORM**      | Spring Data JPA (Hibernate)           |
| **API 문서화** | Swagger |
| **보안**     | Spring Security, JWT                   |
| **클라우드 스토리지** | AWS S3                             |
| **배포**     | Docker, AWS EC2                       |
| **CI/CD**    | GitHub Actions                       |
| **기타**     | Lombok                    |

---



## 📂 프로젝트 구조

<details>
  <summary>폴더 구조 펼치기/접기</summary>

```plaintext
chic_chic
└── spring
    ├── Application.java
    │
    ├── apiPayload
    │   ├── ApiResponse.java
    │   ├── code
    │   │   ├── BaseCode.java
    │   │   ├── BaseErrorCode.java
    │   │   ├── ErrorReasonDTO.java
    │   │   ├── ReasonDTO.java
    │   │   └── status
    │   │       ├── ErrorStatus.java
    │   │       └── SuccessStatus.java
    │   └── exception
    │       ├── ExceptionAdvice.java
    │       ├── GeneralException.java
    │       └── handler
    │           ├── CustomException.java
    │           ├── CustomOAuth2SuccessHandler.java
    │           └── MemberHandler.java
    │
    ├── auth
    │   ├── CustomAccessDeniedHandler.java
    │   ├── CustomAuthenticationEntryPoint.java
    │   └── UserDetailsServiceImpl.java
    │
    ├── client
    │   └── AIRecommendClient.java
    │
    ├── config
    │   ├── RestTemplateConfig.java
    │   ├── S3Config.java
    │   ├── SecurityConfig.java
    │   ├── SwaggerConfig.java
    │   ├── jwt
    │   │   ├── JwtAuthenticationFilter.java
    │   │   ├── JwtTokenProvider.java
    │   │   └── JwtUtil.java
    │   └── properties
    │       ├── Constants.java
    │       └── JwtProperties.java
    │
    ├── converter
    │   ├── ConsultPostConverter.java
    │   ├── MemberConverter.java
    │   ├── TestQuestionConverter.java
    │   └── TestResultConverter.java
    │
    ├── domain
    │   ├── Category.java
    │   ├── ConsultPost.java
    │   ├── Member.java
    │   ├── Note.java
    │   ├── Product.java
    │   ├── ProductNote.java
    │   ├── ProductNoteId.java
    │   ├── RefreshToken.java
    │   ├── Scrap.java
    │   ├── TestResult.java
    │   ├── common
    │   │   └── BaseEntity.java
    │   ├── entity
    │   │   ├── Perfume.java
    │   │   ├── PerfumeDiary.java
    │   │   ├── PerfumeDiaryComments.java
    │   │   ├── PerfumeStory.java
    │   │   └── Review.java
    │   ├── enums
    │   │   ├── CategoryType.java
    │   │   ├── Gender.java
    │   │   ├── PostType.java
    │   │   ├── SocialType.java
    │   │   └── mapping
    │   └── repository
    │       ├── CategoryRepository.java
    │       ├── ConsultPostRepository.java
    │       ├── MemberRepository.java
    │       ├── PerfumeDiaryCommentRepository.java
    │       ├── PerfumeDiaryRepository.java
    │       ├── PerfumeStoryRepository.java
    │       ├── ProductRepository.java
    │       ├── RecommendProductRepository.java
    │       ├── RefreshTokenRepository.java
    │       ├── ReviewRepository.java
    │       ├── ScrapRepository.java
    │       └── TestResultRepository.java
    │
    ├── service
    │   ├── AuthService
    │   │   ├── AuthService.java
    │   │   └── AuthServiceImpl.java
    │   ├── category
    │   │   └── CategoryService.java
    │   ├── ConsultPostService
    │   │   ├── ConsultService.java
    │   │   └── ConsultServiceImpl.java
    │   ├── ImageService
    │   │   ├── S3UploaderService.java
    │   │   └── S3UploaderServiceImpl.java
    │   ├── MemberService
    │   │   ├── MemberCommandService.java
    │   │   └── MemberCommandServiceImpl.java
    │   ├── OauthService
    │   │   ├── CustomOAuth2MemberServiceImpl.java
    │   │   ├── Oauth2MemberService.java
    │   │   └── Oauth2MemberServiceImpl.java
    │   ├── perfumediaryservice
    │   │   ├── PerfumeDiaryService.java
    │   │   └── PerfumeDiaryServiceImpl.java
    │   ├── perfumestoryservice
    │   │   └── PerfumeStoryService.java
    │   ├── product
    │   │   ├── HomeProductService.java
    │   │   ├── ProductDetailService.java
    │   │   └── ProductService.java
    │   ├── Review
    │   │   ├── ReviewService.java
    │   │   └── ReviewServiceImpl.java
    │   ├── ScrapService
    │   │   ├── ScrapService.java
    │   │   └── ScrapServiceImpl.java
    │   └── TestSubmitService
    │       └── TestSubmitService.java
    │
    ├── validator
    │   ├── annotation
    │   │   └── PasswordMatch.java
    │   └── validator
    │       └── PasswordMatchValidator.java
    │
    └── web
        ├── controller
        │   ├── AuthController.java
        │   ├── ConsultPostController.java
        │   ├── HomeController.java
        │   ├── MemberController.java
        │   ├── PerfumeDiaryController.java
        │   ├── PerfumeReviewController.java
        │   ├── PerfumeStoryController.java
        │   ├── S3ImageController.java
        │   ├── ScrapController.java
        │   ├── TestQuestionController.java
        │   ├── TestSubmitController.java
        │   ├── category
        │   └── product
        │       ├── ProductController.java
        │       └── ProductDetailController.java
        └── dto
            ├── AIRequestDto.java
            ├── AIResponseDto.java
            ├── AnswerDto.java
            ├── CommentRequest.java
            ├── CommentResponse.java
            ├── ConsultPostRequest.java
            ├── ConsultPostResponse.java
            ├── MemberRequestDTO.java
            ├── MemberResponseDTO.java
            ├── MyDiaryResponse.java
            ├── OptionDto.java
            ├── PerfumeStoryDetailResponse.java
            ├── PerfumeStoryResponse.java
            ├── ProductDetailResponse.java
            ├── ProductListResponse.java
            ├── ProductResponse.java
            ├── QuestionDto.java
            ├── RecommendedPerfumeDto.java
            ├── RecommendProductResponseDto.java
            ├── ReIssueRequestDTO.java
            ├── ReIssueResponseDTO.java
            ├── S3ResponseDto.java
            ├── TestSubmitRequest.java

```
</details>
 
## 🛠️ 팀원 정보 

| 이름       | 주요 담당 업무                          |
|------------|---------------------------------------|
| 알티 / 남궁강 | 회원/인증/마이홈, JWT 등 공통 설정  |
| 시럽 / 이도훈 | 향수 도메인 ( 메인/카테고리/상세)  |
| 민토리 / 성민주 | 커뮤니티(추천 상담소)  |
| 조이 / 박은서 | 향수 일기장 + 향수 이야기 + 공통 읍답, 예외처리  |


## ⚙️ 아키텍처 구조 

<img width="825" alt="Image" src="https://github.com/user-attachments/assets/00030528-0c2f-468e-b95c-f93c96fc048b" />
