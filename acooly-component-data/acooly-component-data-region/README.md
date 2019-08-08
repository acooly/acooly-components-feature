<!-- title: 全国区域组件 -->
<!-- type: app -->
<!-- author: zhangpu -->

## 1. 组件介绍
acooly-component-data-region组件是data组件的一个子组件，专用于提供统一的全国省市区三级区域数据及应用场景。以实现多端统一区域数据和应用。

>区域数据编码规则来源于身份证号码的前6位规则。

## 2、特性

* 统一数据库维护3000+区域数据, 组件自动初始化。
* 对外提供统一的区域树形结构，便于各端侧缓存直接使用。
* todo: 后台统一管理（CRUD,导入，导出）
* todo: 可在后台调整同级内的区域显示排序
* todo: 用于Web(PC和H5)的通用三级联动js库

>部分功能待开发！

## 3、数据

组件集成（pom依赖）后，自动开启应用。可以通过直接url访问获得树形结构的全国省市区数据。

```html
/acooly/data/region/tree
```
