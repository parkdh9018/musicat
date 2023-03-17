package com.musicat.data.dto.story;

import java.io.File;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class StoryInfoDto {
    private long storySeq;

    private String storyTitle;

    private long memberSeq;

    private LocalDateTime storyCreatedAt;

    private String storyWavFileDirectoryRoot;

    private boolean storyIsRead;

    private boolean storyIsValid;
}
