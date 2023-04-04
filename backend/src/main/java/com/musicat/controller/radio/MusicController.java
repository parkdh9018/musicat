package com.musicat.controller.radio;

import com.musicat.data.dto.spotify.SpotifySearchResultDto;
import com.musicat.data.dto.music.MusicInfoDto;
import com.musicat.data.dto.music.MusicRequestDto;
import com.musicat.data.dto.music.MusicRequestResultDto;
import com.musicat.service.radio.MusicService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/music")
@RequiredArgsConstructor
@CrossOrigin
public class MusicController {

  private final MusicService musicService;

  /**
   * 노래 신청
   *
   * @param musicRequestDto
   * @return
   * @throws Exception
   */
  @PostMapping("/request")
  public ResponseEntity<MusicRequestResultDto> requestMusic(
      @RequestBody MusicRequestDto musicRequestDto) {

    MusicRequestResultDto musicInsertResponseDto = musicService.requestMusic(
        musicRequestDto);
    return ResponseEntity.ok(musicInsertResponseDto);


  }

  /**
   * 신청곡 리스트 10개 조회
   *
   * @return
   */
  @GetMapping("/")
  public ResponseEntity<List<MusicInfoDto>> getRequestMusic() {

    List<MusicInfoDto> requestMusic = musicService.getMusicInfoList();

    return ResponseEntity.ok(requestMusic);
  }

  /**
   * 신청곡 중복 검사
   *
   * @param userSeq
   * @return 200, 409, 500
   */
  @GetMapping("/unique/{userSeq}")
  public ResponseEntity<MusicInfoDto> isUniqueMusic(@PathVariable long userSeq) {
    musicService.isUniqueMusic(userSeq);
    return ResponseEntity.ok().build();
  }

  /**
   * 단일 노래 정보 조회
   *
   * @param musicSeq
   * @return
   */
  @GetMapping("/{musicSeq}")
  public ResponseEntity<MusicInfoDto> getMusic(@PathVariable long musicSeq) {

    MusicInfoDto musicInfoDto = musicService.getMusic(musicSeq);
    return ResponseEntity.ok(musicInfoDto);
  }

  /**
   * 스포티파이 검색
   *
   * @param queryString
   * @return
   */
  @GetMapping("/search")
  public List<SpotifySearchResultDto> searchMusic(@RequestParam String queryString) {
    return musicService.searchMusic(queryString);
  }

  /**
   * 유튜브 영상 정보 검색
   *
   * @param musicTitle
   * @param musicArtist
   * @return
   */
  @GetMapping("/search/youtube")
  public ResponseEntity<?> searchMusicByYoutube(@RequestParam String musicTitle,
      @RequestParam String musicArtist, @RequestParam long spotifyMusicDuration) {
    return ResponseEntity.ok(musicService.searchMusicByYoutube(musicTitle, musicArtist, spotifyMusicDuration));
  }
}
