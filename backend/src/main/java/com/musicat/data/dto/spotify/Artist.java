package com.musicat.data.dto.spotify;

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
public class Artist {

  private ExternalUrls external_urls;
  private String href;
  private String id;
  private String name;
  private String type;
  private String uri;
}
