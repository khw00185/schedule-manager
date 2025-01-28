package com.example.schedulemanager.user.repository;

import com.example.schedulemanager.user.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(String id);
    void saveUser(User user);
    int deleteUserById(String id);
}
