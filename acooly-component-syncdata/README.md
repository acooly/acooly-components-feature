<!-- title: 远程数据同步组件  -->
<!-- type: app -->
<!-- author: cuifuqiang-->
<!-- date: 2023-05-01 -->
## 1. 组件介绍

acooly-component-syncdata 采用框架openAPI接口模式，采用定时拉取数据模式同步方案；
可以配合定时任务组件，完成多表的同步方案。

## 支持模式

### 场景1【服务端数据 同步 客户端，客户端触发】 
- 由客户端通过 openAPI 方案，主动调用 服务端的数据同步接口【asyncData】

### 场景2【服务端数据 同步 客户端，服务端通知客户端触发】
- 服务端 通过 openAPI方案，调用客户端接口【asyncDataTrigger】;客户端拉取服务端的对应的业务数据


