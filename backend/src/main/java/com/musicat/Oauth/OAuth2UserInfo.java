package com.musicat.Oauth;

public interface OAuth2UserInfo {

  String getProviderId();

  String getProvider();

  String getEmail();

  String getNickname();

  String getProfileImage();
}
