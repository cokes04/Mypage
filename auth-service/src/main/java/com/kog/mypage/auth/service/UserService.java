package com.kog.mypage.auth.service;

import com.kog.mypage.auth.entity.User;

import java.util.Optional;

public interface UserService {

    boolean isExistingUser(String email);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserById(Long id);

    User joinUser(User user);
}
