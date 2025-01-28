package com.example.schedulemanager.user.service;

import com.example.schedulemanager.user.dto.CustomUserDetails;
import com.example.schedulemanager.user.dto.UserLoginRequestDto;
import com.example.schedulemanager.user.dto.UserRequestDto;
import com.example.schedulemanager.user.dto.UserResponseDto;
import com.example.schedulemanager.user.entity.User;
import com.example.schedulemanager.user.jwt.JWTUtil;
import com.example.schedulemanager.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JWTUtil jwtUtil;


    @Override
    public UserResponseDto register(UserRequestDto dto) {
        if(userRepository.findById(dto.getId()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 ID입니다.");
        }

        String encryptedPassword = passwordEncoder.encode(dto.getPassword());

        User user = new User(dto.getId(), dto.getUserName(), dto.getEmail(), encryptedPassword,
                java.time.LocalDateTime.now(), java.time.LocalDateTime.now());

        userRepository.saveUser(user);

        String token = jwtUtil.createJwt(user.getId(), 60 * 60 * 10L);
        UserResponseDto responseDto = new UserResponseDto(user.getId(), user.getUserName(), user.getEmail(),
                token, user.getCreatedAt(), user.getUpdatedAt());

        return responseDto;
    }

    @Override
    public UserResponseDto login(UserLoginRequestDto dto) {

        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀립니다.");
        }

        String token = jwtUtil.createJwt(user.getId(), 60 * 60 * 2 * 1000L);
        UserResponseDto responseDto = new UserResponseDto(user.getId(), user.getUserName(), user.getEmail(),
                token, user.getCreatedAt(), user.getUpdatedAt());

        return responseDto;
    }

    @Override
    public void deleteUserById() {
        int deletedRow = userRepository.deleteUserById(getCurrentUserId());

        if(deletedRow == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + getCurrentUserId());
        }
    }

    private String getCurrentUserId() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getId();
    }
}
