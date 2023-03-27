package com.musicat.service.item;


import com.musicat.data.dto.item.*;
import com.musicat.data.dto.user.UserInfoJwtDto;
import com.musicat.data.entity.item.Background;
import com.musicat.data.entity.item.Badge;
import com.musicat.data.entity.item.Theme;
import com.musicat.data.entity.user.MoneyLog;
import com.musicat.data.entity.user.User;
import com.musicat.data.repository.MoneyLogRepository;
import com.musicat.data.repository.UserRepository;
import com.musicat.data.repository.item.BackgroundRepository;
import com.musicat.data.repository.item.BadgeRepository;
import com.musicat.data.repository.item.ThemeRepository;
import com.musicat.jwt.TokenProvider;
import com.musicat.util.ItemBuilderUtil;
import com.musicat.util.MoneyLogBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final UserRepository userRepository;
    private final MoneyLogRepository moneyLogRepository;
    private final MoneyLogBuilderUtil moneyLogBuilderUtil;
    private final BackgroundRepository backgroundRepository;
    private final BadgeRepository badgeRepository;
    private final ThemeRepository themeRepository;
    private final ItemBuilderUtil itemBuilderUtil;
    private final TokenProvider tokenProvider;

    private final String backgroundType = "배경 변경";
    private final String badgeType = "뱃지 변경";
    private final String themeType = "테마 변경";
    


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

    // 배경 변경
    public BackgroundModifyResponseDto modifyBackground(String token, BackgroundModifyRequestDto backgroundModifyDto) {
        UserInfoJwtDto userInfo = tokenProvider.getUserInfo(token);

        User user = userRepository.findById(userInfo.getUserSeq()).orElseThrow();
        Background background = backgroundRepository.findById(backgroundModifyDto.getBackgroundSeq()).orElseThrow();

        String result = "";


        // 이미 설정한 배경을 선택했을 때
        if(user.getBackground().equals(background)) {
            result = "이미 선택하신 배경입니다.";
        }
        // 비용이 높을 때만 바꿀 수 있다.
        else if (user.getUserMoney() >= background.getBackgroundCost()) {
            // 비용 차감
            user.setUserMoney(user.getUserMoney() - background.getBackgroundCost());
            // 거래 내역 저장
            MoneyLog moneyLog = moneyLogBuilderUtil.itemBuyMoneyLog(user, backgroundType, background.getBackgroundName(), background.getBackgroundCost() * -1);
            moneyLogRepository.save(moneyLog);
            // 회원 배경 변경
            user.setBackground(background);

            result = "선택하신 배경으로 변경 되었습니다.";
        }
        else {
            result = "잔액이 부족합니다.";
        }

        return BackgroundModifyResponseDto.builder()
                .result(result)
                .build();
    }

    // 뱃지 변경
    public BadgeModifyResponseDto modifyBadge(String token, BadgeModifyRequestDto badgeModifyRequestDto) {
        UserInfoJwtDto userInfo = tokenProvider.getUserInfo(token);

        User user = userRepository.findById(userInfo.getUserSeq()).orElseThrow();
        Badge badge = badgeRepository.findById(badgeModifyRequestDto.getBadgeSeq()).orElseThrow();

        String result = "";
        if (user.getBadge().equals(badge)) {
            result = "이미 선택하신 뱃지 입니다.";
        }
        // 비용이 높을 때만 바꿀 수 있다.
        else if (user.getUserMoney() >= badge.getBadgeCost()) {
            // 비용 차감
            user.setUserMoney(user.getUserMoney() - badge.getBadgeCost());
            // 거래 내역 저장
            MoneyLog moneyLog = moneyLogBuilderUtil.itemBuyMoneyLog(user, badgeType, badge.getBadgeName(), badge.getBadgeCost() * -1);
            moneyLogRepository.save(moneyLog);
            // 회원 배경 변경
            user.setBadge(badge);

            result = "선택한 뱃지로 변경되었습니다.";
        }
        else {
            result = "잔액이 부족합니다.";
        }

        return BadgeModifyResponseDto.builder()
                .result(result)
                .build();
    }


    // 테마 변경
    public ThemeModifyResponseDto modifyTheme(String token, ThemeModifyRequestDto themeModifyRequestDto) {
        UserInfoJwtDto userInfo = tokenProvider.getUserInfo(token);

        User user = userRepository.findById(userInfo.getUserSeq()).orElseThrow();
        Theme theme = themeRepository.findById(themeModifyRequestDto.getThemeSeq()).orElseThrow();

        String result = "";

        if (user.getTheme().equals(theme)) {
            result = "이미 선택하신 테마 입니다.";
        }
        // 비용이 높을 때만 바꿀 수 있다.
        else if (user.getUserMoney() >= theme.getThemeCost()) {
            // 비용 차감
            user.setUserMoney(user.getUserMoney() - theme.getThemeCost());
            // 거래 내역 저장
            MoneyLog moneyLog = moneyLogBuilderUtil.itemBuyMoneyLog(user, themeType, theme.getThemeName(), theme.getThemeCost() * -1);
            moneyLogRepository.save(moneyLog);
            // 회원 배경 변경
            user.setTheme(theme);

            result = "선택하신 테마로 변경되었습니다.";
        } else {
            result = "잔액이 부족합니다.";
        }

        return ThemeModifyResponseDto.builder()
                .result(result)
                .build();
    }





}
