//package com.musicat.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//@EnableWebMvc
//@CrossOrigin(origins = "*", allowedHeaders = "*")
//public class MusicatConfiguration implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//            .allowedOrigins("http://127.0.0.1:5500")
//            .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
//            .allowedHeaders("*")
//            .allowCredentials(true);
//        registry.addMapping("/websocket/**")
//            .allowedOrigins("http://127.0.0.1:5500")
//            .allowedMethods("*")
//            .allowedHeaders("*")
//            .allowCredentials(true);
//    }
//}