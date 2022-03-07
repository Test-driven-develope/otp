package com.example.otp.resource;

import javax.validation.constraints.Pattern;

import com.example.otp.utils.Constants;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpVerificationRequest {
    @Pattern(regexp = Constants.VERIFY_PHONE_NUMBER_REGEX, message = Constants.ERROR_PHONE_NUMBER)
    private String phoneNumber;
    @Pattern(regexp = Constants.VERIFY_OTP_REGEX, message = Constants.ERROR_OTP)
    private String otp;
}