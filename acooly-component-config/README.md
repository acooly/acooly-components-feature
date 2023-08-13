<!-- title: 参数配置组件 -->
<!-- type: app -->
<!-- author: qiubo -->
<!-- date: 2019-11-06 -->
## 1. 组件介绍
此组件提供配置管理和读取的能力。读取值时首先读取本地缓存，然后读取redis，最后读取数据库。

当后台更新配置时，会使用redis的pub/sub机制通知并更新本地缓存

## 2. 使用

maven坐标：

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-config</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}`为框架最新版本或者购买的版本。

### 2.1 读取配置

    com.acooly.module.config.Configs#getXXX  
       
### 2.2 运行时增加配置

    @Autowired
    private AppConfigManager configManager;
       
    AppConfig config=new AppConfig();
    configManager.create(config);

### 3. changelog
    2022-11-08 by xiyang
    增加configTopic配置项，用于多个不同系统需要共用config配置的场景，
    如appNmae=boss为管理后台，appName=app为真实app应用，
    而app应用需要使用管理后台的config配置，则app应用配置configTopic为boss
    bugfix:当监听redis值更新事件时，仅更新本地缓存，不再删除redis缓存
