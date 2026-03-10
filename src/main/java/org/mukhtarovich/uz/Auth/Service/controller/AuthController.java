package org.mukhtarovich.uz.Auth.Service.controller;

import lombok.RequiredArgsConstructor;
import org.mukhtarovich.uz.Auth.Service.dto.ApiResponse;
import org.mukhtarovich.uz.Auth.Service.dto.AuthRequest;
import org.mukhtarovich.uz.Auth.Service.service.AuthService;
import org.mukhtarovich.uz.Auth.Service.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

     private final AuthenticationManager authenticationManager;
     private final JwtService jwtService;
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> auth(@RequestBody AuthRequest authRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()));

            UserDetails user = (UserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(user);

            return ResponseEntity.ok(
                    new ApiResponse<>("Login successfully", HttpStatus.OK, true, token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>("Invalid username or password", HttpStatus.UNAUTHORIZED, false, null));
        }
    }
}
