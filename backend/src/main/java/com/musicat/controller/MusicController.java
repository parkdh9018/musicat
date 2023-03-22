package com.musicat.controller;

import com.musicat.data.dto.MusicInfoDto;
import com.musicat.data.dto.MusicInsertResponseDto;
import com.musicat.data.dto.MusicRequestDto;
import com.musicat.data.entity.Music;
import com.musicat.service.MusicService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/music")
@RequiredArgsConstructor
@CrossOrigin
public class MusicController {

  private final MusicService musicService;

  @PostMapping("/request")
  public ResponseEntity<MusicInsertResponseDto> insertMusic(
      @RequestBody MusicRequestDto musicRequestDto)
      throws Exception {
    try {
      MusicInsertResponseDto musicInsertResponseDto = musicService.insertMusic(musicRequestDto);
      return ResponseEntity.ok(musicInsertResponseDto);
//    } catch (DuplicateRequestException e) {
//      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

  }

  @GetMapping("/")
  public ResponseEntity<List<Music>> getRequestMusic() throws Exception {

    List<Music> requestMusic = musicService.getMusicInfoList();

    try {
      return ResponseEntity.ok(requestMusic);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

//  @GetMapping("/play")
//  public ResponseEntity<MusicPlayDto> playMusic() throws Exception {
//
//    MusicPlayDto musicPlayDto = musicService.playMusic();
//    try {
//      return ResponseEntity.ok(musicPlayDto);
//    } catch (Exception e) {
//      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//    }
//
//  }

  @DeleteMapping("/{memberSeq}")
  public ResponseEntity deleteMusic(@PathVariable long memberSeq) throws Exception {
    try {
      musicService.deleteMusic(memberSeq);
      return ResponseEntity.status(HttpStatus.OK).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/{musicSeq}")
  public ResponseEntity<MusicInfoDto> getMusic(@PathVariable long musicSeq) throws Exception {

    try {
      MusicInfoDto musicInfoDto = musicService.getMusic(musicSeq);
      return ResponseEntity.ok(musicInfoDto);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

}
