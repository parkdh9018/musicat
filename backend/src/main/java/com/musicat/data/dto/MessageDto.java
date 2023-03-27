package com.musicat.data.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MessageDto {

    private String sender;

    private String receiver;

    private String content;


}

