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
public class Tracks {

  private String href;
  private int limit;
  private String next;
  private int offset;
  private String previous;
  private int total;
  private List<Track> items;

}
