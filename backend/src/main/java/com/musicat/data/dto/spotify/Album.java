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
public class Album {

  private String album_type;
  private List<Artist> artists;
  private String href;
  private String id;
  private List<Image> images;
  private String name;
  private String release_date;
  private String release_date_precision;
  private String type;
  private String uri;
  private boolean is_playable;
  private int total_tracks;
  private String album_group;
}
