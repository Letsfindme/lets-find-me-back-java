package com.fadi.imhere.service;


import com.fadi.imhere.dtos.UserDto;

public interface UserService {

    UserDto getProfile(String username);

    UserDto updateProfile(UserDto userDto);
}
