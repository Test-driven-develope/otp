package com.example.otp.service;

import com.example.otp.client.SmsClient;
import com.example.otp.client.SmsRequestBody;
import com.example.otp.domain.OtpModel;
import com.example.otp.resource.OtpSendRequest;
import com.example.otp.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtpService {
    @Autowired
    private SmsClient smsClient;
    
    public void sendOtp(OtpSendRequest request) {
        OtpModel otp = new OtpModel(request.getPhoneNumber());
        SmsRequestBody smsRequestBody = SmsRequestBody.builder()
                .mobile(otp.getPhoneNumber())
                .message(Constants.OTP_MESSAGE + otp.getOtp()).build();
        smsClient.sendSMS(smsRequestBody);
    }
}
