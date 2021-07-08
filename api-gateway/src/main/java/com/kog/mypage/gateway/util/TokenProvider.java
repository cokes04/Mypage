package com.kog.mypage.gateway.util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RefreshScope
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

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) { // JWT의 기존 signature 검증이 실패 했을 때

        } catch (MalformedJwtException e) { // JWT가 올바르게 구성되지 않을 때

        } catch (ExpiredJwtException e) { // JWT의 유효시간 초과했을 때

        } catch (UnsupportedJwtException e) { // JWT의 형식이 일치 하지 않을 때

        } catch (IllegalArgumentException e) {

        }

        /*
            PrematureJwtException : nbf를 선언했을 경우 토큰 유효 시간전에 사용했을 때
            ClaimJwtException : JWT에서 권한 Claim 검사를 실패했을 때
       */
        return false;
    }
}
