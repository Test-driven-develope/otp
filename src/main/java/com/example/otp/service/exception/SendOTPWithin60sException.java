package com.example.otp.service.exception;

import com.example.otp.exception.BadRequestException;
import com.example.otp.utils.Constants;

public class SendOTPWithin60sException extends BadRequestException {
    public SendOTPWithin60sException() {
        super(Constants.CAN_NOT_SEND_OTP_WITH_60S);
    }
}