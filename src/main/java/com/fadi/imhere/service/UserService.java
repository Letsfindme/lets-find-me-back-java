package com.fadi.imhere.service;

import com.fadi.imhere.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getProfile(String username);
}
