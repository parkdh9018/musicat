package com.musicat.service;

import com.musicat.data.dto.story.StoryInfoDto;
import com.musicat.data.dto.story.StoryInsertResponseDto;
import com.musicat.data.dto.story.StoryRequestDto;
import com.musicat.data.entity.Story;
import com.musicat.data.repository.StoryRepository;
import com.musicat.util.StoryBuilderUtil;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoryService {

    // Utility 정의
    private final StoryBuilderUtil storyBuilderUtil;

    // Repository 정의
    private final StoryRepository storyRepository;

    /**
     * 사용자가 신청한 사연을 DB에 저장합니다. 만약 사용자가 신청한 사연이 큐에 담겨있는 경우 저장하지 않습니다.
     *
     * @return storyInsertResponseDto
     * <p>
     * StoryInsertResponseDto.status         : 해당 요청의 성공 여부 (0 : 성공, 1 : 중복 신청)
     * StoryInsertResponseDto.storyInfoDto   : 비즈니스 로직이 처리된 사연 정보 StoryInsertResponseDto.playOrder :
     * StortyInsertResponseDto.playOrder     : 사연의 순서
     * @Param storyRequestDto
     * @throw Exception
     */

    /**
     * 사연 신청
     */
    @Transactional
    public StoryInsertResponseDto insertStory(StoryRequestDto storyRequestDto) throws Exception {

        Story story = storyRepository.save(
                storyBuilderUtil.buildStoryEntity(storyRequestDto)); // 영속화
        long storySeq = story.getStorySeq();

        int playOrder =
                storyRepository.countByStorySeqLessThanAndStoryIsReadFalseAndStoryIsValidTrue(
                        storySeq) + 1;

        StoryInfoDto storyInfoDto = storyBuilderUtil.buildStoryInfoDto(story);

        // 사연 등록 응답 DTO에 값을 담아서 리턴
        return storyBuilderUtil.buildStoryInsertResponseDto(storyInfoDto, playOrder);
    }

    /**
     * 사연 1개 조회 (읽어야 하는 사연)
     */
    public Object getTopStoryInfo() throws Exception {

        Optional<Story> optionalStory = storyRepository.findTop1ByStoryIsReadFalseAndStoryIsValidTrueOrderByStoryCreatedAt();

        if (optionalStory.isPresent()) { // 사연이 존재함
            Story story = optionalStory.get();

            return storyBuilderUtil.buildStoryInfoDto(story);
        } else { // 사연이 존재하지 않음
            return null;
        }
    }

    /**
     * 사연 중복 검사
     */
    public boolean isUniqueStory(long memberSeq) throws Exception {
        Optional<Story> optionalStory = storyRepository.findByMemberSeqAndStoryIsReadFalse(
                memberSeq);

        return optionalStory.isPresent();
    }

    /**
     * 사연 상세 조회
     */
    public Object getStory(long storySeq) throws Exception {
        Optional<Story> optionalStory = storyRepository.findById(storySeq);

        if (optionalStory.isEmpty()) {
            return null; // 사연이 없음
        }

        Story story = optionalStory.get(); // 사연이 있음

        return storyBuilderUtil.buildStoryInfoDto(story);
    }

    /**
     * 사연 삭제
     */
    @Transactional
    public int deleteStory(long storySeq) throws Exception {

        Optional<Story> optionalStory = storyRepository.findById(storySeq); // 사연 조회

        if (optionalStory.isEmpty()) {
            return 0; // 사연이 존재하지 않음
        }

        storyRepository.deleteById(storySeq); // 사연 삭제
        return 1;
    }


}
