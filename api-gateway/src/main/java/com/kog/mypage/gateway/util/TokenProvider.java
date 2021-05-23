package com.kog.mypage.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TokenProvider {

    @Value("${app.auth.tokenHeaderName}")
    private String tokenHeaderName;

    @Value("${app.auth.secretKey}")
    private String secretKey;

    public String getToken(HttpRequest request) throws NullPointerException{
        return request.getHeaders().get(tokenHeaderName).get(0);
    }

    public UserInfo getInfo(String token){
        Claims claims =
                Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                        .getBody();

        Long id = Long.parseLong(claims.getSubject());
        String email = claims.get("email", String.class);
        List roles = claims.get("roles", List.class);
        boolean isExpired = claims.getExpiration().before(new Date());

        return new UserInfo(id, email, roles, isExpired);
    }
}
