package com.musicat.controller.radio;

import com.musicat.data.dto.radio.CurrentSoundDto;
import com.musicat.data.dto.socket.SocketBaseDto;
import com.musicat.service.radio.RadioService;
import com.musicat.util.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class RadioController {

  private static final Logger logger = LoggerFactory.getLogger(RadioController.class);
  private final RadioService radioService;

  private final SimpMessagingTemplate simpMessagingTemplate;

  /**
   * 소켓에 구독 신청을 보내면 현재 라디오의 정보를 반환합니다.
   *
   * @param sessionId
   */
  @MessageMapping("/subscribe")
  public void subscribeToRadio(@Header("simpSessionId") String sessionId) {
    CurrentSoundDto currentSound = radioService.getCurrentSound();
    SocketBaseDto<CurrentSoundDto> socketBaseDto = SocketBaseDto.<CurrentSoundDto>builder()
        .type("RADIO")
        .operation(radioService.getCurrentState().toUpperCase())
        .data(currentSound)
        .build();
    simpMessagingTemplate.convertAndSendToUser(sessionId, "/queue", socketBaseDto,
        SessionUtil.createHeaders(sessionId));
  }
}
