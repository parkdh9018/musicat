package com.musicat.data.dto.spotify;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Track {

  private Album album;
  private List<Artist> artists;
  private int disc_number;
  private int duration_ms;
  private boolean explicit;
  private ExternalIds external_ids;
  private ExternalUrls external_urls;
  private String href;
  private String id;
  private boolean is_playable;
  private String name;
  private int popularity;
  private String preview_url;
  private int track_number;
  private String type;
  private String uri;
  private boolean is_local;
}
