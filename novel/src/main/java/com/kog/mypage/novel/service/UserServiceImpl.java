package com.kog.mypage.novel.service;


import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public boolean isExistingUser(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User joinUser(User user) {
        if(isExistingUser(user.getEmail())){
            throw new RuntimeException("ccc");
        }
        return userRepository.save(user);
    }
}
