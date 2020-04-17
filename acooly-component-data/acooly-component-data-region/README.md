<!-- title: 数据-全国区域 -->
<!-- type: app -->
<!-- author: zhangpu -->
<!-- date: 2019-11-02 -->
## 1. 组件介绍
acooly-component-data-region组件是data组件的一个子组件，专用于提供统一的全国省市区三级(四级)区域数据及应用场景。以实现多端统一区域数据和应用。

>区域数据编码规则来源于身份证号码的前6位规则。

## 2、特性

* 统一数据库维护3000+区域数据, 组件自动初始化。
	<br/>
	- 初始化数据支持两种：
	<br/>
	- 自动导入数据：/META-INF/database/data-region/mysql/ddl.sql（组件初始化安装，支持 省-市-县  三级联动）
	<br/>
	- 手动导入数据：/META-INF/database/data-region/mysql/data_region.rar （自行解压执行覆盖，支持 省-市-县-街道 四级联动）

* 对外提供统一的区域树形结构，便于各端侧缓存直接使用。
* todo: 后台统一管理（CRUD,导入，导出）
* todo: 可在后台调整同级内的区域显示排序
* todo: 用于Web(PC和H5)的通用三级联动js库

>部分功能待开发！

## 3、使用方式

   demo:  /demo/area_select.html

## 4、数据

组件集成（pom依赖）后，自动开启应用。可以通过直接url访问获得树形结构的全国省市区数据。

```html
/acooly/data/region/tree
```

