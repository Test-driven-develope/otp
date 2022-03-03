package com.example.otp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import com.example.otp.client.OtpClient;
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
    private OtpClient otpClient;
    
    @Test
    void should_can_send_otp_and_save_otp_code() {
        OtpSendRequest request = OtpSendRequest.builder().phoneNumber("15342349111").build();
        ArgumentCaptor<String> arg1 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> arg2 = ArgumentCaptor.forClass(String.class);
        
        otpService.sendOtp(request);
        
        verify(otpClient).sendMessageToMobile(arg1.capture(), arg2.capture());
        assertEquals(request.getPhoneNumber(), arg1.getValue());
        assertTrue(arg2.getValue().startsWith("[OTP] 亲爱的用户，您的一次性验证码为"));
    }
}
