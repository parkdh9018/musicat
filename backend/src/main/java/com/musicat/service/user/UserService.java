package com.musicat.service.user;


import com.musicat.data.dto.user.UserDetailDto;
import com.musicat.data.dto.user.UserListDto;
import com.musicat.data.entity.user.User;
import com.musicat.data.repository.AuthorityRepository;
import com.musicat.data.repository.UserRepository;
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


/*

유저의 비즈니스 로직을 처리하는 class

*/

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;
    private final UserBuilderUtil userBuilderUtil;


    // 회원 상세 조회
    public UserDetailDto getUserDetail(long userSeq)   {
        User user = userRepository
                .findById(userSeq)
                .orElseThrow(() -> new RuntimeException("User not found with userSeq: " + userSeq));
        return userBuilderUtil.userToUserDetailDto(user);
    }

    // 회원 전체 조회 (관리자)
    // page : 현재 번호, size : 크기
    // page, size를 입력 받아 해당되는 데이터를 반환한다.
    public Page<UserListDto> getUserList(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);

        List<UserListDto> userListDtos = userPage.getContent().stream()
                .map(userBuilderUtil::userToUserListDto)
                .collect(Collectors.toList());

        // 새로운 Page<UserListDto> 객체 생성
        Pageable userListDtoPageable = PageRequest.of(userPage.getNumber(), userPage.getSize(), userPage.getSort());
        Page<UserListDto> userListDtoPage = new PageImpl<>(userListDtos, userListDtoPageable, userPage.getTotalElements());
        return userListDtoPage;
    }

    // 금지 회원 전체 조회 (관리자)
    public Page<UserListDto> getUserBanList(int page, String chattingban, String ban) {

    }


    // 회원 정보 수정 (닉네임 변경)





}
