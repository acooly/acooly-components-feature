Acooly应用组件库
====


## 简介

提供独立的功能和特性组件，以根据各系统按需使用。

## 组件列表

|组件 | 名称 | 说明 |
|----|------|------|
|[acooly-component-app](./acooly-component-app/README.md)|前端App组件|提供app常用接口，并通过openapi暴露服务|
|[acooly-component-captcha](./acooly-component-captcha/README.md)|验证码组件|提供各种验证码的生成和验证，管理验证码的生命周期,可用于生成验证token|
|[acooly-component-certification](./acooly-component-certification/README.md)|实名/卡认证组件|此组件提供实名认证、银行卡二三四要素认证、手机在网三要素认证能力。|
|[acooly-component-chart](./acooly-component-chart/README.md)|图表组件|以acooly框架为基础, 集成Apache ECharts(前身:百度echarts)图表；支持 折线图，柱状图，饼图，柱状-堆叠图,中国地图；动态sql语法解析，动态转换为不同的图表展示。|
|[acooly-component-chat](./acooly-component-chat/README.md)|IM聊天组件|封装极光IM聊天组件。|
|[acooly-component-cms](./acooly-component-cms/README.md)|CMS组件|提供动态内容发布和管理的能力，包括支持：1、文章类型的多媒体内容发布（支持截图，批量文件上传等）；2、广告横幅内容。|
|[acooly-component-config](./acooly-component-config/README.md)|参数配置组件|此组件提供配置管理和读取的能力。读取值时首先读取本地缓存，然后读取redis，最后读取数据库。|
|[acooly-component-content-audit](./acooly-component-content-audit/README.md)|内容审计|提供文字，图片和视频的内容合规审计，防止软件或平台政策和政治风险。|
|[acooly-component-data-ip](./acooly-component-data/acooly-component-data-ip/README.md)|数据-IP查询|acooly-component-data-ip组件是data组件的一个子组件，专用于提供全球IP查询判断相关服务。|
|[acooly-component-data-region](./acooly-component-data/acooly-component-data-region/README.md)|数据-全国区域|acooly-component-data-region组件是data组件的一个子组件，专用于提供统一的全国省市区三级(四级)区域数据及应用场景。以实现多端统一区域数据和应用。|
|[acooly-component-eav](./acooly-component-eav/README.md)|动态表单组件|此组件提供[EAV](https://en.wikipedia.org/wiki/Entity%E2%80%93attribute%E2%80%93value_model)模式的动态表达解决方案和数据管理能力。|
|[acooly-component-face](./acooly-component-face/README.md)|人脸识别组件|此组件提供人脸识别相关能力，目前资源接入方为百度。|
|[acooly-component-mail](./acooly-component-mail/README.md)|邮件发送组件|提供基于SMTP服务的邮件发送服务能力。支持：模板邮件发送、普通邮件发送、邮件发送记录查询等功能。|
|[acooly-component-obs](./acooly-component-obs/README.md)|对象存储访问组件|提供对象存储（Object-Based Storage, OBS)客户端的抽象封装存储，使用一套存储接口，可以选择不同的存储方式:阿里OSS。目前支持阿里OSS|
|[acooly-component-ocr](./acooly-component-ocr/README.md)|ocr文字识别组件|提供ocr文字识别相关能力，目前接入有身份证识别,驾驶证识别,银行卡,营业执照, 行驶证 可继续扩展|
|[acooly-component-ofile](./acooly-component-ofile/README.md)|文件上传组件|提供文件上传(图片压缩)和元数据管理的能力。支持：文件上传、下载、访问、文件上传认证等功能。|
|[acooly-component-olog](./acooly-component-olog/README.md)|后台管理审计日志|提供后台管理系统审计日志收集，展示和归档功能.解决：什么人在什么时间操作了什么功能，操作了什么数据，操作结果如何的问题。|
|[acooly-component-pdf](./acooly-component-pdf/README.md)|pdf组件|提供基于模板（FTL），html or docx转换pdf能力，可用于在线协议，文档等处理。|
|[acooly-component-rbac](./acooly-component-rbac/README.md)|权限管理(RBAC)组件|提供基于角色的权限管理数据结构（RBAC）+ Shiro的独立实现（非Web），主要用于负责门户前端（OpenApi或前后端分离）的多角色权限管控|
|[acooly-component-rocketmq](./acooly-component-rocketmq/README.md)|分布式消息组件|此组件提供rocketmq消息集成功能|
|[acooly-component-safety](./acooly-component-safety/README.md)|加解密组件|作为框架的所有安全相关的能力的工具组件。包括：签名，加解密等。防御相关的已独立为defence,不包括在该组件里面。主要是解决openapi等体系场景内的各个组件或系统都独立存在安全相关的能力代码，而且都是采用代码拷贝方式复用，还经常出现名字冲突等问题。|
|[acooly-component-scheduler](./acooly-component-scheduler/README.md)|分布式定时任务组件|提供定时任务能力，定时任务为分布式，默认打开集群模式，集群上只会有一台机器执行任务，依赖数据库锁实现|
|[acooly-component-security](./acooly-component-security/README.md)|后台管理框架组件|提供基于国家安全等保三级别的管理系统框架，包括：安全登录认证，基于角色，权限，资源管理的权限管理系统等。|
|[acooly-component-seq](./acooly-component-seq/README.md)|序列号生成器组件|自增序号生成器，类似于oracle中的seq。 每次会从数据库中读取一批id，当这一批用完后，在从数据库中获取，当有多个节点时，不同节点分配的批次不一样，仅能保证在单个jvm内递增。|
|[acooly-component-sms](./acooly-component-sms/README.md)|短信发送组件|提供应用短信发送的组件能力，支持：阿里云短信，亿美短信，漫道短信，创蓝253短信，创蓝253 2.0短信，创蓝253 Json短信，重庆客亲通短信，容联.云通讯短信，MOCK测试短信。|
|[acooly-component-smsend-core](./acooly-component-smsend/acooly-component-smsend-core/README.md)|短信聚合组件||
|[acooly-component-sso](./acooly-component-sso/README.md)|后台管理单点登录|提供多系统集成单点登录能力（基于同主域名的共享cookies+JWS的反向filter认证/授权） ，主boss系统不需要依赖此组件，仅子boss系统依赖|
|[acooly-component-treetype](./acooly-component-treetype/README.md)|多级分类组件|此组件提供常用多级树形分类管理和应用的通用基础能力。|
|[acooly-component-wechat](./acooly-component-wechat/README.md)|微信接入组件|集成微信公众号认证体系，方便研发团队依赖此组件快速对接微信公众号，微信小程序；|
