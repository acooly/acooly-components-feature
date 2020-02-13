<!-- title: pdf组件 -->
<!-- type: app -->
<!-- author: shuijing -->
<!-- date: 2019-11-08 -->
## 1. 组件介绍

此组件提供html or docx转换pdf能力 原有PDFService  扩展为-》 PdfGeneratorService

html转换流程为：

* dataVO + freemarker模板 -> iTextRender -> 输出pdf （特点：需按指定html格式开发ftl模版，所有样式表必须在html当页）

docx转换流程为:

* dataVO + word模板->freemaker引擎 + xdocreport ->输出pdf （特点：需按指定语法对word模版进行开发）

## 2. 使用说明

maven坐标：

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-pdf</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}`为框架最新版本或者购买的版本。

### 2.1 配置

* `acooly.pdf.templatePath=classpath:/pdf/templates/`

    模板路径模板默认为：/pdf/templates/，文件名作为key(带后缀)，文件内容为模板内容，模板写法参考
    [The Flying Saucer User's Guide](https://flyingsaucerproject.github.io/flyingsaucer/r8/guide/users-guide-R8.html)
    
* `acooly.pdf.imagePath=classpath:/pdf/images/`

    图片路径默认为：/pdf/images/,这个路径下面的图片，在模板上不需要再写路径，如：

          <img src="logo.png"/>

* `acooly.pdf.fontsPath=classpath:/pdf/fonts/` 
    自定义字体路径默认为：/pdf/fonts/，由于flying-saucer-pdf对中文支持不好，已默认添加Adobe思源宋体(可商用)，在模板中只需要指定family即可

           font-family: Source Han Serif SC;
           
* `acooly.pdf.templateType=docx` 
    扩展升级了templateType属性，如使用PdfGeneratorService默认使用docx模版，同时也支持html模版，原有PDFService依然可用，为兼容性升级，推荐使用PdfGeneratorService
       
**以上资源需要放在项目assemble -> resources下,请勿放在其他包下面，否则会导致在linux环境下资源找不到情况**

### 2.2 接口使用

* html

             @Autowired
             private PDFService pdfService;
            
             public void test(){
            
                        // 模板对象
                        PdfDemoVo pdfDemoVo = new PdfDemoVo();
                        //设置模板对应的值
                        pdfDemoVo.setPolicyNo("0000000000000000000000000");
                        pdfDemoVo.setHolderName("张三123abc");
                                                           ...
                                                           ...
                                                           ...
                        pdfDemoVo.setNames(names);
            
                        //模板名
                        String template = "pdftest.html";
                        // 生成pdf路径
                        File outputFile = new File("test.pdf");
                        // 生成pdf
                        pdfService.generate(template, pdfDemoVo, outputFile);
            
             }

              public void test(){

                                     // 模板参数map
                                     Map<String,Object> params = Maps.newHashMap();
                                     //设置模板对应的值
                                     params.put("policyNo","0000000000000000000000000");
                                     params.put("holderName","张三123abc");
                                                                        ...
                                                                        ...
                                                                        ...
                                     //模板名
                                     String template = "pdftest.html";
                                     // 生成pdf路径
                                     File outputFile = new File("test.pdf");
                                     // 生成pdf
                                     pdfService.generate(template, params, outputFile);

                          }


接口见：`PDFService`
详细demo请参考`PdfGeneratorTest`、`PdfServletTest`

* docx
    
                 @Autowired
                 private PdfGeneratorService pdfGeneratorService;
                     
                 pdfGeneratorService.generate("test.docx", map, os);

    word模版上需插入指定邮件合并域，可参考pdf/templates/test.docx， 参考链接：https://www.jianshu.com/p/4fe00aa02ea2  
    特别注意生成表格时注意的地方是在表格第一列前后各有一个标记，他们分别是  
    @before-row[#list agreement.holders as h]  
    @after-row[/#list]