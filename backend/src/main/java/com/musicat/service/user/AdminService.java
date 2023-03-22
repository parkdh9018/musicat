package com.musicat.service.user;

import com.musicat.data.dto.notice.NoticeWriteDto;
import com.musicat.data.dto.user.UserListDto;
import com.musicat.data.dto.user.UserModifyBanDto;
import com.musicat.data.entity.notice.Notice;
import com.musicat.data.entity.user.User;
import com.musicat.data.repository.AuthorityRepository;
import com.musicat.data.repository.NoticeRepository;
import com.musicat.data.repository.UserRepository;
import com.musicat.util.ConstantUtil;
import com.musicat.util.NoticeBuilderUtil;
import com.musicat.util.UserBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {


    private final ConstantUtil constantUtil;

    private final UserRepository userRepository;
    private final UserBuilderUtil userBuilderUtil;

    private final NoticeRepository noticeRepository;
    private final NoticeBuilderUtil noticeBuilderUtil;



    // 회원 전체 조회 (관리자)
    // page : 현재 번호, size : 크기
    // page, size를 입력 받아 해당되는 데이터를 반환한다.
    public Page<UserListDto> getUserList(int page ) {
        PageRequest pageable = PageRequest.of(page, constantUtil.USER_PAGE_SIZE);
        Page<User> userListPage = userRepository.findAll(pageable);


        List<UserListDto> userListDtos = userListPage.getContent().stream()
                .map(userBuilderUtil::userToUserListDto)
                .collect(Collectors.toList());

        // 새로운 Page<UserListDto> 객체 생성
        Pageable userListDtoPageable = PageRequest.of(userListPage.getNumber(), userListPage.getSize(), userListPage.getSort());
        Page<UserListDto> userListDtoPage = new PageImpl<>(userListDtos, userListDtoPageable, userListPage.getTotalElements());
        return userListDtoPage;
    }


    // 금지 회원 전체 조회 (관리자)
    public Page<UserListDto> getUserBanList(int page, String isChattingBan, String isBan) {

        boolean userIsChattingBan = Boolean.parseBoolean(isChattingBan);
        boolean userIsBan = Boolean.parseBoolean(isBan);

        PageRequest pageable = PageRequest.of(page, constantUtil.USER_PAGE_SIZE);
        Page<User> userBanListPage = userRepository.findByUserIsChattingBanAndUserIsBan(userIsChattingBan, userIsBan, pageable);

        List<UserListDto> userBanListDto = userBanListPage.getContent().stream()
                .map(userBuilderUtil::userToUserListDto)
                .collect(Collectors.toList());

        // 새로운 Page<UserListDto> 객체 생성
        Pageable userListDtoPageable = PageRequest.of(userBanListPage.getNumber(), userBanListPage.getSize(), userBanListPage.getSort());
        Page<UserListDto> userBanListDtoPage = new PageImpl<>(userBanListDto, userListDtoPageable, userBanListPage.getTotalElements());

        return userBanListDtoPage;
    }


    // 회원 채팅 금지 설정
    public void modifyUserChattingBan(UserModifyBanDto modifyBanDto) {
        User user = userRepository.findById(modifyBanDto.getUserSeq()).orElseThrow(() -> new RuntimeException());
        // 금지된 상태라면 해제
        if (user.isUserIsChattingBan()) {
            user.setUserIsChattingBan(false);
        }
        // 일반 상태라면 금지
        else {
            user.setUserIsChattingBan(true);
        }
    }

    // 회원 활동 금지 설정
    public void modifyUserBan(UserModifyBanDto modifyBanDto) {
        User user = userRepository.findById(modifyBanDto.getUserSeq()).orElseThrow(() -> new RuntimeException());
        // 금지된 상타래면 해제
        if (user.isUserIsBan()) {
            user.setUserIsBan(false);
        }
        // 일반 상태라면 금지
        else {
            user.setUserIsBan(true);
            // 채팅 금지가 일반이면 그것도 밴하기
            user.setUserIsChattingBan(true);
        }
    }


    // 공지사항 작성
    public void writeNotice(NoticeWriteDto noticeWriteDto) {
        User user = userRepository.findById(noticeWriteDto.getUserSeq()).orElseThrow(() -> new RuntimeException());
        Notice notice = noticeBuilderUtil.noticeWriteDtoToNotice(noticeWriteDto, user);

        System.out.println(notice.toString());

        noticeRepository.save(notice);
    }



}
