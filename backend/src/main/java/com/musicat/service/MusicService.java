package com.musicat.service;

import com.musicat.data.dto.music.MusicInfoDto;
import com.musicat.data.dto.music.MusicRequestResultDto;
import com.musicat.data.dto.music.MusicRequestDto;
import com.musicat.data.dto.SpotifySearchResultDto;
import com.musicat.data.dto.YoutubeSearchResultDto;
import com.musicat.data.entity.Music;
import com.musicat.data.repository.MusicRepository;
import com.musicat.util.MusicBuilderUtil;
import com.musicat.util.ConvertTime;
import java.util.ArrayList;
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
  private final ConvertTime timeConverter;

  // Repository 정의
  private final MusicRepository musicRepository;

  private final SpotifyApiService spotifyApiService;

  private final YoutubeApiService youtubeApiService;

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
  public MusicRequestResultDto requestMusic(MusicRequestDto musicRequestDto) throws Exception {

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
          musicRepository.countByMusicSeqLessThanAndMusicPlayedFalse(music.getMusicSeq()) + 1;
      return musicBuilderUtil.buildMusicRequestResultDto(1, music, playOrder);
    }

    // 중복 곡 여부 체크
    Optional<Music> existingMusicByTitleAndArtist = musicRepository.findByMusicTitleAndMusicArtistAndMusicPlayedFalse(
        musicTitle,
        musicArtist);
    if (existingMusicByTitleAndArtist.isPresent()) {
      Music music = existingMusicByTitleAndArtist.get();
      int playOrder =
          musicRepository.countByMusicSeqLessThanAndMusicPlayedFalse(music.getMusicSeq()) + 1;
      return musicBuilderUtil.buildMusicRequestResultDto(2, music, playOrder);
    }

    // 유튜브 곡 검색
    YoutubeSearchResultDto youtubeSearchResult = youtubeApiService.findVideo(musicTitle, musicArtist);
    if (youtubeSearchResult == null) {
      return musicBuilderUtil.buildMusicRequestResultDto(3, null, -1);
    }

    // 곡 저장
    Music music = musicRepository.save(musicBuilderUtil.buildMusicEntity(musicRequestDto, youtubeSearchResult));

    kafkaProducerService.send("musicRequest", music);

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
  public MusicInfoDto getMusic(long musicSeq) throws Exception {
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
  public List<MusicInfoDto> getMusicInfoList() throws Exception {
    Optional<List<Music>> OptionalMusicList = musicRepository
        .findTop10ByMusicPlayedFalseOrderByMusicSeqAsc();
    if (OptionalMusicList.isPresent()) {
      List<Music> musicList = OptionalMusicList.get();
      List<MusicInfoDto> result = new ArrayList<>();
      for (Music music : musicList) {
        result.add(musicBuilderUtil.buildMusicInfoDto(music));
      }
      return result;
    } else {
      throw new Exception();
    }
  }


  /**
   * SpotifyAPI를 이용해 노래를 검색합니다.
   *
   * @param querystring
   * @return spotifySearchResultList
   * @throws Exception
   */
  public List<SpotifySearchResultDto> searchMusic(String querystring) throws Exception {
        return spotifyApiService.searchSpotifyMusicList(querystring);
    }
}
