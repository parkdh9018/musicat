package com.musicat.service.user;

import com.musicat.data.dto.notice.NoticeModifyDto;
import com.musicat.data.dto.notice.NoticeWriteDto;
import com.musicat.data.dto.user.UserPageDto;
import com.musicat.data.dto.user.UserModifyBanDto;
import com.musicat.data.entity.notice.Notice;
import com.musicat.data.entity.user.User;
import com.musicat.data.repository.NoticeRepository;
import com.musicat.data.repository.UserRepository;
import com.musicat.service.AlertService;
import com.musicat.util.ConstantUtil;
import com.musicat.util.NoticeBuilderUtil;
import com.musicat.util.UserBuilderUtil;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {


    private final ConstantUtil constantUtil;
    private final UserRepository userRepository;
    private final UserBuilderUtil userBuilderUtil;
    private final NoticeRepository noticeRepository;
    private final AlertService alertService;
    private final NoticeBuilderUtil noticeBuilderUtil;


    // 회원 전체 조회 (관리자)
    public Page<UserPageDto> getUserPage(String nickname, String isChattingBan, String isBan, int page) {

        // 값이 없을 때는 전체 조회를 하기 위해 Optional를 입력값으로 활용

        Optional<String> optionalNickname = Optional.ofNullable(nickname).filter(s -> !s.isEmpty());
        Optional<Boolean> optionalIsChattingBan = Optional.ofNullable(isChattingBan).filter(s -> !s.isEmpty()).map(Boolean::parseBoolean);
        Optional<Boolean> optionalIsBan = Optional.ofNullable(isBan).filter(s -> !s.isEmpty()).map(Boolean::parseBoolean);
        PageRequest pageable = PageRequest.of(page, constantUtil.USER_PAGE_SIZE);

        Page<User> userPage = userRepository.findUsersByNicknameAndIsChattingBanAndIsBan(optionalNickname, optionalIsChattingBan, optionalIsBan, pageable);
        return userPage.map(userBuilderUtil::userToUserPageDto);

    }

    // 회원 채팅 금지 설정
    public void userChattingBan(List<Long> userSeqList) {
        for (Long userSeq : userSeqList) {
            User user = userRepository.findById(userSeq).orElseThrow();
            user.setUserIsChattingBan(true);

            // 채팅 금지 알림
            alertService.insertAlertByAlertType(userSeq, "chattingBan");
        }
    }

    // 회원 채팅 금지 해재
    public void userNotChattingBan(List<Long> userSeqList) {
        for (Long userSeq : userSeqList) {
            User user = userRepository.findById(userSeq).orElseThrow();
            user.setUserIsChattingBan(false);

            // 채팅 금지 해재 알림
            alertService.insertAlertByAlertType(userSeq, "notChattingBan");
        }
    }

    // 회원 활동 금지 설정
    public void userBan(List<Long> userSeqList) {
        for (Long userSeq : userSeqList) {
            User user = userRepository.findById(userSeq).orElseThrow();
            if(!user.isUserIsChattingBan()) {
                user.setUserIsChattingBan(true);
            }
            user.setUserIsBan(true);

            // 활동 금지 알림
            alertService.insertAlertByAlertType(userSeq, "ban");
        }
    }

    // 회원 활동 해제
    public void userNotBan(List<Long> userSeqList) {
        for (Long userSeq : userSeqList) {
            User user = userRepository.findById(userSeq).orElseThrow();
            user.setUserIsBan(false);

            alertService.insertAlertByAlertType(userSeq, "notBan");
        }
    }




    // 공지사항 작성
    public void writeNotice(NoticeWriteDto noticeWriteDto) {
        User user = userRepository.findById(noticeWriteDto.getUserSeq())
                .orElseThrow(() -> new RuntimeException());
        Notice notice = noticeBuilderUtil.noticeWriteDtoToNotice(noticeWriteDto, user);

        System.out.println(notice.toString());

        noticeRepository.save(notice);
    }

    /**
     * 공지사항 수정
     * @param noticeModifyDto
     */
    public void modifyNotice(NoticeModifyDto noticeModifyDto) {
        Notice notice = noticeRepository.findById(noticeModifyDto.getNoticeSeq())
                .orElseThrow(() -> new EntityNotFoundException("공지사항이 존재하지 않습니다."));

        notice.setNoticeTitle(noticeModifyDto.getNoticeTitle());
        notice.setNoticeContent(noticeModifyDto.getNoticeContent());

        noticeRepository.save(notice);
    }

    /**
     * 공지사항 삭제
     * @param noticeSeq
     */
    public void deleteNotice(long noticeSeq) {
        Notice notice = noticeRepository.findById(noticeSeq)
                .orElseThrow(() -> new EntityNotFoundException("공지사항이 존재하지 않습니다."));

        noticeRepository.delete(notice);
    }


}
