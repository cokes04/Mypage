package com.kog.mypage.auth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties(prefix = "app")
@RefreshScope
public class AppConfig {

    private final Auth auth = new Auth();

    private final OAuth2 oauth2 = new OAuth2();

    @Getter
    @Setter
    public static class Auth {
        private String secretKey;
        private long tokenValidSecond;
        private String tokenHeaderName;
    }

    @Getter
    @Setter
    public static final class OAuth2 {
        private String authorizedRedirectUris;
    }
}