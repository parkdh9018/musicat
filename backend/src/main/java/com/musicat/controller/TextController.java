package com.musicat.controller;

import com.musicat.service.PerspectiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/text")
public class TextController {

    private final PerspectiveService perspectiveService;

    @GetMapping("")
    public ResponseEntity<?> textFiltering(@RequestParam String text) {
        System.out.println("요청으로 넘어온 텍스트 : " + text);

        String result = "더티 채팅";

        if (perspectiveService.filterText(text)) {
            result = "클린 채팅";
        }

        return ResponseEntity.ok(result);
    }

}
