package com.musicat.config;

import com.musicat.interceptor.HttpSessionIdHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@EnableScheduling
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
//        config.setUserDestinationPrefix("/user"); // 특정 유저에게 메시지 전송
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://127.0.0.1:5173") // 프론트엔드 주소를 허용하도록 설정
                .addInterceptors(new HttpSessionIdHandshakeInterceptor())
                .withSockJS();
    }

//  @Bean
//  public CorsFilter corsFilter() {
//    CorsConfiguration corsConfiguration = new CorsConfiguration();
//    corsConfiguration.setAllowCredentials(true);
//    corsConfiguration.addAllowedOriginPattern("http://127.0.0.1:5500"); // 프론트엔드 주소
//    corsConfiguration.addAllowedHeader("*");
//    corsConfiguration.addAllowedMethod("*");
//
//    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    source.registerCorsConfiguration("/websocket/**",
//        corsConfiguration); // WebSocket 엔드포인트에 대한 CORS 설정
//
//    return new CorsFilter(source);
//  }
}