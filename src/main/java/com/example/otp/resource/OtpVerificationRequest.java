package com.example.otp.resource;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpVerificationRequest {
    private String phoneNumber;
    private String otp;
}
