<!-- title: 单点登录组件 -->
<!-- type: app -->
<!-- author: shuijing -->
## 1. 组件介绍

此组件提供多系统集成单点登录能力

主boss系统不需要依赖此组件，仅子boss系统依赖

## 2. 使用说明

maven坐标：

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-sso</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}`为框架最新版本或者购买的版本。

### 2.1 配置组件参数

配置组件参数,如下：

   * `acooly.sso.ssoServerUrl=http://boss.acooly.com:8070/manage/login.html` 必填，登录服务端地址，url为全路径，ssoServer为主boss系统，统一管理认证权限，可只添加security组件单独启动
   * `acooly.sso.ssoExcludeUrl=/manage/logout.html,/manage/error/**` 可选，不需要登录认证的地址，ant路径匹配规则， 用,分割[Ant-style path patterns](https://github.com/spring-projects/spring-framework/blob/master/spring-core/src/test/java/org/springframework/util/AntPathMatcherTests.java)
   * `acooly.sso.AuthorizationCacheTime=15` 可选，客户端资源权限缓存时间，在主boss验证资源用户的资源权限后缓存的时间,单位分钟,默认10分钟
   * `acooly.sso.enableDubboAuthz=true` 可选，启用dubbo方式去主boss校验资源权限，启用情况需要项目主动依赖dubbo组件`acooly-component-dubbo`，默认false采用http请求主boss校验资源权限
 
* PS: 当使用dubbo作为权限校验时候，主boss应用必须开启服务提供者，配置：`acooly.security.enableSSOAuthzService=true`。需要单点登录的子系统配置:`acooly.sso.enableDubboAuthz=true`*
                                            `
### 2.2. 子系统集成单点登录步骤

#### 业务页面

引入公共静态文件(只需要主页面添加，如：point.jsp需要添加，子页面pointEidt.jsp,pointImport.jsp,pointShow.jsp不用添加)

* freemarker页面

        <#if ssoEnable>
            <#include "*/include.ftl">
        </#if>
* jsp页面 

        <c:if test="${initParam['ssoEnable']=='true'}">
               <%@ include file="/WEB-INF/jsp/manage/common/ssoInclude.jsp" %>
        </c:if>
   
#### 资源菜单 

加载方式由之前的 ajax 改为 IFrame

#### 资源菜单 资源串 

需要改为全路径，如：`http://point.acooly.com:8080/manage/module/point/point/index.html`
   
### 2.3.  注意事项
   
1. 域名支持二级域名，如登录服务器地址`acooly.sso.ssoServerUrl=http://boss.acooly.com/manage/login.html` 那么只有 .acooly.com 子域名才支持 sso 登录
2. 只有子系统才需要添加此组件，主boss不用添加 比如：主boss为`boss.acooly.com` 子系统是`acooly.com`的子域名:`openapi.acooly.com,cms.acooly.com,scheduler.acooly.com,mail.acooly.com` 
3. 本地测试可本机添加hosts
4. 启用sso组件后，获取User两种方式 
  1、`User user=ShiroUtils.getCurrentUser();` 
  2、`User user = (User) request.getAttribute(JWTUtils.CLAIMS_KEY_SUB)`