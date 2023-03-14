package com.musicat.service;

import com.musicat.data.dto.MusicDto;
import com.musicat.data.dto.MusicInfoDto;
import com.musicat.data.dto.MusicInsertResponseDto;
import com.musicat.data.dto.MusicPlayDto;
import com.musicat.data.dto.MusicRequestDto;
import com.musicat.data.entity.Music;
import com.musicat.data.repository.MusicRepository;
import com.musicat.util.MusicBuilderUtil;
import com.musicat.util.ConvertTimeToMillisecond;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MusicService {

  // Utility 정의
  private final MusicBuilderUtil musicBuilderUtil;
  private final ConvertTimeToMillisecond timeConverter;

  // Repository 정의
  private final MusicRepository musicRepository;


  /**
   * 사용자가 신청한 노래를 DB에 저장합니다. 만약 사용자가 이미 곡을 신청했거나, 신청한 곡이 이미 DB에 존재할 경우 저장하지 않습니다.
   *
   * @param musicRequestDto
   * @return musicInsertResponseDto
   * MusicInsertResponseDto.status : 해당 요청의 성공 여부 (0 : 성공, 1 : 중복 신청, 2 : 중복 곡)
   * MusicInsertResponseDto.musicInfoDto : status에 맞는 곡 정보
   * MusicInsertResponseDto.playOrder : status에 맞는 곡의 순서
   * @throws Exception
   */
  @Transactional
  public MusicInsertResponseDto insertMusic(MusicRequestDto musicRequestDto) throws Exception {

    // 검증 로직에 사용할 변수 호출
    String musicName = musicRequestDto.getMusicName();
    String musicArtist = musicRequestDto.getMusicArtist();
    long memberSeq = musicRequestDto.getMemberSeq();

    // 중복 신청 여부 체크
    Optional<Music> existingMusicByMember = musicRepository.findByMemberSeqAndMusicIsPlayedFalse(
        memberSeq);
    if (existingMusicByMember.isPresent()) {
      Music music = existingMusicByMember.get();
      int playOrder =
          musicRepository.countByMusicSeqLessThanAndMusicIsPlayedFalse(music.getMusicSeq()) + 1;
      return musicBuilderUtil.buildMusicInsertResponseDto(music, 1, playOrder);
    }

    // 중복 곡 여부 체크
    Optional<Music> existingMusicByNameAndArtist = musicRepository.findByMusicNameAndMusicArtistAndMusicIsPlayedFalse(
        musicName,
        musicArtist);
    if (existingMusicByNameAndArtist.isPresent()) {
      Music music = existingMusicByNameAndArtist.get();
      int playOrder =
          musicRepository.countByMusicSeqLessThanAndMusicIsPlayedFalse(music.getMusicSeq()) + 1;
      return musicBuilderUtil.buildMusicInsertResponseDto(music, 2, playOrder);
    }

    // 곡 저장
    Music music = musicRepository.save(musicBuilderUtil.buildMusicEntity(musicRequestDto));
    int playOrder =
        musicRepository.countByMusicSeqLessThanAndMusicIsPlayedFalse(music.getMusicSeq()) + 1;
    return musicBuilderUtil.buildMusicInsertResponseDto(music, 0, playOrder);
  }

  /**
   * 노래의 ID(musicSeq)로 노래의 정보를 불러옵니다.
   *
   * @param musicSeq
   * @return musicInfoDto
   * @throws Exception
   */
  public MusicInfoDto getMusic(long musicSeq) throws Exception {
    Music music = musicRepository.findById(musicSeq)
        .orElseThrow(IllegalArgumentException::new);

    return musicBuilderUtil.buildMusicInfoDto(music);
  }

  /**
   * 대기열에 등록된 노래를 제거합니다.
   *
   * @param memberSeq
   * @throws Exception
   */
  public void deleteMusic(long memberSeq) throws Exception {
    Optional<Music> existingMusicByMember = musicRepository.findByMemberSeqAndMusicIsPlayedFalse(
        memberSeq);
    if (existingMusicByMember.isPresent()) {
      Music music = existingMusicByMember.get();
      musicRepository.deleteById(music.getMusicSeq());
    }
  }

  /**
   *
   *
   * @param musicDto
   * @return
   * @throws Exception
   */
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

  /**
   * 대기열 최상단의 노래를 재생 처리하고, 재생 시작 시간을 저장합니다.
   *
   * @return musicPlayDto
   * @throws Exception
   */
  @Transactional
  public MusicPlayDto playMusic() throws Exception {
    int status = 1;
    long musicSeq = 0;
    String message = "신청곡 리스트가 비어 있습니다.";

    Optional<Music> optionalMusic = musicRepository.findTop1ByMusicIsPlayedOrderByMusicSeqAsc(false);
    if (optionalMusic.isPresent()) {
      Music music = optionalMusic.get();
      LocalDateTime time = LocalDateTime.now();

      music.setMusicPlayedMs(timeConverter.convertTimeToMillisecond(time));
      music.setMusicPlayedAt(time);
      music.setMusicIsPlayed(true);
      musicRepository.save(music);

      status = 0;
      musicSeq = music.getMusicSeq();
      message = musicSeq + " 번째 신청곡을 재생합니다. | 재생 시작 시간 : " + time;
    }

    return MusicPlayDto.builder()
        .status(status)
        .musicSeq(musicSeq)
        .message(message)
        .build();
  }


}
