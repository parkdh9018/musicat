package com.musicat.data.dto.item;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BadgeListDto {

  private long badgeSeq;
  private String badgeName;
  private int badgeCost;
}
