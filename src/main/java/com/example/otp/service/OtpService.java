package com.example.otp.service;

import java.util.Optional;

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
        final Optional<OtpPo> optionalOtpPo = otpRepository.findById(request.getPhoneNumber());
        
        if (optionalOtpPo.isPresent()) {
            final OtpPo otpPo = optionalOtpPo.get();
            if (Constants.OTP_TIME_OUT - otpPo.getTimeOut() <= Constants.SEND_OTP_MIN_INTERVAL) {
                throw new SendOTPWithin60sException();
            }
            this.sendSMS(otpPo.getId(), otpPo.getCode());
        } else {
            final OtpModel otp = generateOtp(request);

            this.sendSMS(otp.getPhoneNumber(), otp.getPhoneNumber());
        }
    }
    
    private OtpModel generateOtp(OtpSendRequest request) {
        OtpModel otp = new OtpModel(request.getPhoneNumber());
        
        OtpPo otpPo = OtpPo.builder()
                .id(otp.getPhoneNumber())
                .code(otp.getOtp())
                .timeOut(Constants.OTP_TIME_OUT).build();
        otpRepository.save(otpPo);
        return otp;
    }
    
    private void sendSMS(String mobile, String code) {
        SmsRequestBody smsRequestBody = SmsRequestBody.builder()
                .mobile(mobile)
                .message(Constants.OTP_MESSAGE + code).build();
        smsClient.sendSMS(smsRequestBody);
    }
}
