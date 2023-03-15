package com.musicat.service;

import com.musicat.data.dto.MusicModifyDto;
import com.musicat.data.dto.MusicInfoDto;
import com.musicat.data.dto.MusicInsertResponseDto;
import com.musicat.data.dto.MusicModifyResponseDto;
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
  @Transactional
  public void deleteMusic(long memberSeq) throws Exception {
    Optional<Music> existingMusicByMember = musicRepository.findByMemberSeqAndMusicIsPlayedFalse(
        memberSeq);
    if (existingMusicByMember.isPresent()) {
      Music music = existingMusicByMember.get();
      musicRepository.deleteById(music.getMusicSeq());
    }
  }

  /**
   * 등록된 노래를 수정합니다. 만약 사용자가 대기열에 노래를 등록하지 않았거나, 바꾸려는 노래가 이미 대기열에 있다면 바꾸지 않습니다.
   *
   * @param musicModifyDto
   * @return musicModifyResponseDto
   * MusicModifyResponseDto.status : 해당 요청의 성공 여부 (0 : 성공, 1 : 신청하지 않음, 2 : 중복 곡)
   * MusicModifyResponseDto.musicInfoDto : status에 맞는 곡 정보
   * MusicModifyResponseDto.playOrder : status에 맞는 곡의 순서
   * @throws Exception
   */
  @Transactional
  public MusicModifyResponseDto modifyMusic(MusicModifyDto musicModifyDto) throws Exception {

    // 검증 로직에 사용할 변수 호출
    String musicName = musicModifyDto.getMusicName();
    String musicArtist = musicModifyDto.getMusicArtist();
    long memberSeq = musicModifyDto.getMemberSeq();

    // 중복 신청 여부 체크
    Optional<Music> existingMusicByMember = musicRepository.findByMemberSeqAndMusicIsPlayedFalse(
        memberSeq);
    if (existingMusicByMember.isPresent()) {
      // 중복 곡 여부 체크
      Optional<Music> existingMusicByNameAndArtist = musicRepository.findByMusicNameAndMusicArtistAndMusicIsPlayedFalse(
          musicName,
          musicArtist);
      if (existingMusicByNameAndArtist.isPresent()) {
        Music music = existingMusicByNameAndArtist.get();
        int playOrder =
            musicRepository.countByMusicSeqLessThanAndMusicIsPlayedFalse(music.getMusicSeq()) + 1;
        return musicBuilderUtil.buildMusicModifyResponseDto(music, 2, playOrder);
      }
      Music music = musicRepository.findById(musicModifyDto.getMemberSeq())
          .orElseThrow(IllegalArgumentException::new);
      int playOrder =
            musicRepository.countByMusicSeqLessThanAndMusicIsPlayedFalse(music.getMusicSeq()) + 1;
      music.setMusicArtist(musicModifyDto.getMusicArtist());
      music.setMusicCover(musicModifyDto.getMusicCover());
      music.setMusicLength(musicModifyDto.getMusicLength());
      music.setMusicGenre(musicModifyDto.getMusicGenre());
      music.setMusicName(musicModifyDto.getMusicName());
      musicRepository.save(music);
      return musicBuilderUtil.buildMusicModifyResponseDto(music, 0, playOrder);
    }
    return musicBuilderUtil.buildMusicModifyResponseDto(null, 1, 0);
  }

  /**
   * 대기열 상위 10개의 노래를 반환합니다.
   * @return musicInfoList
   * @throws Exception
   */
  public List<Music> getMusicInfoList() throws Exception {
    return musicRepository
        .findTop10ByMusicIsPlayedFalseOrderByMusicSeqAsc()
        .orElseThrow(IllegalArgumentException::new);
  }


}
