package com.musicat.service;

import com.musicat.data.dto.story.StoryInfoDto;
import com.musicat.data.dto.story.StoryInsertInfoDto;
import com.musicat.data.dto.story.StoryInsertResponseDto;
import com.musicat.data.dto.story.StoryRequestDto;
import com.musicat.data.entity.Story;
import com.musicat.data.repository.StoryRepository;
import com.musicat.util.StoryBuilderUtil;
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
     * StoryInsertResponseDto.status : 해당 요청의 성공 여부 (0 : 성공, 1 : 중복 신청)
     * StoryInsertResponseDto.storyInfoDto : 비즈니스 로직이 처리된 사연 정보 StoryInsertResponseDto.playOrder :
     * 사연의 순서
     * @Param storyRequestDto
     * @throw Exception
     */

    // 사연 신청 응답 반환
    @Transactional
    public StoryInsertResponseDto insertStory(StoryRequestDto storyRequestDto) throws Exception {

        // 임의의 값
        int status = 1;
        int playOrder = 1;

        Story story = storyRepository.save(storyBuilderUtil.buildStoryEntity(storyRequestDto));
        Long storySeq = story.getStorySeq();

        StoryInfoDto storyInfoDto = StoryInfoDto.builder()
                .storyTitle(storyRequestDto.getStoryTitle())
                .storySeq(storySeq)
                .build();

        return StoryInsertResponseDto.builder()
                .status(status)
                .playOrder(playOrder)
                .storyInfoDto(storyInfoDto)
                .build();
    }


}
