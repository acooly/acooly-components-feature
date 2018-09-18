<!-- title: 分布式定时任务组件  -->
<!-- type: app -->
<!-- author: qiubo -->
## 1. 组件介绍

此组件提供定时任务能力，定时任务为分布式，默认打开集群模式，集群上只会有一台机器执行任务，依赖数据库锁实现

## 2. 使用说明

### 2.1 任务类型

目前支持 本地任务、http、dubbo:

* 本地任务只需要填全路径类名、方法名，并把此类标注@Component;
* http任务只需要填http地址
* dubbo任务需要实现`com.acooly.module.scheduler.api.ScheduleCallBackService`接口。参数：dubbo group(必填),dubbo version(必填),dubbo param(可选，获取参数`Map<String, String> attachments = RpcContext.getContext().getAttachments();` 测试用例参考`com.acooly.core.test.scheduler.DemoScheduleCallBackService`
  

### 2.2 配置

* `acooly.scheduler.threadCount=10` 定时任务执行线程数大小
* dubbo任务引用了`acooly-component-dubbo`组件，开启定时任务的同时必须开启`acooly.dubbo.enable=true`
* 当scheduler执行dubbo调度，此组件作为服务消费者，dubbo服务提供者和服务消费者必须配置同一个`acooly.dubbo.zkUrl`
* dubbo服务提供者需要实现的接口`com.acooly.module.scheduler.api.ScheduleCallBackService`在`acooly-component-scheduler`此组件里面，引用此组件后，不需要定时任务相关功能，需要设置`acooly.scheduler.enable=false`关闭定时任务
* 配置定时任务请在boss页面，切勿在数据库直接修改`QRTZ_`开头的表数据
