package com.musicat.data.dto.story;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoryInsertResponseDto {

    private int status;

    private StoryInfoDto storyInfoDto;

    private int playOrder;

}
