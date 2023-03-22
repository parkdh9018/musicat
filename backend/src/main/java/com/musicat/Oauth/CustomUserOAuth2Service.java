package com.musicat.Oauth;


import com.musicat.auth.PrincipalDetails;
import com.musicat.data.entity.user.Authority;
import com.musicat.data.entity.user.User;
import com.musicat.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserOAuth2Service extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(CustomUserOAuth2Service.class);

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

        // Attribute를 파싱해서 공통 객체로 묶는다. 관리가 편함.
        OAuth2UserInfo oAuth2UserInfo = null;

        // kakao naver 구분 하기 위한 provider 변수
        String provider = userRequest.getClientRegistration().getRegistrationId();


        if (provider.equals("kakao")) {
            logger.info("카카오톡 로그인 요청");
            oAuth2UserInfo = new KakaoUserInfo((Map)oAuth2User.getAttributes());
        }


        User user;
        if (userRepository.existsByUserId(oAuth2UserInfo.getProviderId())){
            user = userRepository.findByUserId(oAuth2UserInfo.getProviderId()).orElseThrow();
            logger.info("가입 한적 있음");
        } else {
            Authority authority = Authority.builder()
                    .authorityName("ROLE_USER") // ROLE_ 이런 형식으로 권한을 표시해야한다.
                    .build();

            user = User.builder()
                    .userId(oAuth2UserInfo.getProviderId())
                    .userNickname(oAuth2UserInfo.getNickname())
                    .userProfileImage(oAuth2UserInfo.getProfileImage())
                    .userEmail(oAuth2UserInfo.getEmail())
                    .userAuthority(Collections.singleton(authority)) // 꼭 DB에 ROLE_USER를 먼저 넣기
                    .build();



            System.out.println(user.getUserAuthority().toString());

            userRepository.save(user);
        }
        logger.info("성공");
        logger.info("user , {}", user);
        logger.info("oAuth2User , {}", oAuth2User.getAttributes());

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }


}
