package com.example.otp;

import com.example.otp.utils.ClientConfiguration;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.http.trafficlistener.ConsoleNotifyingWiremockNetworkTrafficListener;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.test.context.ActiveProfiles;

@ImportAutoConfiguration(
        value = {
                FeignAutoConfiguration.class,
                HttpMessageConvertersAutoConfiguration.class,
                ClientConfiguration.class}
)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class ClientTestBase {
    private final WireMockConfiguration wireMockConfiguration = WireMockConfiguration.wireMockConfig()
            .networkTrafficListener(new ConsoleNotifyingWiremockNetworkTrafficListener())
            .port(8550);
    
    private final WireMockServer wiremockServer = new WireMockServer(wireMockConfiguration);
    
    @BeforeAll
    protected void startMockServer() {
        wiremockServer.start();
    }
    
    @AfterAll
    protected void stopMockServer() {
        wiremockServer.stop();
    }
}
