package com.musicat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MusicatApplication {

  public static void main(String[] args) {
    SpringApplication.run(MusicatApplication.class, args);
  }

}
