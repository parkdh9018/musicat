package com.musicat.service.radio;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.musicat.data.dto.music.YoutubeSearchResultDto;
import com.musicat.data.entity.radio.CacheMusic;
import com.musicat.data.repository.radio.CacheMusicRepository;
import com.musicat.util.ConvertTime;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
public class YoutubeApiService {

  private final YouTube youtubeApi;

  private final ConvertTime convertTime;

  private final CacheMusicRepository cacheMusicRepository;
  private static final Logger logger = LoggerFactory.getLogger(YoutubeApiService.class);

  /**
   * 유튜브 영상 찾는 로직
   *
   * @param title
   * @param artist
   * @param spotifyMusicDuration
   * @return
   */
  public YoutubeSearchResultDto findVideo(String title, String artist, long spotifyMusicDuration) {
    YoutubeSearchResultDto result = new YoutubeSearchResultDto();
    Optional<CacheMusic>  optionalCacheMusic = cacheMusicRepository.findByMusicTitleAndMusicArtist(title, artist);
    if (optionalCacheMusic.isPresent()) {
      logger.debug("이미 신청한 적 있는 노래 신청!!");
      CacheMusic cacheMusic = optionalCacheMusic.get();
      result.setMusicLength(cacheMusic.getMusicLength());
      result.setMusicYoutubeId(cacheMusic.getMusicYoutubeId());
      return result;
    }
    String query = title + " " + artist;
    YouTube.Search.List searchRequest;
    try {
      searchRequest = youtubeApi.search().list(Arrays.asList("id", "snippet"));
      searchRequest.setQ(query);
      searchRequest.setType(Arrays.asList("video"));
      searchRequest.setMaxResults(20L);
      searchRequest.setFields(
          "items(id(videoId),snippet(publishedAt,channelId,title,description))");

      SearchListResponse searchResponse = searchRequest.execute();
      List<SearchResult> searchResults = searchResponse.getItems();

      BigInteger maxViews = BigInteger.ZERO;

      for (SearchResult searchResult : searchResults) {
        String musicYoutubeId = searchResult.getId().getVideoId();
        VideoListResponse videoResponse = youtubeApi.videos()
            .list(Arrays.asList("id", "statistics", "contentDetails"))
            .setId(Arrays.asList(musicYoutubeId))
            .execute();

        Video video = videoResponse.getItems().get(0);

        // 재생 시간 검증 (+,- 3초)
        long playTime = convertTime.convertDurationToMillis(
            video.getContentDetails().getDuration());
        if (Math.abs(spotifyMusicDuration - playTime) > 5000) {
          continue;
        }

        // 조회수 가장 많은 동영상 1개 리턴
        BigInteger viewCount = video.getStatistics().getViewCount();
        if (viewCount.compareTo(maxViews) > 0
            && viewCount.compareTo(BigInteger.valueOf(100000)) > 0) {
          maxViews = viewCount;
          result.setMusicYoutubeId(musicYoutubeId);
          result.setMusicLength(
              convertTime.convertDurationToMillis(video.getContentDetails().getDuration()));
        }
      }
      CacheMusic curCacheMusic = CacheMusic.builder()
          .musicTitle(title)
          .musicArtist(artist)
          .musicLength(result.getMusicLength())
          .musicYoutubeId(result.getMusicYoutubeId()).build();
      cacheMusicRepository.save(curCacheMusic);
      return result;
    } catch (Exception e) {
      CacheMusic curCacheMusic = CacheMusic.builder()
          .musicTitle(title)
          .musicArtist(artist)
          .musicLength(0)
          .musicYoutubeId(null).build();
      cacheMusicRepository.save(curCacheMusic);
      throw new EntityNotFoundException("youtube Video Not Found");
    }
  }

  /**
   * LastFM 검색 사용할 때 영상의 길이 구하는 로직
   *
   * @param youtubeVideoId
   * @return
   */
  public long findLength(String youtubeVideoId) {
    try {
      VideoListResponse videoResponse = youtubeApi.videos()
          .list(Arrays.asList("contentDetails"))
          .setId(Arrays.asList(youtubeVideoId))
          .execute();
    Video video = videoResponse.getItems().get(0);
    long length = convertTime.convertDurationToMillis(
            video.getContentDetails().getDuration());
    return length;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}