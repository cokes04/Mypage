package com.kog.mypage.auth.util;

import com.kog.mypage.auth.config.AppProperties;
import com.kog.mypage.auth.entity.User;
import com.kog.mypage.auth.security.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Component
public class TokenProvider {

    private final AppProperties appProperties;

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();


        Date now = new Date();
        Claims claims = Jwts.claims().setSubject(Long.toString(userPrincipal.getId()));
        claims.put("email", userPrincipal.getEmail());
        claims.put("roles",
                userPrincipal.getAuthorities().stream()
                        .map( i -> i.getAuthority().replace("ROLE_", ""))
                        .collect(Collectors.toList())

        );
        claims.setExpiration(new Date(now.getTime() + appProperties.getAuth().getTokenValidSecond()));


        return getToken(claims);
    }


    public String createToken(User user){
        Date now = new Date();
        Claims claims = Jwts.claims().setSubject(Long.toString(user.getId())); //
        claims.put("email", user.getEmail());
        claims.put("roles", user.getRoleList());
        claims.setExpiration(new Date(now.getTime() + appProperties.getAuth().getTokenValidSecond()));

        return getToken(claims);
    }

    private String getToken(Claims claims){
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getSecretKey())
                .compact();
    }

    public Long getUserId(String token){
        Claims claims =
                Jwts.parser()
                .setSigningKey(appProperties.getAuth().getSecretKey())
                .parseClaimsJws(token)
                        .getBody();

        return Long.parseLong(claims.getSubject());
    }
}
