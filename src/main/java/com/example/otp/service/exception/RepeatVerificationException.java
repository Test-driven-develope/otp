package com.example.otp.service.exception;

import com.example.otp.exception.BadRequestException;
import com.example.otp.utils.Constants;

public class RepeatVerificationException extends BadRequestException {
    public RepeatVerificationException() {
        super(Constants.CAN_NOT_REPEAT_VERIFY);
    }
}