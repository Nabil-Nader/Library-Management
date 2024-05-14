package com.library.management.dto;


import com.library.management.model.Role;

public record UserResponse (Long id , String token, Role role, String username){
}
