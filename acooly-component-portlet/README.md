前端组件
====

## 负责人

张浦

## 组件介绍

此组件提供常见的前端网站的常用小功能。

## 使用说明

### 网站配置

常规的网站都会用需要简单key/value方式配置的需求，例如：服务热线，服务时间，服务QQ等，该功能封装了按分类的KEY/VALUE参数配置。默认情况下，分类为：def，提供一组常规的网站配置，包括：

|type|title  |name         |value
|----|-------|-------------|---------
|def|客服热线	|serviceTel	 |023123456
|def|服务时间	|serviceWorkHours	|09:00 - 18:00
|def|服务邮箱	|serviceEmail	    |pu.zhang@foxmail.com
|def|服务微博	|serviceWeibo	    |aaaaaaaaa
|def|服务QQ	|serviceQQ	        |15366632
|def|服务QQ群	|serviceQQGroup	|15366632
|def|微信公众号	|serviceWeChat	|zp820705

> 以上参数已在初始化脚本中自动加入到数据库中，可以直接使用。

网站开发的时候，可以使用组件内的SiteConfigService服务对应的接口直接获取组件后台管理管理的参数。

SiteConfigService 接口：

```java
public interface SiteConfigService extends EntityService<SiteConfig> {
    /**
     * 查询默认类型的所有配置
     */
    List<SiteConfig> findByType();
    /**
     * 查询默认类型的指定名称的配置
     * 参数可以从：com.acooly.module.portlet.enums.SiteConfigKeyEnum定义的key
     */
    SiteConfig findUnique(String name);
}
```

### 客户反馈

提供通用的客户反馈收集和处理功能。

#### 提交反馈

前端功能集成可以直接注入FeedbackService服务，调用submit接口方法提交用户反馈，然后可以在后台BOSS的“客户反馈”模块查询和处理用户反馈信息。

```java
public interface FeedbackService extends EntityService<Feedback> {

    /**
     * 提交反馈
     */
    Feedback submit(FeedbackTypeEnum type, String content,
                    @Nullable String title, @Nullable String phoneNo, @Nullable String address,
                    @Nullable String contactInfo, @Nullable String comments, @Nullable String userName);

    Feedback submit(FeedbackTypeEnum type, String content);
}

```

#### 处理反馈

后面管理BOSS提供了专用的处理界面。如果需要扩展自动以处理。可以实现FeedbackHandler接口并标志为@Primary,该回调接口是BOSS界面处理完成（事务提交）后的回调。

例如：

```java
@Slf4j
@Primary
@Component
public class TestFeedbackHandler implements FeedbackHandler {

    @Override
    public void handle(Feedback feedback) {
        log.info("集成测试，自定义feedback处理...");
    }
}
```

### 访问日志

提供通用的前端访问日志收集，用于作为用户行为分析的基础数据。

#### 收集

默认情况下，所有的访问日志都是收集访问url，以及对应的客户端访问信息，如：浏览器，OS，IP等。如果参数配置的sessionKey存在session值，则收集对应的用户（采用session保持的对象的toString）。

主要收集方式有两种方式：

* 接口调用方式

直接注入ActionLogService接口，调用对应的收集方法：

```java
public interface ActionLogService extends EntityService<ActionLog> {

    ActionLog logger(String action, String actionName, String userName, ActionChannelEnum actionChannel,
                     String version, String comments, HttpServletRequest request);
                     
    ActionLog logger(@NotNull HttpServletRequest request, String userName);
}
```

* 通过filter收集

通过开启配置参数，打开filter收集功能。

```ini
# portal组件开关
acooly.portlet.enable=true
# 访问日志收集filter功能开关
acooly.portlet.actionlog.filterEnable=true
# 访问日志收集filter收集会话中用户名的sessionKey
acooly.portlet.actionlog.sessionUserKey=sessionCustomer
# 访问日志收集filter收集日志的url配置（AntPath模式）
acooly.portlet.actionlog.filterUrlPatterns=/portal/**
# 是否开启批量写入
acooly.portlet.actionlog.cacheable=true
# 批量写入的批次大小
acooly.portlet.actionlog.cacheSize=1000
```

#### 配置映射

可以通过后台管理模块添加url的中文映射名称，配置后，日志收集时会记录对应的中文映射名称。组件自动对mapping列表缓存。




