package com.musicat.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicat.data.dto.CurrentSoundDto;
import com.musicat.data.dto.PlaylistDto;
import com.musicat.data.dto.SocketBaseDto;
import java.util.LinkedList;
import java.util.Queue;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RadioService {

  private String currentState = "idle";
  private Queue<PlaylistDto> playlist = new LinkedList<PlaylistDto>();
  private String type = "none";
  private String path = "none";
  private long length = 0L;
  private long startTime = System.currentTimeMillis();
  private long count = 0L;

  private final KafkaProducerService kafkaProducerService;

  private final SimpMessagingTemplate simpMessagingTemplate;

  private static final Logger logger = LoggerFactory.getLogger(RadioService.class);

  /**
   * kafka로부터 현재 라디오 상태와 재생할 음원들의 정보를 불러옵니다. 받아온 데이터는 파싱하고 저장합니다.
   *
   * @param message
   */
  @KafkaListener(topics = "radioState", groupId = "local")
  public void getRadioState(String message) {
    if (message != null) {
      logger.debug("수신한 라디오 상태 데이터 : {} ", message);
      clearState();
      parseJsonMessageAndSetState(message);
    }
  }

  /**
   * Springboot App의 라디오 상태를 지우는 함수.
   */
  private void clearState() {
    currentState = "idle";
    type = "none";
    path = "none";
    startTime = System.currentTimeMillis();
    length = 0L;
    playlist.clear();
  }

  /**
   * kafka로부터 받아온 메세지를 파싱하여 라디오 상태에 저장합니다.
   *
   * @param message
   */
  private void parseJsonMessageAndSetState(String message) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      JsonNode jsonNode = objectMapper.readTree(message);
      if (jsonNode.has("state")) {
        JsonNode currentStateNode = jsonNode.get("state");
        currentState = currentStateNode.asText();
      }
      if (jsonNode.has("playlist")) {
        JsonNode playlistNode = jsonNode.get("playlist");
        for (JsonNode itemNode : playlistNode) {
          String type = itemNode.get("type").asText();
          String path = itemNode.get("path").asText();
          long length = itemNode.get("length").asLong() + 3000L;
          playlist.add(new PlaylistDto(type, path, length));
        }
      }
    } catch (JsonMappingException e) {
      throw new RuntimeException(e);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 1초마다 라디오 상태를 갱신하는 로직입니다. idle 상태가 지속되면 강제로 finishState에 메세지를 보냅니다.
   */
  @Scheduled(fixedRate = 1000)
  public void checkAndPlayNextItem() {
    if (count % 5 == 0) {
      logger.debug("현재 라디오의 상태 : {} | 재생중인 음원 타입 : {} | 재생중인 음원 : {} | 경과 시간 : {} | 음원 길이 : {}",
          currentState, type, path, System.currentTimeMillis() - startTime, length);
    }
    if (currentState.equals("idle")) {
      idleTimer();
    } else {
      radioProcess();
    }
  }

  /**
   * 라디오 진행 로직입니다. 현재 재생중인 음원의 길이를 초과하면 음원을 다음 음원으로 바꿉니다.
   */
  public void radioProcess() {
    count = 0;
    if (checkSoundChange()) {
      CurrentSoundDto currentSound = getCurrentSound();
      currentSound.setPlayedTime(0L);
      SocketBaseDto<CurrentSoundDto> socketBaseDto = SocketBaseDto.<CurrentSoundDto>builder()
          .type("radio")
          .operation(currentState)
          .data(currentSound)
          .build();
      simpMessagingTemplate.convertAndSend("/topic/sound", socketBaseDto);
    }
  }

  /**
   * 음원이 바뀌어야하는지 확인하는 로직입니다.
   *
   * @return
   */
  public boolean checkSoundChange() {
    long currentTime = System.currentTimeMillis();
    if (currentTime - startTime > length) {
      if (!playlist.isEmpty()) {
        PlaylistDto sound = playlist.poll();
        type = sound.getType();
        path = sound.getPath();
        length = sound.getLength();
        startTime = currentTime;
      } else {
        try {
          kafkaProducerService.send("finishState", currentState);
          clearState();
        } catch (JsonProcessingException e) {
          throw new RuntimeException(e);
        }
      }
      return true;
    }
    return false;
  }

  /**
   * idle 상태가 얼마나 지속되었는지 체크하는 로직입니다. 30초 이상 idle 상태가 지속된다면 라디오 서버에 강제로 finishState 이벤트를 보냅니다.
   */
  public void idleTimer() {
    if (count == 30) {
      logger.debug("서버에서 상태를 받아올 수 없습니다. 서버에 요청을 보냅니다.");
      try {
        kafkaProducerService.send("finishState", "idle");
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
      count = 0;
    } else {
      count++;
    }
  }

  /**
   * 현재 재생해야 하는 Sound를 반환하는 로직입니다.
   *
   * @return
   */
  public CurrentSoundDto getCurrentSound() {
    long currentTime = System.currentTimeMillis();
    CurrentSoundDto currentSound = CurrentSoundDto.builder()
        .type(type)
        .path(path)
        .startTime(startTime)
        .playedTime(currentTime - startTime)
        .length(length)
        .build();
    return currentSound;
  }

  /**
   * 현재 상태를 반환하는 로직입니다.
   *
   * @return
   */
  public String getCurrentState() {
    return currentState;
  }


}
