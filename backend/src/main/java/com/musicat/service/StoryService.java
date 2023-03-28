package com.musicat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.musicat.data.dto.SpotifySearchResultDto;
import com.musicat.data.dto.YoutubeSearchResultDto;
import com.musicat.data.dto.story.StoryInfoDto;
import com.musicat.data.dto.story.StoryRequestDto;
import com.musicat.data.entity.Story;
import com.musicat.data.repository.StoryRepository;
import com.musicat.util.ConstantUtil;
import com.musicat.util.StoryBuilderUtil;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Todo : 사연 Valid 검사 false일 경우 readed 처리 해줘야 한다. -> 이미 신청한 사연이 있는지 검증하기 위해서
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoryService {

    // logger 정의
    private static final Logger logger = LoggerFactory.getLogger(StoryService.class);

    // Utility 정의
    private final StoryBuilderUtil storyBuilderUtil;
    private final ConstantUtil constantUtil;

    // Service 정의
    private final KafkaProducerService kafkaProducerService;

    // Repository 정의
    private final StoryRepository storyRepository;


    /**
     * 사연 신청
     *
     * @param storyRequestDto
     */
    @Transactional
    public void insertStory(StoryRequestDto storyRequestDto) {

        Story story = storyRepository.save(
                storyBuilderUtil.buildStoryEntity(storyRequestDto));

        // 사연 데이터, 신청곡 를 카프카로 전송 -> 파이썬 서버에서 valid 체크 후 DB 반영, 인트로 음성 파일 생성, Reaction 음성 파일 생성, Outro 음성 파일 생성
        try {
            // Todo : Topic과 보낼 데이터 재정의 필요
            kafkaProducerService.send("verifyStory", story.getStoryContent());
            kafkaProducerService.send("musicRequest", story.getStoryMusicTitle());
        } catch (JsonProcessingException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("카프카 에러");
        }
    }

//    /**
//     * 사연 1개 조회 (읽어야 하는 사연)
//     */
//    public Object getTopStoryInfo() throws Exception {
//
//        Optional<Story> optionalStory = storyRepository.findTop1ByStoryReadedFalseAndStoryValidTrueOrderByStoryCreatedAt();
//
//        if (optionalStory.isPresent()) { // 사연이 존재함
//            Story story = optionalStory.get();
//
//            return storyBuilderUtil.buildStoryInfoDto(story);
//        } else { // 사연이 존재하지 않음
//            return null;
//        }
//    }

    /**
     * 사연 중복 검사
     *
     * @param userSeq
     * @return
     */
    public void isUniqueStory(long userSeq) {
        Optional<Story> optionalStory = storyRepository.findByUserSeqAndStoryReadedFalseOrStoryReadedNull(
                userSeq);

        if (optionalStory.isPresent()) {
            throw new EntityExistsException("중복 사연이 존재합니다.");
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
