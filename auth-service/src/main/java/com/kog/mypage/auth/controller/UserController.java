package com.kog.mypage.auth.controller;

import com.kog.mypage.auth.entity.User;
import com.kog.mypage.auth.payload.response.ApiResponse;
import com.kog.mypage.auth.payload.response.UserProfileResponse;
import com.kog.mypage.auth.util.TokenProvider;
import com.kog.mypage.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import java.util.Optional;

@RequiredArgsConstructor
//@RestController
//@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping
    public ResponseEntity<?> getProfile(@RequestHeader(name="Authorization") String token){
        Long userId = tokenProvider.getUserId(token);
        Optional<User> optionalUser = userService.getUserById(userId);

        if(optionalUser.isPresent()){
            User user = optionalUser.orElseThrow();
            return ResponseEntity.ok().body(UserProfileResponse.of(true, "성공", user));
        }else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body( new ApiResponse(false, "실패"));

    }

}
