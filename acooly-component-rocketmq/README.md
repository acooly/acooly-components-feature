<!-- title: 分布式消息组件 -->
<!-- type: app -->
<!-- author: qiubo -->
<!-- date: 2019-11-16 -->
## 1. 组件介绍

此组件提供rocketmq消息集成功能

## 2. 使用说明

maven坐标：

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-rocketmq</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}`为框架最新版本或者购买的版本。

### 2.1 消息发送

配置：

    acooly.rocketmq.nameSrvAddr=127.0.0.1:9876
    acooly.rocketmq.producer.enable=true

使用`MessageProducer`发送消息。

参考`com.acooly.core.test.rocketmq.MQController`

### 2.2 消息接收

配置：

    acooly.rocketmq.consumer.enable=true

      @RocketListener(topic = "test")
      public void processMessage(MessageDto message) {
        log.info("{}",message);
      }