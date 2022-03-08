package com.example.otp.utils;

public class Constants {
    public final static String OTP_MESSAGE = "[OTP] 亲爱的用户，您的一次性验证码为";
    public final static String ERROR_OTP = "验证失败，请输入正确的验证码或重新获取验证码验证";
    public final static String ERROR_PHONE_NUMBER = "手机号输入有误，请重新输入";
    public final static String CAN_NOT_SEND_OTP_WITH_60S = "不能连续发送验证码，请在60秒之后重试";
    public final static String VERIFY_SUCCESSFULLY = "验证成功";
    public final static String SEND_OTP_TO_PHONE = "验证码已发送至手机号：";
    public static final int RANGE_BOUND = 10;
    public static final int OTP_LENGTH = 6;
    public static final Long OTP_TIME_OUT = 90L;
    public static final Long SEND_OTP_MIN_INTERVAL = 60L;
    public static final String VERIFY_PHONE_NUMBER_REGEX = "^1(3\\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$";
    public static final String VERIFY_OTP_REGEX = "\\d{6}";
}