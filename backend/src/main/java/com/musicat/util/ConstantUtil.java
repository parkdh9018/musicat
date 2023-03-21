package com.musicat.util;

/*

상수를 관리하는 class
 */


import org.springframework.stereotype.Component;

@Component

public class ConstantUtil {

    // yml 파일로 값을 주입하면 완벽
    //  @Value("${user.page.size}")
    public static final int USER_PAGE_SIZE = 10;

}
