<!-- title: CMS组件  -->
<!-- name: acooly-component-cms -->
<!-- type: app -->
<!-- author: zhangpu -->
<!-- date: 2019-11-24 -->
## 1. 组件介绍
提供动态内容发布和管理的能力，包括支持：1、文章类型的多媒体内容发布（支持截图，批量文件上传等）；2、广告横幅内容。

## 2. 使用说明

maven坐标：

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-cms</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}`为框架最新版本或者购买的版本。

## 3. FAQ

### 3.1 保存内容后，需要做其他业务操作，如何扩展？

    //异步事件处理器
    @Handler(delivery = Invoke.Asynchronously)
    public void handleCreateCustomerEventAsyn(ContentCreatedEvent c) {
        //do what you like
        log.info("异步保存内容后事件处理器{}",c.toString());
    }

### 3.1 facade接口

CMS组件的接口前端，包括：dubbo的facade定义和openapi的message定义

```xml
    <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-cms-facade</artifactId>
        <version>${acooly-latest-version}</version>
    </dependency>
```

## 4. changelog

### 5.0.0-SNAPSHOT.20211102

2021-11-02

* 2021-11-02 - 使用新的多媒体编辑器封装+boostrap风格升级cms - [zhangpu] ab4b82dc

### 5.0.0-SNAPSHOT-20200114

* 2020-01-14 - fixed:Issue#14:V5的CMS在添加内容时报错JAP报错。原因：重复的自动生成外接和初始化脚本问题，目前已删除初始化脚本中的外键关系。 - [zhangpu] 480f366



