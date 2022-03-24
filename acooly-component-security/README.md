<!-- title: 后台管理框架组件 -->
<!-- name: acooly-component-security -->
<!-- type: app -->
<!-- author: zhangpu,qiubo,shuijing -->
<!-- date: 2019-11-30 -->

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

### 2.1 Session模式

从5.0.0-SNAPSHOT.20220324开始，后台管理的session模式从spring-session(`acooly-component-web`组件内配置的redis-session模式)调整为Shiro-Redis-Session模式，Cookies只针对/manage有效。并提供了对Session有效期的参数控制。

```
# 会话存储到redis的过期时间（单位：分钟），默认8小时
acooly.security.session.redisTimeout=480
# 会话超时时间（单位：秒），默认30分钟
acooly.security.session.timeout=1800
# 会话过期检查时间间隔（单位：秒），默认30分钟
acooly.security.session.checkInterval=1800
```

> 会话有效期控制方法：
> 
1. 参数`acooly.security.session.timeout`为真实有效期。
2. `acooly.security.session.timeout`的时间（注意单位）必须小于等于`acooly.security.session.redisTimeout`；
3. 如非特殊情况，建议无效会话检测时间间隔参数`acooly.security.session.checkInterval`应该与`acooly.security.session.timeout`一致。



## 3. 使用说明

maven坐标

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-security</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}`为框架最新版本或者购买的版本，如：5.0.0-SNAPSHOT。

## 3. FQA

### 3.1 怎么开启登录短信验证码

后台登录短信验证是通过用户的注册手机号码下发短信验证码方式验证。

* 项目在引入security组件同时必须引入sms组件

```xml

<dependency>
    <groupId>com.acooly</groupId>
    <artifactId>acooly-component-sms</artifactId>
</dependency>
```

* 配置开启登录短信验证

```ini
# 开启登录短信认证
acooly.security.enable-sms-auth=true
# 登录短信验证码重复时长(秒)
acooly.security.sms-send-interval=60
```

短信验证码发送已经适配各个短信通道,特殊的通道，比如 阿里云，容联云通讯 可以修改短信模板id和内容签名.

以下是阿里云短信的demo配置

```ini
## 短信渠道配置
acooly.sms.provider=Aliyun
acooly.sms.aliyun.accountId=aaaaa
acooly.sms.aliyun.accessKeyId=AAAAAAAAAAAAAAAAAAAA
acooly.sms.aliyun.accessKeySecret=XXXXXXXXXXXXXXX
acooly.sms.aliyun.topicName=sms.topic-cn-hangzhou

## 登录短信模板和签名
acooly.sms.aliyun.loginCodeTemplate=SMS_187930527
acooly.sms.aliyun.contentSign=我是签名
```

### 3.2 怎么设置不需要需登录(授权、认证)访问所有boss页面

只需要添加配置：
`acooly.security.shiroFilterAnon=true
`

开启后shiro filter链都会设为不拦截，可在系统不需要任何授权、认证时开启（慎用）

### 3.3 设置单点登录启用dubbo权限校验服务

在使用单点登录的时候，主boss作为权限的主配置提供者，默认提供http接口校验权限`{@link com.acooly.module.security.web.RoleFacadeController.isPermitted}`
当使用dubbo作为权限校验时候，主boss应用必须开启服务提供者：
`acooly.security.enable-sso-auth=true
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

> 注意：扩展加入自定义的js和css引用后，会在扩展主界面（基础）加载，扩展内全局可用，请注意不要与现有扩展内资源冲突。

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

   或则：使用shiro的annotation

    ```java
           @RequiresPermissions("customer:create")
           @Override
           public String create(HttpServletRequest request, HttpServletResponse response, Model model) {
               return super.create(request, response, model);
           }
    ```

### 3.7 定义默认主题

在首次登陆时，默认的主题设置可以通过参数控制。

```ini
## 默认为acooly
acooly.framework.default-theme=acooly
```

### 3.8 单人登录

