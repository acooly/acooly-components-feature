<!-- title: 权限管理(RBAC)组件 -->
<!-- name: acooly-component-rbac -->
<!-- type: app -->
<!-- author: zhangpu -->
<!-- date: 2022-10-11 -->

## 1. 简介

提供基于角色的权限管理数据结构（RBAC）+ Shiro的独立实现（非Web），主要用于负责门户前端（OpenApi或前后端分离）的多角色权限管控

* 基于角色的权限管理体系，支持URL和FUNCTION(功能或按钮)权限控制，同时支持资源菜单的Tree结构。
* 独立的Shiro线程安全访问机制，可与标准Web的Shiro后台认证和授权体系兼容。

## 2. 使用

组件集成后，主要通过三个方面进行使用。

### 2.1 权限判断

直接使用封装的线程绑定静态工具类`RbacShiroUtils`

### 2.2 `Shiro`的`SecurityManager`

直接使用静态工具类：`RbacSecurityUtils`

### 2.3 RBAC的数据访问

直接注入会员，资源，角色，组织结构对象服务访问。例如：用户授权的资源树形列表：`RbacResourceService.getAuthorizedResourceNode(...)`

## 3. TODO_LIST

* TODO:REDIS缓存，并要与security组件隔离

## 4. 特性

## 5. changelog

### 5.2.0-SNAPSHOT.20221011

* 采用memberNo方式的授权静态工具类：`RbacShiroUtils`
* 非受控管理的资源可以忽略权限控制(是否存在的判断不支持AntPath)
* 提供根据用户查询可访问资源的列表: `RbacResourceService.getAuthorizedResourceNode(...)`
