<!-- title: 后台管理框架组件 -->
<!-- name: acooly-component-security -->
<!-- type: app -->
<!-- author: zhangpu,qiubo,shuijing -->
<!-- date: 2019-11-30 -->

## 1. 组件介绍
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

### 2.2 资源加载优化控制

针对后台管理框架基础JS和CSS资源的不断增大，现优化所有非必选特性的前端插件可通过参数配置是否加载，以实现人工方式控制资源加载数量。

> 目前测试效果：如果关闭所有可选插件，首页加载时间从1500ms降低到850ms，大致提升一倍性能。

默认情况下，本次优化的大多（部分参数使用场景固定可控的设置为false）参数都为true，特性表现为与以前兼容一致。

```ini
## layui，框架优化后，已提出了对layui框架的依赖，如果你的功能界面中有使用layui，建议打开，默认也是打开的。
acooly.framework.plugin.layui=true
## easyui的扩展功能，包括：datagrid-detailview，datagrid-dnd，datagrid-groupview，datagrid-statistics,
acooly.framework.plugin.easyuiExtension=true
## kingEdit的全功能，包括多图片上传
acooly.framework.plugin.kindEditor=true
## 首页顶部加载的进度条（看你喜好，SSO场景关闭）
acooly.framework.plugin.pace=false
## JS方式调用的剪贴板控制复制粘贴（建议按需加载，不全局加载）
acooly.framework.plugin.clipboard=false
## bootstrip提供的日期时间段，非目前模式使用插件
acooly.framework.plugin.dateRangePicker=false
## bootstrip风格的checkbox，按需使用
acooly.framework.plugin.icheck=false
## PDF在线浏览，调用方法: `$.acooly.file.play(...)`
acooly.framework.plugin.media=false
## 视频播放器支持 `$.acooly.file.play(...)`
acooly.framework.plugin.videoJs=false
## 等保要求的前端xss防御，没多少实际意义，平时关闭。
acooly.framework.plugin.xss=false
```

以上参数控制的是你当前服务的全局设置，同时支持SSO单点登录(`单点登录的客户端系统内设置参数控制`)。从该版本后，当你需要对应的功能支持时，你可用有两种方式获取插件支持。

* 全局开启，首页加载对应的资源文件，这种情况一般是你的项目大范围使用。
* 局部加载，在你使用的具体功能界面，通过`script`段引用加载对应的资源, 如果你选择这种方式，可以打开`acooly-component-security`组件的jar包内`resources/templates/manage/index_adminlte3.ftl`文件，搜索定义的参数名称（例如：`media`）,找到对应的资源文件，手动加载。

>注意：该版本特性的增加，调整了session中对象的结构，会照常已存在的redis缓存对账反序列化失败，如遇该清理，请清理本地浏览器缓存刷新即可。

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

### 3.9 AcoolyJs的debug

