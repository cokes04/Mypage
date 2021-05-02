package com.kog.mypage.novel.service;

import com.kog.mypage.novel.entity.User;

import java.util.Optional;

public interface UserService {
    boolean isExistingUser(String email);

    Optional<User> getUserById(Long id);

    Optional<User> getUSerByEmail(String email);

    User joinUser(User user);
}
