<!-- title: 分布式消息组件 -->
<!-- type: app -->
<!-- author: qiubo -->
## 1. 组件介绍

此组件提供rocketmq消息集成功能

## 2. 使用说明

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