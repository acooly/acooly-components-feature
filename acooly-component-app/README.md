<!-- title: 前端App组件 -->
<!-- type: app -->
<!-- author: zhangpu -->
<!-- date: 2019-10-30 -->
## 1. 组件介绍

提供app常用接口，并通过openapi暴露服务

服务包括：

* welcomeInfo 欢迎信息
* bLog 业务日志收集
* bannerList 横幅广告
* appMessageList 推送消息列表
* appLatestVersion 最新版本
* appCrashReport 崩溃上报

## 2. 使用说明

maven坐标：

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-app</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}`为框架最新版本或者购买的版本。


### 3.jpush扩展功能说明

.   新增配置文件：tags【用户标签】；iosJump【苹果手机跳转】；androidJump【安卓手机跳转】

. tags【用户标签】：支持动态标签tag推送， 一次推送最多 20 个  ；前提：极光推送-此功能为VIP用户功能

.  手机跳转：支持手机客户端跳转页面设置，需要 手机客户端定制开发；ios解析参数：iosJump；android解析参数：androidJump

#### jpush扩展配置如下
  
		
		//[购买此功能为VIP用户功能]
		//tag配置如下	acooly.app.jpush.tags[key]=value
		acooly.app.jpush.tags[vip]=VIP
		acooly.app.jpush.tags[社区]=社区
		
		//iosJump配置如下	acooly.app.jpush.iosJump[key]=value
		acooly.app.jpush.iosJump[ios加油首页]=fuel://com.cartechfin.efillingstation/app/main#0
		acooly.app.jpush.iosJump[ios加油-订单列表]=fuel://com.cartechfin.efillingstation/app/main#2
		
		
		//androidJump配置如下	acooly.app.jpush.androidJump[key]=value
		acooly.app.jpush.androidJump[android加油-首页]=ycyrefuel://ycysaas.com/c_fule_index
		acooly.app.jpush.androidJump[android加油-订单列表]=ycyrefuel://ycysaas.com/c_fule_order_list
