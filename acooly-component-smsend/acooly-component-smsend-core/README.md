<!-- title: 短信聚合组件  -->
<!-- type: app -->
<!-- author: zhangpu -->
<!-- date: 2020-05-21 -->
## 1. 简介

此组件提供聚合多个短信通道的发送短信能力，可自动采用可用渠道重发，是作为短信微服务的基础。

## 2. 特性

* 多渠道短信聚合，多渠道顺序或负载发送
* 可发送应用管理，分配给其他系统appId及认证
* 模板管理，自定义模板，同时管理映射不同短信渠道的渠道模板
* 短信发送记录，包括失败记录。
* 可配置流控
* 可配置黑名单
* [todo] 短信发送分析报表

## 3. 短信服务

建立独立的微服务，集成短信聚合服务组件，对内提供统一的短信发送微服务。

### 3.1. 集成

maven坐标：

```xml
<dependency>
    <groupId>com.acooly</groupId>
    <artifactId>acooly-component-smsend</artifactId>
</dependency>
```

`${acooly-latest-version}`为框架最新版本(5.x)或者购买的版本，一般不用管理，在parent中已集中管理。

### 3.2. 参数配置

#### 3.2.1. 短信服务配置

```ini
# 组件开关
acooly.smsend.enable=true
# 开启对外facade短信发送接口服务
acooly.smsend.facade=true
# 黑名单配置(不推荐)，请直接在后台BOSS管理黑名单
acooly.smsend.blacklist[0]=13811111111
acooly.smsend.blacklist[1]=13811111111
# 网络超时配置（毫秒）以下是默认值
acooly.smsend.connTimeout=20000
acooly.smsend.readTimeout=10000
# 流控（以下是默认值）
acooly.smsend.rate-limit.timeUnit=minutes
acooly.smsend.rate-limit.maxPerIp=200
acooly.smsend.rate-limit.maxPerMobile=5
# 渠道配置，优先级：根据配置文件配置顺序
## 渠道1：阿里云
acooly.smsend.providers.aliyun.access-key=XXXXXXX
acooly.smsend.providers.aliyun.secret-key=XXXXXXX
acooly.smsend.providers.aliyun.content-sign=XXXXX
acooly.smsend.providers.aliyun.ext.regionId=cn-hangzhou
## 渠道2：容联云
acooly.smsend.providers.cloopen.app-id=aaf98f89512446e2015142c111111111
acooly.smsend.providers.cloopen.access-key=8a48b5514ecd7fa8014e1111111111
acooly.smsend.providers.cloopen.secret-key=30cbffc2d17240e7be2a1111111111
## 渠道3：其他
acooly.smsend.providers.anycmp.access-key=52AQCwgE+111111111111111
acooly.smsend.providers.anycmp.secret-key=t2BRy849l222222222222222
acooly.smsend.providers.anycmp.content-sign=我是签名
```

#### 3.2.2. 线程池配置

smsend组件异步发送使用通用线程池组件，如有需求，请进行配置

```ini
acooly.threadpool.threadMax=1000
acooly.threadpool.threadMin=10
acooly.threadpool.threadQueue=1000
```

#### 3.2.3. 其他配置

短信服务默认使用dubbo方式提供内部服务，这里是dubbo相关的参数配置

```ini
# 开启appservice，并对组件内的dubbo服务设置对应的包扫描路径
acooly.appservice.enable=true
acooly.appservice.scanPackages.smsend=com.acooly.module.smsend
```

### 3.3. 管理功能

在你集成的短信服务启动后，可以通过登录后台进行对应的配置管理。主要包括：

#### 3.3.1 发送应用管理

短信服务一般是公司中台的基础能力微服务，对内提供短信发送的统一能力。为区分，认证和统计分析数据，我们同个发送应用管理为每个需要发送的系统或应用分配唯一的appId，标志发送短信的应用。

#### 3.3.2 模板管理

目前主流的短信发送渠道都切换为只支持模板方式短信发送，但很多提供商定义的模板id都是不能有客户端自定义，而是渠道分配的，这造成业务应用端同一个短信需要在多家对应不同的模板id，必能通用。

短信服务提供统一的短信模板映射管理功能。在我们自己的短信服务中定义模板ID，然后配置不同短信渠道对应的渠道模板ID，当短信聚合发送服务自动选择发送渠道的时候，转换为对应的渠道模板ID。

