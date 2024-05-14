package com.library.management.security;

import com.library.management.model.User;
import com.library.management.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Component(value = "userAuthenticate")
@AllArgsConstructor
public class UserAuthenticate implements UserDetailsService {

    @Autowired
    private UserRepository storeUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optional = storeUserRepository.findById(Long.valueOf(username));
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("Invalid/Expired api_key.");
        }

        return new org.springframework.security.core.userdetails.User(optional.get().getId().toString(), optional.get().getUsername(), getAuthority("user"));
    }

    private Set<SimpleGrantedAuthority> getAuthority(String role) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;

    }


}
