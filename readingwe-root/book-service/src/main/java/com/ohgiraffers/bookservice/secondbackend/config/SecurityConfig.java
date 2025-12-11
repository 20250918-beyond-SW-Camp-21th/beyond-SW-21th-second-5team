package com.ohgiraffers.bookservice.secondbackend.config;

import com.ohgiraffers.bookservice.secondbackend.book.util.HeaderAuthenticationFilter;
import com.ohgiraffers.bookservice.secondbackend.book.util.RestAccessDeniedHandler;
import com.ohgiraffers.bookservice.secondbackend.book.util.RestAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final RestAccessDeniedHandler restAccessDeniedHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session
                        -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception ->
                        exception
                                .authenticationEntryPoint(restAuthenticationEntryPoint)
                                .accessDeniedHandler(restAccessDeniedHandler)
                )
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(HttpMethod.POST, "/users", "/auth/login", "/auth/refresh", "/auth/signup"
                                        , "/internal/mail/**","/book/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/users/me","/user/**","/booklike/**").hasAuthority("USER")
                                .requestMatchers(HttpMethod.POST, "/users/me","/user/**","/booklike/**").hasAuthority("USER")
                                .requestMatchers("/actuator/**").permitAll()

                                //book-report관련
                                .requestMatchers(HttpMethod.POST, "/book-report").permitAll()         // 등록
                                .requestMatchers(HttpMethod.PUT, "/book-report/**").hasAuthority("USER")     // 수정
                                .requestMatchers(HttpMethod.DELETE, "/book-report/**").hasAuthority("USER")  // 삭제
                                .requestMatchers(HttpMethod.GET, "/book-report/**").permitAll()              // 조회(단건/목록 모두 허용)

                                // BookReportComment 권한 설정
                                .requestMatchers(HttpMethod.POST, "/book-report-comment").hasAuthority("USER")          // 댓글 등록
                                .requestMatchers(HttpMethod.PUT, "/book-report-comment/**").hasAuthority("USER")       // 댓글 수정
                                .requestMatchers(HttpMethod.DELETE, "/book-report-comment/**").hasAuthority("USER")    // 댓글 삭제
                                .requestMatchers(HttpMethod.GET, "/book-report-comment/**").permitAll()                 // 댓글 조회

                                // BookReportLike 권한 설정
                                .requestMatchers(HttpMethod.POST, "/book-report-like/**").hasAuthority("USER")

                                .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").permitAll()
                                .requestMatchers( "/swagger-ui.html","/swagger-ui/**","/v3/api-docs/**","/swagger-resources/**").permitAll()
                                .anyRequest().authenticated()
                )
                // 기존 JWT 검증 필터 대신, Gateway가 전달한 헤더를 이용하는 필터 추가
                .addFilterBefore(headerAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public HeaderAuthenticationFilter headerAuthenticationFilter() {
        return new HeaderAuthenticationFilter();
    }

}
