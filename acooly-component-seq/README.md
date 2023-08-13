<!-- title: 序列号生成器组件 -->
<!-- type: app -->
<!-- author: qiubo -->
<!-- date: 2019-10-26 -->
## 1. 组件介绍
自增序号生成器，类似于oracle中的seq。 每次会从数据库中读取一批id，当这一批用完后，在从数据库中获取，当有多个节点时，不同节点分配的批次不一样，仅能保证在单个jvm内递增。

比如：节点A获取0-1000，节点B获取1001-2000

## 2. 使用说明

maven坐标：

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-seq</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}`为框架最新版本或者购买的版本。

## 2.1 使用服务

    @Autowired
    private SeqService seqService;
