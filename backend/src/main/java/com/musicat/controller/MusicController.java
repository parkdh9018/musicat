package com.musicat.controller;

import com.musicat.data.dto.MusicInsertResponseDto;
import com.musicat.data.dto.MusicPlayDto;
import com.musicat.data.dto.MusicRequestDto;
import com.musicat.data.dto.MusicResponseDto;
import com.musicat.data.entity.Music;
import com.musicat.service.MusicService;
import com.sun.jdi.request.DuplicateRequestException;
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
  public ResponseEntity<MusicInsertResponseDto> insertMusic(
      @RequestBody MusicRequestDto musicRequestDto)
      throws Exception {

    try {
      MusicInsertResponseDto musicInsertResponseDto = musicService.insertMusic(musicRequestDto);
      return ResponseEntity.ok(musicInsertResponseDto);
    } catch (DuplicateRequestException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
  public ResponseEntity<MusicPlayDto> playMusic() throws Exception {

    MusicPlayDto musicPlayDto = musicService.playMusic();
    try {
      return ResponseEntity.ok(musicPlayDto);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

  }

}
