package com.musicat.util.builder;


import com.musicat.data.dto.user.*;
import com.musicat.data.entity.user.MoneyLog;
import com.musicat.data.entity.user.User;
import com.musicat.util.ConstantUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserBuilderUtil {

  private final ConstantUtil constantUtil;

  /*
  user -> userDetailDto
   */
  public UserDetailDto userToUserDetailDto(User user) {
    return UserDetailDto.builder()
        .userEmail(user.getUserEmail())
        .userCreatedAt(user.getUserCreatedAt().format(constantUtil.simpleFormatter))
        .build();
  }

  /*
  user -> userMoneyDto
   */
  public UserMoneyDto userToUserMoneyDto(User user) {
    return UserMoneyDto.builder()
        .userMoney(user.getUserMoney())
        .build();
  }

  /*
  user -> userMoneyDto
   */
  public UserUnreadMessageDto userToUserUnreadMessageDto(long userUnreadMessage) {
    return UserUnreadMessageDto.builder()
        .userUnreadMessage(userUnreadMessage)
        .build();
  }

  /*
  user -> userConfigDto
   */
  public UserConfigDto userToUserConfigDto(User user) {
    return UserConfigDto.builder()
        .userIsDarkmode(user.isUserIsDarkmode())
        .backgroundSeq(user.getBackground().getBackgroundSeq())
        .badgeSeq(user.getBadge().getBadgeSeq())
        .themeSeq(user.getTheme().getThemeSeq())
        .build();
  }


  /*
  user -> userPageDto
   */
  public UserPageDto userToUserPageDto(User user) {
    return UserPageDto.builder()
        .userSeq(user.getUserSeq())
        .userNickname(user.getUserNickname())
        .userEmail(user.getUserEmail())
        .userCreatedAt(user.getUserCreatedAt().format(constantUtil.simpleFormatter))
        .userIsChattingBan(user.isUserIsChattingBan())
        .userIsBan(user.isUserIsBan())
        .userIsUser(user.isUserIsUser())
        .build();
  }

  public UserMoneyLogDto moneyLogToUserMoneyLogDto(MoneyLog moneyLog) {
    return UserMoneyLogDto.builder()
        .moneyLogSeq(moneyLog.getMoneyLogSeq())
        .moneyLogType(moneyLog.getMoneyLogType())
        .moneyLogDetail(moneyLog.getMoneyLogDetail())
        .moneyLogChange(moneyLog.getMoneyLogChange())
        .moneyLogCreatedAt(moneyLog.getMoneyLogCreatedAt().format(constantUtil.detailFormatter))
        .build();
  }

    /*
    MoneyLog -> MoneyLogPageDto
     */

  public UserMoneyLogPageDto moneyLogToUserMoneyLogPageDto(MoneyLog moneyLog) {
    return UserMoneyLogPageDto.builder()
        .moneyLogSeq(moneyLog.getMoneyLogSeq())
        .moneyLogType(moneyLog.getMoneyLogType())
        .moneyLogChange(moneyLog.getMoneyLogChange())
        .moneyLogCreatedAt(moneyLog.getMoneyLogCreatedAt().format(constantUtil.simpleFormatter))
        .build();
  }


}
