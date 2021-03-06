package com.kog.mypage.novel.security.oauth;


import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.entity.enumerate.AuthProvider;
import com.kog.mypage.novel.repository.UserRepository;
import com.kog.mypage.novel.security.UserPrincipal;
import com.kog.mypage.novel.security.oauth.user.OAuth2UserInfo;
import com.kog.mypage.novel.security.oauth.user.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    public final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
           OAuth2User oAuth2User =  super.loadUser(userRequest);
           OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(userRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());


        Optional<User> optionalUser = userRepository.findByEmail(oAuth2UserInfo.getEmail());

        User user;
        if(optionalUser.isPresent()){
            user = optionalUser.get();
            if(!user.getAuthProvider().equals(AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId()))){
                throw new RuntimeException("xxx");
            }
            updateExistingUser(user, oAuth2UserInfo);
        }
            else{
            user = joinNewUser(userRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private User joinNewUser(OAuth2UserRequest userRequest, OAuth2UserInfo oAuth2UserInfo){
            User user = User.builder()
                    .email(oAuth2UserInfo.getEmail())
                    .name(oAuth2UserInfo.getName())
                    .providerId(oAuth2UserInfo.getId())
                    .authProvider(AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId()))
                            .build();
            return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo){

        return userRepository.save(existingUser);
    }
}
