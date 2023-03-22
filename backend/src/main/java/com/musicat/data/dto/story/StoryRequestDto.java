package com.musicat.data.dto.story;

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
public class StoryRequestDto {

    private long userSeq;

    private String storyTitle;

    private String storyContent;

    private String storyMusicName;

    private String storyMusicArtist;

}
