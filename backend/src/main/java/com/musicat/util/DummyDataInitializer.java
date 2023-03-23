//package com.musicat.util;
//
//
//import com.musicat.data.entity.user.Authority;
//import com.musicat.data.entity.user.User;
//import com.musicat.data.repository.AuthorityRepository;
//import com.musicat.data.repository.UserRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//
///*
//
//더미데이터를 만드는 class 입니다.
//
// */
//
//@Component
//public class DummyDataInitializer implements CommandLineRunner {
//
//    private final UserRepository userRepository;
//    private final AuthorityRepository authorityRepository;
//
//    public DummyDataInitializer(UserRepository userRepository, AuthorityRepository authorityRepository) {
//        this.userRepository = userRepository;
//        this.authorityRepository = authorityRepository;
//    }
//
//
//
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        Authority admin = Authority.builder()
//                .authorityName("admin")
//                .build();
//
//        Authority user = Authority.builder()
//                .authorityName("user")
//                .build();
//
//        authorityRepository.save(admin);
//        authorityRepository.save(user);
//
//
//        User user1 = User.builder()
//                .userId("user1")
//                .userNickname("admin")
//                .userProfileImage("profile_image_1.png")
//                .userThumbnailImage("thumbnail_image_1.png")
//                .userEmail("user1@email.com")
//                .build();
//
//
//        User user2 = User.builder()
//                .userId("user2")
//                .userNickname("user")
//                .userProfileImage("profile_image_2.png")
//                .userThumbnailImage("thumbnail_image_2.png")
//                .userEmail("user2@email.com")
//                .build();
//
//
//
//        user1.getUserAuthority().add(user);
//        user1.getUserAuthority().add(admin);
//
//        user2.getUserAuthority().add(user);
//
//        userRepository.save(user1);
//        userRepository.save(user2);
//
//
//
//
//
//
//
//
//        // 추가한 더미 데이터를 저장합니다.
//
//    }
//}
