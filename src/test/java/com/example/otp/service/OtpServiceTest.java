package com.example.otp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import com.example.otp.client.SmsClient;
import com.example.otp.client.SmsRequestBody;
import com.example.otp.persistence.OtpPo;
import com.example.otp.persistence.OtpRepository;
import com.example.otp.resource.OtpSendRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OtpServiceTest {
    @InjectMocks
    private OtpService otpService;
    
    @Mock
    private SmsClient smsClient;
    
    @Mock
    private OtpRepository otpRepository;
    
    @Test
    void should_can_send_otp_and_save_otp_code() {
        OtpSendRequest request = OtpSendRequest.builder().phoneNumber("15342349111").build();
        ArgumentCaptor<SmsRequestBody> arg = ArgumentCaptor.forClass(SmsRequestBody.class);
        ArgumentCaptor<OtpPo> argPo = ArgumentCaptor.forClass(OtpPo.class);
        
        otpService.sendOtp(request);
        
        verify(otpRepository).save(argPo.capture());
        assertEquals(request.getPhoneNumber(), argPo.getValue().getId());
        assertNotNull(argPo.getValue().getCode());
        assertEquals(900, argPo.getValue().getTimeOut());
        verify(smsClient).sendSMS(arg.capture());
        assertEquals(request.getPhoneNumber(), arg.getValue().getMobile());
        assertTrue(arg.getValue().getMessage().startsWith("[OTP] 亲爱的用户，您的一次性验证码为"));
    }
}
