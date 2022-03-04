# 发送短信API说明文档

短信验证采用购买某第三方平台提供的服务，此第三方平台提供了如下的API用于我们系统调用：

## Request
```bash
Method: POST
API: https://wwww.mou.com/sms/send
Request Body:
{
  "mobile":"15342349111",
  "message": "[OTP] 亲爱的用户，您的一次性验证码为123456"
}
```

## Response

```bash
Status: 200
```