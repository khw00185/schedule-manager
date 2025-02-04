package com.example.schedulemanager.user.service;

import com.example.schedulemanager.common.dto.ResponseDto;
import com.example.schedulemanager.user.dto.CustomUserDetails;
import com.example.schedulemanager.user.dto.UserLoginRequestDto;
import com.example.schedulemanager.user.dto.UserRequestDto;
import com.example.schedulemanager.user.dto.UserResponseDto;
import com.example.schedulemanager.user.entity.User;
import com.example.schedulemanager.user.exception.DuplicatedIdException;
import com.example.schedulemanager.user.exception.InvalidPasswordException;
import com.example.schedulemanager.user.exception.InvalidUserIdException;
import com.example.schedulemanager.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public ResponseDto<UserResponseDto> register(UserRequestDto dto) {
        if(userRepository.findById(dto.getId()).isPresent()){
            throw new DuplicatedIdException();
        }

        String encryptedPassword = passwordEncoder.encode(dto.getPassword());

        User user = new User(dto.getId(), dto.getUserName(), dto.getEmail(), encryptedPassword,
                java.time.LocalDateTime.now(), java.time.LocalDateTime.now());

        userRepository.saveUser(user);

        UserResponseDto responseDto = new UserResponseDto(user.getId(), user.getUserName(), user.getEmail(),
                user.getCreatedAt(), user.getUpdatedAt());

        return ResponseDto.success(responseDto);
    }

    @Override
    public ResponseDto<UserResponseDto> login(UserLoginRequestDto dto) {
        System.out.println("이게 떠야 login서비스가 동작한것임");
        User user = userRepository.findById(dto.getId())
                .orElseThrow(InvalidUserIdException::new);

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        UserResponseDto responseDto = new UserResponseDto(user.getId(), user.getUserName(), user.getEmail(),
                user.getCreatedAt(), user.getUpdatedAt());
        return ResponseDto.success(responseDto);
    }

    @Override
    public ResponseDto<String> deleteUserById() {
        int deletedRow = userRepository.deleteUserById(getCurrentUserId());

        if(deletedRow == 0){
            throw new InvalidUserIdException();
        }
        return ResponseDto.success("회원탈퇴가 완료되었습니다.");
    }

    private String getCurrentUserId() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getId();
    }
}
