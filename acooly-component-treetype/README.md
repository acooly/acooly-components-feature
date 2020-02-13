<!-- title: 多级分类组件 -->
<!-- type: app -->
<!-- author: zhangpu -->
<!-- date: 2019-11-01 -->
## 1. 组件介绍

此组件提供常用多级树形分类管理和应用基础能力。主要包括：

* 无限极支持
* 支持多主题，同一系统可以根据主题配置完全隔离的多个多级分类。
* 支持分主题，独立后台功能模块管理。比如：busiA的多级分类和busiB的多级分类可以共存在后台的两个独立菜单。功能和数据都隔离管理。
* 完整的后台管理集成
* 支持id和pid模式的树形结构管理
* 支持path方式的目录服务，每个节点都唯一归属一个目录path，并记录到数据节点上，可半like方式快速查找所有子。
* 支持单层内的移动排序（置顶和上移）
* 支持导出一个主题的整个树型结构JSON数据，服务器端有缓存，也可以下载到客户端直接缓存使用。
* [待支持] 全缓存逻辑控制

## 2. 集成配置

### 2.1 标准POM方式集成

maven坐标：

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-treetype</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}` v5.x新增功能组件

### 2.2 配置组件参数

组件标准开关配置。

````ini
acooly.tree-type.enable=true
````

### 2.3 后台管理配置

组件集成后，后台的分类管理界面依赖`acooly-component-security`组件，启动后，会自动生成默认主题（scheme=default）的多级分类管理功能，你可以根据需要改变权限。

菜单地址： 通用功能 --> 多级分类

如果你的系统需要多个多级分类，组件可以支持你通过主题（scheme）的方式隔离和管理多个多级分类。


1. 在你的系统中定义个枚举或常量，管理你的多个多级分类的编码和名称。例如：
	
	````java
	public enum BusiTypeEnum implements Messageable {
	   
		busiA("busiA","业务A多级分类"),
   		busiB("busiB","业务B多级分类");
   		
   		//...
	
	}
	```
	
2. 启动后，在BOSS后台的资源管理中，分别添加两个资源：
	
	* 业务A多级分类管理/AJAX模式/URL/显示：/manage/module/treeType/treeType/index.html?theme=<span style="color:red;font-weight:800;">busiA</span>
	* 业务B多级分类管理/AJAX模式/URL/显示：/manage/module/treeType/treeType/index.html?theme=<span style="color:red;font-weight:800;">busiB</span>
  	

>完成以上两步后，你可以在后台方便的同时管理多个多级分类。


## 3. 应用

### 3.1 接口服务

````java
public interface TreeTypeService extends EntityService<TreeType> {

    /**
     * 树形结构
     *
     * @param theme    主题（为空，则默认：default）
     * @param rootPath 根节点Path（为空，则默认：/）
     * @return
     */
    List<TreeType> tree(@NotEmpty String theme, String rootPath);

    /**
     * 树形结构
     *
     * @param theme  主题（为空，则默认：default）
     * @param rootId 根节点Id（为空，则默认：0）
     * @return
     */
    List<TreeType> tree(@NotEmpty String theme, Long rootId);


    /**
     * 树形结构
     * 默认主题为：theme
     *
     * @param rootPath 根节点Path（为空，则默认：/）
     * @return
     */
    List<TreeType> tree(String rootPath);

    /**
     * 树形结构
     * 默认主题为：theme
     *
     * @param rootId 根节点Id（为空，则默认：0）
     * @return
     */
    List<TreeType> tree(Long rootId);

    /**
     * 单层查询
     * （默认主题：default）
     *
     * @param parentId
     * @return
     */
    List<TreeType> level(Long parentId);

    /**
     * 单层查询
     *
     * @param parentId 为空：顶层（parentId=TOP_PARENT_ID）
     * @param theme    为空则：默认主题（theme=DEFAULT_THEME）
     * @return
     */
    List<TreeType> level(Long parentId, String theme);

    /**
     * 根据编码查询类型
     *
     * @param typeCode
     * @return
     */
    TreeType findByCode(String typeCode);

}
````



