package com.kog.mypage.auth.security.oauth;

import com.kog.mypage.auth.config.AppConfig;
import com.kog.mypage.auth.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;

    private final AppConfig appConfig;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String token = tokenProvider.createToken(authentication);
        response.setHeader(appConfig.getAuth().getTokenHeaderName(), token);

        getRedirectStrategy().sendRedirect(request,response, appConfig.getOauth2().getAuthorizedRedirectUris());
    }

}
