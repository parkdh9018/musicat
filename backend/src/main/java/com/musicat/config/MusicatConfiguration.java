package com.musicat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MusicatConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://127.0.0.1:5500") // Live Server에서 실행 중인 프론트엔드 주소를 추가합니다.
            .allowedMethods("*")
            .allowedHeaders("*")
            .allowCredentials(true);
        registry.addMapping("/websocket/**") // SockJS 관련 엔드포인트에 대한 CORS 설정을 추가합니다.
            .allowedOrigins("http://127.0.0.1:5500")
            .allowedMethods("*")
            .allowedHeaders("*")
            .allowCredentials(true);
    }
}