package org.mukhtarovich.uz.Auth.Service.service;

import lombok.RequiredArgsConstructor;
import org.mukhtarovich.uz.Auth.Service.entity.Users;
import org.mukhtarovich.uz.Auth.Service.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;

    public Users getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumberAndIsActive(phoneNumber, true).orElseThrow(() -> new UsernameNotFoundException("user not found " + phoneNumber));

    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        Users user = getUserByPhoneNumber(phoneNumber);

        return User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!user.getIsActive())
                .build();
    }
}
