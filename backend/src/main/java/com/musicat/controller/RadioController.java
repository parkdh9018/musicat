package com.musicat.controller;

import com.musicat.data.dto.CurrentSoundDto;
import com.musicat.data.dto.SocketBaseDto;
import com.musicat.service.RadioService;
import com.musicat.service.StoryService;
import com.musicat.util.SessionUtil;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class RadioController {

  private static final Logger logger = LoggerFactory.getLogger(RadioController.class);
  private final RadioService radioService;

  private final SimpMessagingTemplate simpMessagingTemplate;

  @MessageMapping("/subscribe")
  public void subscribeToRadio(@Header("simpSessionId") String sessionId) {
    CurrentSoundDto currentSound = radioService.getCurrentSound();
    SocketBaseDto<CurrentSoundDto> socketBaseDto = SocketBaseDto.<CurrentSoundDto>builder()
        .type("RADIO")
        .operation(radioService.getCurrentState().toUpperCase())
        .data(currentSound)
        .build();
    simpMessagingTemplate.convertAndSendToUser(sessionId, "/queue", socketBaseDto, SessionUtil.createHeaders(sessionId));
  }
}
