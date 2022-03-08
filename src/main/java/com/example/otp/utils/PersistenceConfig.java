package com.example.otp.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories(basePackages = { "com.example.otp.persistence" })
@EnableTransactionManagement
@EnableJpaAuditing
@Configuration
public class PersistenceConfig {
}