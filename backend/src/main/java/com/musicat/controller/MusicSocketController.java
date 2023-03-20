package com.musicat.controller;

import com.musicat.data.entity.Music;
import com.musicat.service.MusicSocketService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MusicSocketController {

  @Autowired
  private MusicSocketService musicSocketService;

  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;

  @Scheduled(fixedRate = 1000)
  public void playMusic() throws Exception {
    Music currentPlayingMusic = musicSocketService.getCurrentPlayingMusic();

    if (currentPlayingMusic != null) {
      long currentTimeMs = System.currentTimeMillis();
      long playedTimeMs = currentTimeMs - currentPlayingMusic.getMusicPlayedMs();

      if (playedTimeMs >= currentPlayingMusic.getMusicLength()) {
        Music nextSong = musicSocketService.getNextUnplayedSong();
        if (nextSong != null) {
          Music playMusic = musicSocketService.playMusic(nextSong);
          sendMusicInfoToSubscribers(playMusic);
        }
      }
    } else {
      Music nextSong = musicSocketService.getNextUnplayedSong();
      if (nextSong != null) {
        Music playMusic = musicSocketService.playMusic(nextSong);
        sendMusicInfoToSubscribers(playMusic);
      }
    }
  }

  @SubscribeMapping("/topic/music")
  public Map<String, Object> subscribeToMusic() {
    Music currentPlayingMusic = musicSocketService.getCurrentPlayingMusic();

    Map<String, Object> musicInfo = new HashMap<>();
    if (currentPlayingMusic != null) {
      long currentTimeMs = System.currentTimeMillis();
      long playedTimeMs = currentTimeMs - currentPlayingMusic.getMusicPlayedMs();
      musicInfo.put("music", currentPlayingMusic);
      musicInfo.put("playedTime", playedTimeMs);
    } else {
      musicInfo.put("message", "There are no songs in the queue");
    }

    return musicInfo;
  }

  @MessageMapping("/music")
  public void handleMusicRequest() {
    Music currentPlayingMusic = musicSocketService.getCurrentPlayingMusic();

    Map<String, Object> musicInfo = new HashMap<>();
    if (currentPlayingMusic != null) {
      long currentTimeMs = System.currentTimeMillis();
      long playedTimeMs = currentTimeMs - currentPlayingMusic.getMusicPlayedMs();
      if (playedTimeMs < currentPlayingMusic.getMusicLength()) {
        musicInfo.put("music", currentPlayingMusic);
        musicInfo.put("playedTime", playedTimeMs);
      } else {
        musicInfo.put("message", "There are no songs in the queue");
      }
    } else {
      musicInfo.put("message", "There are no songs in the queue");
    }

    simpMessagingTemplate.convertAndSend("/topic/music", musicInfo);
  }

  private void sendMusicInfoToSubscribers(Music music) {
    Map<String, Object> musicInfo = new HashMap<>();
    if (music != null) {
      musicInfo.put("music", music);
      musicInfo.put("playedTime", 0L);
    } else {
      musicInfo.put("message", "No music playing");
    }
    simpMessagingTemplate.convertAndSend("/topic/music", musicInfo);
  }


}
