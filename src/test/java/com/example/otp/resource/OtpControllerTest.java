package com.example.otp.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.otp.ResourceTestBase;
import com.example.otp.service.OtpService;
import com.example.otp.service.exception.SendOTPWithin60sException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(OtpController.class)
class OtpControllerTest extends ResourceTestBase {
    
    @MockBean
    private OtpService otpService;
    
    @Test
    void should_sent_otp_successfully() throws Exception {
        OtpSendRequest request = OtpSendRequest.builder().phoneNumber("15342349111").build();
        
        ArgumentCaptor<OtpSendRequest> arg = ArgumentCaptor.forClass(OtpSendRequest.class);
        
        this.mockMvc.perform(MockMvcRequestBuilders.
                        post("/otp")
                        .content(toJson(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("验证码已发送至手机号：15342349111"));
    
        Mockito.verify(otpService).sendOtp(arg.capture());
    
        assertEquals("15342349111", arg.getValue().getPhoneNumber());
    }
    
    @Test
    void should_return_error_message_when_send_otp_in_60s() throws Exception {
        OtpSendRequest request = OtpSendRequest.builder().phoneNumber("15342349111").build();
    
        doThrow(new SendOTPWithin60sException()).when(otpService).sendOtp(any());
        this.mockMvc.perform(MockMvcRequestBuilders.
                        post("/otp")
                        .content(toJson(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("不能连续发送验证码，请在60秒之后重试"));
    }
    
    @Test
    void should_validate_failed_when_input_error_phone_number() throws Exception {
        OtpSendRequest request = OtpSendRequest.builder().phoneNumber("eee3334").build();
    
        doNothing().when(otpService).sendOtp(any());
        this.mockMvc.perform(MockMvcRequestBuilders.
                        post("/otp")
                        .content(toJson(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("手机号输入有误，请重新输入"));
    }
    
    @Test
    void should_can_verify_otp_successfully() throws Exception {
        OtpVerificationRequest otpVerificationRequest = OtpVerificationRequest.builder()
                .phoneNumber("15342349111")
                .otp("123456").build();
        ArgumentCaptor<OtpVerificationRequest> arg = ArgumentCaptor.forClass(OtpVerificationRequest.class);

        this.mockMvc.perform(MockMvcRequestBuilders.
                        delete("/otp")
                        .content(toJson(otpVerificationRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("验证成功"));
    
        Mockito.verify(otpService).verifyOtp(arg.capture());
        assertEquals("15342349111", arg.getValue().getPhoneNumber());
        assertEquals("123456", arg.getValue().getOtp());
    }
}