package com.example.otp.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@RedisHash(value = "otp")
public class OtpPo {
    @Id
    private String id;
    private String code;
    @TimeToLive
    private long timeOut;
}
