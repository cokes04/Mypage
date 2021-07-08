package com.kog.mypage.auth.controller;

import com.kog.mypage.auth.entity.User;
import com.kog.mypage.auth.util.TokenProvider;
import com.kog.mypage.auth.payload.request.LoginRequest;
import com.kog.mypage.auth.payload.request.SignUpRequest;
import com.kog.mypage.auth.payload.response.ApiResponse;
import com.kog.mypage.auth.payload.response.AuthResponse;
import com.kog.mypage.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@RefreshScope
public class AuthController {

    private final UserService userService;

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    @Value("${aaa}")
    private String aaa;

    @GetMapping("/aaa")
    public String aaaaaaaa(){
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println(aaa );
        return aaa;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        }catch (BadCredentialsException e){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "실패"));
        }

        String token = tokenProvider.createToken(authentication);

        return ResponseEntity.ok()
                .body(new AuthResponse(true, "성공", token));
    }


    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> joinUser(@Validated @RequestBody SignUpRequest signUpRequest){
        User user = User.builder()
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .roles("USER")
                .build();

        try {
            User result = userService.joinUser(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse(false, "실패"));
        }

        return ResponseEntity.ok()
                .body(new ApiResponse(true, "성공"));
    }
}
