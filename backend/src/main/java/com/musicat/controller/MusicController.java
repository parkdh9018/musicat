package com.musicat.controller;

import com.musicat.data.dto.MusicDto;
import com.musicat.data.dto.MusicResponseDto;
import com.musicat.data.entity.Music;
import com.musicat.service.MusicService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/music")
@RequiredArgsConstructor
public class MusicController {

  private final MusicService musicService;

  @PostMapping("/request")
  public ResponseEntity<?> insertMusic(@RequestBody MusicDto musicDto) throws Exception {

    Music music = musicService.insertMusic(musicDto);
    MusicResponseDto result = new MusicResponseDto();

    try {

      result.setMusicSeq(music.getMusicSeq());
      result.setSuccess(true);

      return new ResponseEntity<MusicResponseDto>(result, HttpStatus.OK);
    } catch (Exception e) {
      result.setSuccess(false);
      return new ResponseEntity<MusicResponseDto>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  @GetMapping("/")
  public ResponseEntity<?> getRequestMusic() throws Exception {

    List<Music> requestMusic = musicService.getRequestMusic();

    MusicResponseDto result = new MusicResponseDto();

    try {
      result.setSuccess(true);
      return new ResponseEntity<List<Music>>(requestMusic, HttpStatus.OK);
    } catch (Exception e) {
      result.setSuccess(false);
      return new ResponseEntity<MusicResponseDto>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/play")
  public ResponseEntity<?> playMusic() throws Exception {

    Music music = musicService.playMusic();

    MusicResponseDto result = new MusicResponseDto();

    try {
      result.setMusicSeq(music.getMusicSeq());
      result.setSuccess(true);
      return new ResponseEntity<MusicResponseDto>(result, HttpStatus.OK);
    } catch (Exception e) {
      result.setSuccess(false);
      return new ResponseEntity<MusicResponseDto>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

}
