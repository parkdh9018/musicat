package com.musicat.data.dto.chat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageKafkaDto {

  private long userSeq; // 사용자

  private String content; // 텍스트 내용
}
