<!-- title: 动态表单组件 -->
<!-- type: app -->
<!-- author: qiubo,zhangpu -->
<!-- date: 2019-10-24 -->
## 1. 组件介绍
此组件提供[EAV](https://en.wikipedia.org/wiki/Entity%E2%80%93attribute%E2%80%93value_model)模式的动态表达解决方案和数据管理能力。

主要应用场景为：
1. 电商动态参数管理
2. 流程节点可配置表单
3. 简单数据管理

## 2. 组件特性

* 可配置动态表方案，定义动态表的权限（列表，创建，修改，删除，查看等）和视图参数（宽高等）
* 可定义属性列的丰富功能参数，包括类型，长度，最小值，最大值，默认值，选项值等
* 支持数据类型：整数，小数，金额(2位小数,Money类型),日期时间和枚举（Messageable）
* 可定义属性列的正则验证
* 提供全局共享的选项值定义和管理，用于列的枚举类型支持
* 支持属性分组
* 支持属性排序
* 支持属性显示格式配置
* 支持属性显示权限配置（是否查询条件，是否列表显示，是否表单显示等）
* 支持后台统一数据管理模块数据关系，通过下拉动态切换表方案，同时也支持基于schemeId的独立资源配置单表方案的数据管理和权限控制。
* 提供非online环境的restful API接口。

## 3. 使用说明

maven坐标：

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-eav</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}`为框架最新版本或者购买的版本。

>本组件全部依赖数据库配置，集成及使用，无编译时参数配置。
    
