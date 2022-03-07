package com.example.otp.service.exception;

import com.example.otp.exception.BadRequestException;

public class VerifyOtpFailed extends BadRequestException {
    public VerifyOtpFailed() {
        super("验证码有误，验证失败，请输入正确的验证码或重新获取验证码验证");
    }
}