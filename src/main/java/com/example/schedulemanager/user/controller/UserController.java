package com.example.schedulemanager.user.controller;

import com.example.schedulemanager.common.dto.ResponseDto;
import com.example.schedulemanager.user.dto.UserLoginRequestDto;
import com.example.schedulemanager.user.dto.UserRequestDto;
import com.example.schedulemanager.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<?>> register(@Valid @RequestBody UserRequestDto dto) {
        return ResponseEntity.ok(userService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<?>> login(@Valid @RequestBody UserLoginRequestDto dto) {

        return ResponseEntity.ok(userService.login(dto));
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto<?>> delete() {
        return ResponseEntity.ok(userService.deleteUserById());
    }

}
