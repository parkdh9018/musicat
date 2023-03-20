package com.musicat.service;

import com.musicat.data.entity.Music;
import com.musicat.data.repository.MusicRepository;
import com.musicat.util.ConvertTimeToMillisecond;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MusicSocketService {

  private final MusicRepository musicRepository;

  private final ConvertTimeToMillisecond timeConverter;

  /**
   * 대기열 최상단의 노래를 가져옵니다.
   *
   * @return
   */
  public Music getNextUnplayedSong() {
    Optional<Music> music = musicRepository.findTop1ByMusicIsPlayedFalseOrderByMusicSeqAsc();
    if (music.isPresent())
      return music.get();
    return null;
  }

  /**
   * 가장 최근에 재생 처리된 노래를 가져옵니다.
   *
   * @return music
   */
  public Music getCurrentPlayingMusic() {
    Optional<Music> music = musicRepository.findTop1ByMusicIsPlayedTrueOrderByMusicSeqDesc();
    if (music.isPresent())
      return music.get();
    return null;
  }

  /**
   * 대기열 최상단의 노래를 재생 처리하고, 재생 시작 시간을 저장합니다.
   *
   * @return music
   * @throws Exception
   */
  @Transactional
  public Music playMusic(Music music) throws Exception {

    LocalDateTime time = LocalDateTime.now();
    music.setMusicPlayedMs(timeConverter.convertTimeToMillisecond(time));
    music.setMusicPlayedAt(time);
    music.setMusicIsPlayed(true);
    musicRepository.save(music);

    return music;
  }

}
