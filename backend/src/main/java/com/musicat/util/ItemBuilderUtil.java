package com.musicat.util;


import com.musicat.data.dto.item.BackgroundListDto;
import com.musicat.data.dto.item.BadgeListDto;
import com.musicat.data.dto.item.ThemeListDto;
import com.musicat.data.entity.item.Background;
import com.musicat.data.entity.item.Badge;
import com.musicat.data.entity.item.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemBuilderUtil {

    /*
    background -> backgroundListDto
     */

    public BackgroundListDto backgroundToBackgroundListDto(Background background) {
        return BackgroundListDto.builder()
                .backgroundSeq(background.getBackgroundSeq())
                .backgroundName(background.getBackgroundName())
                .backgroundCost(background.getBackgroundCost())
                .build();
    }

    /*
    badge -> badgeListDto
     */

    public BadgeListDto badgeToBadgeListDto(Badge badge) {
        return BadgeListDto.builder()
                .badgeSeq(badge.getBadgeSeq())
                .badgeName(badge.getBadgeName())
                .badgeCost(badge.getBadgeCost())
                .build();
    }


        /*
    theme -> themeListDto
     */

    public ThemeListDto themeToThemeListDto(Theme theme) {
        return ThemeListDto.builder()
                .themeSeq(theme.getThemeSeq())
                .themeName(theme.getThemeName())
                .themeCost(theme.getThemeCost())
                .build();
    }




}
