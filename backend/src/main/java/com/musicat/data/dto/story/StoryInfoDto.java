package com.musicat.data.dto.story;

import java.io.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoryInfoDto {
    private long storySeq;

    private String storyTitle;
}
