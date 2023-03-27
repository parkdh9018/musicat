package com.musicat.controller.item;


import com.musicat.data.dto.item.BackgroundListDto;
import com.musicat.data.dto.item.ThemeListDto;
import com.musicat.service.ItemService;
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
        List<BackgroundListDto> backgroundListDto = itemService.getBackgroundListDto();
        return ResponseEntity.ok(backgroundListDto);
    }

    // 테마 전체 조회
    @GetMapping("/theme")
    public ResponseEntity<?> getTheme() {
        List<ThemeListDto> themeListDto = itemService.getThemeListDto();
        return ResponseEntity.ok(themeListDto);
    }
}
