<!-- title: 参数配置组件 -->
<!-- type: app -->
<!-- author: qiubo -->

## 1. 组件介绍

此组件提供配置管理和读取的能力。读取值时首先读取本地缓存，然后读取redis，最后读取数据库。

当后台更新配置时，会使用redis的pub/sub机制通知并更新本地缓存

## 2. 使用

### 2.1 读取配置

    com.acooly.module.config.Configs#getXXX  
       
### 2.2 运行时增加配置

        @Autowired
       private AppConfigManager configManager;
       
       AppConfig config=new AppConfig();
       configManager.create(config);