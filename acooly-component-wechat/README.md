<!-- title: 微信接入组件  -->
<!-- type: app -->
<!-- author: cuifuqiang-->

## 1. 组件介绍

acooly-component-wechat 以acooly框架为基础, 集成微信公众号认证体系；方便研发团队依赖此组件快速对接微信公众号；


[微信公众平台开发](https://mp.weixin.qq.com);

[微信公众平台开发指南](https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421135319)

[微信网页授权](https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842)

### 1.1优势

* 快速完成微信公众号的对接
* 简化微信公众号复杂的对接流程
* 支持接口请求access_token 缓存
* 支持校验用户是否关注微信公众号判断





## 2. 使用说明

maven坐标：

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-wechat</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}`为框架最新版本或者购买的版本。


## 2.1 微信对接操作说明

* step 1: 登录微信公众号平台 [网址](https://mp.weixin.qq.com/?token=&lang=zh_CN)
* step 2： 左边菜单，首页-->开发-->基本配置-->公众号开发信息
* step 3: 启用-开发者密码(AppSecret) 并保存对应的AppSecret
* step 4: 设置IP白名单, 配置服务器出口IP 
* step 5: 设置-服务器配置，URL：服务器回调地址（/wechat/webApi/callback.html）；Token；EncodingAESKey（随机生成）；消息加解密方式 [参考](https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421135319)
* step 6: 左边菜单，首页-->设置-->公众号设置-->网页授权域名
* step 7: 左边菜单，首页-->设置-->功能-->自定义菜单  组件默认访问地址 （ /wechat/webApi/index.html）


## 3.版本说明

#### 2019-02-27

* 1.微信公众号快速对接授权
* 2.支持接口请求access_token 缓存
* 3.支持校验用户是否关注微信公众号判断
* 3.支持获取授权用户用户基本信息（已经关注了公众号，并且已经授权；未关注了公众号，已经授权过）



## 4.基础能力说明

### 4.1 授权说明

* 参考 com.acooly.module.wechat.portal.WechatWebApiControl

*  授权访问地址：/wechat/webApi/index.html
*  微信回调地址：/wechat/webApi/backRedirect.html

### 4.2 配置说明

*  微信openapi请求地址
* acooly.wechat.apiUrl=https://api.weixin.qq.com
*  
*  组件开关 
* acooly.wechat.enable=true
*  
*  微信公众号的appid 详情 step 3
* acooly.wechat.appid=wxa6147f5ff0905222
*  
*   微信公众号的secret 详情 step 3
* acooly.wechat.secret=afbfe133f13cdec8d82c31340f1c1109
*  
* snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid）  
* snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。即使在未关注的情况下，只要用户授权，也能获取其信息 
* acooly.wechat.scope=snsapi_userinfo
*  
*  令牌(Token)  详情 step 5
* acooly.wechat.serverToken=HelloWorld
*   
*  微信授权后跳转业务系统地址(URL);可以参考 com.acooly.module.wechat.portal.WechatWebApiControl#backRedirect
* acooly.wechat.redirectUri=http://www.xxx.com/wechat/webApi/backRedirect.html
*   
*  微信重定向后会带上state参数
* acooly.wechat.state=hello,world
 

### 4.3 提供基础功能说明

 * 参考 com.acooly.module.wechat.oauth.service.WechatClientService
 
	/**
	 * 非用户授权 access_token
	 * 
	 * access_token是公众号的全局唯一接口调用凭据； 公众号调用各接口时都需使用access_token
	 * 
	 * @return
	 */
	public String getAccessToken();

	/**
	 * 清除系统缓存 access_token
	 */
	public void cleanAccessToken();

	/**
	 * 刷新 系统缓存 access_token（每日2000次）
	 */
	public String refreshAccessToken();

	/**
	 * 是否已经关注公众号
	 * 
	 * <li>true 已关注
	 * <li>false 未关注
	 * 
	 * @param openId
	 * @return
	 */
	public boolean isSubscribe(String openId);

	/**
	 * 获取用户用户基本信息
	 * <li>已经关注了公众号，并且已经授权
	 * 
	 * @param openId
	 * @return
	 */
	public WechatUserInfoDto getUserInfoBySubscribe(String openId);

	/**
	 * 获取用户用户基本信息
	 * <li>未关注了公众号，已经授权过
	 * 
	 * @param openId
	 * @return
	 */
	public WechatUserInfoDto getUserInfoByOpenId(String openId);
