<!-- title: 安全后台管理组件   -->
<!-- type: app -->
<!-- author: zhangpu,qiubo,shuijing -->

管理系统框架组件
====
acooly-component-security
----

## 1. 简介

提供基于国家安全等保三级别的管理系统框架，包括：安全登录认证，基于角色，权限，资源管理的权限管理系统等。

## 2. 特性

* 基于角色的权限管理体系，支持URL和FUNCTION(功能或按钮)权限控制
* 多级菜单树定义，ajax加载和IFrame加载功能界面两种方式
* 支持EASYUI原生界面和AdminLTE风格界面
* 支持国家安全等保三要求的密码安全和认证安全规范
* 支付多因子认证（图片验证码+手机验证码）
* 支持单点登录集成基于微服务的子系统
* 可扩展JS和CSS库
* 支持EASYUI,LAYUI和AdminLTE三种UI风格开发。

## 2. 使用说明

maven坐标

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-security</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}`为框架最新版本或者购买的版本，如：4.2.0-SNAPSHOT。



## 3. FAQ

### 3.1 怎么开启登录短信验证码

* 配置开启短信验证
    `acooly.security.loginSmsEnable=true
    `
* 项目在引入security组件同时必须引入sms组件
    `<dependency>
          <groupId>com.acooly</groupId>
          <artifactId>acooly-component-sms</artifactId>
     </dependency>`
     
     短信验证码发送已经适配各个短信通道
     特殊的通道，比如 容联云通讯 可以修改短信模板id
     `acooly.sms.cloopen.loginVerifCodeTemplateId=183256
     `
* 可配置页面重新发送按钮间隔,单位秒，默认为30秒
     `acooly.framework.smsSendInterval=60
      `
* 开启短信验证码功能后，在新增user的时候，填写手机号码


### 3.2 怎么设置不需要需登录(授权、认证)访问所有boss页面

只需要添加配置：
`acooly.security.shiroFilterAnon=true
`

开启后shiro filter链都会设为不拦截，可在系统不需要任何授权、认证时开启（慎用）

### 3.3 设置单点登录启用dubbo权限校验服务

在使用单点登录的时候，主boss作为权限的主配置提供者，默认提供http接口校验权限`{@link com.acooly.module.security.web.RoleFacadeController.isPermitted}`
当使用dubbo作为权限校验时候，主boss应用必须开启服务提供者：
`acooly.security.enableSSOAuthzService=true
`

### 3.4 新增用户后，需要做其他业务操作，如何扩展？
       
               //异步事件处理器
               @Handler(delivery = Invoke.Asynchronously)
               public void handleCreateCustomerEventAsyn(UserCreatedEvent user) {
                   //do what you like
                   log.info("异步用户保存事件处理器{}",user.toString());
               }
 
### 3.5 增加扩展资源（js和css库）
配置参数：

```ini
acooly.framework.styles[0]=/manage/assert/plugin/layui/css/layui.css
acooly.framework.styles[1]=/manage/assert/plugin/layui/css/layui.css

acooly.framework.scripts[0]=/manage/assert/script/acooly.ui.layer.js
acooly.framework.scripts[1]=/manage/assert/script/acooly.portal.js
```

>注意：扩展加入自定义的js和css引用后，会在扩展主界面（基础）加载，扩展内全局可用，请注意不要与现有扩展内资源冲突。
   
### 3.6 设置功能权限控制按钮权限

组件本身的权限控制粒度为最小业务操作（可以理解为控制层的方法级别），但为了简化管理配置，我们一般使用URL权限通配方式，即：只需配置控制器的首页则该模块都具有权限。在实际生产中，URL通配方式可以解决80%以上的需求，但也有明确需要控制某个页面上具体一个功能权限的需求，这个时候，我们使用功能权限进行设置，框架本身是能很好支持的。

1. 功能权限配置

	首选功能权限需要定义：在现有的：系统管理菜单->资源功能中，新增资源时请选择FUNCATION类型，资源值格式为：entity:action格式（*:* 表示所有功能的所有操作）。然后正常授权给具体的角色即可。

	例如：我们需要控制会员管理界面的添加会员的权限，则可以定义FUNCTION的资源值为：**customer:create**

2. 功能权限开发

	因为是控制页面上的按钮，根据授权情况，是否显示，所有需要开发上进行一下操作：

	* **页面上**：原有的按钮HTML标签使用shiro的权限判断标签包装并根据当前用户授权情况判断是否显示该按钮。

	案例：acooly-showcase的客户管理功能：acooly-showcase/acooly-showcase-core/src/main/resources/META-INF/resources/WEB-INF/jsp/manage/showcase/customer.jsp

	```html
	<!-- 控制按钮权限：添加 -->
	<shiro:hasPermission name="customer:create">
		<a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/showcase/customer/create.html',entity:'customer',width:700,height:400,reload:true})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
	</shiro:hasPermission>
	```

	* **控制层**：[可选]，严谨的做法，需要控制该功能的控制层访问权限，方式用户通过URL直接访问跳过页面上的显示控制。在功能对应的控制层方法中，增加对应的权限检查代码。如下：

	```java
	SecurityUtils.getSubject().checkPermission("customer:create");
	```
	    
               
## 4.附录

### 开发资源

1. **easyui文档**：http://www.jeasyui.com/
1. **图标库**：
    boss后台的开发可以使用：fontawesome字体图标库，已引入到框架中。
    图标地址：http://www.fontawesome.com.cn/faicons/
    案例：
    ```html
    <i class="fa fa-flask fa-fw fa-lg fa-col" aria-hidden="true"></i>
    ```
2. **layui库**：已经引入，可以直接使用其组件。文档：https://www.layui.com/

### 风格

* acooly：v3标准/easyui标准风格
* acooly4：layui风格，AdminLTE风格


             