package com.library.management.controller;

import com.library.management.dto.UserDto;
import com.library.management.dto.UserLoginRequest;
import com.library.management.dto.UserResponse;
import com.library.management.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public String createUser(@Valid @RequestBody UserDto user) {
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public UserResponse login(@RequestBody UserLoginRequest userLoginRequest){
        return userService.login(userLoginRequest);
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, @Valid @RequestBody UserDto user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
    // user with buyer role can deposit 5,10,20,50 and 100 cent coins


}
