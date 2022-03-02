package com.example.otp.resource;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.otp.ResourceTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(OtpController.class)
class OtpControllerTest extends ResourceTestBase {
    
    @Test
    void should_sent_otp_successfully() throws Exception {
        OtpSendRequest request = OtpSendRequest.builder().phoneNumber("15342349111").build();

        this.mockMvc.perform(MockMvcRequestBuilders.
                        post("/otp")
                        .content(toJson(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("验证码已发送至手机号：15342349111"));
    }
}
