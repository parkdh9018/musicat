package com.musicat.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeRequestInitializer;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YoutubeApiConfig {

  @Value("${youtube.client.api-key}")
  private String apiKey;

  @Bean
  public YouTube youtubeApi() throws GeneralSecurityException, IOException {
    YouTube.Builder builder = new YouTube.Builder(
        GoogleNetHttpTransport.newTrustedTransport(),
        GsonFactory.getDefaultInstance(),
        null);
    builder.setApplicationName("musicat");
    builder.setYouTubeRequestInitializer(new YouTubeRequestInitializer(apiKey));
    return builder.build();
  }
}