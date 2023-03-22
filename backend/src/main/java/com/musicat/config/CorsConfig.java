package com.musicat.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;



/*
CORS는 웹 페이지가 다른 도메인의 리소스를 요청할 수 있는 권한을 부여하는 보안 메커니즘 입니다.

CORS 설정 관리 class 입니다.

 */

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        // URL 패턴을 기반으로 한 CORS 구성을 관리
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();


        // CorsConfiguration 객체를 생성
        // 이 객체에 모든 출저, 헤더 및 HTTP 메소드를 허용하도록 구성 
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        // 합쳐진 웅렬의 코드
        // config.addAllowedOriginPattern("http://127.0.0.1:5500");
        // 웅렬에게 물어보자
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        

        // CORS 구성에 등록
        // API 경로와 그 외 모든 경로에 대한 요청이 CORS 정책에 따라 허용됨
        source.registerCorsConfiguration("/api/**", config);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }


}
