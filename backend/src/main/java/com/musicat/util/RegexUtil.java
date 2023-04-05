package com.musicat.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class RegexUtil {

  /**
   * '.' 을 제외한 특수기호, 괄호 제거 (정규표현식)
   *
   * @param input
   * @return
   */
  public String removeTextAfterSpecialChar(String input) {

    return input.replaceAll("[(!).*].*", "");

  }
}
