package com.example.otp.client;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.example.otp.ClientTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {SmsClient.class})
class SmsClientTest extends ClientTestBase {
    @Autowired
    private SmsClient smsClient;
    
    @Test
    void should_can_send_sms_successfully() {
        SmsRequestBody smsRequestBody = SmsRequestBody.builder()
                .mobile("phone number")
                .message("otp message").build();
        assertDoesNotThrow(() -> smsClient.sendSMS(smsRequestBody));
    }
}
