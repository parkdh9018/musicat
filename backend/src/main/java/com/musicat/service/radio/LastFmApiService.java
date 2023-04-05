package com.musicat.service.radio;

import com.musicat.data.dto.music.YoutubeSearchResultDto;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class LastFmApiService {

  private static final Logger logger = LoggerFactory.getLogger(LastFmApiService.class);
  @Value("${lastfm.client.api-key}")
  private String lastFmApiKey;

  private final RestTemplate restTemplate = new RestTemplate();
  private final YoutubeApiService youtubeApiService;

  public Map<String, Object> searchTrack(String artist, String track) {
    String url = "http://ws.audioscrobbler.com/2.0/";

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
        .queryParam("method", "track.search")
        .queryParam("api_key", lastFmApiKey)
        .queryParam("artist", artist)
        .queryParam("track", track)
        .queryParam("format", "json");

    ResponseEntity<Map> response = restTemplate.getForEntity(builder.toUriString(), Map.class);
    logger.debug(response.toString());
    Map<String, Object> results = (Map<String, Object>) response.getBody().get("results");
    logger.debug(results.toString());

    Map<String, Object> trackMatches = (Map<String, Object>) results.get("trackmatches");
    List<Map<String, Object>> tracks = (List<Map<String, Object>>) trackMatches.get("track");
    return tracks.get(0);
  }

  public YoutubeSearchResultDto getYoutubeVideoIdAndLength(String artist, String track) {
    Map<String, Object> trackResult = searchTrack(artist, track);
    logger.debug(trackResult.toString());
    String lastFmTrackUrl = (String) trackResult.get("url");
    String musicYoutubeId = getYoutubeVideoIdFromLastFmPage(lastFmTrackUrl);
    long length = youtubeApiService.findLength(musicYoutubeId);

    YoutubeSearchResultDto result = YoutubeSearchResultDto.builder()
        .musicYoutubeId(musicYoutubeId)
        .musicLength(length)
        .build();
    return result;
  }


  private String getYoutubeVideoIdFromLastFmPage(String lastFmTrackUrl) {
    try {
      Document document = Jsoup.connect(lastFmTrackUrl).get();
      Element youtubeLinkElement = document.selectFirst("a[data-youtube-id]");
      if (youtubeLinkElement != null) {
        String youtubeVideoId = youtubeLinkElement.attr("data-youtube-id");
        return youtubeVideoId;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}