package com.musicat.controller.user;


import com.musicat.data.dto.user.*;
import com.musicat.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    // 회원 상세정보 조회
    // 이메일, 생성날짜
    @GetMapping("/detail")
    public ResponseEntity<?> getUserDetail(@RequestHeader("token") String token)  {
        UserDetailDto userDetailDto = userService.getUserDetail(token);
        return ResponseEntity.ok(userDetailDto);
    }

    // 회원 재화 조회
    @GetMapping("/money")
    public ResponseEntity<?> getUserMoney(@RequestHeader("token") String token)  {
        UserMoneyDto userMoneyDto = userService.getUserMoney(token);
        return ResponseEntity.ok(userMoneyDto);
    }

    // 회원 읽지 않은 메세지 수 조회
    @GetMapping("/unread-message")
    public ResponseEntity<?> getUserUnreadMessage(@RequestHeader("token") String token)  {
        UserUnreadMessageDto userUnreadMessageDto = userService.getUserUnreadMessage(token);
        return ResponseEntity.ok(userUnreadMessageDto);
    }


    // 회원 설정 조회 (다크모드 + 아이템 3종)
    @GetMapping("/config")
    public ResponseEntity<?> getUserConfig(@RequestHeader("token") String token)  {
        UserConfigDto userConfigDto = userService.getUserConfig(token);
        return ResponseEntity.ok(userConfigDto);
    }

    // 회원 재화 내역 조회
    @GetMapping("/money-log")
    public ResponseEntity<?> getUserMoneyLog(@RequestHeader("token") String token,
                                             @RequestParam int page)  {
        Page<UserMoneyLogPageDto> userMoneyLogPage = userService.getUserMoneyLog(token, page);
        return ResponseEntity.ok(userMoneyLogPage);
    }

    // 회원 다크모드 설정
    @PutMapping("/darkmode")
    public ResponseEntity<?> modifyDarkmode(@RequestHeader("token") String token)  {
        userService.modifyDarkmode(token);
        return ResponseEntity.ok().build();
    }


    // 회원 정보 수정
    @PutMapping("/nickname")
    public ResponseEntity<?> modifyUserNickname(@RequestHeader("token") String token,
                                                @RequestBody UserModifyRequestDto userModifyRequestDto)  {
        UserModifyResponseDto userModifyResponseDto = userService.modifyUserNickname(token, userModifyRequestDto);
        return ResponseEntity.ok(userModifyResponseDto);
    }

    // 회원 탈퇴
    @DeleteMapping("")
    public ResponseEntity<?> deleteUser(@RequestHeader String token)  {
        userService.deleteUser(token);
        return ResponseEntity.ok().build();
    }
    
    // 회원 재화 내역 상세 조회
    @GetMapping("/money/detail")
    public ResponseEntity<?> getUserMoneyLogDetail(@RequestParam long moneyLogSeq)  {
        UserMoneyLogDto userMoneyLogDetail = userService.getUserMoneyLogDetail(moneyLogSeq);
        return ResponseEntity.ok(userMoneyLogDetail);
    }












}
