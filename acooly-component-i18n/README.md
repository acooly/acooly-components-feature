<!-- title: 国际化增强组件  -->
<!-- type: app -->
<!-- author: zhangpu -->
<!-- date: 2021-10-06 -->

## 1. 介绍

springboot默认开启国际化，本组件是在此基础上提供了静态的工具类。

> 注意: SpringBoot的国际化配置，一定要配置默认的`messages.properties`,可以为空，否则不会加载配置

## 2. 集成

maven坐标：

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-i18n</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}`为框架最新版本或者购买的版本。

## 配置

```properties
# 国际化配置(全可选)
acooly.i18n.enabled=true
acooly.i18n.default-locale=zh_CN
acooly.i18n.change-locale-param-name=lang
# 国际化配置文件相关配置，直接采用springboot的配置u
# 配置文件路径，例如：i18n/messages
spring.messages.basename=messages
```

## 使用

### 静态工具类

集成组件后，在容器启动的情况下，可以直接使用静态工具类来访问国际化的资源。

工具类：`com.acooly.component.i18n.support.I18nMessages`，主要提供了：

1. 各种`getMessage(...)`：返回国际化的资源
2. `supports(...)`: 返回当前环境支持的语言列表（根据classpath*:messages*.properties）

> 注意：内部团队，相关的测试案例，也可以参考组件库的单元测试：`com.acooly.module.test.i18n.I18nTestController`
> 和 `com.acooly.module.test.i18n.I18nTester`

### 独立动态修改语言环境

Spring环境中，任意注入和调用`LocaleChangeService.changeLocale(Locale)`方法，可以动态修改当前语言环境。目前已应用于OpenApi的国际化。

### 枚举的国际化

针对枚举值，一般会有`name`或`code`方式映射现实名称`message`（例如：我们框架采用的`Messageable`接口定义的枚举有`code`
和`message`）,针对该种情况，我们建议的做法是：针对code的枚举，可以直接使用`I18nMessages.getMessage(Messageable)`来获取国际化的资源，

* 配置国际化值： 采用`{枚举全类路径}.{code}=国际化值`
  方式进行配置，例如：`com.acooly.core.common.facade.OrderStatusEnum.WAIT_PAY=待支付`
* 获取国际化值：然后，采用`I18nMessages.getMessage(Messageable)`获取当前语言环境的国际化值

### SpringMvc的支持

默认机车改组件后，会增加全局拦截器动态设置会话级别的当期语言环境。可以通过在请求中附带`lang`
参数（可以通过`acooly.i18n.change-locale-param-name`修改参数名配置）来修改当前语言环境。

### OpenApi的支持

Acooly的OpenApi框架也对此做了增加，请参考OpenApi的文档重点国际化模块

## 3. changelog

### 5.2.0-SNAPSHOT.20240115

* 发布重构版本，增加了对国际化消息的增强支持。

