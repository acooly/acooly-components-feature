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
```

## 使用

### 静态工具类

集成组件后，在容器启动的情况下，可以直接使用静态工具类来访问国际化的资源。

工具类：`com.acooly.component.i18n.support.I18nMessages`，主要提供了：

1. 各种`getMessage(...)`：返回国际化的资源
2. `supports(...)`: 返回当前环境支持的语言列表（根据classpath*:messages*.properties）

> 注意：内部团队，相关的测试案例，也可以参考组件库的单元测试：`com.acooly.module.test.i18n.I18nTestController`
> 和 `com.acooly.module.test.i18n.I18nTester`

### SpringMvc的支持

默认机车改组件后，会增加全局拦截器动态设置会话级别的当期语言环境。可以通过在请求中附带`lang`
参数（可以通过`acooly.i18n.change-locale-param-name`修改参数名配置）来修改当前语言环境。

### OpenApi的支持

Acooly的OpenApi框架也对此做了增加，请参考OpenApi的文档重点国际化模块

## 3. changelog

### 5.0.0-SNAPSHOT.20240115

* 发布重构版本，增加了对国际化消息的增强支持。

