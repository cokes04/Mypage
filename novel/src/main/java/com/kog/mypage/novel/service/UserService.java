package com.kog.mypage.novel.service;

import com.kog.mypage.novel.entity.User;

public interface UserService {
    boolean isExistingUser(String email);
    User joinUser(User user);
}
