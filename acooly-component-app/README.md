<!-- title: 移动端app组件 -->
<!-- type: app -->
<!-- author: zhangpu -->

## 1. 组件介绍

提供app常用接口，并通过openapi暴露服务

服务包括：

* welcomeInfo 欢迎信息
* bLog 业务日志收集
* bannerList 横幅广告
* appMessageList 推送消息列表
* appLatestVersion 最新版本
* appCrashReport 崩溃上报

## 2. 使用说明

maven坐标：

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-app</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}`为框架最新版本或者购买的版本。