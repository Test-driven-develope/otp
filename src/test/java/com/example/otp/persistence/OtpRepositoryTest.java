package com.example.otp.persistence;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.otp.RedisTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {OtpRepository.class})
class OtpRepositoryTest extends RedisTestBase {
    
    @Autowired
    private OtpRepository otpRepository;
    
    @Test
    void should_can_save_otp() {
        OtpPo otpPo = OtpPo.builder()
                .id("15342349111")
                .code("123456")
                .timeOut(900).build();
        
        otpRepository.save(otpPo);
        
        Optional<OtpPo> result = otpRepository.findById(otpPo.getId());
        
        assertTrue(result.isPresent());
        assertEquals(otpPo.getCode(), result.get().getCode());
    }
    
}
