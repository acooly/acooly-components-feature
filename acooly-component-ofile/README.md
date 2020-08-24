<!-- title: 文件上传组件   -->
<!-- type: app -->
<!-- author: zhangpu,qiubo -->
<!-- date: 2019-11-26 -->
## 1. 组件介绍

此组件提供文件上传(图片压缩)、下载、访问的能力

## 2. 使用说明

maven坐标：

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-ofile</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}`为框架最新版本或者购买的版本。

### 2.1 命名空间
 
 `acooly.ofile.storageNameSpace=taodai` 存储命名空间，默认为空，如果填写，文件存储路径会变为：`storageRoot/storageNameSpace`，如：`/data/media/taodai`


## 3. 特性

### 3.1 文件上传认证

ofile的文件上传都可以直接调用/ofile/upload.html方法，通过OFileUploadAuthenticate接口定义了安全认证。默认内置的认证方式支持两种，Session和摘要签名。

一个系统可以采用多个认证器，只要其中一个认证器通过认证则可上传

#### 3.1.1 Session方式认证
无需开发，内置：com.acooly.module.ofile.auth.OFileUploadSessionAuthenticate

你可以通过配置文件定义行为：

```ini
# 是否打开session认证
acooly.ofile.checkSession=true
# 你的系统内的定义的需要验证的Session变量名称
acooly.ofile.checkSessionKey=YOUR_SYSTEM_SESSION_NAME
```
#### 3.1.2 配置方式的签名认证

应对App的简单场景，采用固定的预定accessKey和secretKey签名认证。无需开发，内置：com.acooly.module.ofile.auth.ConfiguredSignatureOFileUploadAuthenticate

你可以通过配置文件定义行为：

```
acooly.ofile.configuredSignAuthEnable=true
acooly.ofile.configuredSignAuthAccessKey=YOUR_ACCESSKEY
acooly.ofile.configuredSignAuthSecretKey=YOUR_SECRETKEY
```

支持标准的multipart/form-data方式的form提交上传文件。参数如下：

```html
<form enctype="multipart/form-data" method="post">
    <input name="accessKey" value="test" type="text">
    <input name="fileName" value="xxxxx" type="file">
    <input name="timestamp" value="yyyyMMddHHmmss" type="text">
    <input name="sign" value="w87a6sdf87a6s78df6a87sdfdf">
</form>
```

>1. timestamp请客户端每次区当前时间。
>2. sign的计算方法请参考下方的”签名算法“
>3. 其他非web客户端（小程序，App）可构造标准form的（multipart/form-data）模式上传。

**签名算法**：

 ```java
String signWaiting = "accessKey=" + accessKey + "&fileName=" + fileName + "&timestamp=" + timestamp + secretKey;
// md5的hex编码字符串
String sign = DigestUtils.md5Hex(signWaiting);
 ```


#### 3.1.3 自定义签名认证

在你的系统内实现OFileUploadAuthenticate接口，加入到spring容器即可。

*特别的需要说明，如果你需要与OpenAPI集成统一的认证秘钥体系，你可以继承com.acooly.module.ofile.auth.AbstractSignatureOFileUploadAuthenticate抽象类，实现getSecretKey(String accessKey)方法即可（注入AuthInfoRealm获取accessKey对应的secretKey）。*

#### 3.1.4 上传图片自动加水印图片、文字

应于很多场景，图片上传完的时候是需要加水印的，一种是水印图片，一种是水印文字

* 水印图片 上传请求参数需要传入 watermarkImage = true，会自动给图片加上图片水印，配置如下

```
#开启水印图片，默认ture
acooly.ofile.watermarkimage.enable=true

#水印图片路径
acooly.ofile.watermarkimage.markImageFilePath=/Users/aalin/downloads/about-1.png

#位置x轴
acooly.ofile.watermarkimage.x=30

#位置y轴
acooly.ofile.watermarkimage.y=30
```

* 水印文字 上传请求参数需要传入 watermarkText = true，会自动给图片加上水文字水印，配置如下

```
#开启后，上传的图片自动加水印文字，上传请求参数需要传入 watermarkText = true
acooly.ofile.watermarktext.enable=true

#水印文字
acooly.ofile.watermarktext.markText=\u8fd9\u662f\u6c34\u5370\u6587\u5b57

#字体大小
acooly.ofile.watermarktext.fontSize=16

#透明度 0-1
acooly.ofile.watermarktext.alpha=1

#位置x轴
acooly.ofile.watermarktext.x=10

#位置y轴
acooly.ofile.watermarktext.y=10
```

请求路径示例：`http://127.0.0.1:8081/ofile/upload?watermarkImage=true&watermarkText=true`

#### 3.1.5 上传图片进行缩放操作

为了节省存储空间，可以对用户上传的图片进行缩放，以减少图片的大小。

* 相关配置如下

```
#开启图片缩放功能，默认为false
acooly.ofile.resizePicture.enable=true

#oversize=true，如果原始图片宽或高小于设定值，不进行操作，大于设定值则对图片进行缩小操作。
#oversize=false，如果原始图片宽或高小于设定值，对图片进行放大操作，大于设定值则对图片进行缩小操作。
#默认值为true
acooly.ofile.resizePicture.oversize=true

#图片最大宽度
acooly.ofile.resizePicture.width=1500
#图片最大高度
acooly.ofile.resizePicture.height=1500
```

缩放规则：缩放操作是将图片的最长边 max(width,height) ，设定为指定值进行等比缩放。例如原始图片分辨率为3000x2000，按实例中的配置进行缩放操作后，图片分辨率为1500x1000，而不是1500x1500。


## 4. 集成obs能力
### 4.1 说明
ofile组件内部集成了obs相关能力，通过配置开启acooly.ofile.storageType=OBS，则默认使用obs上传，同时也可以通过参数化进行控制如http://127.0.0.1:8081/ofile/upload.html?storageType=OBS
默认obs相关实现已经定义，对于单个应用使用单bucket时，无需进行任何开发，仅需完成obs配置。
### 4.2 能力扩展
OfileSupportService提供文件上传相关能力的扩展，
例如一个应用有多个bucket的情况，需要由业务定制实现OfileSupportService接口，默认实现路径com.acooly.module.ofile.support.DefaultOfileSupportService，根据不同的业务请求返回不同的bucket。
 
