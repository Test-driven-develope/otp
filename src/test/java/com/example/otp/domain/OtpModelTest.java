package com.example.otp.domain;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

import com.example.otp.utils.Constants;
import org.junit.jupiter.api.Test;

class OtpModelTest {
    
    @Test
    void should_can_generate_otp() {
        OtpModel otpModel = new OtpModel("15342349111");
        
        assertFalse(otpModel.isVerified());
        assertNotNull(otpModel.getOtp());
        assertTrue(Pattern.compile(Constants.VERIFY_OTP_REGEX).matcher(otpModel.getOtp()).matches());
    }
    
}