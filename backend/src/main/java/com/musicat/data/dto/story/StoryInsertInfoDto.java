package com.musicat.data.dto.story;

import java.io.File;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor// 기본 생성자로는 생성 금지 (빌더 패턴을 통해 값을 무조건 포함하기 위해서)
@AllArgsConstructor
@Builder
public class StoryInsertInfoDto {

    private long storySeq;

    private long memberSeq;

    private String storyTitle;

    private File storyWav;

    private LocalDateTime storyCreatedAt;

    private boolean storyIsRead;

    private boolean storyIsValid;


}
