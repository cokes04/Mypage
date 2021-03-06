package com.kog.mypage.novel.controller;

import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.payload.request.LoginRequest;
import com.kog.mypage.novel.payload.request.SignUpRequest;
import com.kog.mypage.novel.payload.response.ApiResponse;
import com.kog.mypage.novel.payload.response.AuthResponse;
import com.kog.mypage.novel.security.TokenProvider;
import com.kog.mypage.novel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final TokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> joinUser(@Valid @RequestBody SignUpRequest signUpRequest){
            User user = User.builder()
                    .name(signUpRequest.getName())
                    .email(signUpRequest.getEmail())
                    .password( passwordEncoder.encode( signUpRequest.getPassword() ))
                    .build();

            User result = userService.joinUser(user);

            return ResponseEntity.ok(new ApiResponse(true, "User registered successfully"));
    }
}
