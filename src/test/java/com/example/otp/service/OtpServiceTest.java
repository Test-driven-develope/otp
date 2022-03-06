package com.example.otp.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.otp.client.SmsClient;
import com.example.otp.client.SmsRequestBody;
import com.example.otp.persistence.OtpPo;
import com.example.otp.persistence.OtpRepository;
import com.example.otp.resource.OtpSendRequest;
import com.example.otp.service.exception.SendOTPWithin60sException;
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
    
    @Test
    void should_throw_SendOTPWithin60sException_when_send_otp_in_60s() {
        OtpSendRequest request = OtpSendRequest.builder().phoneNumber("15342349111").build();
        OtpPo otpPo = OtpPo.builder().id(request.getPhoneNumber()).code("123456").timeOut(860).build();
        when(otpRepository.findById(request.getPhoneNumber())).thenReturn(Optional.of(otpPo));
        
        assertThrows(SendOTPWithin60sException.class, () -> otpService.sendOtp(request));
    }
}
