package com.example.otp.resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/otp")
public class OtpController {
    
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity sendOtp(@RequestBody OtpSendRequest otpSendRequest) {
        return ResponseEntity.builder()
                .message("验证码已发送至手机号：" + otpSendRequest.getPhoneNumber())
                .build();
    }
}
