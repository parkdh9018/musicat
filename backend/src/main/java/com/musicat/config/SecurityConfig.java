package com.musicat.config;


import com.musicat.Oauth.CustomUserOAuth2Service;
import com.musicat.handler.OAuth2AuthenticationSuccessHandler;
import com.musicat.jwt.JwtAccessDeniedHandler;
import com.musicat.jwt.JwtAuthenticationEntryPoint;
import com.musicat.jwt.JwtSecurityConfig;
import com.musicat.jwt.TokenProvider;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


  private final TokenProvider tokenProvider;
  private final CorsFilter corsFilter;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
  private final CustomUserOAuth2Service CustomUserOAuth2Service;
  private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

  /*
  webSecurityCustomizer 인터페이스의 customize 메소드를 구현하면 WebSecurity 객체를 사용해 보안 설정을 변경할 수 있다.
  이 코드에서는 web 이라는 람다 매개변수를 사용해 WebSecurity 객체에 접근합니다...

  web.ignoring().antMatchers() 메소드를 사용하면 Spring Security 가 특정 URL 패턴에 대한 요청을 무시하도록 설정할 수 있습니다.
  무시하도록 설정하면 이에 대한 요청은 Spring Security에 의한 인증 및 인가 처리를 거치지 않아도 된다.
  즉 사용자 인증을 거치지 않아도 접근할 수 있게 된다.

   */
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return web -> {
      web.ignoring()
          .antMatchers("/api-document/**", "/swagger-ui/**")
          .antMatchers("/swagger/**", "/v3/**", "/swagger-resources/**");

    };
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
        // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
        .csrf().disable()

        // 설정된 로그인 URL로 오는 요청을 감시하며, 유저인증을 처리합니다. 인증 실패 시, AuthenticationFailureHandler를 실행합니다.
        .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

        /**
         * 예외 처리를 구성하는 부분
         * exception handling 할 때 사용자가 만든 클래스를 추가
         */
                .exceptionHandling()
        /** 인증되지 않은 사용자가 보호된 엔드포인트에 액세스하려 할 때 적절할 응답을 반환 */
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
        /** 인증된 사용자가 권한이 없는 엔드포인트에 엑세스하려 할 때 적절한 응답을 반환 */
                .accessDeniedHandler(jwtAccessDeniedHandler)

        /**
         * 세션 관리를 구성하는 부분
         */
//                .and()
//                .sessionManagement()

        /**
         * 세션 정책을 설정
         * SessionCreationPolicy.STATELESS :
         * Spring Security 에게 세션을 생성하거나 사용하지 않도록 지시
         * 서버는 클라이언트의 인증 상태를 세션을 통해 추적하지 않음
         * 클라이언트는 매 요청마다 인증 정보를 포함해야함
         * 일반적으로 이런 경우 JWT 같은 토큰 기반 인증 방식이 사용
         *
         * 상태를 유지하지 않는(stateless) 서비스나 확장성 있는 애플리케이션에 유용한 전략
         */
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        /**
         * HTTP 요청에 대한 접근 제한을 설정하는 부분
         * Spring Security를 사용하여 특정 URL 패턴에 대한 접근을 허용하거나 제한 할 수 있음
         */
                .and()
        /** .authorizeHttpRequests() 호출하여 HTTP 요청에 대한 접근 제한 설정 시작  */
                .authorizeHttpRequests()

        /**
         * CORS에 사용되는 Preflight 요청을 모두 허용
         * Preflight 요청은 본 요청 전에 클라이언트가 서버에 보내는 요청으로,
         * 실제 요청이 서버에 의해 허용되는지 확인하는 목적
         */
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()

        /**
         * .antMatchers 패턴에 권한 설정
         * "/api/login" -> /api/login 으로 시작하는 모든 요청
         * "/api/user/**" -> /api/user 으로 시작하고 그 뒤에 어떤 문자열이든 올 수 있는 모든 요청
         *
         * 실험해본 결과 /** 을 해야 모든 요청을 막는다....
         *
         * .antMatchers의 경로에 없으면 인증을 요구하는 페이지로 리다이렉트
         *
         *  역할 별로 접근 제한 설정
         * .permitAll() -> 모든 요청 허용
         * .hasRole(String role) -> 특정 역할을 가진 사용자만 접근
         * .hasAnyRole(String role1, String role2) -> 여러 역할 중 하나를 가진 사용자에게만 접근을 허용하도록 제한
         *
         * hasRole 과 hasAuthority 의 차이점
         * hasRole 역할 기반, hasAuthority 권한 기반
         * hasRole 은 ROLE_ 접두사가 추가된다 -> hasRole("ADMIN") 은 ROLE_ADMIN 이 된다.
         * hasAuthority 는 접두사가 자동으로 추가되지 않는다 -> hasAuthority("ROLE_ADMIN")
         */
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/user/**").permitAll()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/login**").permitAll()

                 .antMatchers("/user/**").hasAuthority("ROLE_USER")
                 .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")

        // 모든 경로에 대한 접근 설정
                 .anyRequest().permitAll()

        // jwt filter를 적용
                .and()
                .apply(new JwtSecurityConfig(tokenProvider))

        // oauth2 를 이용한 소셜 로그인
                .and()
        .oauth2Login()
        .userInfoEndpoint()
        .userService(CustomUserOAuth2Service)

        .and()
        .successHandler(oAuth2AuthenticationSuccessHandler)

        // 로그아웃
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessHandler((request, response, authentication) -> {
          // 로그아웃 성공 시 처리 로직, 예를 들어, 성공 메시지 반환
          response.setStatus(HttpServletResponse.SC_OK);
          response.getWriter().write("Successfully logged out");
          response.sendRedirect("https://musicat.kr");
        })
        .invalidateHttpSession(true); // 로그아웃 시 현재 세션을 무효화

    return http.build();

  }


}
