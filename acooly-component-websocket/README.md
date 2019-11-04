<!-- title: websocket接入组件  -->
<!-- type: app -->
<!-- author: cuifuqiang-->

## 1. 组件介绍

acooly-component-websocket 以acooly框架为基础封装而成。采用redis做分布式缓存存储结构，保障多节点部署消息的实时性，可靠性和安全性。本组件与业务系统完全解耦，不参与业务处理，消息交互为双向事件处理机制。

- WebSocket是一种在单个TCP连接上进行全双工通信的协议; 
- WebSocket使得客户端和服务器之间的数据交换变得更加简单，允许服务端主动向客户端推送数据。
- 在WebSocket API中，浏览器和服务器只需要完成一次握手，两者之间就直接可以创建持久性的连接，并进行双向数据传输。


### 1.1 WebSocket优势

-  数据大小的控制开销；
-  报文交互的实时性；
-  长时间保持连接状态；
-  二进制报文结构支持；
-  支持子协议扩展；
-  支持内容上下文；

### 1.2 传统模式实现（WebSocket有较好的优势）
多数网站为了实现推送技术，所用的技术都是 Ajax 轮询。轮询是在特定的的时间间隔（如每1秒），由浏览器对服务器发出HTTP请求，然后由服务器返回最新的数据给客户端的浏览器。

- 1. 增加浏览器与服务器的交互次数
- 2. HTTP请求较长的头部报文，浪费带宽资源
- 3. 服务端数据数据需要 数据库的支持，从而增加数据库的压力（redis缓存可以减压，开发成本较高）
- 4. 无法充分利用服务器内存做效率优化（分布式部署）



### 1.2 解决核心问题（本组件）
- 1.数据实时性
	 -	客户端与服务端建立连接后，服务端随时可发送信息到客户端，(需要客户端按照业务报文解析)

- 2.分布式部署
	- WebSocket session是非序列化对象，无法采用redis做对象缓存，本组件采用单机服务器内容共享机制，由redis消息订阅事件达到分布式session共享的作用，保障多节点消息实时送达

- 3.多端通讯
	- 客户端为手机端，手机端的消息请求到服务器，经过服务端业务处理后，可实时推送到客户端（浏览器）
	
- 4.报文格式化	
	- 客户端请求与接受报文格式为json,服务端处理客户为map;
	
- 5.与业务系统完全解耦	



## 2. 使用说明

maven坐标：

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-websocket</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}`为框架最新版本或者购买的版本。


## 3.接入方案

- 交互体验模式参考：/test/websocket/index.html
- HTML5 WebSocket开发说明[HTML5 WebSocket](https://www.runoob.com/html/html5-websocket.html?_blank)


### 3.1 文本接入协议

#### 3.1.2 服务端 http协议： 
- 请求地址：ws://127.0.0.1:8080/websocket/text/{businessKey}/{businessType}

#### 3.1.2 服务端 https协议：
- 请求地址：wss://127.0.0.1:8080/websocket/text/{businessKey}/{businessType}

####  3.1.3 服务端 https协议


- 固定地址：/websocket/text/
- businessKey：业务参数唯一性
- businessType：业务类型定义；业务参数定义

例如: 
- ws://127.0.0.1:8080/websocket/text/activity_12/activity
- ws://127.0.0.1:8080/websocket/text/activity_12/game

- activity_12:活动id（业务id唯一）
- activity，game 业务类型（活动，游戏）


## 组件设置


	public static final String PREFIX = "acooly.websocket";

	/**
	 * 开关（默认 打开）
	 */
	private Boolean enable = true;

	/**
	 * websocket 消息订阅渠道
	 * <li>1.用于消息订阅key
	 * 
	 */
	private String subscribeKey = "websocket_subscribe_channel";

	/**
	 * 是否支持群发消息（默认关闭）
	 */
	private Boolean groupMessage = false;

	/**
	 * webSocket 消息连接过期时间（小时）
	 */
	private Long timeOut = 2L;



## 版本说明

####v1:2019-11
组件基本功能：

- 支持text报文格式，前端数据可格式为 json，服务端处理格式为map
- 支持分布式部署方案，多节点消息发送与接受（解决session非序列化）
- 与业务系统完全解耦，双向消息同步采用事件机制
- 支持消息单个（包括 定向），多个 目标客户端消息发送
- 支持并采用分布式锁，保障客户端节点数 安全，准确 达到高可用模式













