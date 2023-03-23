package com.musicat.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.stereotype.Component;

@Component
public class ConvertTime {

  public long convertTimeToMillisecond(LocalDateTime localDateTime) {
    if (localDateTime != null) {
      return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    return 0L;
  }

  public long convertDurationToMillis(String duration) {
    java.time.Duration parsedDuration = java.time.Duration.parse(duration);
    return parsedDuration.toMillis();
  }
}
