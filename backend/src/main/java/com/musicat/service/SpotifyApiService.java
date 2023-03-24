package com.musicat.service;

import com.musicat.data.dto.SpotifySearchResultDto;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.albums.GetAlbumRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotifyApiService {

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    private SpotifyApi getSpotifyApi() {
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();

        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
        try {
            ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
        } catch (IOException | SpotifyWebApiException e) {
            throw new RuntimeException("Error obtaining Spotify access token", e);
        } catch (ParseException e) {
          throw new RuntimeException(e);
        }

      return spotifyApi;
    }

    public List<SpotifySearchResultDto> searchSpotifyMusicList(String query) throws IOException {
    SpotifyApi spotifyApi = getSpotifyApi();

    SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks(query).build();
    List<SpotifySearchResultDto> results = new ArrayList<>();

    try {
        Paging<Track> trackPaging = searchTracksRequest.execute();
        Track[] tracks = trackPaging.getItems();

        for (Track track : tracks) {
            String imageUrl = null;
            Image[] images = track.getAlbum().getImages();

            if (images != null && images.length >= track.getDiscNumber()) {
                imageUrl = images[track.getDiscNumber()].getUrl();
            }

            // Get additional album information
            GetAlbumRequest getAlbumRequest = spotifyApi.getAlbum(track.getAlbum().getId()).build();
            Album album = getAlbumRequest.execute();

            String musicGenre = album.getGenres().length > 0 ? album.getGenres()[0] : "";
            String musicReleaseDate = album.getReleaseDate();

            SpotifySearchResultDto spotifySearchResultDto = SpotifySearchResultDto.builder()
                    .musicTitle(track.getName())
                    .musicArtist(track.getArtists()[0].getName())
                    .musicAlbum(track.getAlbum().getName())
                    .musicImage(imageUrl)
                    .musicGenre(musicGenre)
                    .musicReleaseDate(musicReleaseDate)
                    .build();

            results.add(spotifySearchResultDto);
        }
    } catch (SpotifyWebApiException e) {
        throw new IOException("Error searching tracks on Spotify API", e);
    } catch (ParseException e) {
        throw new RuntimeException(e);
    }

    return results;
}
}