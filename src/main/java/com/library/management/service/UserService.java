package com.library.management.service;

import com.library.management.dto.UserDto;
import com.library.management.dto.UserLoginRequest;
import com.library.management.dto.UserResponse;

public interface UserService {
    String createUser(UserDto userDto);

    UserDto getUserById(Long id);

    void deleteUser(Long id);

    String updateUser(Long id, UserDto user);

    UserResponse login(UserLoginRequest userLoginRequest);
}
