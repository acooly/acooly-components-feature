<!-- title: 数据-IP查询 -->
<!-- name: acooly-component-data-ip -->
<!-- type: app -->
<!-- author: zhangpu -->
<!-- date: 2019-11-02 -->

## 1. 组件介绍

acooly-component-data-ip组件是data组件的一个子组件，专用于提供全球IP查询判断相关服务。

## 2、特性

* IP段数据来源：http://www.ipdeny.com
* 缓存策略：每天更新一次
* 判断指定公网IP是否某个国家的IP（如：判断是否中国IP）

## 3、使用方式

### 3.1、引入集成

```xml
<dependency>
    <groupId>com.acooly</groupId>
    <artifactId>acooly-component-data-ip</artifactId>
</dependency>
```

> 最新推荐版本由框架统一管理，可直接parentPom设置。

### 3.2、核心接口

请直接在目标工程注入服务`com.acooly.component.data.ip.service.IpSearchService`,调用判断方法。

接口定义如下：

```java
public interface IpSearchService {
    /**
     * 判断IP是否在指定国家范围
     *
     * @param locale   国家
     * @param publicIp
     * @return
     */
    Boolean isInRange(Locale locale, String publicIp);

    /**
     * 判断是否中国IP
     *
     * @param publicIp
     * @return
     */
    default Boolean isChinaIp(String publicIp) {
        return isInRange(Locale.CHINA, publicIp);
    }
}
```

### 3.3、调用案例

团队内部请直接参考单元测试：`com.acooly.module.test.dataip.DataIpTest` 

```java
@Test
public void testIpSearch() {
    // 中国大陆IP
    String publicIp = "125.85.16.220";
    log.info("Test {} is China ip : {}", publicIp, ipSearchService.isChinaIp(publicIp));
    // 中国香港IP
    publicIp = "47.57.232.42";
    log.info("Test {} is China ip : {}", publicIp, ipSearchService.isChinaIp(publicIp));
}
```

## 4. changelog

### 5.0.0-SNAPSHOT.20211004

* 首次发布版本
* 判断指定公网IP是否某个国家的IP（如：判断是否中国IP，`ipSearchService.isChinaIp(publicIp)`）
* IP段数据来源：http://www.ipdeny.com
* 缓存策略：每天更新一次
