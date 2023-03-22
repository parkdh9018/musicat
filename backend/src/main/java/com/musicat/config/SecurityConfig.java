package com.musicat.config;


import com.musicat.Oauth.CustomUserOAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    private final CorsFilter corsFilter;
    private final CustomUserOAuth2Service CustomUserOAuth2Service;

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

                // oauth2 를 이용한 소셜 로그인
                .oauth2Login()
                .userInfoEndpoint()
                .userService(CustomUserOAuth2Service);


        return http.build();
    }

    

}
