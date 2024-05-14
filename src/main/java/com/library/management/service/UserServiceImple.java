package com.library.management.service;


import com.library.management.dto.UserDto;
import com.library.management.dto.UserLoginRequest;
import com.library.management.dto.UserResponse;
import com.library.management.exception.UserNotFound;
import com.library.management.model.Role;
import com.library.management.model.User;
import com.library.management.repository.UserRepository;
import com.library.management.security.JwtTokenUtil;
import com.library.management.util.HashingUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImple implements UserService{

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;


    @Override
    public String createUser(UserDto userDto) {
        User user = modelMapper.map(userDto,User.class);
        if(userDto.getRole().equalsIgnoreCase(Role.PUBLIC_USER.toString())|| userDto.getRole().equalsIgnoreCase(Role.LIBRARIAN.toString())){
            user.setRole(userDto.getRole().equalsIgnoreCase(Role.PUBLIC_USER.toString())?Role.PUBLIC_USER : Role.LIBRARIAN);
            user.setPassword(HashingUtil.sha256(userDto.getPassword()));
        }else {
            throw new UserNotFound("invalid role");
        }
        userRepository.save(user);
        return "User has been saved";
    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<User> optional = userRepository.findById(id);
        if(optional.isEmpty()){
            throw new UserNotFound("User does not exists with this id"+id);
        }else{
            return modelMapper.map(optional.get(),UserDto.class);
        }
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public String updateUser(Long id, UserDto dto) {
        Optional<User> optional = userRepository.findById(id);
        if(optional.isEmpty()) {
            throw new UserNotFound("User does not exists with this id" + id);
        }else{
            User user = optional.get();
            user.setUsername(dto.getUsername());
            user.setPassword(dto.getPassword());
            userRepository.save(user);
            return "User has been updated";

        }

    }

      @Override
    public UserResponse login(UserLoginRequest userLoginRequest) {
     User user = userRepository.findByUsername(userLoginRequest.username()).orElseThrow(() -> new RuntimeException("User not found"));

     if(!user.getPassword().equalsIgnoreCase(HashingUtil.sha256(userLoginRequest.password()))) {
         throw new UserNotFound("password not correct");
     }
     String token = getAuthenticationToken(user.getId().toString(),user.getUsername(),user);
        return new UserResponse(user.getId(),token,user.getRole(),user.getUsername());
    }

    private String getAuthenticationToken(String id, String displayName, User user) {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(id, displayName)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenUtil.generateToken(user);

    }
}
