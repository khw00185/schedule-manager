package com.example.schedulemanager.user.service;

import com.example.schedulemanager.user.dto.CustomUserDetails;
import com.example.schedulemanager.user.entity.User;
import com.example.schedulemanager.user.exception.InvalidUserIdException;
import com.example.schedulemanager.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new InvalidUserIdException());
        // CustomUserDetails 객체를 반환
        return new CustomUserDetails(user.getId(), user.getPassword());
    }
}
