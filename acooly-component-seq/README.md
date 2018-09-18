<!-- title: 序列号生成器组件 -->
<!-- type: infrastructure -->
<!-- author: qiubo -->
## 1. 组件介绍

自增序号生成器，类似于oracle中的seq。

每次会从数据库中读取一批id，当这一批用完后，在从数据库中获取。

当有多个节点时，不同节点分配的批次不一样，仅能保证在单个jvm内递增。

比如：节点A获取0-1000，节点B获取1001-2000

## 2. 使用说明

    @Autowired
    private SeqService seqService;