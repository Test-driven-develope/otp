package com.example.otp.domain;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Getter;

@Getter
public class OtpModel {
    private final String phoneNumber;
    private final String otp;
    private final boolean verified;
    
    private static final int RANGE_BOUND = 10;
    private static final int OTP_LENGTH = 6;
    
    public OtpModel(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.verified = false;
        this.otp = generateOtp();
    }
    
    private static String generateOtp() {
        return IntStream.generate(() -> new Random().nextInt(RANGE_BOUND)).limit(OTP_LENGTH)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining("", "", ""));
    }
}
