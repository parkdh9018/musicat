package com.musicat.data.dto.story;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class StoryInsertResponseDto {


    private StoryInfoDto storyInfoDto;

    private int playOrder;

}
