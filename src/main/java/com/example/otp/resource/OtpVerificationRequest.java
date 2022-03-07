package com.example.otp.resource;

import javax.validation.constraints.Pattern;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpVerificationRequest {
    @Pattern(regexp = "^1(3\\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$", message = "手机号输入有误，请重新输入")
    private String phoneNumber;
    @Pattern(regexp = "\\d{6}", message = "验证码有误，验证失败，请输入正确的验证码或重新获取验证码验证")
    private String otp;
}