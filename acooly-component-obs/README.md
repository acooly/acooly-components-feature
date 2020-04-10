<!-- title: 对象存储访问组件  -->
<!-- type: infrastructure -->
<!-- author: xiyang -->
## 1. 组件介绍

对象存储（Object-Based Storage, OBS)

抽象封装存储，使用一套存储接口，可以选择不同的存储方式:阿里OSS。目前支持阿里OSS

## 2. 使用说明

maven坐标：

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-obs</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}`为框架最新版本或者购买的版本。

### 2.1 OSS 

[OSS简介](https://help.aliyun.com/product/31815.html?spm=5176.doc31817.6.55.hkKLeQ)

#### 2.1.1 配置： 
    acooly.obs.provider=Aliyun
    #地域id，sts转换时需要使用。
    acooly.obs.aliyun.regionId=cn-shanghai
    acooly.obs.aliyun.protocol=http://
    acooly.obs.aliyun.accessKeyId=LTAIKG****xZdYJQ
    acooly.obs.aliyun.accessKeySecret=WVQ1t6yxlL*********um86
    #内网使用的endpoint（通常用于上传）
    acooly.obs.aliyun.endpoint=oss-cn-hangzhou-internal.aliyuncs.com
    #外网使用的endpoint（通常用于下载查看）
    acooly.obs.aliyun.endpointExternal=oss-cn-hangzhou.aliyuncs.com
    #项目默认使用的桶名称
    acooly.obs.aliyun.bucketName=xxxx
   
    #sts需要使用的配置
    #是否使用sts，当使用sts时，bucket内容应设置为公网不可访问
    acooly.obs.aliyun.stsEnable=true
    acooly.obs.aliyun.stsAccessKeyId=LTAIKG****xZdYJQ
    acooly.obs.aliyun.stsAccessKeySecret=WVQ1t6yxlL*********um86
    #url过期时间，单位秒（默认值60*60*24，默认一天过期）    
    acooly.obs.aliyun.stsExpiresTime=86400
    #指定角色的ARN。格式：acs:ram::$accountID:role/$roleName，阿里云ram控制台获取
    acooly.obs.aliyun.stsRoleArn=acs:ram::1822369285247857:role/aliyunosstokengeneratorrole
    #阿里云ram控制台获取
    acooly.obs.aliyun.stsRoleSessionName=AliyunOSSTokenGeneratorRole
    #sts公共参数，默认版本传入2015-04-01
    acooly.obs.aliyun.stsApiVersion=2015-04-01
####  2.1.2 接口：

    com.acooly.module.obs.ObsService

   