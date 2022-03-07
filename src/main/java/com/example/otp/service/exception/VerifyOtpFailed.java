package com.example.otp.service.exception;

import com.example.otp.exception.BadRequestException;
import com.example.otp.utils.Constants;

public class VerifyOtpFailed extends BadRequestException {
    public VerifyOtpFailed() {
        super(Constants.ERROR_OTP);
    }
}