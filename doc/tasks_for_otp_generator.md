# 生成OTP阶段Tasks

通过生成阶段AC，我们可以推导出此阶段的所有Task：

**1. 用户输入正确的手机号，可以收到6位数字的验证码。**
* Request：
```bash
URL: http://localhost:8080/otp
Method: POST
Request Body: 
{
  "phoneNumber": "15342349111"
}
```
* Response:
```bash
Status: 201
Response Body: 
{
  "message": "验证码已发送"
}
```

**2. 当用户在60s内连续发送验证码时，提示相关异常信息。**
* Request：
```bash
URL: http://localhost:8080/otp
Method: POST
Request Body: 
{
  "phoneNumber": "15342349111"
}
```
* Response:
```bash
Status: 400
Response Body: 
{
  "message": "不能连续发送验证码，请在60秒之后重试"
}
```

**3. 当用户在60s之后，15min以内，再次发送验证码，发送系统中存储的验证码**

**4. 当验证码超过15min并未验证时，验证码将失效**

**5. 当用户输入境外的手机号时，系统提示不支持境外手机号**
* Request：
```bash
URL: http://localhost:8080/otp
Method: POST
Request Body: 
{
  "phoneNumber": "85215342349111"
}
```
* Response:
```bash
Status: 400
Response Body: 
{
  "message": "目前该系统仅支持境内手机号"
}
```

**6. 当用户输入错误的手机号码时，提示错误消息。**

* Request：
```bash
URL: http://localhost:8080/otp
Method: POST
Request Body: 
{
  "phoneNumber": "32423sfsd"
}
```
* Response:
```bash
Status: 400
Response Body: 
{
  "message": "手机号输入有误，请重新输入"
}
```
