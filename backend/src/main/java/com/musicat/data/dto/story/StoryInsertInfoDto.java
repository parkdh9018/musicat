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
public class StoryInsertInfoDto {

    private long storySeq;

    private long memberSeq;

    private String storyTitle;

    private File storyWav;

    private LocalDateTime storyCreatedAt;

    private boolean storyIsRead;

    private boolean storyIsValid;


}
