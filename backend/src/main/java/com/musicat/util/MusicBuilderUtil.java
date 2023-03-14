package com.musicat.util;

import com.musicat.data.dto.MusicInfoDto;
import com.musicat.data.dto.MusicInsertResponseDto;
import com.musicat.data.dto.MusicModifyResponseDto;
import com.musicat.data.dto.MusicRequestDto;
import com.musicat.data.entity.Music;
import org.springframework.stereotype.Component;

@Component
public class MusicBuilderUtil {

  public MusicInsertResponseDto buildMusicInsertResponseDto(Music music, int status,
      int playOrder) {
    MusicInfoDto musicInfo = MusicInfoDto.builder()
        .memberSeq(music.getMemberSeq())
        .musicPlayedAt(music.getMusicPlayedAt())
        .musicCreatedAt(music.getMusicCreatedAt())
        .musicName(music.getMusicName())
        .musicArtist(music.getMusicArtist())
        .musicGenre(music.getMusicGenre())
        .musicCover(music.getMusicCover())
        .musicLength(music.getMusicLength())
        .build();
    return MusicInsertResponseDto.builder()
        .status(status)
        .musicInfoDto(musicInfo)
        .playOrder(playOrder)
        .build();
  }

  public MusicModifyResponseDto buildMusicModifyResponseDto(Music music, int status,
      int playOrder) {
    MusicInfoDto musicInfo = MusicInfoDto.builder()
        .memberSeq(music.getMemberSeq())
        .musicPlayedAt(music.getMusicPlayedAt())
        .musicCreatedAt(music.getMusicCreatedAt())
        .musicName(music.getMusicName())
        .musicArtist(music.getMusicArtist())
        .musicGenre(music.getMusicGenre())
        .musicCover(music.getMusicCover())
        .musicLength(music.getMusicLength())
        .build();
    return MusicModifyResponseDto.builder()
        .status(status)
        .musicInfoDto(musicInfo)
        .playOrder(playOrder)
        .build();
  }

  public Music buildMusicEntity(MusicRequestDto musicRequestDto) {
    return Music.builder()
        .memberSeq(musicRequestDto.getMemberSeq())
        .musicName(musicRequestDto.getMusicName())
        .musicArtist(musicRequestDto.getMusicArtist())
        .musicGenre(musicRequestDto.getMusicGenre())
        .musicLength(musicRequestDto.getMusicLength())
        .musicCover(musicRequestDto.getMusicCover())
        .build();
  }

  public MusicInfoDto buildMusicInfoDto(Music music) {
    return MusicInfoDto.builder()
        .memberSeq(music.getMemberSeq())
        .musicPlayedAt(music.getMusicPlayedAt())
        .musicCreatedAt(music.getMusicCreatedAt())
        .musicName(music.getMusicName())
        .musicArtist(music.getMusicArtist())
        .musicGenre(music.getMusicGenre())
        .musicLength(music.getMusicLength())
        .musicCover(music.getMusicCover())
        .build();
  }

}
