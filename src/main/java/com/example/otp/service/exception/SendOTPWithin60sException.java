package com.example.otp.service.exception;

import com.example.otp.exception.BadRequestException;

public class SendOTPWithin60sException extends BadRequestException {
    public SendOTPWithin60sException() {
        super("不能连续发送验证码，请在60秒之后重试");
    }
}
