<!-- title: 加解密组件 -->
<!-- type: app -->
<!-- author: zhangpu -->
<!-- date: 2019-11-22 -->
## 简介

作为框架的所有安全相关的能力的工具组件。包括：签名，加解密等。防御相关的已独立为defence,不包括在该组件里面。主要是解决openapi等体系场景内的各个组件或系统都独立存在安全相关的能力代码，而且都是采用代码拷贝方式复用，还经常出现名字冲突等问题。

>为什么叫safety？
>只是因为security这个名字已被boss组件acooly-component-security使用。

## todo

* 待完成加解密部分

## 直接使用

请参考本组件的单元测试代码。单元测试中，分别对基于digest的签名，基于证书(pfx或jks)的签名和基于公私钥对的签名都做了测试（或者说是demo），这里都是采用直接编码方式使用。

集成方式为：依赖该组件，但是不要打开组件开关（spring不会自动扫描）


## Spring方式

请参考acooly-core-test模块中的SafetyTestController.

你可以运行工程后，访问：/safety/signDigest.html?signType=Sha256Hex&key=123
获得以下结果：

```js
{
    signature: "8343ada7565a844344ab07a2b21e42b28cc51096ae8bfc7b136e15e77357a744",
    plain: {
        1: "12312312",
        a: "12asdf",
        ccc: "中国人",
        asdf: "2323"
    },
    signType: "Sha256Hex",
    verify: true,
    key: "123"
}
```

## 集成说明

### 签名

所有的签名/验签的接口都采用Singer接口实现，组件内置了标准通用的集中签名/验签实现。主要包括: MD5Hex,Sha1Hex,Sha256Hex,Rsa,Cert，你可以直接在你的系统中使用。如下：

```java
Safes.getSigner("MD5Hex").sign(plain,key);
```

如果组件内的实现不能满足你的需求，你需要进行如下扩展：

1、在您的集成工程中新增CustomSigner实现Signer接口,假设provider为Custom
2、把你实现的CustomSigner放置到当前spring容器
3、采用上面通用的方法调用：Safes.getSigner("Custom")

### 秘钥加载

在简单情况下，你可以自己实现秘钥信息加载。比如，你可以通过配置文件的访问注入秘钥的信息，代码构建key(String,KeyStoreInfo或者KeyPair),然后用于签名或加密。

在同一项目中使用多个相同的秘钥（典型场景：网关系统），你需要通过KeyLoader接口方式加载。具体来说，你需要根据秘钥类型不同，选择实现：

* KeySimpleLoader : 外部动态加载（比如数据库）字符串秘钥（MD5等）
* KeyPairLoader: 外部加载公私钥对秘钥信息
* KeyStoreLoader: 外部加载Keystore和证书秘钥信息

你的实现代码请参考：

```java
@Slf4j
@Component
public class EmptyKeyPairLoader extends AbstractKeyLoadManager<KeyStoreInfo> implements KeyStoreLoader {

    @Override
    public KeyStoreInfo doLoad(String principal) {
        log.warn("KeyPairLoader的空实现，请在集成项目中实现：KeyPairLoader接口并配置到spring容器中");
        throw new SafetyException(SafetyResultCode.NOT_EXSIST_KEYLOADER);
    }

    @Override
    public String getProvider() {
        return "EmptyKeyPairLoader";
    }
}
```

> 关键点：同一个目标集成系统中（比如网关系统），KeyLoader的provider必须唯一，否则会覆盖冲突。推荐命名方式：providerName_xxxx

完成自定义KeyLoader的实现后，获取Key信息的方法同一如下：

1、注入：KeyLoadManager的springBean
2、调用：keyLoadManager.load("身份ID信息(比如：partnerId)","providerName")

```java
@Slf4j
@SpringBootApplication
@BootApp(sysName = "saftyTest")
public class KeyLoadManagerTest extends AppTestBase {

    static {
        Apps.setProfileIfNotExists("sdev");
    }

    @Autowired
    KeyLoadManager keyStoreLoadManager;


    @Test
    public void testLoadKeyStore() {
        keyStoreLoadManager.load("123123123", "EmptyKeyStoreLoader");
    }
}
```