package com.example.otp.client;

public interface OtpClient {
    void sendMessageToMobile(String phoneNumber, String message);
}
