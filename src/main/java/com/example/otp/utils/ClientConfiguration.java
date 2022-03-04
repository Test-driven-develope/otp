package com.example.otp.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Logger;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@EnableFeignClients(basePackages = {"com.example.otp.client"})
@Configuration
public class ClientConfiguration {
    @Bean
    @Primary
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
    
    @Bean
    public ObjectMapper serializingObjectMapper() {
        return new ObjectMapper();
    }

}
