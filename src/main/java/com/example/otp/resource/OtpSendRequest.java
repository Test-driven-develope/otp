package com.example.otp.resource;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpSendRequest {
    private String phoneNumber;
}
