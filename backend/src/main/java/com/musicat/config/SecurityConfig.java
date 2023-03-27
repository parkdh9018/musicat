package com.musicat.config;


import com.musicat.Oauth.CustomUserOAuth2Service;
import com.musicat.handler.OAuth2AuthenticationSuccessHandler;
import com.musicat.jwt.JwtAccessDeniedHandler;
import com.musicat.jwt.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CustomUserOAuth2Service CustomUserOAuth2Service;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    /*
    webSecurityCustomizer 인터페이스의 customize 메소드를 구현하면 WebSecurity 객체를 사용해 보안 설정을 변경할 수 있다.
    이 코드에서는 web 이라는 람다 매개변수를 사용해 WebSecurity 객체에 접근합니다.

    web.ignoring().antMatchers() 메소드를 사용하면 Spring Security 가 특정 URL 패턴에 대한 요청을 무시하도록 설정할 수 있습니다.
    무시하도록 설정하면 이에 대한 요청은 Spring Security에 의한 인증 및 인가 처리를 거치지 않아도 된다.
    즉 사용자 인증을 거치지 않아도 접근할 수 있게 된다.

     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> {
            web.ignoring()
                    .antMatchers("/api-document/**", "/swagger-ui/**")
                    .antMatchers("/swagger/**","/v3/**","/swagger-resources/**");

        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
                .csrf().disable()

                // 설정된 로그인 URL로 오는 요청을 감시하며, 유저인증을 처리합니다. 인증 실패 시, AuthenticationFailureHandler를 실행합니다.
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

                // exception handling 할 때 우리가 만든 클래스를 추가
//                .exceptionHandling()
//                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                .accessDeniedHandler(jwtAccessDeniedHandler)


                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                //.and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // HtppServletRequest을 사용하는 요청들에 대해 접근 제한 설정
//                .and()
//                .authorizeHttpRequests()
//                // Preflight 요청은 허용한다는 의미
//                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//
//                .antMatchers("/api/login").permitAll()
//                .antMatchers("/api/user/**").permitAll()
//                .antMatchers("/tag/**").permitAll()
//                .antMatchers("/oauth2/**").permitAll()
//                .antMatchers("/login**").permitAll()

                // oauth2 를 이용한 소셜 로그인
                // .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(CustomUserOAuth2Service)

                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)

                // 로그아웃
                .and()
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler((request, response, authentication) -> {
                    // 로그아웃 성공 시 처리 로직, 예를 들어, 성공 메시지 반환
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("Successfully logged out");
                })
                .invalidateHttpSession(true); // 로그아웃 시 현재 세션을 무효화

        return http.build();

    }

    

}
