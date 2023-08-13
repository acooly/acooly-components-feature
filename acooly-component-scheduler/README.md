<!-- title: 分布式定时任务组件  -->
<!-- type: app -->
<!-- author: qiubo -->
<!-- date: 2019-11-18 -->
## 1. 组件介绍
提供定时任务能力，定时任务为分布式，默认打开集群模式，集群上只会有一台机器执行任务，依赖数据库锁实现

## 2. 使用说明

maven坐标：

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-scheduler</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}`为框架最新版本或者购买的版本。

### 2.1 任务类型

目前支持 本地任务、http、dubbo:

* 本地任务只需要填全路径类名（注意是spring容器托管的接口全路径）、方法名，并把此类标注@Component;
* http任务只需要填http地址
* dubbo任务需要实现`com.acooly.module.scheduler.api.ScheduleCallBackService`接口。参数：dubbo group(必填),dubbo version(必填),dubbo param(可选，获取参数`Map<String, String> attachments = RpcContext.getContext().getAttachments();` 测试用例参考`com.acooly.core.test.scheduler.DemoScheduleCallBackService`
  

### 2.2 配置

* `acooly.scheduler.threadCount=10` 定时任务执行线程数大小
* dubbo任务引用了`acooly-component-dubbo`组件，开启定时任务的同时必须开启`acooly.dubbo.enable=true`
* 当scheduler执行dubbo调度，此组件作为服务消费者，dubbo服务提供者和服务消费者必须配置同一个`acooly.dubbo.zkUrl`
* dubbo服务提供者需要实现的接口`com.acooly.module.scheduler.api.ScheduleCallBackService`在`acooly-component-scheduler`此组件里面，引用此组件后，不需要定时任务相关功能，需要设置`acooly.scheduler.enable=false`关闭定时任务
* 配置定时任务请在boss页面，切勿在数据库直接修改`QRTZ_`开头的表数据

## 3 FQA

### 3.1 关闭特殊节点的Job触发

在Pre环境时，与生产环境的数据库是共用的，为避免定时任务的日志漂移到pre环境的节点上，需要关闭pre环境的job触发执行功能，但保留正常接口功能。

通过在pre环境的配置文件中增加如下控制配置参数：

```ini
acooly.scheduler.enablejob=false
```

## 4 changelogs

### 5.2.0-SNAPSHOT.20221103

* 2022-11-03 - 优化管理界面，对各种类型的通知编辑界面进行表单验证；删除JSP视图 - [zhangpu] 67749097
* 2022-11-02 - 完成显示界面的升级ftl - [zhangpu] b214948b
* 2022-11-02 - 升级JPA版本后，解决驼峰格式字段名称不规范则不兼容的问题，通过修改实体属性名称解决；同时重构管理界面的列表和编辑为FTL - [zhangpu] dbb4cab1
