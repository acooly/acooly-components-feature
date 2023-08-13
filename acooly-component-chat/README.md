<!-- title: IM聊天组件  -->
<!-- type: app -->
<!-- author: cuifuqiang -->
<!-- date: 2019-11-12 -->
## 1. 组件介绍
封装极光IM聊天组件。

登陆访问地址：http://127.0.0.1:8080/im/index.html#/login


	 * <li>使用场景: IM聊天、社交
	 * <li>面向对象: 用户、帐号
	 * <li>消息对象: 用户之间互相交流 s
	 * <li>发送方式: 单聊、群聊


## 2. 使用说明

maven坐标：

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-chat</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}`为框架最新版本或者购买的版本。

组件开关<br/>
acooly.im.chat.enable=true

极光IM配置地址<br/>
acooly.im.chat.gateway=https://api.im.jpush.cn

极光IM appKey<br/>
acooly.im.chat.appKey=f0c5f57dd1733b00f5a36957

极光IM masterSecret<br/>
acooly.im.chat.masterSecret=ce1f57ecef53e776b54d985a


Android：app下载体验地址：<br/>
[百度网盘](https://pan.baidu.com/s/1U0Tk5NBpCEGkjw_Apb8MMw)   
密码：zzy1
	 

## 参考：

* [Android IM SDK 开发指南](https://docs.jiguang.cn/jmessage/client/im_sdk_android/) 

* [IOS IM SDK 开发指南](https://docs.jiguang.cn/jmessage/client/im_sdk_ios/) 

##支持功能：
* 1.开户注册（普通用户，管理员用户）；修改密码，修改用户信息，用户在线状态，账户状态管理（启用，禁用）

* 2.强制聊天（管理员用户作为发起方）

* 3.支持在线客服模式，由服务端动态指定在线客服主动发起聊天

* 4.接口类：com.acooly.module.chat.jchat.service.ChatService


## 版本：
#### 2018-09-03

1.开户注册（普通用户，管理员用户）；修改密码，修改用户信息，用户在线状态，账户状态管理（启用，禁用）

2.强制聊天（管理员用户作为发起方）

3.客服消息模板定制
	


