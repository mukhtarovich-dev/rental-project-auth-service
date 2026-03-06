package org.mukhtarovich.uz.Auth.Service.service;

import lombok.RequiredArgsConstructor;
import org.mukhtarovich.uz.Auth.Service.entity.Users;
import org.mukhtarovich.uz.Auth.Service.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public Users getUserByUsername(String username) {
        return userRepository.findByUsernameAndIsActive(username, true).orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = getUserByUsername(username);

        return User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(Collections.emptyList())
                .build();
    }
}
