# 生成OTP阶段Tasks

通过生成阶段AC，我们可以推导出此阶段的所有Task：

**1. 用户输入正确的手机号，可以收到6位数字的验证码。**

- [ ] 新增`OtpController`用于发送验证码接口，API如下：
    ```bash
    Request：{
      URL: http://localhost:8080/otp
      Method: POST
      Request Body: 
      {
        "phoneNumber": "15342349111"
      }
    }
    Response: {
      Status: 201
      Response Body:
      {
         "message": "验证码已发送至手机号：15342349111"
      }
    }
    ```
- [ ] 新增一个`OTP Domain`用于生成随机验证码
- [ ] 新增一个`OtpService`用于存储和发送随机生成的验证码
- [ ] 新增一个`OtpClient`调用第三方服务发送短信
- [ ] 使用`Redis`用于存储生成的验证码

**2. 当用户在60s内连续发送验证码时，提示相关异常信息。**
- [ ] 修改`Controller`的发送验证码接口，能够处理一下异常API：
    ```bash
    Request：{
      URL: http://localhost:8080/otp
      Method: POST
      Request Body: 
      {
        "phoneNumber": "15342349111"
      }
    }
    Response: {
      Status: 400
      Response Body:
      {
         "message": "不能连续发送验证码，请在60秒之后重试"
      }
    }
    ```
- [ ] 修改`OtpService`发送验证码接口，用于抛出`SendOTPWithin60sException`异常

**3. 当用户在60s之后，15min以内，再次发送验证码，发送系统中存储的验证码**
- [ ] 修改Service，判断如果Redis里面存储的验证码在15min之内，将发送存储的验证码

**4. 当验证码超过15min并未验证时，验证码将失效**
- [ ] 修改Redis熟悉，添加过期删除

**5. 当用户输入输入境外的手机号或错误的手机号码时，提示错误消息。**
- [ ] 给Controller添加校验：正确的境内手机号
    ```bash
    Request：{
      URL: http://localhost:8080/otp
      Method: POST
      Request Body: 
      {
        "phoneNumber": "32423sfsd"
      }
    }
    Response: {
      Status: 400
      Response Body:
      {
         "message": "手机号输入有误，请重新输入"
      }
    }
    ```