package com.ohgiraffers.secondbackend.user.config;

import com.ohgiraffers.secondbackend.user.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(restAuthenticationEntryPoint)
                                .accessDeniedHandler(restAccessDeniedHandler)
                )
                .authorizeHttpRequests(auth ->
                        auth
                                // 인증 없이 가능한 요청
                                .requestMatchers(
                                        "/auth/**",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**"
                                ).permitAll()

                                // ADMIN 전용 API
                                .requestMatchers("/user/userId/**").hasAuthority("ADMIN")

                                // 그 외 일반 요청은 인증 필요
                                .anyRequest().authenticated()
                )
                // Gateway가 넣어준 X-User-Name, X-User-Role 기반 인증
                .addFilterBefore(headerAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public HeaderAuthenticationFilter headerAuthenticationFilter() {
        return new HeaderAuthenticationFilter();
    }

}
