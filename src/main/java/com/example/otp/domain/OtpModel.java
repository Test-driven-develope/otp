package com.example.otp.domain;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.example.otp.utils.Constants;
import lombok.Getter;

@Getter
public class OtpModel {
    private final String phoneNumber;
    private final String otp;
    private final boolean verified;
    
    public OtpModel(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.verified = false;
        this.otp = generateOtp();
    }
    
    private static String generateOtp() {
        return IntStream.generate(() -> new Random().nextInt(Constants.RANGE_BOUND)).limit(Constants.OTP_LENGTH)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining("", "", ""));
    }
}
