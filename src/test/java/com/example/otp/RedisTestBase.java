package com.example.otp;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@ActiveProfiles("test")
@EnableAutoConfiguration
public abstract class RedisTestBase {
    @ClassRule
    private static final GenericContainer redis = new GenericContainer<>(DockerImageName.parse("redis:5.0"))
            .withCreateContainerCmdModifier(
                    createContainerCmd -> createContainerCmd.withHostConfig(HostConfig.newHostConfig()
                            .withPortBindings(new PortBinding(Ports.Binding.
                                    bindPort(6388),
                                    new ExposedPort(6379)))));
    
    @BeforeAll
    public static void setUp() {
        redis.start();
    }
    
    @AfterAll
    public static void destroy() {
        redis.stop();
    }
}

