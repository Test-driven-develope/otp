# 生成OTP验证阶段Tasks

通过生成阶段AC，我们可以推导出此阶段的所有Task：

**1. 用户输入正确的验证码，系统提示验证成功**

- [ ] 在`OtpController`新增API用于验证，API如下：
    ```bash
    Request：{
      URL: http://localhost:8080/otp
      Method: DELETE
      Request Body: 
      {
        "phoneNumber": "15342349111",
        "otp": "123456"
      }
    }
    Response: {
      Status: 200
      Response Body:
      {
         "message": "验证成功"
      }
    }
    ```
- [ ] 在Service里面，从Redis里面读取相关手机号的验证码，与前端传入的验证码对比，如果相等表示验证成功
- [ ] 在Service里面，如果验证成功，删除Redis里面的相关验证码

**2. 当用户输入异常的验证码时，验证失败。**
- [ ] 修改`Controller`的验证OTP接口，能够处理以下异常API：
  - 验证码小于6位的数字
  - 验证码包含非法的字符
  - 手机号包含非法字符

      ```bash
      Request：{
        URL: http://localhost:8080/otp
        Method: DELETE
        Request Body: 
        {
          "phoneNumber": "15342349111",
          "otp": "123456"
        }
      }
      Response: {
        Status: 400
        Response Body:
        {
           "message": "验证失败，请输入正确的验证码或重新获取验证码验证"
        }
      }
      ```
- [ ] 修改`Service`OTP验证接口接口，用于抛出`VerifyOtpFailed`异常, 包括但不限于如下场景：
  * 错误的验证码
  * 错误的手机号
  * 获取验证码在15min之后再去验证
  * 其他情况

**3. 当用户在15min之内验证成功之后，不能重复验证**
- [ ] 在`OtpController`修改验证OTP API：
    ```bash
    Request：{
      URL: http://localhost:8080/otp
      Method: DELETE
      Request Body: 
      {
        "phoneNumber": "15342349111",
        "otp": "123456"
      }
    }
    Response: {
      Status: 400
      Response Body:
      {
         "message": "此手机号已经被验证成功，无需再次验证"
      }
    }
    ```
- [ ] 在`Service`层中，如果手机号被验证成功，应当被存储起来，并被标注为已验证
- [ ] 在`Service`层中，应当先判断此手机是否被验证，如果已经被验证，抛出`RepeatVerificationException`异常
- [ ] 在`Perisitence`层中，使用MySQL来存储及查询已经被验证的信息