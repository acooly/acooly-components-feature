<!-- title: pdf组件 -->
<!-- type: app -->
<!-- author: shuijing -->
## 1. 组件介绍

此组件提供html转换pdf能力

转换流程为：

* dataVO + freemarker模板 -> iTextRender -> 输出pdf

## 2. 使用说明


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
           
**以上资源需要放在项目assemble -> resources下,请勿放在其他包下面，否则会导致在linux环境下资源找不到情况**

### 2.2 接口使用


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