例如：
* 短信服务自定义模板ID（app1-login-verify）-(aliyun)-> MS-121212
* 短信服务自定义模板ID（app1-login-verify）-(容联云)-> 181212

#### 3.3.3 黑名单管理

后台提供动态管理全局黑名单的能力，短信聚合发送服务会合并配置文件和后台设置的黑名单作为系统的全局黑名单。

#### 3.3.4 短信发送记录

提供全面详细的发送明细查询，包括失败的发送记录。任何发送短信未明的情况都可通过记录查询定位问题。

## 4. 短信发送客户端

所有内部服务都可以方便的集成短信发送客户端，采用本地服务注入的方式，通过短信服务发送短信。不必在关系投递成功，渠道发送限制和失败的问题。

### 4.1 集成

```xml
<dependency>
    <groupId>com.acooly</groupId>
    <artifactId>acooly-component-smsend-client</artifactId>
</dependency>
```
`${acooly-latest-version}`为框架最新版本(5.x)或者购买的版本，一般不用管理，在parent中已集中管理。

### 4.2 配置

默认情况是不用配置的，只有你需要在本地开发采用mock时，需要配置下：`acooly.smsend.client.type=mock`

```ini
## smsend client type: mock,dubbo
acooly.smsend.client.type=mock
```

### 4.3 接口

集成和配置好后，你可以通过客户端工具类直接发送短信。

```java
public interface SmsSendClientService {

    /**
     * 通用短信发送
     *
     * @param smsSendOrder
     * @return
     */
    SmsSendResult send(SmsSendOrder smsSendOrder);

    default SmsSendResult sendAsync(String appId, String mobileNo, String templateCode, Map<String, String> templateParams, String contentSign, String clientIp) {
        SmsSendOrder order = new SmsSendOrder(appId, mobileNo, templateCode, templateParams, contentSign, clientIp);
        return send(order);
    }

    default SmsSendResult sendAsync(String appId, String mobileNo, String templateCode, Map<String, String> templateParams, String contentSign) {
        return sendAsync(appId, mobileNo, templateCode, templateParams, contentSign, null);
    }

    default SmsSendResult sendAsync(String appId, String mobileNo, String templateCode, Map<String, String> templateParams) {
        return sendAsync(appId, mobileNo, templateCode, templateParams, null, null);
    }

    default SmsSendResult send(String appId, String mobileNo, String templateCode, Map<String, String> templateParams, String contentSign, String clientIp) {
        SmsSendOrder order = new SmsSendOrder(appId, mobileNo, templateCode, templateParams, contentSign, clientIp);
        order.setAsync(false);
        return send(order);
    }

    default SmsSendResult send(String appId, String mobileNo, String templateCode, Map<String, String> templateParams, String contentSign) {
        return send(appId, mobileNo, templateCode, templateParams, contentSign, null);
    }

    default SmsSendResult send(String appId, String mobileNo, String templateCode, Map<String, String> templateParams) {
        return send(appId, mobileNo, templateCode, templateParams, null, null);
    }

}
```

### 4.3 Demo

```java
@Slf4j
@RestController
@RequestMapping("/smssend/test/")
public class SmsSenderTestController {


    @Autowired
    private SmsSendClientService smsSendClientService;
    
    @RequestMapping("sendTemplate")
    public Object facadeSendTemplate(HttpServletRequest request) {
        // 短信服务统一分配的发送应用的Id
        String appId = "youcheyun";
        // 短信服务统一配置的模板编码，需要对需要的多个渠道配置对应的绑定渠道模板ID
        String templateCode = "youcheyun_register";
        // 如果渠道需预先注册签名，则请先在渠道方申请签名；如果不传入，则表示使用短信服务的渠道默认配置，建议都传入
        String contentSign = null; //""有车云";
        // 客户请求IP，如果需要IP流控，则传入，短信服务默认配置为：100/IP/分钟
        String clientIp = IPUtil.getIpAddr(request);
        Map<String, String> smsParam = new ListOrderedMap<String, String>();
        smsParam.put("captcha", "121312");
        smsParam.put("effectiveMinute", "10");
        SmsSendOrder smsSendOrder = new SmsSendOrder(appId, "189000000", templateCode, smsParam, contentSign, clientIp);
        String gid = Ids.gid();
        smsSendOrder.setGid(gid);
        smsSendOrder.setPartnerId(appId);
        return smsSendClientService.send(smsSendOrder);
    }
}
```
