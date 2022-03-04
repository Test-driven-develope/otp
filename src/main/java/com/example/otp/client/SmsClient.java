package com.example.otp.client;

import com.example.otp.utils.ClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "otpClient", url = "${feign.url}", configuration = {ClientConfiguration.class})
public interface SmsClient {
    @PostMapping("/sms/send")
    void sendSMS(@RequestBody SmsRequestBody smsRequestBody);
}
