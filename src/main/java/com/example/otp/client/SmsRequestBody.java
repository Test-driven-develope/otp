package com.example.otp.client;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SmsRequestBody {
    private String mobile;
    private String message;
}
