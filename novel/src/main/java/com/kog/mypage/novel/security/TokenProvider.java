package com.kog.mypage.novel.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TokenProvider {
    @Value("${app.auth.tokenValidSecond}")
    private int validSecond;

    @Value("${app.auth.secretKey}")
    private String secretKey;

    public String createToken(Authentication authentication){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        List<String> authoritys =( (List<GrantedAuthority>) userPrincipal.getAuthorities() ).stream()
                .map(auth -> auth.getAuthority())
                .collect(Collectors.toList());

        Date now = new Date();
        Claims claims = Jwts.claims().setSubject(Long.toString(userPrincipal.getId()));
        claims.put("roles", authoritys);
        claims.setIssuedAt(now);
        claims.setExpiration(new Date(now.getTime() + validSecond));

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public boolean vailidateToken(String token){
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token){return null; }

    public String getToken(HttpServletRequest request){
        return request.getHeader("Authorization");
    }

    public Long getUserId(String token){
        Claims claims =
                Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                        .getBody();

        return Long.parseLong(claims.getSubject());
    }
}
