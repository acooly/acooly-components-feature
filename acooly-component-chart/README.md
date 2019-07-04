<!-- title: 图表组件  -->
<!-- type: app -->
<!-- author: cuifuqiang ,xierui-->

## 1. 组件介绍

acooly-component-chart 以acooly框架为基础, 集成百度echarts图表；支持 折线图，柱状图，饼图；动态sql语法解析，动态转换为不同的图表展示；

参考：[echartsjs](http://www.echartsjs.com);  网址：http://www.echartsjs.com

### 1.1优势
* 快速开发报表图形化，简化Java代码编程
* 对java编程无基础要求，对sql基础语法略懂即可
* 动态sql语法解析，动态转换为不同的图表展示
* 支持日常图形格式：折线图，柱状图，饼图
* 一个图形主题下，支持多个不同种类图表展示，并支持图表排序
* 支持页面ajax定时访问主题，动态加载实时数据
* 支持 折线图，柱状图 相互转化，并支持下载



## 2. 使用说明

maven坐标：

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-chart</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}`为框架最新版本或者购买的版本。

## 2.1 访问连接说明

**支持使用iframe **

* 主题访问：/manage/module/echarts/index___{id}.html


* 主题项试图访问：/manage/module/echarts/chartItem___{type}___{chartItemId}.html
* type说明：line:折线图，bar:柱状图，pie:饼图


* 数据加载访问：
* 折线图：/manage/module/echarts/charDatat___line___{chartItemId}.html
* 柱状图：/manage/module/echarts/charDatat___bar___{chartItemId}.html
* 饼图：/manage/module/echarts/charDatat___pie___{chartItemId}.html


## 3.版本说明

#### 2019-03-21

* 1.支持数据值显示在图形上：（支持：折线图，柱状图，饼图；默认关闭）



#### 2018-10-10

* 1.支持日常图形格式：折线图，柱状图，饼图
* 2.动态sql语法解析，动态转换为不同的图表展示
* 3.页面支持定时访问，动态加载实时数据，最小循环时间为10秒，小于10秒则无定时功能




## 4.知识点

* x轴y轴坐标图 知识图
![x轴y轴坐标图](https://graph.baidu.com/resource/191ef90f5a3819c9eec1201539674644.jpg "xy轴")

### 4.1 设置说明

 * 折线图，柱状图：x轴，仅支持当个字段对应；y轴支持n个字段（根据业务复杂度执行设置）
 * 饼图 仅支持y轴支持n个字段（根据业务复杂度执行设置数量）请使用 limit n
 * 建议 折线图，柱状图设置sql语句查询结果集条数，饼图限制查询结果集为一条，请使用 limit 1
 
 

### 4.2 mysql时间语法（GROUP BY） 

* 年：GROUP BY YEAR(时间字段)
* 月：GROUP BY DATE_FORMAT(时间字段,'%Y-%m')
* 日：GROUP BY DATE_FORMAT(时间字段,'%Y-%c-%d')
* 星期：GROUP BY DATE_FORMAT(trans_day,'%Y-%u')


* 季度特殊处理：
* select concat(year(时间字段),'-',FLOOR((date_format(时间字段, '%m ')+2)/3),'季度') from table;
* GROUP BY YEAR(时间字段)*10+((MONTH(时间字段)-1) DIV 3) +1|


* 最近一个月
* SELECT * FROM table WHERE DATE_SUB(CURDATE(), INTERVAL 1 MONTH) <= date(时间字段);　　　


* 本月、当前月
* SELECT * FROM table WHERE DATE_FORMAT(时间字段,'%Y-%m') = DATE_FORMAT(CURDATE(),'%Y-%m');　　　


* 上个月
* SELECT * FROM table WHERE PERIOD_DIFF(DATE_FORMAT(NOW(),'%Y-%M'),DATE_FORMAT(时间字段,'%Y-%m')) = 1;　　


* 最近一周、7天
* SELECT * FROM table WHERE DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(时间字段);　　　


* 最近一天
* SELECT * FROM table WHERE DATE_SUB(CURDATE(), INTERVAL 1 DAY) <= date(时间字段);　　　


* 当天
* SELECT * FROM table WHERE to_days(时间字段) = to_days(now());　

