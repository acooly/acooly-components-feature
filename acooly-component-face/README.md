<!-- title: 人脸识别组件  -->
<!-- type: h5/pc -->
<!-- author: xiyang -->
<!-- date: 2020-03-25 -->
## 1. 组件介绍

此组件提供人脸识别相关能力，目前资源接入方为百度

## 2. 使用说明


maven坐标：

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-face</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}`为框架最新版本或者购买的版本。

### 2.1 百度人脸识别服务类

    com.acooly.module.face.service.BaiduFaceService

### 2.2 如何使用人脸识别服务

#### 2.2.1. 设置人脸识别阀值

    参考com.acooly.module.face.FaceProperties
    
    通过百度云账户，人工智能->人脸识别->创建应用后获得
    acooly.face.appId=xxx
    acooly.face.apiKey=xxx
    acooly.face.secretKey=xxx

#### 2.2.2. 使用人脸识别

    可直接使用service，controller封装可参考
    com.acooly.module.face.web.BaiduFaceApiController
