package com.musicat.data.dto.story;

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
// Todo : 프론트에서 필요한 데이터 제공 예정
public class StoryInfoDto { // 사연 정보 Dto
//    private long storySeq;
//
//    private long userSeq;

    private LocalDateTime storyCreatedAt;

    private boolean storyReaded;

    private Boolean storyValid;

    private String storyTitle;

//    private String storyContent;

    private String storyMusicTitle;

    private String storyMusicArtist;

    private String storyMusicCover;

    private long storyMusicLength;

    private String storyMusicYoutubeId;

    private String storyReaction;

    private String storyOutro;
}
