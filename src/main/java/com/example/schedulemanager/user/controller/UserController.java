package com.example.schedulemanager.user.controller;

import com.example.schedulemanager.user.dto.UserLoginRequestDto;
import com.example.schedulemanager.user.dto.UserRequestDto;
import com.example.schedulemanager.user.dto.UserResponseDto;
import com.example.schedulemanager.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto dto) {
        return ResponseEntity.ok(userService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody UserLoginRequestDto dto) {
        return ResponseEntity.ok(userService.login(dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete() {
        userService.deleteUserById();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
