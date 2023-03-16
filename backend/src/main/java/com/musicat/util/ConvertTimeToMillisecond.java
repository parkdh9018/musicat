package com.musicat.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.stereotype.Component;

@Component
public class ConvertTimeToMillisecond {
  public long convertTimeToMillisecond(LocalDateTime localDateTime) {
    if (localDateTime != null)
      return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    return 0L;
  }
}
