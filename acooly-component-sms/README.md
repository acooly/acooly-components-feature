<!-- title: 短信发送组件  -->
<!-- type: app -->
<!-- author: zhangpu,shuijing,qiubo -->
<!-- date: 2019-11-20 -->
## 1. 组件介绍

此组件给应用短信服务能力

## 2. 使用说明

maven坐标：

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-sms</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}`为框架最新版本或者购买的版本。

### 2.1 短信服务类

    com.acooly.module.sms.SmsService

### 2.2 如何配置短信模板


    acooly.sms.template.key1=value1
    acooly.sms.template.key2=value2


key为模版名，value为模版的值，`com.acooly.module.sms.SmsService.sendByTemplate`接口传入key即为配置的key
    
### 2.3 各个渠道配置说明

#### 1. 亿美

    acooly.sms.provider=emay
    acooly.sms.emay.sn=9SDK-***-0999-RKRMS
    acooly.sms.emay.password=7****4
    acooly.sms.emay.sign=\u9526\u5DDE\u94F6\u884C


#### 2. 漫道

    acooly.sms.url=http://sdk.entinfo.cn:8061
    acooly.sms.username=xxx
    acooly.sms.password=xxx
    acooly.sms.batchUser=xxx
    acooly.sms.batchPswd=xxx
    acooly.sms.prefix=xxx(前缀)
    acooly.sms.posfix=xxx(后缀)

#### 重庆客亲通

    acooly.sms.url=http://sms.17871.net/WS
    acooly.sms.username=xxx
    acooly.sms.password=xxx

#### 创蓝253

    acooly.sms.provider=cl253
    acooly.sms.cl253.un=N8***498
    acooly.sms.cl253.pw=9RnI****83d7
    acooly.sms.cl253.sign=\u519B\u878D\u878D\u8D44\u79DF\u8D41


#### 阿里云

    acooly.sms.provider=aliyun
    acooly.sms.aliyun.accountId=109579****52390
    acooly.sms.aliyun.accessKeyId=LTAIKG****SxZdYJQ
    acooly.sms.aliyun.accessKeySecret=WVQ1t6y****KFgMq9xTUJ4um86
    acooly.sms.aliyun.topicName=sms.topic-cn-hangzhou


#### 容联.云通讯

    acooly.sms.provider=cloopen
    acooly.sms.cloopen.accountId=8aaf07085a3c*****44aee64302f2
    acooly.sms.cloopen.accountToken=54b8454ee795*****f386582305b7fe
    acooly.sms.cloopen.appId=8aaf07085b8e61*****b8f6274f40111

 
#### MOCK测试 记录日志但不会发送短信

    acooly.sms.provider=mock
    acooly.sms.cloopen.accountId=8aaf07085a3c*****44aee64302f2
    acooly.sms.cloopen.accountToken=54b8454ee795*****f386582305b7fe
    acooly.sms.cloopen.appId=8aaf07085b8e61*****b8f6274f40111
    

####  创蓝253 2.0接口

    acooly.sms.provider=cl253plus
    acooly.sms.cl253plus.account=xxx
    acooly.sms.cl253plus.pswd=xxx

####  创蓝253 Json接口

    acooly.sms.provider=cl253json
    acooly.sms.username=N674***
    acooly.sms.password=acQfyo9Z***
    acooly.sms.url=http://smssh1.253.com/msg/send/json
    # 前缀就是短信签名，需要后台审核通过，配置中文需转为unicode
    acooly.sms.prefix=\u89c2\u4e16\u5b87
    # 短信后缀 选填
    acooly.sms.posfix=\u6536\u5230\u8bf7\u56de\u590d



### 2.4 注意
   
#### 2.4.1. 阿里云短信通道,阿里云通道只支持模板和签名为短信内容,
   发送接口`send(String mobileNo, String content) content`内容需为json格式 如：
```java
    AliyunSmsSendVo vo=new AliyunSmsSendVo();
    params.put("customer", "Testcustomer");
    asa.setFreeSignName("观世宇");
    asa.setSmsParamsMap(params);
    asa.setTemplateCode("SMS_67185863");
    content = asa.toJson();
```
    测试用例见 `com.acooly.core.test.web.TestController#testAliyunSms()`
    
#### 2.4.2. 容联.云通讯通道,只支持模板短信内容,
   发送接口`send(String mobileNo, String content) content`内容需为json格式 如：
```java
    CloopenSmsSendVo clo = new CloopenSmsSendVo();
    clo.setTemplateId("1");
    List<String> data = new ArrayList<>();
    data.add("aaattt");
    clo.setDatas(data);
    smsService.send("18612299409", clo.toJson());
```
    测试用例见 `com.acooly.core.test.web.TestController#testCloopenSms()`    
    
#### 2.4.3. 短信黑名单遵循以下规则
  如数据库表`sms_black_list`有黑名单记录，则发送以数据库记录为准。如数据库表无记录，则以配置文件`acooly.sms.blacklist`配置为准
    