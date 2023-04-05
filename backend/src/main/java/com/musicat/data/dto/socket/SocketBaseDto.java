package com.musicat.data.dto.socket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocketBaseDto<T> {

    private String type;

    private String operation;

    private T data;
}
