package com.musicat.data.dto.story;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class StoryInfoDto { // 사연 정보 Dto
    private long storySeq;

    private long userSeq;

    private LocalDateTime storyCreatedAt;

    private LocalDateTime storyReadAt;

    private boolean storyIsRead;

    private boolean storyIsValid;

    private boolean storyIsReady;

    private String storyTitle;

    private String storyContent;

    private String storyWavFileDirectoryRoot;

    private String storyMusicName;

    private String storyMusicArtist;

    private boolean storyMusicIsPlayed;

    private String storyMusicCover;

    private long storyMusicPlayedMs;

    private long storyMusicLength;

    private String storyMusicYoutubeId;
}
