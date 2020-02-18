<!-- title: 邮件发送组件  -->
<!-- type: app -->
<!-- author: qiubo -->
<!-- date: 2019-11-19 -->
## 1. 组件介绍

此组件给应用邮件服务能力

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

    	MailDto dto = new MailDto();
    	dto.to("qiuboboy@qq.com").subject("恭喜您注册成功").param("name", "x").param("message", "how are you!")
    			.templateName("register_success");
    		mailService.sendAsync(dto);
