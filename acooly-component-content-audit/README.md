<!-- title: 内容审计 -->
<!-- name: acooly-component-content-audit -->
<!-- type: app -->
<!-- author: zhangpu -->
<!-- date: 2021-07-24 -->          
## 1. 组件介绍
提供文字，图片和视频的内容合规审计，防止软件或平台政策和政治风险。

## 2. 特性

* 文字（批量）的合规审计
* 图片（批量）的合规审计
* 审计调用，异常则表示未通过，异常中code为原因label，message为中文说明。

## 3. 集成配置

### 3.1 标准POM方式集成

maven坐标：

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-content-audit</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}` v5.x新增功能组件，例如：`5.0.0-SNAPSHOT`

### 3.2 配置组件参数

组件标准开关配置。

````ini
# mock设置为true则不会发送请求到提供方，默认全部成功
acooly.content.audit.mock=false
acooly.content.audit.aliyun.accessKey=XXXXXXX
acooly.content.audit.aliyun.secretKey=XXXXX
acooly.content.audit.aliyun.regionId=cn-shanghai
````

## 4. changelogs

### 5.0.0-SNAPSHOT.20210724

* 文字和图片（批量）的合规审计
* 审计调用，异常则表示未通过，异常中code为原因label，message为中文说明。

