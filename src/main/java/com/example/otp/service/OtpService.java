package com.example.otp.service;

import com.example.otp.client.OtpClient;
import com.example.otp.domain.OtpModel;
import com.example.otp.resource.OtpSendRequest;
import com.example.otp.utils.Constants;
import org.springframework.stereotype.Service;

@Service
public class OtpService {
    private OtpClient otpClient;
    
    public void sendOtp(OtpSendRequest request) {
        OtpModel otp = new OtpModel(request.getPhoneNumber());
        otpClient.sendMessageToMobile(otp.getPhoneNumber(), Constants.OTP_MESSAGE + otp.getOtp());
    }
}
