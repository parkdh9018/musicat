package com.musicat.controller;

import com.musicat.data.dto.CurrentSoundDto;
import com.musicat.service.RadioService;
import com.musicat.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class RadioController {

  private static final Logger logger = LoggerFactory.getLogger(RadioController.class);
  private final RadioService radioService;

  @SubscribeMapping("/topic/music")
  public CurrentSoundDto subscribeToRadio() {
    CurrentSoundDto currentSound = radioService.getCurrentSound();
    logger.debug("라디오 소켓 연결됨. 현재 재생중인 음원 정보 : {}", currentSound);
    return currentSound;
  }
}
