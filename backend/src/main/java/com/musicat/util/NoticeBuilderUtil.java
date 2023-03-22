package com.musicat.util;

import com.musicat.data.dto.notice.NoticeDetailDto;
import com.musicat.data.dto.notice.NoticeListDto;
import com.musicat.data.dto.notice.NoticeWriteDto;
import com.musicat.data.entity.notice.Notice;
import com.musicat.data.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NoticeBuilderUtil {

    private final ConstantUtil constantUtil;



    // 공지사항 작성할 때 사용
    // NoticeWriteDto -> Notice
    public Notice noticeWriteDtoToNotice(NoticeWriteDto noticeWriteDto, User user) {
        return Notice.builder()
                .user(user)
                .noticeTitle(noticeWriteDto.getNoticeTitle())
                .noticeContent(noticeWriteDto.getNoticeContent())
                .build();

    }


    // List를 만들 때 사용
    // notice -> NoticeListDto
    public NoticeListDto noticeToNoticeListDto(Notice notice) {
        return NoticeListDto.builder()
                .noticeSeq(notice.getNoticeSeq())
                .userNickname(notice.getUser().getUserNickname())
                .noticeTitle(notice.getNoticeTitle())
                .noticeCreatedAt(notice.getNoticeCreatedAt().format(constantUtil.simpleFormatter))
                .build();
    }

    // 공지사항 상세 페이지를 정보를 보낼 때 사용
    // notice -> NoticeDetailDto
    public NoticeDetailDto noticeToNoticeDetailDto(Notice notice) {
        return NoticeDetailDto.builder()
                .noticeSeq(notice.getNoticeSeq())
                .userNickname(notice.getUser().getUserNickname())
                .noticeTitle(notice.getNoticeTitle())
                .noticeContent(notice.getNoticeContent())
                .noticeCreatedAt(notice.getNoticeCreatedAt().format(constantUtil.detailFormatter))
                .noticeUpdatedAt(notice.getNoticeUpdatedAt().format(constantUtil.detailFormatter))
                .build();
    }

}
