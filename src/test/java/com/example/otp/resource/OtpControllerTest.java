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
import com.example.otp.service.exception.VerifyOtpFailed;
import com.example.otp.utils.Constants;
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
                .andExpect(jsonPath("$.message").value(Constants.SEND_OTP_TO_PHONE + request.getPhoneNumber()));
        
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
                .andExpect(jsonPath("$.message").value(Constants.CAN_NOT_SEND_OTP_WITH_60S));
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
                .andExpect(jsonPath("$.message").value(Constants.ERROR_PHONE_NUMBER));
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
                .andExpect(jsonPath("$.message").value(Constants.VERIFY_SUCCESSFULLY));
        
        Mockito.verify(otpService).verifyOtp(arg.capture());
        assertEquals("15342349111", arg.getValue().getPhoneNumber());
        assertEquals("123456", arg.getValue().getOtp());
    }
    
    @Test
    void should_verify_failed_with_illegal_otp() throws Exception {
        OtpVerificationRequest otpVerificationRequest = OtpVerificationRequest.builder()
                .phoneNumber("15342349111")
                .otp("safd").build();
        
        this.mockMvc.perform(MockMvcRequestBuilders.
                        delete("/otp")
                        .content(toJson(otpVerificationRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(Constants.ERROR_OTP));
    }
    
    @Test
    void should_verify_failed_with_illegal_phone_number() throws Exception {
        OtpVerificationRequest otpVerificationRequest = OtpVerificationRequest.builder()
                .phoneNumber("sdfsaf")
                .otp("123456").build();
        
        this.mockMvc.perform(MockMvcRequestBuilders.
                        delete("/otp")
                        .content(toJson(otpVerificationRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(Constants.ERROR_PHONE_NUMBER));
    }
    
    @Test
    void should_verify_failed_with_error_otp() throws Exception {
        OtpVerificationRequest otpVerificationRequest = OtpVerificationRequest.builder()
                .phoneNumber("15342349100")
                .otp("123456").build();
        doThrow(new VerifyOtpFailed()).when(otpService).verifyOtp(any());
        this.mockMvc.perform(MockMvcRequestBuilders.
                        delete("/otp")
                        .content(toJson(otpVerificationRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(Constants.ERROR_OTP));
    }
}