支持同用户单人登录，其他人登录后，先进先出的方式踢出已登录会话。

```ini
# 是否开启单用户登录和踢人功能（默认是开启的）
acooly.security.session.enableKickOut=true
# 开启单用户登录和踢人功能后，允许一个用户名同时登录的会话数量（默认为：1）
acooly.security.session.maxSessionPerUser=1
```


## 4.资源

### 4.1 开发

1. **EasyUI文档**：http://www.jeasyui.com/
1. **图标库**： boss后台的开发可以使用：fontawesome字体图标库，已引入到框架中。 图标地址：http://www.fontawesome.com.cn/faicons/
   案例：
    ```html
    <i class="fa fa-flask fa-fw fa-lg fa-col" aria-hidden="true"></i>
    ```
    
### 4.2 风格

* Acooly3：v3标准/easyui标准风格
* Acooly4：AdminLTE风格

## 5. changelog


### 5.0.0-SNAPSHOT.20220324

* 完成从spring-session-redis到shiro-session-redis方案的变更。
* 增加kickout单人登录限制功能（踢人）,同一用户名重复登录，会自动踢出前面登录的会话。
* 支持用户选择多个角色，调整现有权限管理配置的组为角色模式


### 5.0.0-SNAPSHOT.20220302

* 2022-03-02 - 修正：操作员管理自动生成姓名拼音修改时没更新的问题 - [zhangpu] 629b4c7f
* 2022-03-02 - 修正：系统操作员管理编辑界面中角色和组织结构的正确回显 - [zhangpu] ad3e1ef6
* 2022-02-14 - 修正：textarea的word倒计时功能在初始界面时未根据当前已有内容计算剩余计数的问题。 - [zhangpu] 68b70846
* 2022-02-12 - acooly.format.content方法中省略号和按钮图片间去除空格，解决按钮图标换行问题 - [zhangpu] de010d43
* 2022-01-16 - 新增动态渲染工具方法：renderDynamic - [zhangpu] d6b37a1a
* 2022-01-16 - 修正 $.acooly.framework.getFormItem的选择判断BUG - [zhangpu] 7d1e51bc

### 5.0.0-SNAPSHOT.20220110

* 2022-01-10 - 完成蓝色天空（paiggo）主题的开发 - [zhangpu] c746ea02
* 2022-01-08 - 为新增的主题paiggio调整easyui相关对应的样式，包括表格，查询条件等。具体项目可以使用参数来设置默认主题`acooly.framework.default-theme=paiggio` - [zhangpu] 87d8fee8
* 2021-12-29 - CMS组件内容管理，开放keycode功能，为文章配置唯一编码。 - [xiyang] e33e9013

### 5.0.0-SNAPSHOT.20211102

2021-11-02

* 2021-11-01 - 多媒体编辑器kindEditor增强：H5多图片上传，剪切板图粘贴上传，视频分享，手机浏览 - [zhangpu] e4ba708a
* 2021-11-02 - kindEditor相关封装代码迁移到`acooly.editor.js`，并保持原有代码的兼容性。

### 5.0.0-SNAPSHOT(20211004)

* 后台管理的系统管理所有功能都升级为ftl，包括：会员，角色，资源，组织结构等
* 增加select2ztree的组件，并扩展支持在框架内应用下拉树形结构选择（目前支持单元）。
* 优化重构系统样式管理和切换的代码为静态JS类方式。
* 更新ssoinclude，包括sso组件。

### 5.0.0-SNAPSHOT(20200428)

* 修复因重构验证码造成的系统功能：修改密码验证码验证错误BUG.
* 同时优化datagrid的fireSelectRow的弹出款显示为：acooly.messager

### 5.0.0-SNAPSHOT-20200114

2020-01-14

* 2020-01-14 - fixed：资源权限管理中，从下层拖动到顶层时无法正确报错位置的问题。 - [zhangpu] 763906e
* 2020-01-14 - fixed：PasswordStrength为低等级时，支持高等级的密码。例如：simple可支持：AA123!@#$模式的密码。

