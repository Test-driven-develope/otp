package com.example.otp.service;

import com.example.otp.client.SmsClient;
import com.example.otp.client.SmsRequestBody;
import com.example.otp.domain.OtpModel;
import com.example.otp.persistence.OtpPo;
import com.example.otp.persistence.OtpRepository;
import com.example.otp.resource.OtpSendRequest;
import com.example.otp.service.exception.SendOTPWithin60sException;
import com.example.otp.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtpService {
    @Autowired
    private SmsClient smsClient;
    
    @Autowired
    private OtpRepository otpRepository;
    
    public void sendOtp(OtpSendRequest request) {
        otpRepository.findById(request.getPhoneNumber()).ifPresent(otpPo -> {
            if (Constants.OTP_TIME_OUT - otpPo.getTimeOut() <= Constants.SEND_OTP_MIN_INTERVAL) {
                throw new SendOTPWithin60sException();
            }
        });
        
        OtpModel otp = new OtpModel(request.getPhoneNumber());
    
        OtpPo otpPo = OtpPo.builder()
                .id(otp.getPhoneNumber())
                .code(otp.getOtp())
                .timeOut(Constants.OTP_TIME_OUT).build();
        otpRepository.save(otpPo);

        SmsRequestBody smsRequestBody = SmsRequestBody.builder()
                .mobile(otp.getPhoneNumber())
                .message(Constants.OTP_MESSAGE + otp.getOtp()).build();
        smsClient.sendSMS(smsRequestBody);
    }
}
