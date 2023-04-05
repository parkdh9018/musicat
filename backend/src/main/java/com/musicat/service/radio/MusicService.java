package com.musicat.service.radio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.musicat.data.dto.music.MusicInfoDto;
import com.musicat.data.dto.music.MusicRequestResultDto;
import com.musicat.data.dto.music.MusicRequestDto;
import com.musicat.data.dto.spotify.SpotifySearchResultDto;
import com.musicat.data.dto.music.YoutubeSearchResultDto;
import com.musicat.data.entity.radio.Music;
import com.musicat.data.repository.radio.MusicRepository;
import com.musicat.service.kafka.KafkaProducerService;
import com.musicat.util.builder.MusicBuilderUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MusicService {

  // Utility 정의
  private final MusicBuilderUtil musicBuilderUtil;

  // Repository 정의
  private final MusicRepository musicRepository;

  // Service 정의
  private final SpotifyApiService spotifyApiService;
  private final YoutubeApiService youtubeApiService;
  private final LastFmApiService lastFmApiService;
  private final KafkaProducerService kafkaProducerService;

  /**
   * 사용자가 신청한 노래를 DB에 저장합니다. 만약 사용자가 이미 곡을 신청했거나, 신청한 곡이 이미 DB에 존재할 경우 저장하지 않습니다.
   *
   * @param musicRequestDto
   * @return musicInsertResponseDto
   * MusicInsertResponseDto.status : 해당 요청의 성공 여부 (0 : 성공, 1 : 중복 신청, 2 : 중복 곡, 3 : 재생 가능한 유튜브 노래 없음)
   * MusicInsertResponseDto.musicInfoDto : status에 맞는 곡 정보
   * MusicInsertResponseDto.playOrder : status에 맞는 곡의 순서
   * @throws Exception
   */
  @Transactional
  public MusicRequestResultDto requestMusic(MusicRequestDto musicRequestDto) {

    // 검증 로직에 사용할 변수 호출
    String musicTitle = musicRequestDto.getMusicTitle();
    String musicArtist = musicRequestDto.getMusicArtist();
    long UserSeq = musicRequestDto.getUserSeq();

    // 중복 신청 여부 체크
    Optional<Music> existingMusicByUser = musicRepository.findByUserSeqAndMusicPlayedFalse(
        UserSeq);
    if (existingMusicByUser.isPresent()) {
      Music music = existingMusicByUser.get();
      int playOrder =
          musicRepository.countByMusicSeqLessThanAndMusicPlayedFalse(music.getMusicSeq())
              + 1;
      return musicBuilderUtil.buildMusicRequestResultDto(1, music, playOrder);
    }

    // 중복 곡 여부 체크
    Optional<Music> existingMusicByTitleAndArtist = musicRepository.findByMusicTitleAndMusicArtistAndMusicPlayedFalse(
        musicTitle,
        musicArtist);
    if (existingMusicByTitleAndArtist.isPresent()) {
      Music music = existingMusicByTitleAndArtist.get();
      int playOrder =
          musicRepository.countByMusicSeqLessThanAndMusicPlayedFalse(music.getMusicSeq())
              + 1;
      return musicBuilderUtil.buildMusicRequestResultDto(2, music, playOrder);
    }

    // 곡 저장
    Music music = musicRepository.save(
        musicBuilderUtil.buildMusicEntity(musicRequestDto));

    try {
      kafkaProducerService.send("musicRequest", music);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    int playOrder =
        musicRepository.countByMusicSeqLessThanAndMusicPlayedFalse(music.getMusicSeq()) + 1;
    return musicBuilderUtil.buildMusicRequestResultDto(0, music, playOrder);
  }

  /**
   * 노래의 ID(musicSeq)로 노래의 정보를 불러옵니다.
   *
   * @param musicSeq
   * @return musicInfoDto
   * @throws Exception
   */
  public MusicInfoDto getMusic(long musicSeq) {
    Music music = musicRepository.findById(musicSeq)
        .orElseThrow(IllegalArgumentException::new);

    return musicBuilderUtil.buildMusicInfoDto(music);
  }

  /**
   * 대기열 상위 10개의 노래를 반환합니다.
   *
   * @return musicInfoList
   * @throws Exception
   */
  public List<MusicInfoDto> getMusicInfoList() {
    List<Music> musicList = musicRepository
        .findTop10ByMusicPlayedFalseOrderByMusicSeqAsc()
        .orElseThrow(() -> new EntityNotFoundException("검색 결과가 없습니다."));

    List<MusicInfoDto> result = new ArrayList<>();
    for (Music music : musicList) {
      result.add(musicBuilderUtil.buildMusicInfoDto(music));
    }
    return result;
  }


  /**
   * SpotifyAPI를 이용해 노래를 검색합니다.
   *
   * @param querystring
   * @return spotifySearchResultList
   * @throws Exception
   */
  public List<SpotifySearchResultDto> searchMusic(String querystring) {
    return spotifyApiService.searchSpotifyMusicList(querystring);
  }

    /**
     * Youtube API를 이용해 노래의 정보를 불러옵니다
     *
     * @param musicTitle
     * @param musicArtist
     * @return
     */
  public YoutubeSearchResultDto searchMusicByYoutube(String musicTitle, String musicArtist, long spotifyMusicDuration) {
    return youtubeApiService.findVideo(musicTitle, musicArtist, spotifyMusicDuration);
  }


  /**
   * 음악 신청 중복 여부를 확인합니다
   * @param userSeq
   * @return
   */
  public void isUniqueMusic(long userSeq) {
    Optional<Music> optionalMusic = musicRepository.findByUserSeqAndMusicPlayedFalse(userSeq);
    if (optionalMusic.isPresent()) {
      throw new EntityExistsException("이미 신청한 노래가 있습니다.");
    }
  }

  public Object searchMusicByLastFm(String musicTitle, String musicArtist) {
    return lastFmApiService.getYoutubeVideoIdAndLength(musicArtist, musicTitle);
  }
}
