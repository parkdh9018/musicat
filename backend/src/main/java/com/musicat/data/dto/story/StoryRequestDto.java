package com.musicat.data.dto.story;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor// 기본 생성자로는 생성 금지 (빌더 패턴을 통해 값을 무조건 포함하기 위해서)
@AllArgsConstructor
@Builder
public class StoryRequestDto {

    private long memberSeq;

    private String storyTitle;

    private String storyContent;

}
