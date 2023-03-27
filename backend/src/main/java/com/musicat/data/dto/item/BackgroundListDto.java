package com.musicat.data.dto.item;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BackgroundListDto {
    private long backgroundSeq;
    private String backgroundName;
    private int backgroundCost;
}
