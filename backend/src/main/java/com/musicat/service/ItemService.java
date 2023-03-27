package com.musicat.service;


import com.musicat.data.dto.item.BackgroundListDto;
import com.musicat.data.dto.item.BadgeListDto;
import com.musicat.data.dto.item.ThemeListDto;
import com.musicat.data.entity.item.Background;
import com.musicat.data.entity.item.Badge;
import com.musicat.data.entity.item.Theme;
import com.musicat.data.repository.item.BackgroundRepository;
import com.musicat.data.repository.item.BadgeRepository;
import com.musicat.data.repository.item.ThemeRepository;
import com.musicat.util.ItemBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final BackgroundRepository backgroundRepository;
    private final BadgeRepository badgeRepository;
    private final ThemeRepository themeRepository;
    private final ItemBuilderUtil itemBuilderUtil;

    // 배경 전체 조회
    public List<BackgroundListDto> getBackgroundListDto() {
        List<Background> backgroundList = backgroundRepository.findAll();
        return backgroundList
                .stream()
                .map(itemBuilderUtil::backgroundToBackgroundListDto)
                .collect(Collectors.toList());
    }

    // 뱃지 전체 조회
    public List<BadgeListDto> getBadgeListDto() {
        List<Badge> badgeList = badgeRepository.findAll();
        return badgeList
                .stream()
                .map(itemBuilderUtil::badgeToBadgeListDto)
                .collect(Collectors.toList());
    }

    // 테마 전체 조회
    public List<ThemeListDto> getThemeListDto() {
        List<Theme> themeList = themeRepository.findAll();
        return themeList
                .stream()
                .map(itemBuilderUtil::themeToThemeListDto)
                .collect(Collectors.toList());
    }





}
