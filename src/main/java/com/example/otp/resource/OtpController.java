package com.example.otp.resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/otp")
public class OtpController {
    
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public String sendOtp() {
        return "{\n" +
                "  \"message\": \"验证码已发送\"\n" +
                "}";
    }
}
