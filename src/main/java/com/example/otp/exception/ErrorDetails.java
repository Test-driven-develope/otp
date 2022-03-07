package com.example.otp.exception;

import lombok.Data;

@Data
public class ErrorDetails {
    private String message;
    
    public ErrorDetails(String message) {
        super();
        this.message = message;
    }
}