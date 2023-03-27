package com.musicat.service;

import com.musicat.data.dto.SpotifySearchResultDto;
import com.musicat.data.dto.YoutubeSearchResultDto;
import com.musicat.data.dto.story.StoryInfoDto;
import com.musicat.data.dto.story.StoryRequestDto;
import com.musicat.data.entity.Story;
import com.musicat.data.repository.StoryRepository;
import com.musicat.util.ConstantUtil;
import com.musicat.util.StoryBuilderUtil;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final SpotifyApiService spotifyApiService;
    private final YoutubeApiService youtubeApiService;
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
        // 1. Spotify, Youtube API를 사용해서 DB에 반영하기
        String spotifyQuery =
                storyRequestDto.getStoryMusicTitle() + " " + storyRequestDto.getStoryMusicArtist();
        try {
            List<SpotifySearchResultDto> spotifySearchResultDtos = spotifyApiService.searchSpotifyMusicList(
                    spotifyQuery);
            // Todo : spotify 검색 결과가 유효한지 체크하는 로직 재정의 필요
            logger.debug("검색 결과 사이즈 : {}", spotifySearchResultDtos.size());
            if (spotifySearchResultDtos.size() > 0) {
                // a. 스포티파이 검색 결과 -> 커버 이미지 -> DB
                for (SpotifySearchResultDto data : spotifySearchResultDtos) {
                    logger.debug("스포티파이 검색 결과 : {}, {}", data.getMusicTitle(),
                            data.getMusicImage());
                }

                SpotifySearchResultDto spotifyResult = spotifySearchResultDtos.get(0);

                // b. 유튜브 검색 -> 동영상 ID -> DB
                YoutubeSearchResultDto youtubeSearchResultDto = youtubeApiService.findVideo(
                        storyRequestDto.getStoryMusicTitle(),
                        storyRequestDto.getStoryMusicArtist());
                logger.debug("유튜브 검색 결과 : {}", youtubeSearchResultDto);

                if (youtubeSearchResultDto == null) {
                    throw new RuntimeException("유튜브 검색 결과가 없습니다.");
                }

                // c. 스포티파이 + 유튜브 검색 성공시 -> DB
                Story story = storyRepository.save(
                        storyBuilderUtil.buildStoryEntity(storyRequestDto));
                story.setStoryMusicCover(spotifyResult.getMusicImage());
                story.setStoryMusicYoutubeId(youtubeSearchResultDto.getVideoId());
                story.setStoryMusicLength(youtubeSearchResultDto.getMusicLength());

            } else {
                throw new RuntimeException("스포티파이 검색 결과가 없습니다.");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("입출력 예외 발생");
        }

        // 2. 사연 데이터, 신청곡 를 카프카로 전송 -> 파이썬 서버에서 valid 체크 후 DB 반영, 인트로 음성 파일 생성, Reaction 음성 파일 생성, Outro 음성 파일 생성
//        try {
//            // Todo : Topic과 보낼 데이터 재정의 필요
//            kafkaProducerService.send("storyContent", story.getStoryContent());
//            kafkaProducerService.send("musicRequest", story.getStoryMusicTitle());
//        } catch (JsonProcessingException e) {
//            System.err.println(e.getMessage());
//            throw new RuntimeException(카프카 에러);
//        }

    }

//    /**
//     * 사연 신청
//     */
//    @Transactional
//    public StoryInsertResponseDto insertStory(StoryRequestDto storyRequestDto) throws Exception {
//
//        Story story = storyRepository.save(
//                storyBuilderUtil.buildStoryEntity(storyRequestDto)); // 영속화
//        long storySeq = story.getStorySeq();
//
//        int playOrder =
//                storyRepository.countByStorySeqLessThanAndStoryIsReadFalseAndStoryIsValidTrue(
//                        storySeq) + 1;
//
//        StoryInfoDto storyInfoDto = storyBuilderUtil.buildStoryInfoDto(story);
//
//        // 사연 등록 응답 DTO에 값을 담아서 리턴
//        return storyBuilderUtil.buildStoryInsertResponseDto(storyInfoDto, playOrder);
//    }

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
    public boolean isUniqueStory(long userSeq) {
        Optional<Story> optionalStory = storyRepository.findByUserSeqAndStoryReadedFalse(
                userSeq);

        return optionalStory.isPresent();
    }


    /**
     * 사연 상세 조회
     *
     * @param storySeq
     * @return
     */
    public StoryInfoDto getStory(long storySeq) {
        Story story = storyRepository.findById(storySeq)
                .orElseThrow(IllegalArgumentException::new);

        return storyBuilderUtil.buildStoryInfoDto(story);
    }
//
//    /**
//     * 사연 삭제
//     */
//    @Transactional
//    public int deleteStory(long storySeq) throws Exception {
//
//        Optional<Story> optionalStory = storyRepository.findById(storySeq); // 사연 조회
//
//        if (optionalStory.isEmpty()) {
//            return 0; // 사연이 존재하지 않음
//        }
//
//        storyRepository.deleteById(storySeq); // 사연 삭제
//        return 1;
//    }


}
