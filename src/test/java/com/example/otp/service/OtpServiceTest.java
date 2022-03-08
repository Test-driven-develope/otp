package com.example.otp.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.otp.client.SmsClient;
import com.example.otp.client.SmsRequestBody;
import com.example.otp.persistence.AccountPo;
import com.example.otp.persistence.AccountRepository;
import com.example.otp.persistence.OtpPo;
import com.example.otp.persistence.OtpRepository;
import com.example.otp.resource.OtpSendRequest;
import com.example.otp.resource.OtpVerificationRequest;
import com.example.otp.service.exception.RepeatVerificationException;
import com.example.otp.service.exception.SendOTPWithin60sException;
import com.example.otp.service.exception.VerifyOtpFailed;
import com.example.otp.utils.Constants;
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
    
    @Mock
    private AccountRepository accountRepository;
    
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
        assertTrue(arg.getValue().getMessage().startsWith(Constants.OTP_MESSAGE));
    }
    
    @Test
    void should_throw_SendOTPWithin60sException_when_send_otp_in_60s() {
        OtpSendRequest request = OtpSendRequest.builder().phoneNumber("15342349111").build();
        OtpPo otpPo = OtpPo.builder().id(request.getPhoneNumber()).code("123456").timeOut(860).build();
        when(otpRepository.findById(request.getPhoneNumber())).thenReturn(Optional.of(otpPo));
        
        assertThrows(SendOTPWithin60sException.class, () -> otpService.sendOtp(request));
    }
    
    @Test
    void should_send_same_otp_when_more_than_60s_less_than_900s() {
        OtpSendRequest request = OtpSendRequest.builder().phoneNumber("15342349111").build();
        OtpPo otpPo = OtpPo.builder().id(request.getPhoneNumber()).code("123456").timeOut(800).build();
        when(otpRepository.findById(request.getPhoneNumber())).thenReturn(Optional.of(otpPo));
        
        ArgumentCaptor<SmsRequestBody> arg = ArgumentCaptor.forClass(SmsRequestBody.class);
        
        otpService.sendOtp(request);
        
        verify(smsClient).sendSMS(arg.capture());
        assertEquals(request.getPhoneNumber(), arg.getValue().getMobile());
        assertTrue(arg.getValue().getMessage().startsWith(Constants.OTP_MESSAGE + otpPo.getCode()));
    }
    
    @Test
    void should_verify_otp_successfully() {
        OtpVerificationRequest otpVerificationRequest = OtpVerificationRequest.builder()
                .phoneNumber("15342349111")
                .otp("123456").build();
    
        OtpPo otpPo = OtpPo.builder().id(otpVerificationRequest.getPhoneNumber()).code("123456").timeOut(800).build();
        when(otpRepository.findById(otpVerificationRequest.getPhoneNumber())).thenReturn(Optional.of(otpPo));
        ArgumentCaptor<OtpPo> arg = ArgumentCaptor.forClass(OtpPo.class);
        ArgumentCaptor<AccountPo> arg1 = ArgumentCaptor.forClass(AccountPo.class);
        
        boolean result = otpService.verifyOtp(otpVerificationRequest);
        
        verify(accountRepository).save(arg1.capture());
        assertEquals(otpVerificationRequest.getPhoneNumber(), arg1.getValue().getPhoneNumber());
        
        verify(otpRepository).delete(arg.capture());
        assertEquals(true, result);
        assertEquals(otpVerificationRequest.getPhoneNumber(), arg.getValue().getId());
        assertEquals(otpVerificationRequest.getOtp(), arg.getValue().getCode());
    }
    
    @Test
    void should_verify_failed_when_input_error_otp() {
        OtpVerificationRequest otpVerificationRequest = OtpVerificationRequest.builder()
                .phoneNumber("15342349111")
                .otp("123456").build();
    
        OtpPo otpPo = OtpPo.builder().id(otpVerificationRequest.getPhoneNumber()).code("123455").timeOut(800).build();
        when(otpRepository.findById(otpVerificationRequest.getPhoneNumber())).thenReturn(Optional.of(otpPo));

        assertThrows(VerifyOtpFailed.class, () -> otpService.verifyOtp(otpVerificationRequest));
    }
    
    @Test
    void should_verify_failed_when_input_error_phone_number() {
        OtpVerificationRequest otpVerificationRequest = OtpVerificationRequest.builder()
                .phoneNumber("15342349111")
                .otp("123456").build();
        
        when(otpRepository.findById(otpVerificationRequest.getPhoneNumber())).thenReturn(Optional.empty());
        
        assertThrows(VerifyOtpFailed.class, () -> otpService.verifyOtp(otpVerificationRequest));
    }
    
    @Test
    void should_can_not_repeat_verify() {
        OtpVerificationRequest otpVerificationRequest = OtpVerificationRequest.builder()
                .phoneNumber("15342349111")
                .otp("123456").build();
        when(accountRepository.findByPhoneNumber(otpVerificationRequest.getPhoneNumber()))
                .thenReturn(Optional.of(AccountPo.builder().build()));

        assertThrows(RepeatVerificationException.class, () -> otpService.verifyOtp(otpVerificationRequest));
    }
}