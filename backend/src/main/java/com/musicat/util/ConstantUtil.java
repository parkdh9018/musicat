package com.musicat.util;

/*

상수를 관리하는 class
 */


import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

// yml 파일로 값을 주입하면 완벽
// @Value("${user.page.size}")


@Component
public class ConstantUtil {

    /**
     * 페이지 변수
     */
    public final int USER_PAGE_SIZE = 10;
    // 공지사항 페이지
    public final int NOTICE_PAGE_SIZE = 10;
    // 알림 페이지
    public final int ALERT_PAGE_SIZE = 10;
    // 재화 내역 페이지
    public final int MONEY_PAGE_SIZE = 5;

    /**
     * 날짜 포멧
     */

    // 2022년 12월 10일 같은 형색
    public DateTimeFormatter simpleFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
    // 2022년 12월 10일 (수) 18:30
    public DateTimeFormatter detailFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 (E) HH:mm", Locale.KOREA);

    // 사연 재생 경로 Todo : EC2 서버에 Intro 음성 파일 경로 저장 예정
    public String STORY_INTRO = "something";

    /**
     * 아이템
     */
    public final long DEFAULT_NUMBER = 1L;

    /**
     * 재화
     */
    public final long TODAY_MONEY = 100;

}
