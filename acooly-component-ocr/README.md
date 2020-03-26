<!-- title: ocr文字识别组件  -->
<!-- type: h5/pc -->
<!-- author: xiyang -->
<!-- date: 2020-03-25 -->
## 1. 组件介绍

此组件提供ocr文字识别相关能力，目前接入有身份证识别，驾驶证识别，可继续扩展

## 2. 使用说明


maven坐标：

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-ocr</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}`为框架最新版本或者购买的版本。

### 2.1 百度ocr文字识别服务类

    com.acooly.module.ocr.service.OcrService

### 2.2 如何使用ocr文字识别服务

#### 2.2.1. 设置ocr文字识别配置

    参考com.acooly.module.ocr.OcrProperties
    
    通过百度云账户，人工智能->文字识别->创建应用后获得
    acooly.face.appId=xxx
    acooly.face.apiKey=xxx
    acooly.face.secretKey=xxx

#### 2.2.2. 使用人脸识别

    直接通过OcrService注入后使用
