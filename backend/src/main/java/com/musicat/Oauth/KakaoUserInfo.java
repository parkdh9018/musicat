package com.musicat.Oauth;


import java.util.Map;

/*
카카오 유저 정보를 담을 class
 */
public class KakaoUserInfo implements OAuth2UserInfo  {

    private Map<String, Object> attributes;
    private Map<String, Object> attributesAccount;
    private Map<String, Object> attributesProperties;

    // attribute 은 보내주는 정보
    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.attributesAccount = (Map<String, Object>) attributes.get("kakao_account");
        this.attributesProperties = (Map<String, Object>) attributes.get("properties");
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "kakao";
    }


    // 이메일이 없을 경우 추가
    @Override
    public String getEmail() {
        if(attributesAccount.containsKey("email")) {
            return attributesAccount.get("email").toString();
        }
        else {
            return "";
        }
    }

    @Override
    public String getNickname() {
        return attributesProperties.get("nickname").toString();
    }

    @Override
    public String getProfileImage() {
        return attributesProperties.get("profile_image").toString();
    }


}
