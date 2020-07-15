<!-- title: 实名/卡认证组件  -->
<!-- type: app -->
<!-- author: qiubo -->
<!-- date: 2019-11-14 -->
## 1. 组件介绍

此组件提供实名认证、银行卡二三四要素认证、手机在网三要素认证能力。
实名认证服务地址：https://market.aliyun.com/products/56928004/cmapi014760.html?spm=5176.730005.productlist.d_cmapi014760.9QqrCb#sku=yuncode876000009
银行卡二三四要素认证服务地址：https://market.aliyun.com/products/56928004/cmapi013074.html?spm=5176.730005.0.0.1wZOy0#sku=yuncode707400003
企业工商信息查询：https://market.aliyun.com/products/57000002/cmapi031025.html#sku=yuncode2502500001
手机在网三要素认证：https://market.aliyun.com/products/57126001/cmapi033477.html?spm=5176.10695662.1996646101.searchclickresult.455a7071tixlIP#sku=yuncode2747700001
## 2. 使用说明

maven坐标：

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-certification</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}`为框架最新版本或者购买的版本。

### 2.1 服务类

    com.acooly.module.certification.CertificationService
    

### 2.2 配置

* `acooly.certification.bankcert.appCode=a08c5badb879******bc8d67e709b` 
  使用阿里云二三四要素认证 必填
  
* `acooly.certification.realname.appCode=a08c5badb879******bc8d67e709b` 
  使用阿里云实名认证 必填  
  
* `acooly.certification.enterprise-bsui-info.appCode=a08c5badb879******bc8d67e709b` 
  使用阿里云工商信息查询 必填
  
* `acooly.certification.phoneCert.appCode=a08c5badb879******bc8d67e709b` 
  使用阿里云手机在网三要素认证 必填  
  
* `acooly.certification.realnameurl=http://idcard.market.alicloudapi.com`
  可选，实名认证服务地址，当使用非阿里云时配置   
  
* `acooly.certification.bankCertProvider=ali`
  可选，银行卡二三四要素认证 提供者，目前默认为ali(阿里云提供商)  

* `acooly.certification.provider=ali`
  可选，实名认证 提供者，目前默认为ali(阿里云提供商)
  
* `acooly.certification.phoneCertProvider=ali`
  手机在网三要素认证 提供者，目前仅支持为ali(阿里云提供商) 必填
    




### 2.3 接口使用

    @Autowired private CertificationService certificationService;
            
    public void four(String realName, String cardNo, String certId, String phoneNum) {
        BankCardResult result =certificationService.bankCardCertFour(realName, cardNo, certId, phoneNum);
        log.info("银行卡四要素验证结果:{}", result.toString());
    }


            
              