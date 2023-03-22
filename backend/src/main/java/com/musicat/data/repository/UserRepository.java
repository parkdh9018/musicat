package com.musicat.data.repository;

import com.musicat.data.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findByUserIsChattingBanAndUserIsBan(boolean userIsChattingBan, boolean userIsBan, Pageable pageable);

    Optional<User> findOneByUserId(String userId);


    // 카카오에서 제공한 ID로 회원이 이미 가입되어 있는지 확인
    boolean existsByUserId(String userId);

    // 카카오에서 제공한 ID로 회원이 이미 가입되어 있다면 회원 정보를 반환
    Optional<User> findByUserId(String userId);


}
