package com.musicat.service.radio;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.musicat.data.dto.music.YoutubeSearchResultDto;
import com.musicat.util.ConvertTime;
import java.io.IOException;
import java.util.Arrays;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
public class YoutubeApiService {

  private final YouTube youtubeApi;

  private final ConvertTime convertTime;

  public YoutubeSearchResultDto findVideo(String title, String artist, long spotifyMusicDuration) {
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
      YoutubeSearchResultDto result = new YoutubeSearchResultDto();

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
      return result;
    } catch (Exception e) {
      throw new EntityNotFoundException("youtube Video Not Found");
    }
  }

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