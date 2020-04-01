<!-- title: 对象存储访问组件  -->
<!-- type: infrastructure -->
<!-- author: shuijing -->
## 1. 组件介绍

对象存储（Object-Based Storage, OBS)

抽象封装存储，使用一套存储接口，可以选择不同的存储方式:阿里OSS、fastdfs。目前支持OSS

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

    acooly.obs.provider=aliyun
    acooly.obs.aliyun.accessKeyId=LTAIKG****xZdYJQ
    acooly.obs.aliyun.accessKeySecret=WVQ1t6yxlL*********um86
    acooly.obs.aliyun.endpoint=http://oss-cn-hangzhou.aliyuncs.com
 

####  2.1.2 接口：

    com.acooly.module.obs.ObsService

#### 2.1.3 测试用例：

    com.acooly.core.test.web.ObsTestController

   