```ini
## 设置acoolyJS位debug模式，加载源代码，便于开发调试
acooly.framework.plugin.acooly-debug=true
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

### 5.2.0-SNAPSHOT.20230831...

* 2023-08-17 - 扩展datagrid支持动态调整列的显示和隐藏，并通过cookies个性化保存在本地。通过参数`acooly.framework.plugin.datagrid-ext=true`开启（默认是false，关闭的），开启后，可以通过在datagrid的表
    头上右键选择显示和隐藏列。另外两个需求暂时不支持： 
  * 列的动态宽度，本来都是支持的拖动的，但不提供保存，原则上来说，不需要，因为表格是根据列内容动态自适应的 
  * 列的显示顺序动态调整暂时不支持，一般每个列表的列显示顺序是有逻辑的，个性化调整的必要性不大。 
* 2023-08-13 - 整理文档，并编写脚本components.sh实现自动生产组件库文档列表功能。 - [zhangpu] 01ef75ef
* 2023-08-07 - 增加开源LICENSE - [zhangpu] 7404ef6a
* 2023-07-28 - 优化portallet的列表界面 - [zhangpu] 471a3763
* 2023-07-24 - 完成组织结构功能的增强：1、默认根据添加时间顺序排序，支持手动调整排序；2、支持每个组织结构节点挂载对应的负责人用户名，可用于通过部门找到对应的负责人。 - [zhangpu] dfa8f858
* 2023-07-19 - 修复-账户管理-查询组织结构数据（仅20条） - [cuifuq7] 8cc87e91
* 2023-07-19 - 增加：$.acooly.layout.addTabAndRender方法，支持动态添加form到tab中，并自动渲染。请参考feature组件的test模块下的customer.ftl页面上的测试 - [zhangpu] da87ae34
* 2023-07-09 - 删除冗余的kindeidtor资源 - [zhangpu] 83daf24c

### 5.2.0-SNAPSHOT.20230628

* 2023-06-28 - 新特性：收藏夹功能的开发，开启功能后，每个功能右上角都有五角星的收藏按钮；收藏后，可刷新在左侧顶部`收藏夹`主菜单中快速定位；同时可以在`系统管理`->`收藏夹`列表中调整排序。为保证兼容性，收藏夹功能通过配置参数`acooly.framework.enable-favorite=true`开启（默认为false：关闭）；开启使用，如果是已运行的工程，请执行jar包内`META-INF/database/security/mysql/security_favorite_upgrade.sql`的升级SQL；如果是新工程，则无需处理，初始化SQL已进行了初始化。 - [zhangpu] b4d2373e
* 2023-06-04 - fixed:SSO模式下（集成了SSO组件），关闭SSO`acooly.sso.enable=false`,会自动管理shiro的相关安全tag的远程认证功能，恢复使用本地。修正issue:#42 - [zhangpu] b72105c8

### 5.2.0-SNAPSHOT.20221210

* 2022-12-09 - 设置后台druid数据库监控功能，自动忽略CSRF防御 - [zhangpu] 6009a7fd
* 2022-12-09 - 增加Shiro的Session在线管理和查询功能，同时配置自动集成到默认的系统功能菜单中：`系统管理 -> 会话监控`` - [zhangpu] 99cf79c8
* 2022-12-07 - 清楚login控制方法内遗留的session创建代码 - [zhangpu] be066ef2
* 2022-12-07 - shiro-session优化。主要包括： 
  * 一、明确划分Session领域为`/manage/`下采用ShiroSession,其他采用spring-session(redis) 
  * 二、Shiro的Session内容优化精简，不考虑序列化（原有是JDK）情况下，默认登录后端情况下，从41K/session降低到1.2K/session。 
    * 1、session中的User对象从持久化状态调整为无状态（bean Copy）。 
    * 2、去除重复设置的session变量user，直接使用ShiroUtils.getSessionUser,也就是Shiro的subject.getSession。 
    * 3、从session中去除securityConfig,调整为Request Scope. 
  * 三、Session的redis序列化，增加看kryo模式，可通过配置`acooly.security.session.redis-serialize-type=Kryo`切换，为保持兼容性默认仍然jdk，切换序列化模式后，前端浏览器需要清理cookies缓存或重启浏览器。 
- [zhangpu] b61cce48
* 2022-12-05 - 优化：kindeditor的初始化参数：默认字体14px，字体列表增加8px和11px,默认换行为br - [zhangpu] cc6a1dc5
* 2022-12-04 - fixed: 白板问题。单点登录时，如果打开多个tab时，非当前tab的页面渲染高度为0，造成白板问题。初步解决方案是调整include业务的HTML布局结构和HTML标签错误解决，MOCK服务器端慢（3-5秒返回）的情况验证通过，
  待其他项目组验证。 - [zhangpu] dd64422d
* 2022-12-04 - fieed：CMS的预览功能无法正常显示。迁移到/manage/下，如果需要原来的/cms/路径下的预览，需要配置JPA的openSessionView地址。 - [zhangpu] ba046ed0
* 2022-12-04 - fixed：为控制ofile的上传为可控session验证，调整后台上传地址为:/manage/ofile/..., 并对权限及session认证逻辑进行对应的优化调整。兼容原有逻辑的情况下，保证基础文件上传的安全。 - [zhangpu] d602cb89

### 5.2.0-SNAPSHOT.20221123

* 2022-11-23 - security或SSO中，针对`acooly.framework.font-size`兼容ztree - [zhangpu] 1eb5669c
* 2022-11-23 - 在Security组件和SSO组件中替换JWTUtils实现为本地的jwts实现，去除redis白名单特性，直接使用jwt的过期时间为准，解决SSO场景认证服务和子服务不使用同一个redis造成认证授权失败的问题。 - [zhangpu] 6488185d
* 2022-11-21 - 优化后台的linkButton的disabled样式 - [zhangpu] 1185af89

### 5.2.0-SNAPSHOT.20221025

* 2022-10-25 - 修正收缩左边主菜单时候，右边主空间为resize的问题。 - [zhangpu] 856cd66e
* 2022-10-25 - security组件增加配置参数`acooly.framework.plugin.acooly-debug=false`以控制acooly.xxx.js是源代码加载还是编译压缩的`acooly.min.js`加载，以适应开发环境JS调试需求 - [zhangpu] 679bee30

### 5.0.0-SNAPSHOT.20220703

* 完成security的资源优化，包括对应的SSO，可以根据本文档中的`2.2 资源加载优化控制`说明对后台管理的资源进行精细化管理。 资源默认加载：`easyui扩展`,`layUI`和`kingEdit`，其他默认为不加载，但可以根据上面文档说明开启。
* 优化清除了框架中对`layui`的依赖，但保留应该程序支持layui能力
* 优化清除了框架对部分不必须要的资源依赖（如：`ionicons`）

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

