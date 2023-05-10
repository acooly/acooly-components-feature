<!-- title: 内容审计 -->
<!-- name: acooly-component-content-audit -->
<!-- type: app -->
<!-- author: zhangpu -->
<!-- date: 2021-07-24 -->          
## 1. 组件介绍

提供文字，图片和视频的内容合规审计，防止软件或平台政策和政治风险。

* 产品：https://help.aliyun.com/product/28415.html?spm=a2c4g.477720.0.0.11d44c93pvRmWG
* 调试：https://next.api.aliyun.com/api/Green/2018-05-09/TextScan?lang=JAVA

## 2. 特性

* 文字（批量）的合规审计
* 图片（批量）的合规审计
* 审计调用，异常则表示未通过，异常中code为原因label，message为中文说明。


## 3. 集成使用

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

### 3.3 使用

正常集成后，直接注入接口`ContentAuditService`即可使用。API及参数说明请参考下面的接口javadoc

```java
public interface ContentAuditService {

    /**
     * 文字审计
     * <p>
     * 使用简介：
     * 本次封装主要是为简化使用，屏蔽接口中复杂的多层结构和逻辑判断。时间一个简单的功能：判断输入文字是否合格如果不合规返回原因。
     * <li>可同时支持多个文字的判断（批量，通过request.addTask）</li>
     * <li>只有所有通过审计才不会抛出异常，否则都抛出异常。</li>
     * <li>异常内容为违规的原因。一般结构为：code:违规label，message是中文说明，detail可选返回问题文字（*号mask）</li>
     * <li>支持配置自定义文字黑白名单（阿里云后台配置）</li>
     *
     * @param request
     * @return
     */
    void textScan(TextAuditRequest request);


    /**
     * 图片审计
     * <p>
     * 使用简介：
     * 本次封装主要是为简化使用，屏蔽接口中复杂的多层结构和逻辑判断。时间一个简单的功能：判断输入公网可访问图片URL是否合规，如果不合规返回原因（异常）。
     * <li>可同时支持多个图片URL的判断（批量，通过request.addTask）</li>
     * <li>只有所有通过审计才不会抛出异常，否则都抛出异常。</li>
     * <li>异常内容为违规的原因。一般结构为：code:违规label，message是中文说明</li>
     *
     * @param request
     */
    void imageScan(ImageAuditRequest request);
```

## 4. changelogs

### 5.0.0-SNAPSHOT.20210724

* 文字和图片（批量）的合规审计
* 审计调用，异常则表示未通过，异常中code为原因label，message为中文说明。

