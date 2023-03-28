package com.musicat.controller;

import com.musicat.data.dto.SpotifySearchResultDto;
import com.musicat.data.dto.music.MusicInfoDto;
import com.musicat.data.dto.music.MusicRequestDto;
import com.musicat.data.dto.music.MusicRequestResultDto;
import com.musicat.service.MusicService;
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

    @GetMapping("/")
    public ResponseEntity<List<MusicInfoDto>> getRequestMusic() {

        List<MusicInfoDto> requestMusic = musicService.getMusicInfoList();

        return ResponseEntity.ok(requestMusic);

    }

    @GetMapping("/{musicSeq}")
    public ResponseEntity<MusicInfoDto> getMusic(@PathVariable long musicSeq) {

        MusicInfoDto musicInfoDto = musicService.getMusic(musicSeq);
        return ResponseEntity.ok(musicInfoDto);

    }

    @GetMapping("/search")
    public List<SpotifySearchResultDto> searchMusic(@RequestParam String queryString) {
        return musicService.searchMusic(queryString);
    }

    @GetMapping("/search/youtube")
    public ResponseEntity<?> searchMusicByYoutube(@RequestParam String musicTitle,
            @RequestParam String musicArtist) {
        return ResponseEntity.ok(musicService.searchMusicByYoutube(musicTitle, musicArtist));
    }
}
