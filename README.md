# TDD Practise For OTP(one time password)

![otp](./src/main/resources/images/otp.png)

一次性验证码如短信验证是我们在日常生活中最常见的情景了，不管是新用户注册验证手机号，还是手机号码一键登录等，均需要进行一次性验证。

之所以称之为一次性验证是因为该密码是动态生成的，只能验证一次，验证成功之后就不能被再次验证。

该需求涉及到使用第三方API发送验证码，使用Redis一次性验证码的存储，提供API测试接口等，全程我们将使用TDD的方式来开发，相信你学完之后大有裨益。

## 你需要具备什么？

由于此次课程属于TDD的中高级课程，所以你需要具备以下技能才能完整的学习：

* TDD基础知识
* Java基本技能
* Spring Boot/Redis/Mockito等基础知识