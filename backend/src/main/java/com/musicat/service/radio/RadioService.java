package com.musicat.service.radio;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicat.data.dto.radio.CurrentSoundDto;
import com.musicat.data.dto.radio.PlaylistDto;
import com.musicat.data.dto.socket.SocketBaseDto;
import com.musicat.service.kafka.KafkaProducerService;
import java.util.LinkedList;
import java.util.Queue;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RadioService {

  private String currentState = "idle";
  private long seq = 0L;
  private Queue<PlaylistDto> playlist = new LinkedList<PlaylistDto>();
  private String type = "none";
  private String path = "";
  private long length = 0L;
  private String title = "";
  private String artist = "";
  private String image = "";
  private long startTime = System.currentTimeMillis();
  private long idleTimer = 0L;
  private long chatTimer = 0L;
  private long logTimer = 0L;
  private final KafkaProducerService kafkaProducerService;
  private final SimpMessagingTemplate simpMessagingTemplate;
  private static final Logger logger = LoggerFactory.getLogger(RadioService.class);
  private final ObjectMapper objectMapper = new ObjectMapper();


  /**
   * kafka로부터 현재 라디오 상태와 재생할 음원들의 정보를 불러옵니다. 받아온 데이터는 파싱하고 저장합니다.
   *
   * @param message
   */
  @KafkaListener(topics = "radioState")
  public void getRadioState(String message, Acknowledgment acknowledgment) {
    if (message != null) {
      String tempState = currentState;
      logger.debug("수신한 라디오 상태 데이터 : {} ", message);
      acknowledgment.acknowledge();
      parseJsonMessageAndSetState(message);
      if (!tempState.equals(currentState) && currentState.equals("chat")) {
        sendCurrentSound(true);
      }
    }
  }


  /**
   * Springboot App의 라디오 상태를 지우는 함수.
   */
  private void resetState() {
    currentState = "idle";
    seq = 0L;
    type = "none";
    path = "";
    title = "";
    artist = "";
    image = "";
    startTime = System.currentTimeMillis();
    length = 0L;
    playlist.clear();
  }

  private void resetTimer() {
    idleTimer = 10;
    chatTimer = 100;
  }

  /**
   * kafka로부터 받아온 메세지를 파싱하여 라디오 상태에 저장합니다.
   *
   * @param message
   */
  private void parseJsonMessageAndSetState(String message) {
    try {
      JsonNode jsonNode = objectMapper.readTree(message);
      updateState(jsonNode);
      updateSeq(jsonNode);
      updatePlaylist(jsonNode);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 받아온 radioState에서 state가 존재하는지 확인하고 업데이트
   *
   * @param jsonNode
   */
  private void updateState(JsonNode jsonNode) {
    if (jsonNode.has("state")) {
      JsonNode currentStateNode = jsonNode.get("state");
      String tempState = currentStateNode.asText();
      if (!tempState.equals(currentState)) {
        playlist.clear();
      }
      currentState = tempState;
    }
  }

  private void updateSeq(JsonNode jsonNode) {
    seq = 0L;
    if (jsonNode.has("seq")) {
      JsonNode currentSeqNode = jsonNode.get("seq");
      seq = currentSeqNode.asLong();
    }
  }

  /**
   * 받아온 radioState에서 playlist가 존재하는지 확인하고 업데이트
   *
   * @param jsonNode
   */
  private void updatePlaylist(JsonNode jsonNode) {
    if (jsonNode.has("playlist")) {
      JsonNode playlistNode = jsonNode.get("playlist");
      for (JsonNode itemNode : playlistNode) {
        String type = itemNode.get("type").asText();
        String path = itemNode.get("path").asText();
        long length = itemNode.get("length").asLong() + 3000L;
        String title = "";
        String artist = "";
        String image = "";
        if (itemNode.has("title")) {
          title = itemNode.get("title").asText();
          artist = itemNode.get("artist").asText();
          image = itemNode.get("image").asText();
        }
        playlist.add(PlaylistDto.builder()
            .type(type)
            .path(path)
            .length(length)
            .title(title)
            .artist(artist)
            .image(image)
            .build());
      }
    }
  }

  /**
   * 1초마다 라디오 상태를 갱신하는 로직입니다. idle 상태가 지속되면 강제로 finishState에 메세지를 보냅니다.
   */
  @Scheduled(fixedRate = 1000)
  public void checkAndPlayNextItem() {
    logRadioStatus();
    switch (currentState) {
      case "idle":
        idleProcess();
        break;
      case "chat":
        chatProcess();
        break;
      default:
        radioProcess();
    }
  }

  /**
   * 라디오의 현재 상태를 로그로 출력하는 함수
   */
  private void logRadioStatus() {
    logTimer++;
    if (logTimer % 5 == 0) {
      logger.debug(
          "라디오 상태: {} | 음원 타입: {} | 음원 경로: {} | 경과 시간: {} | 음원 길이: {} | 현재 큐에 들어있는 음원 수: {}",
          currentState, type, path, System.currentTimeMillis() - startTime, length,
          playlist.size());
    }
  }

  /**
   * idle 상태가 얼마나 지속되었는지 체크하는 로직입니다. 30초 이상 idle 상태가 지속된다면 라디오 서버에 강제로 finishState 이벤트를 보냅니다.
   */
  public void idleProcess() {
    if (idleTimer == 0) {
      logger.debug("서버에서 상태를 받아올 수 없습니다. 서버에 요청을 보냅니다.");
      try {
        resetTimer();
        kafkaProducerService.send("finishState", "idle");
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
    } else {
      --idleTimer;
    }
  }

  /**
   * 채팅 관련 프로세스
   */
  public void chatProcess() {
    logger.debug("채팅 상태 들어옴");
    if (checkSoundChange()) {
      sendCurrentSound(true);
    }
    if (chatTimer > 0) {
      --chatTimer;
    } else if (chatTimer == 0) {
      logger.debug("채팅 응답 시간 초과로 채팅 응답 생성을 종료합니다.");
      sendKafkaMessage("finishChat", "finish");
      --chatTimer;
    } else {
      long currentTime = System.currentTimeMillis();
      if (currentTime - startTime > length && playlist.isEmpty()) {
        sendKafkaMessage("finishState", "chat");
        resetState();
        resetTimer();
      }
    }
  }

  /**
   * 음원이 바뀌어야하는지 확인하는 로직입니다.
   *
   * @return
   */
  public boolean checkSoundChange() {
    long currentTime = System.currentTimeMillis();
    if (currentTime - startTime > length && !playlist.isEmpty()) {
      PlaylistDto sound = playlist.poll();
      type = sound.getType();
      path = sound.getPath();
      length = sound.getLength();
      title = sound.getTitle();
      artist = sound.getArtist();
      image = sound.getImage();
      startTime = currentTime;
      return true;
    }
    return false;
  }

  /**
   * 라디오 진행 로직입니다. 현재 재생중인 음원의 길이를 초과하면 음원을 다음 음원으로 바꿉니다. 만약 playlist가 비어있다면 다음 상태를 요청하는 메세지를
   * fastAPI로 보냅니다.
   */
  public void radioProcess() {
    resetTimer();
    if (checkSoundChange()) {
      if (playlist.isEmpty()) {
        sendKafkaMessage("finishState", currentState);
        resetState();
      }
      sendCurrentSound(true);
    }
  }

  /**
   * 현재 재생해야 하는 Sound를 사용자에게 보내는 로직입니다.
   */
  private void sendCurrentSound(boolean isChanged) {
    CurrentSoundDto currentSound = getCurrentSound();
    if (isChanged) {
      currentSound.setPlayedTime(0L);
    }
    SocketBaseDto<CurrentSoundDto> socketBaseDto = SocketBaseDto.<CurrentSoundDto>builder()
        .type("RADIO")
        .operation(currentState.toUpperCase())
        .data(currentSound)
        .build();
    simpMessagingTemplate.convertAndSend("/topic", socketBaseDto);
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
        .title(title)
        .artist(artist)
        .image(image)
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

  /**
   * Kafka의 Topic에 Message를 보내는 함수
   *
   * @param topic
   * @param value
   */
  private void sendKafkaMessage(String topic, String value) {
    try {
      kafkaProducerService.send(topic, value);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
