<!-- title: 微信接入组件  -->
<!-- type: app -->
<!-- author: cuifuqiang-->
<!-- date: 2019-11-10 -->
## 1. 组件介绍

acooly-component-wechat 以acooly框架为基础, 集成微信公众号认证体系；方便研发团队依赖此组件快速对接微信公众号，微信小程序；


[微信公众平台开发](https://mp.weixin.qq.com);

[微信公众平台开发指南](https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421135319)

[微信网页授权](https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842)

### 1.1优势

* 快速完成微信公众号，微信小程序的对接
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


## 2.1 微信公众号对接操作说明

* step 1: 登录微信公众号平台 [网址](https://mp.weixin.qq.com/?token=&lang=zh_CN)
* step 2： 左边菜单，首页-->开发-->基本配置-->公众号开发信息
* step 3: 启用-开发者密码(AppSecret) 并保存对应的AppSecret
* step 4: 设置IP白名单, 配置服务器出口IP 
* step 5: 设置-服务器配置，URL：服务器回调地址（/wechat/webApi/callback.html）；Token；EncodingAESKey（随机生成）；消息加解密方式 [参考](https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421135319)
* step 6: 左边菜单，首页-->设置-->公众号设置-->网页授权域名
* step 7: 左边菜单，首页-->设置-->功能-->自定义菜单  组件默认访问地址 （ /wechat/webApi/index.html）

## 2.2 微信小程序对接操作说明

* step 1: 登录凭证校验 com.acooly.module.wechat.oauth.mini.WechatMiniClientService#loginAuthVerify



## 3.版本说明


#### 2019-03-31

* 1.微信组件对上一个版本不兼容升级，同时支持公众号，小程序对接
* 2.微信组件新增对小程序对接支持功能(微信用户信息（openid），无限获取小程序码等)
* 3.新增依赖acooly-component-ofile组件，满足 无限获取小程序码并本地化存储(小程序可以设置多个入口)



#### 2019-02-27

* 1.微信公众号快速对接授权
* 2.支持接口请求access_token 缓存
* 3.支持校验用户是否关注微信公众号判断
* 4.支持获取授权用户用户基本信息（已经关注了公众号，并且已经授权；未关注公众号，已经授权过）



## 4.基础能力说明

### 4.1 授权说明

* 参考 com.acooly.module.wechat.portal.WechatWebApiControl

*  授权访问地址：/wechat/webApi/index.html
*  微信回调地址：/wechat/webApi/backRedirect.html

### 4.2.1微信公众号 配置说明

*  组件开关 
* acooly.wechat.enable=true

*  微信openapi请求地址
* acooly.wechat.webClient.apiUrl=https://api.weixin.qq.com
*  
*  微信公众号的appid 详情 step 3
* acooly.wechat.webClient.appid=wxa6147f5ff0905222
*  
*   微信公众号的secret 详情 step 3
* acooly.wechat.webClient.secret=afbfe133f13cdec8d82c31340f1c1109
*  
* snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid）  
* snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。即使在未关注的情况下，只要用户授权，也能获取其信息 
* acooly.wechat.webClient.scope=snsapi_userinfo
*  
*  令牌(Token)  详情 step 5
* acooly.wechat.webClient.serverToken=HelloWorld
*   
*  微信授权后跳转业务系统地址(URL);可以参考 com.acooly.module.wechat.portal.WechatWebApiControl#backRedirect
* acooly.wechat.webClient.redirectUri=http://www.xxx.com/wechat/webApi/backRedirect.html
*   
*  微信重定向后会带上state参数
* acooly.wechat.webClient.state=hello,world


### 4.2.2微信小程序 配置说明
acooly.wechat.miniClient.apiUrl=https://api.weixin.qq.com
acooly.wechat.miniClient.appid=xxxx
acooly.wechat.miniClient.secret=xxxxxxxxxxxxxxxxxx

 

### 4.3.1 提供公众号基础功能说明

 * 参考 com.acooly.module.wechat.oauth.service.WechatClientService
 
	/**
	 * 用户授权 access_token
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
	
	
	
	
### 4.3.2 提供微信小程序基础功能说明
	/**
	 * 获取小程序全局唯一后台接口调用凭据（access_token）
	 * 
	 * @return
	 */
	public String getAccessToken();

	/**
	 * 登录凭证校验
	 * 
	 * @param jsCode
	 *               <li>调用接口获取登录凭证（code）
	 *               <li>详情参考 wx.login(Object object)
	 * 
	 * @return
	 */
	public WechatMiniSession loginAuthVerify(String jsCode);

	/**
	 * 获取小程序码，适用于需要的码数量极多的业务场景。
	 * 
	 * @param scene
	 *              <li>
	 *              最大32个可见字符，只支持数字，大小写英文以及部分特殊字符：!#$&'()*+,/:;=?@-._~，其它字符请自行编码为合法字符（因不支持%，中文无法使用
	 *              urlencode 处理，请使用其他编码方式）
	 * @param page
	 *              <li>必须是已经发布的小程序存在的页面（否则报错），例如 pages/index/index, 根路径前不要填加
	 *              /,不能携带参数（参数请放在scene字段里），如果不填写这个字段，默认跳主页面
	 * @return
	 */
	public String getMiniProgramImgCode(String scene, String page);
	
