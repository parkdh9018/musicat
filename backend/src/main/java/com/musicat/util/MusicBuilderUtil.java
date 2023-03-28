package com.musicat.util;

import com.musicat.data.dto.music.MusicInfoDto;
import com.musicat.data.dto.music.MusicRequestResultDto;
import com.musicat.data.dto.music.MusicRequestDto;
import com.musicat.data.dto.YoutubeSearchResultDto;
import com.musicat.data.entity.Music;
import org.springframework.stereotype.Component;

@Component
public class MusicBuilderUtil {

  public MusicRequestResultDto buildMusicRequestResultDto(int status, Music music,
      int playOrder) {
    return MusicRequestResultDto.builder()
        .status(status)
        .musicInfo(music == null ? null : buildMusicInfoDto(music))
        .playOrder(playOrder)
        .build();
  }

  public Music buildMusicEntity(MusicRequestDto musicRequestDto) {
    return Music.builder()
        .userSeq(musicRequestDto.getUserSeq())
        .musicTitle(musicRequestDto.getMusicTitle())
        .musicArtist(musicRequestDto.getMusicArtist())
        .musicAlbum(musicRequestDto.getMusicAlbum())
        .musicImage(musicRequestDto.getMusicImage())
        .musicYoutubeId(musicRequestDto.getMusicYoutubeId())
        .musicLength(musicRequestDto.getMusicLength())
        .musicIntro(null)
        .musicOutro(null)
        .musicReleaseDate(musicRequestDto.getMusicReleaseDate())
        .build();
  }

  public MusicInfoDto buildMusicInfoDto(Music music) {
    return MusicInfoDto.builder()
        .musicSeq(music.getMusicSeq())
        .userSeq(music.getUserSeq())
        .musicTitle(music.getMusicTitle())
        .musicArtist(music.getMusicArtist())
        .musicAlbum(music.getMusicAlbum())
        .musicImage(music.getMusicImage())
        .musicYoutubeId(music.getMusicYoutubeId())
        .musicLength(music.getMusicLength())
        .musicReleaseDate(music.getMusicReleaseDate())
        .musicCreatedAt(music.getMusicCreatedAt())
        .musicIsPlayed(music.isMusicPlayed())
        .build();
  }

}
