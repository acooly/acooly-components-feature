## 1. 组件介绍

此组件提供内容发布管理的能力

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

