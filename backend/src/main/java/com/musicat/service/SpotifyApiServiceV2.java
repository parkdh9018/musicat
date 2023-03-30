package com.musicat.service;

import com.musicat.data.dto.SpotifySearchResultDto;
import com.musicat.data.dto.spotify.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class SpotifyApiServiceV2 {

  @Value("${spotify.client.id}")
  private String clientId;

  @Value("${spotify.client.secret}")
  private String clientSecret;

  private String getAccessToken() {
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.setBasicAuth(clientId, clientSecret);

    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "client_credentials");

    HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

    ResponseEntity<AccessTokenResponse> responseEntity = restTemplate.exchange(
        "https://accounts.spotify.com/api/token",
        HttpMethod.POST,
        requestEntity,
        AccessTokenResponse.class);

    return responseEntity.getBody().getAccess_token();
  }

  public List<SpotifySearchResultDto> searchSpotifyMusicList(String query) {
    String accessToken = getAccessToken();
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);

    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

    String encodedQuery;
    try {
      encodedQuery = URLEncoder.encode(query, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Failed to encode the query string.", e);
    }

    ResponseEntity<SearchResponse> responseEntity = restTemplate.exchange(
        "https://api.spotify.com/v1/search?q={query}&type=track&limit=5&market=KR&locale=ko-KR",
        HttpMethod.GET,
        requestEntity,
        SearchResponse.class,
        query);

    System.out.println(responseEntity);
    List<SpotifySearchResultDto> results = new ArrayList<>();
    for (Track track : responseEntity.getBody().getTracks().getItems()) {
      String imageUrl = null;
      List<Image> images = track.getAlbum().getImages();
      if (images != null && !images.isEmpty()) {
        imageUrl = images.get(0).getUrl();
      }

      String musicReleaseDate = track.getAlbum().getRelease_date();

      SpotifySearchResultDto spotifySearchResultDto = SpotifySearchResultDto.builder()
          .musicTitle(track.getName())
          .musicArtist(track.getArtists().get(0).getName()) // Use get() to access the first artist
          .musicAlbum(track.getAlbum().getName())
          .musicImage(imageUrl)
          .musicReleaseDate(musicReleaseDate)
          .build();

      results.add(spotifySearchResultDto);
    }

    return results;
  }
}