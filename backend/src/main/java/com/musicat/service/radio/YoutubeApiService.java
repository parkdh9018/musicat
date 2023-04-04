package com.musicat.service.radio;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.musicat.data.dto.music.YoutubeSearchResultDto;
import com.musicat.util.ConvertTime;
import java.util.ArrayList;
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

        searchRequest = youtubeApi.search().list(Arrays.asList("id","snippet"));
        searchRequest.setQ(query);
        searchRequest.setType(Arrays.asList("video"));
        searchRequest.setMaxResults(20L);
        searchRequest.setFields("items(id(videoId),snippet(publishedAt,channelId,title,description))");

        SearchListResponse searchResponse = searchRequest.execute();
        List<SearchResult> searchResults = searchResponse.getItems();

        List<String> videoIds = new ArrayList<>();
        for (SearchResult searchResult : searchResults) {
            videoIds.add(searchResult.getId().getVideoId());
        }

        VideoListResponse videoResponse = youtubeApi.videos().list(Arrays.asList("id", "statistics", "contentDetails"))
                .setId(videoIds)
                .execute();
        List<Video> videoList = videoResponse.getItems();

        // 검색 결과 필터링
        BigInteger maxViews = BigInteger.ZERO;
        YoutubeSearchResultDto result = new YoutubeSearchResultDto();
        for (Video video : videoList) {
            // 음악 길이 차이 5초 이내만 검색
            long playTime = convertTime.convertDurationToMillis(video.getContentDetails().getDuration());
            if (Math.abs(spotifyMusicDuration - playTime) > 5000) continue;

            // 최대 시청 횟수가 10만 회 이상인 경우에만 반환
            BigInteger viewCount = video.getStatistics().getViewCount();
            if (viewCount.compareTo(maxViews) > 0 && viewCount.compareTo(BigInteger.valueOf(100000)) > 0) {
                maxViews = viewCount;
                result.setMusicYoutubeId(video.getId());
                result.setMusicLength(convertTime.convertDurationToMillis(video.getContentDetails().getDuration()));
            }
        }
        return result;
    } catch (Exception e) {
        throw new EntityNotFoundException("youtube Video Not Found");
    }
}
}