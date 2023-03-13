package com.musicat.service;

import com.musicat.data.dto.MusicDto;
import com.musicat.data.dto.MusicResponseDto;
import com.musicat.data.entity.Music;
import com.musicat.data.repository.MusicRepository;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MusicService {

  private final MusicRepository musicRepository;

  public Music insertMusic(MusicDto musicDto) throws Exception {
    Music music = new Music(musicDto);
    Music result = musicRepository.save(music);

    return result;
  }

  public Music getMusic(long musicSeq) throws Exception {
    return musicRepository
        .findById(musicSeq)
        .orElseThrow(IllegalArgumentException::new);
  }

  public List<Music> getAllMusic() throws Exception {
    return musicRepository
        .findAll();
  }

  public Music checkMusicIsExist(String musicName, String musicArtist) throws Exception {
    return musicRepository
        .findByMusicNameAndMusicArtist(musicName, musicArtist)
        .orElseThrow(IllegalArgumentException::new);
  }

  public void deleteMusic(long musicSeq) throws Exception {
    musicRepository.deleteById(musicSeq);
  }

  public Music modifyMusic(MusicDto musicDto) throws Exception {
    Music music = musicRepository
        .findById(musicDto.getMusicSeq())
        .orElseThrow(IllegalArgumentException::new);

    music.setMusicArtist(musicDto.getMusicArtist());
    music.setMusicCover(musicDto.getMusicCover());
    music.setMusicLength(musicDto.getMusicLength());
    music.setMusicGenre(musicDto.getMusicGenre());
    music.setMusicName(musicDto.getMusicName());

    return music;
  }

  public List<Music> getRequestMusic() throws Exception {
    return musicRepository
        .findTop10ByMusicIsPlayedOrderByMusicSeqAsc(false)
        .orElseThrow(IllegalArgumentException::new);
  }

  public Music playMusic() throws Exception {
    Music music = musicRepository
        .findTop1ByMusicIsPlayedOrderByMusicSeqAsc(false)
        .orElseThrow(IllegalArgumentException::new);

    music.setMusicIsPlayed(true);
    music.setMusicPlayedAt(LocalDateTime.now());

    return music;
  }





}
