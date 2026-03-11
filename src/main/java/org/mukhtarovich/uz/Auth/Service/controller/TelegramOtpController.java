package org.mukhtarovich.uz.Auth.Service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mukhtarovich.uz.Auth.Service.dto.ApiResponse;
import org.mukhtarovich.uz.Auth.Service.dto.OtpRequest;
import org.mukhtarovich.uz.Auth.Service.service.AuthService;
import org.mukhtarovich.uz.Auth.Service.service.JwtService;
import org.mukhtarovich.uz.Auth.Service.service.TelegramOtpService;
import org.mukhtarovich.uz.Auth.Service.service.TempSessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/otp")
@RequiredArgsConstructor
@Slf4j
public class TelegramOtpController {
    private final TelegramOtpService telegramOtpService;
    private final TempSessionService tempSessionService;
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/send")
    public ResponseEntity<Void> send(@RequestBody OtpRequest otpRequest) {
        String s = telegramOtpService.sendOtp(otpRequest.getPhoneNumber());
        log.error(" telegram response id "+s);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<String>> verify(@RequestBody OtpRequest request) {

        String username = tempSessionService.getUsername(request.getTempToken());

        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>("Session expired", HttpStatus.UNAUTHORIZED, false, null));
        }

        boolean valid = telegramOtpService.verifyOtp(username, request.getOtpCode());

        if (!valid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>("Invalid OTP", HttpStatus.UNAUTHORIZED, false, null));
        }

        UserDetails user = authService.loadUserByUsername(username);

        String token = jwtService.generateToken(user);

        tempSessionService.remove(request.getTempToken());

        return ResponseEntity.ok(
                new ApiResponse<>("Login success", HttpStatus.OK, true, token));
    }
}
