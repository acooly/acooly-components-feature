<!-- title: 邮件发送组件  -->
<!-- type: app -->
<!-- author: qiubo -->
<!-- date: 2019-11-19 -->

## 1. 组件介绍

此组件给应用邮件服务能力

* 通过模板方式发送
* 发送记录并后台可查询


## 2. 使用说明


maven坐标：

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-mail</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}`为框架最新版本或者购买的版本。

### 2.1 邮件服务类

    com.acooly.module.mail.service.MailService

### 2.2 如何使用邮件模板

#### 2.2.1. 新增邮件模板

    邮件模板为`freemarker`模板

    直接在boss上配置邮件模板和主题。模板查找顺序为：
    1. 优先使用boss上配置的模板，根据模板名匹配
    2. 再从项目中classpath:/mail/下查找模板
    
    `src/main/resources/mail/register_success.ftl`：

            <html>
              <head>
                    <title>恭喜您注册成功</title>
                </head>
                <body>
                    <h1>${message},${name}</h1>
                </body>
            </html>


#### 2.2.2. 使用邮件模板

```java
    MailDto dto = new MailDto();
    dto.to("qiuboboy@qq.com").subject("恭喜您注册成功").param("name", "x").param("message", "how are you!")
            .templateName("register_success");
    mailService.sendAsync(dto);
```

### 2.3 常用邮件服务配置

#### 2.3.1 阿里云邮箱

```properties
acooly.mail.host=smtp.mxhichina.com
acooly.mail.username=support@xxxx.cn
acooly.mail.password=XXXXX
acooly.mail.from-name=acooly
```

#### 2.3.2 hotmail

```properties
acooly.mail.hostname=smtp-mail.outlook.com
acooly.mail.port=587
acooly.mail.starttls=true
acooly.mail.username=aaaaaa@hotmail.com
acooly.mail.password=aaaaaa@Pswd
acooly.mail.from-name=aaaaaa
```

### 2.3.3 腾讯企业邮箱

```properties
## 腾讯邮箱
acooly.mail.hostname=smtp.exmail.qq.com
acooly.mail.ssl-connect=true
acooly.mail.ssl-port=465
acooly.mail.username=xxxx@domain.com
# 注意：密码需要使用客户端专用密码（Web版邮箱：设置->微信绑定->安全登录，打开安全登录，设置新的"客户端专用密码"）
acooly.mail.password=3CZKJxopgpFj1111
acooly.mail.from-name=苦乐
```



## 3. changelog

### 5.0.0-SNAPSHOT.20220111

* 增强版本，非兼容，请手动升级更新数据库: `META-INF/database/mail/mysql/mail_update_20220111.sql`。
* 对模板增加了模板标题字段属性，可用来为模板名称加入中文说明，或者用于邮件类型标注。
* 使用新版本boss重写后台邮件模板功能。
* 增加邮件发送记录，后台管理BOSS可查询发送记录和邮件内容详情
* 增加邮件mock发送功能，只解析模板，并记录发送记录，但不真实发送.`acooly.mail.mock=true`
* 添加单元测试`MailSenderTest`,提供完整的邮寄发送测试和demo

