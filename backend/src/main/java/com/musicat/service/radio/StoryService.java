package com.musicat.service.radio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicat.data.dto.story.StoryInfoDto;
import com.musicat.data.dto.story.StoryKafkaDto;
import com.musicat.data.dto.story.StoryRequestDto;
import com.musicat.data.dto.user.UserInfoJwtDto;
import com.musicat.data.entity.radio.Story;
import com.musicat.data.entity.user.Alert;
import com.musicat.data.entity.user.MoneyLog;
import com.musicat.data.entity.user.User;
import com.musicat.data.repository.radio.StoryRepository;
import com.musicat.data.repository.user.AlertRepository;
import com.musicat.data.repository.user.MoneyLogRepository;
import com.musicat.data.repository.user.UserRepository;
import com.musicat.jwt.TokenProvider;
import com.musicat.service.kafka.KafkaProducerService;
import com.musicat.util.ConstantUtil;
import com.musicat.util.RegexUtil;
import com.musicat.util.builder.MoneyLogBuilderUtil;
import com.musicat.util.builder.StoryBuilderUtil;
import java.util.Optional;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 선택적 Transactional (읽기 작업은 성능 향상) (DB 변경 작업만 Transactional 적용)
public class StoryService {

  // logger 정의
  private static final Logger logger = LoggerFactory.getLogger(StoryService.class);

  // Utility 정의
  private final StoryBuilderUtil storyBuilderUtil;
  private final ConstantUtil constantUtil;
  private final TokenProvider tokenProvider;

  // Service 정의
  private final KafkaProducerService kafkaProducerService;

  // Repository 정의
  private final StoryRepository storyRepository;
  private final UserRepository userRepository;
  private final MoneyLogRepository moneyLogRepository;
  private final AlertRepository alertRepository;

  // Util 정의
  private final RegexUtil regexUtil;
  private final MoneyLogBuilderUtil moneyLogBuilderUtil;

  // mapper 정의
  private final ObjectMapper objectMapper = new ObjectMapper();


  @Transactional
  @KafkaListener(topics = "storyValidateResult")
  public void getRadioState(String message, Acknowledgment acknowledgment) {
    if (message != null) {

      logger.debug("수신한 사연 유효성 결과 데이터 : {} ", message);
      acknowledgment.acknowledge(); // offset commit

      try {
        JsonNode jsonNode = objectMapper.readTree(message);
        JsonNode storyValidateResultNode = jsonNode.get("valid");
        JsonNode userSeqNode = jsonNode.get("userseq");

        // true, false
        String valid = storyValidateResultNode.asText().toLowerCase();
        long userSeq = userSeqNode.asLong();

        String alertTitle = "사연 신청 결과";
        String alertContent = "불합격";

        if (valid.equals("true")) {
          User user = userRepository.findById(userSeq)
              .orElseThrow(() -> new EntityNotFoundException("유저 정보가 존재하지 않습니다."));

          // 100 츄르 지급
          long userMoney = user.getUserMoney();
          user.setUserMoney(userMoney + 100);

          // 합격 문구 생성
          alertContent = "합격 \n" + user.getUserNickname() + "님 좋은 사연 감사합니다.\n감사의 의미로 100츄르가 지급되었습니다.";
        }

        Alert alert = Alert.builder()
            .userSeq(userSeq)
            .alertTitle(alertTitle)
            .alertContent(alertContent)
            .build();

        alertRepository.save(alert);

      } catch (Exception e) {
        throw new RuntimeException("카프카 에러 발생!!");
      }

    }
  }


  /**
   * 사연 신청 (-50 츄르)
   *
   * @param storyRequestDto
   */
  @Transactional
  public void insertStory(StoryRequestDto storyRequestDto) {
    Story story = storyRepository.save(
        storyBuilderUtil.buildStoryEntity(storyRequestDto));

    User user = userRepository.findById(storyRequestDto.getUserSeq())
        .orElseThrow(() -> new EntityNotFoundException("유저 정보가 존재하지 않습니다."));

    if (user.getUserMoney() < 50) {
      return;
    }

    user.setUserMoney(user.getUserMoney() - 50);
    MoneyLog moneyLog = moneyLogBuilderUtil.buildMoneyLog(user, constantUtil.MONEYLOG_STORY_TYPE, constantUtil.MONEYLOG_STORY_DETAIL, constantUtil.STORY_REQUEST_MONEY * -1);
    moneyLogRepository.save(moneyLog);

    // (사연 + 신청곡) 데이터 -> 카프카 -> 파이썬 서버 : valid 체크 후 DB 반영, Intro 음성 파일 생성, Reaction 음성 파일 생성, Outro 음성 파일 생성
    try {
      /**
       * storyKafkaDto : 사연 Seq, 사용자Seq, 사연 제목, 사연 내용, 신청곡 제목, 신청곡 가수
       */
      StoryKafkaDto storyKafkaDto = storyBuilderUtil.buildStoryKafkaDto(story);

      /**
       * 사연 신청곡 데이터 전처리 :
       * 제목, 가수에 '.'을 제외한 (특수 기호 + 괄호) 공백으로 replace (정규 표현식 사용)
       */
      storyKafkaDto.setStoryMusicTitle(
          regexUtil.removeTextAfterSpecialChar(storyKafkaDto.getStoryMusicTitle()));
      storyKafkaDto.setStoryMusicArtist(
          regexUtil.removeTextAfterSpecialChar(storyKafkaDto.getStoryMusicArtist()));

      // kafKa로 storyKafkaDto 전송
      kafkaProducerService.send(constantUtil.STORY_TOPIC, storyKafkaDto);

    } catch (JsonProcessingException e) {
      logger.error("카프카 로직 처리 에러 발생!!! : {}", e.getMessage());
      throw new RuntimeException("카프카 에러"); // 500 Status Code 리턴
    }
  }

  /**
   * 사연 중복 검사
   *
   * @param token
   * @return
   */
  public void isUniqueStory(String token) {

    UserInfoJwtDto userInfo = tokenProvider.getUserInfo(token);

    Optional<Story> optionalStory = storyRepository.findByUserSeqAndStoryReadedFalseOrUserSeqAndStoryReadedNull(
        userInfo.getUserSeq(), userInfo.getUserSeq());

    if (optionalStory.isPresent()) {
      throw new EntityExistsException("이미 신청한 사연이 있습니다.");
    }
  }


  /**
   * 사연 상세 조회
   *
   * @param storySeq
   * @return
   */
  public StoryInfoDto getStory(long storySeq) {
    Story story = storyRepository.findById(storySeq)
        .orElseThrow(EntityNotFoundException::new);

    return storyBuilderUtil.buildStoryInfoDto(story);
  }

  /**
   * 사연 삭제
   *
   * @param storySeq
   */
  @Transactional
  public void deleteStory(long storySeq) {
    Story story = storyRepository.findById(storySeq)
        .orElseThrow(() -> new EntityNotFoundException("사연이 존재하지 않습니다.")); // 사연 조회

    storyRepository.delete(story); // 사연 삭제
  }

}
