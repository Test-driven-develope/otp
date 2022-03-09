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

## 你能收获什么？

学习完成本课程，你讲获得如下的收获：

* Tasking的基本思路与实现方案
* 可用于生产环境的基于三层架构的测试策略
* 集成测试与单元测试的价值
* 测试策略的选取
* Mock、Stub、Spy的异同

## 你需要做什么？

**1. 确保你本地安装了`Java 8`和`Docker`。**

```bash 
javac -version
docker info
```

**2. Clone此仓库到你的本地**

```bash
git clone https://github.com/Jinghua-Li/otp.git
```

**3. 进入到此项目的文件夹下，执行Build命令：**

```bash
./gradlew clean build
```

**4. 仔细阅读doc下面的所有文档**

* **[requirement.md](./doc/requirement.md):** 关于业务需求描述的详细文档。
* **[architecture.md](./doc/architecture.md):** 关于此应用程序的架构设计图。
* **[tasks_for_otp_generator.md](./doc/tasks_for_otp_generator.md):** 关于发送验证码的所有任务拆分列表。
* **[task_for_otp_verification.md](./doc/tasks_for_otp_verification.md):** 关于验证OTP的所有任务拆分列表。
* **[send_sms_api.md](./doc/send_sms_api.md):** 关于第三方发送验证码的服务API。

**5. 使用IntellJ Idea打开此项目**
使用IntellJ Idea打开此项目，等待相关依赖导入成功，之后切换到tdd分支进行开发。

**6. 如果本地需要启动程序来测试此项目，请做好如下配置：**

* 启动MySQL和Redis：
```bash 
docker-compose -f docker-compose-local.yml up -d
```
* 自己创建一个发送短信的第三方API服务用于domo, 第三方API请参考文档：[send_sms_api.md](./doc/send_sms_api.md) 

## 分支管理说明

此项目总共三个分支：

* **main分支：** 此分支已经完整实现了此应用程序，如果您自己实现的时候有问题，可以适当参考。
* **tdd分支：** 此分支用于让大家练习TDD的分支，包括基本的依赖和相关的脚手架代码。
* **test分支：** 此分支用于对测试不熟悉的小伙伴，想在有测试辅助的情况下练习TDD，新手可以依次去掉测试的注释逐步来实现。