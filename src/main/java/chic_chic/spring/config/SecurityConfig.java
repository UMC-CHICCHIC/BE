package chic_chic.spring.config;

import chic_chic.spring.apiPayload.exception.handler.CustomOAuth2SuccessHandler;
import chic_chic.spring.auth.CustomAccessDeniedHandler;
import chic_chic.spring.auth.CustomAuthenticationEntryPoint;
import chic_chic.spring.config.jwt.JwtAuthenticationFilter;
import chic_chic.spring.service.oauthservice.CustomOAuth2MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.Duration;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomOAuth2MemberServiceImpl customOAuth2UserService;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .authorizeHttpRequests(auth -> auth
                        // preflight은 항상 허용
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 공개 리소스 / 인증 없이
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/actuator/health",
                                "/home/popular-products",         // 메인페이지
                                "/test/questions",
                                "/swagger-resources/**",
                                "/categories",      // 카테고리 출력
                                "/products/**",      // 제품 관련 출력
                                "/member/reissue"
                        ).permitAll()

                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/products/search"
                        ).permitAll()

                        .requestMatchers(HttpMethod.GET, "/consult-posts", "/consult-posts/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/consult-posts/*/comments").permitAll()
                        .requestMatchers(HttpMethod.POST, "/consult-posts", "/images/**").authenticated()
                        .requestMatchers("/auth/**", "/login", "/signup").permitAll()

                        // 향수 일기장 관련: 공개 목록/상세는 허용
                        .requestMatchers(HttpMethod.GET, "/diary/public").permitAll()
                        .requestMatchers(HttpMethod.GET, "/diary/*/comments").permitAll() // public/private 구분은 service 에서 진행
                        .requestMatchers(HttpMethod.GET, "/diary/*").permitAll() // 개별 일기 상세 (공개면 누구나, 비공개면 service에서 owner 체크)

                        // 인증 필요: 내 일기 조회, 작성, 댓글 작성
                        .requestMatchers(HttpMethod.GET, "/diary/my").authenticated()
                        .requestMatchers(HttpMethod.POST, "/diary").authenticated()
                        .requestMatchers(HttpMethod.POST, "/diary/*/comments").authenticated()

                        // 리뷰 관련: GET 목록/상세 : 공개
                        .requestMatchers(HttpMethod.GET, "/perfumes/*/reviews").permitAll()

                        // 리뷰 작성/수정/삭제 : 인증 필요
                        .requestMatchers(HttpMethod.POST,   "/perfumes/*/reviews").authenticated()
                        .requestMatchers(HttpMethod.PUT,    "/perfumes/*/reviews/*").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/perfumes/*/reviews/*").authenticated()

                        // 그 외는 인증 필요
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .successHandler(customOAuth2SuccessHandler)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {  // cors 설정
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*")); // 운영 환경에서는 정확한 도메인만 명시
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true);
        config.setMaxAge(Duration.ofHours(1));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
