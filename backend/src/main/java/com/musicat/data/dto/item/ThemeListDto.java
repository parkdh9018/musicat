package com.musicat.data.dto.item;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThemeListDto {

    private long themeSeq;
    private String themeName;
    private int themeCost;
}
