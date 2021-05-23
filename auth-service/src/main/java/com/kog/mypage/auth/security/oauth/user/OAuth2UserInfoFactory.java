package com.kog.mypage.auth.security.oauth.user;

import com.kog.mypage.auth.entity.AuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes){
        if(registrationId.equals(AuthProvider.google.toString())){
            return new GoogleOAuth2UserInfo(attributes);
        }
        else if(registrationId.equals(AuthProvider.kakao.toString())){
            return  new KakaoOAuth2UserInfo(attributes);
        }
        else{
            throw new RuntimeException("zzz");
        }
    }
}
