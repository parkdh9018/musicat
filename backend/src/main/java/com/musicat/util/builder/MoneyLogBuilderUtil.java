package com.musicat.util.builder;


import com.musicat.data.entity.user.MoneyLog;
import com.musicat.data.entity.user.User;
import org.springframework.stereotype.Component;

@Component
public class MoneyLogBuilderUtil {


  /*
  아이템을 구매했을 때의 거래 내역
   */
  public MoneyLog buildMoneyLog(User user, String moneyLogType, String moneyLogDetail,
                                long moneyLogChange) {
    return MoneyLog.builder()
        .user(user)
        .moneyLogType(moneyLogType)
        .moneyLogDetail(moneyLogDetail)
        .moneyLogChange(moneyLogChange)
        .build();
  }






}
