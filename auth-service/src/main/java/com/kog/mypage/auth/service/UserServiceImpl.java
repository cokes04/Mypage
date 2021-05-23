package com.kog.mypage.auth.service;



import com.kog.mypage.auth.entity.User;
import com.kog.mypage.auth.event.JoinedEvent;
import com.kog.mypage.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final ApplicationEventPublisher publisher;

    @Transactional
    @Override
    public boolean isExistingUser(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    @Override
    public User joinUser(User user) {
        if(isExistingUser(user.getEmail())){
            throw new RuntimeException("가입불가");
        }
        User joinUser =  userRepository.save(user);
        publisher.publishEvent(new JoinedEvent(joinUser.getId()));
        return joinUser;
    }
}
