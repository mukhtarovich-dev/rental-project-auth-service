package org.mukhtarovich.uz.Auth.Service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OtpRequest {
    private String phoneNumber;
    private String otpCode;
    private String tempToken;
}
