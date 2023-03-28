package com.musicat.controller.item;


import com.musicat.data.dto.item.*;
import com.musicat.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
@CrossOrigin
public class ItemController {

    private final ItemService itemService;

    // 배경 전체 조회
    @GetMapping("/background")
    public ResponseEntity<?> getBackground() {
        List<BackgroundListDto> backgroundListDto = itemService.getBackgroundListDto();
        return ResponseEntity.ok(backgroundListDto);
    }

    // 뱃지 전체 조회
    @GetMapping("/badge")
    public ResponseEntity<?> getBadge() {
        List<BadgeListDto> badgeListDto = itemService.getBadgeListDto();
        return ResponseEntity.ok(badgeListDto);
    }

    // 테마 전체 조회
    @GetMapping("/theme")
    public ResponseEntity<?> getTheme() {
        List<ThemeListDto> themeListDto = itemService.getThemeListDto();
        return ResponseEntity.ok(themeListDto);
    }

    // 배경 변경
    @PutMapping("/background")
    public ResponseEntity<?> modifyBackground(@RequestHeader("token") String token,
                                              @RequestBody BackgroundModifyRequestDto backgroundModifyDto) {
        BackgroundModifyResponseDto backgroundModifyResponseDto = itemService.modifyBackground(token, backgroundModifyDto);
        return ResponseEntity.ok(backgroundModifyResponseDto);
    }

    // 뱃지 변경
    @PutMapping("/badge")
    public ResponseEntity<?> modifyBadge(@RequestHeader("token") String token,
                                         @RequestBody BadgeModifyRequestDto badgeModifyRequestDto) {
        BadgeModifyResponseDto badgeModifyResponseDto = itemService.modifyBadge(token, badgeModifyRequestDto);
        return ResponseEntity.ok(badgeModifyResponseDto);
    }

    // 테마 변경
    @PutMapping("/theme")
    public ResponseEntity<?> modifyTheme(@RequestHeader("token") String token,
                                         @RequestBody ThemeModifyRequestDto themeModifyRequestDto) {
        ThemeModifyResponseDto themeModifyResponseDto = itemService.modifyTheme(token, themeModifyRequestDto);
        return ResponseEntity.ok(themeModifyResponseDto);
    }

}
