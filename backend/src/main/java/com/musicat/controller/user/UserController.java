package com.musicat.controller.user;


import com.musicat.data.dto.user.UserConfigDto;
import com.musicat.data.dto.user.UserDetailDto;
import com.musicat.data.dto.user.UserModifyRequestDto;
import com.musicat.data.dto.user.UserModifyResponseDto;
import com.musicat.data.dto.user.UserMoneyDto;
import com.musicat.data.dto.user.UserMoneyLogDto;
import com.musicat.data.dto.user.UserMoneyLogPageDto;
import com.musicat.data.dto.user.UserUnreadMessageDto;
import com.musicat.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

  private final UserService userService;

  /**
   * 회원 상세정보 조회
   *
   * @param token
   * @return
   */
  @GetMapping("/detail")
  public ResponseEntity<?> getUserDetail(@RequestHeader("token") String token) {
    UserDetailDto userDetailDto = userService.getUserDetail(token);
    return ResponseEntity.ok(userDetailDto);
  }

  /**
   * 회원 재화 조회
   *
   * @param token
   * @return
   */
  @GetMapping("/money")
  public ResponseEntity<?> getUserMoney(@RequestHeader("token") String token) {
    UserMoneyDto userMoneyDto = userService.getUserMoney(token);
    return ResponseEntity.ok(userMoneyDto);
  }

  /**
   * 회원 안읽은 메세지 수 조회
   *
   * @param token
   * @return
   */
  @GetMapping("/unread-message")
  public ResponseEntity<?> getUserUnreadMessage(@RequestHeader("token") String token) {
    UserUnreadMessageDto userUnreadMessageDto = userService.getUserUnreadMessage(token);
    return ResponseEntity.ok(userUnreadMessageDto);
  }


  /**
   * 회원 설정 조회 (다크모드 + 아이템 3종)
   *
   * @param token
   * @return
   */
  @GetMapping("/config")
  public ResponseEntity<?> getUserConfig(@RequestHeader("token") String token) {
    UserConfigDto userConfigDto = userService.getUserConfig(token);
    return ResponseEntity.ok(userConfigDto);
  }

  /**
   * 회원 재회 내역 조회
   *
   * @param token
   * @param page
   * @return
   */
  @GetMapping("/money-log")
  public ResponseEntity<?> getUserMoneyLog(@RequestHeader("token") String token,
      @RequestParam int page) {
    Page<UserMoneyLogPageDto> userMoneyLogPage = userService.getUserMoneyLog(token, page);
    return ResponseEntity.ok(userMoneyLogPage);
  }

  /**
   * 회원 다크모드 설정
   *
   * @param token
   * @return
   */
  @PutMapping("/darkmode")
  public ResponseEntity<?> modifyDarkmode(@RequestHeader("token") String token) {
    userService.modifyDarkmode(token);
    return ResponseEntity.ok().build();
  }

  /**
   * 회원 정보 수정
   *
   * @param token
   * @param userModifyRequestDto
   * @return
   */
  @PutMapping("/nickname")
  public ResponseEntity<?> modifyUserNickname(@RequestHeader("token") String token,
      @RequestBody UserModifyRequestDto userModifyRequestDto) {
    UserModifyResponseDto userModifyResponseDto = userService.modifyUserNickname(token,
        userModifyRequestDto);
    return ResponseEntity.ok(userModifyResponseDto);
  }

  /**
   * 회원 탈퇴
   *
   * @param token
   * @return
   */
  @DeleteMapping("")
  public ResponseEntity<?> deleteUser(@RequestHeader String token) {
    userService.deleteUser(token);
    return ResponseEntity.ok().build();
  }

  /**
   * 회원 재화 내역 상세 조회
   *
   * @param moneyLogSeq
   * @return
   */
  @GetMapping("/money/detail")
  public ResponseEntity<?> getUserMoneyLogDetail(@RequestParam long moneyLogSeq) {
    UserMoneyLogDto userMoneyLogDetail = userService.getUserMoneyLogDetail(moneyLogSeq);
    return ResponseEntity.ok(userMoneyLogDetail);
  }


}
