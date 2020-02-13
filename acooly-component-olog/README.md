<!-- title: 后台管理审计日志  -->
<!-- type: app -->
<!-- author: zhangpu,qiubo -->
<!-- date: 2019-11-28 -->
## 1. 组件介绍

此组件提供后台管理系统审计日志收集，展示功能.

此组件结构如下：

1. collector

    负责日志收集，通过`com.acooly.module.olog.collector.OlogForwarder`发送日志到存储端

2. storage

    负责日志存储、展示、归档等能力。

## 2. 使用说明

maven坐标：

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-olog</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}`为框架最新版本或者购买的版本。
    
### 2.1 组件设计

olog组件主要定位为后端BOSS操作的审计日志特性，分为收集和归档两部分功能。

* 设计通过AOP拦截方式实现对整个控制层的Around拦截（框架基类方式配置切面）。
* 设计支持配置需要忽略的URL（Ant模式），参数
* 设计支持配置是否记录参数和结果
* 设计支持配置方式实现类与模块中文名的映射，方法名与功能名的映射，以提高可读性。
* 设计支持通过一组annotation方式定义olog日志行为（包括：映射中文名称，参数中文名等，忽略等）
* 设计归档根据配置时间段，数据量归档到制定目录并压缩打包，清理已归档的数据。

    
### 2.2 特性介绍

#### 2.2.1 日志收集（collector）

   * 实现记录的日志数据项：模块及功能，功能操作，时间，操作员，参数，执行时间，客户端信息(IP,操作系统，浏览器),结果，备注等。
   * 通过配置拦截切面自动拦截类及方法执行（默认配置为拦截Controller），实现收集操作日志。
   * 支持配置是否记录参数；支持配置忽略拦截类，方法和参数。
   * 模块名称映射：默认通过安全框架的Resource-URL功能映射实现功能名称的映射。即：访问的URL显示为对应的模块
   * 操作名称映射：默认使用MAP静态配置文件配置映射(如：list访问映射为分页查询，save方法因素为新增，update方法因素为更新)，可以解决80%的操作名称映射。同时提供@OlogAction和@OlogModule两个Annotation用于直接指定模块操作名称。
   * 支持两种方式收集和整合日志模块。分别为AOP拦截模式和springMVC拦截器模式
   
#### 2.2.2 日志归档（storage）
   
   * 归档到指定目录，默认在WEB-INF/logs/archive/Olog_yyyyMMdd.log
   * 支持归档后自动压缩归档文件
   * 支持归档后清除已归档的数据
   * 支持设置归档的数据范围，使用设定N天前的数据可以归档
   * 支持数据每次抓取的大小（服务器内存占用更下，可控）
   * 支持指定归档文件的前缀和文件的编码    

### 2.3 集成使用

#### 2.3.1 单机集成部署

如果你的单服务应用，请直接依赖坐标到你的POM文件即可，会根据默认配置，收集所有后端的操作日志和数据。

#### 2.3.2 sso应用部署

当启用sso boss应用场景时，需要把各个子系统的审计日志发送到boss系统。

需要在各子系统中禁用storage.

    acooly.olog.storage.enable=false

如果启用了`acooly.security.enableSSOAuthzService=true`，可以不配置此值。

#### 2.3.3 日志元数据配置

1. 通过在Controller类上标注`@OlogModule`配置模块名称,也可通过`acooly.olog.collector.moduleNameMapping`
2. 通过在Controller类或者方法上标注`@Olog.Ignore`忽略日志收集
3. 通过在Controller方法上标注`@OlogAction`配置操作名称，也可以通过配置`acooly.olog.collector.actionNameMapping`

#### 2.3.4 管理入口

组件集成后，首次启动会初始化数据结构和权限（系统管理员），你可以在"系统管理" -> "操作日志"找到功能并直接访问。
功能地址：/manage/component/olog/olog/index.html

### 2.4 组件配置

    ## olog
    # 组件开关
    acooly.olog.enable=true
    # 需要忽略记录的请求参数
    acooly.olog.collector.ignoreParameters=
    # 模块映射配置，url=模块中文名,可配置多个, 也可以通过@OlogModule配置覆盖
    acooly.olog.collector.moduleNameMapping./manage/xxx=XX业务管理模块
    # ...
    # 功能名称映射配置，也可以通过@OlogModule配置覆盖
    acooly.olog.collector.actionNameMapping.customerList=客户列表
    acooly.olog.collector.actionNameMapping.customerSave=创建客户
    acooly.olog.collector.actionNameMapping.customerUpdate=修改客户
    # ...
    # 是否记录请求参数
    acooly.olog.collector.saveParameter=true
    
    # 归档到指定目录，默认在WEB-INF/logs/archive/Olog_yyyyMMdd.log
    # 支持归档后自动压缩归档文件
    # 支持归档后清除已归档的数据
    # 支持设置归档的数据范围，使用设定N天前的数据可以归档
    # 支持数据每次抓取的大小（服务器内存占用更下，可控）
    # 支持指定归档文件的前缀和文件的编码
    
    # 是否启动本地存储归档功能
    acooly.olog.storage.archive.enable=true
    
    # 归档调度corn表达式
    acooly.olog.storage.archive.cron=0 1 0 1 * ?
    
    #存储根目录,可选使用JVM系统变量和物理路径. 默认存在在webapp下的/WEB-INF/logs/archive目录
    acooly.olog.storage.archive.path=/media/data/olog/
    
    #按天设定每次归档的数据量（多少天前的数据需要归档）
    acooly.olog.storage.archive.beforeDays=0
    
    #抓取数据批量大小：为防止OR-MAPPING一次抓取过的数据到服务器内存，采用分页多次抓取，该参数为每次抓取的行数
    acooly.olog.storage.archive.batchSize=1000
    
    #归档完成后是否清除已归档的数据
    acooly.olog.storage.archive.cleanup=true
    
    #归档文件前缀,组件会在后面自动加上："_yyyyMMdd.log"
    acooly.olog.storage.archive.fileNamePrefix=olog_archive
    
    #归档文件的编码
    acooly.olog.storage.archive.fileEncoding=UTF-8
    
    #归档文件是否需要压缩（如果压缩后，则替换原来的文本文件）
    acooly.olog.storage.archive.fileCompress=true




