<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<c:if test="${initParam['ssoEnable']=='true'}">
    <%@ include file="/WEB-INF/jsp/manage/common/ssoInclude.jsp" %>
</c:if>
<script type="text/javascript">
$(function() {
	// $.acooly.framework.registerKeydown('manage_eavAttribute_searchform','manage_eavAttribute_toolbar');
});

</script>
<div class="easyui-layout" data-options="fit : true,border : false">

  <!-- 方案列表 -->
  <div data-options="region:'west',border:true,split:true" style="width: 200px;" align="left">
        <div class="easyui-panel" style="padding: 0 5px;">
            <a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="manage_scheme_tree_add()"> 添加方案</a>
        </div>
        <div id="manage_eavScheme_menu" class="ztree"></div>
  </div>


  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_eavAttribute_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/module/eav/eavAttribute/listJson.html" toolbar="#manage_eavAttribute_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="40" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="asc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="id" sortable="true">id</th>
            <th field="schemeName">方案名称</th>
            <th field="tag">分组标签</th>
			<th field="name">属性名称</th>
			<th field="displayName">展示名称</th>
            <th field="attributeType" formatter="mappingFormatter">属性类型</th>
			<th field="nullable">是否为空</th>
            <th field="showPerms">显示类型</th>
            <th field="showFormat" formatter="mappingFormatter">显示格式</th>
			<th field="minimum" data-options="formatter:function(v,r,i){ var str=''; if(!v){v=0;} str+=v; if(r.maximum){str+='-'+r.maximum;} return str; }">数字范围</th>
			<th field="minLength" data-options="formatter:function(v,r,i){ var str=''; if(!v){v=0} str+=v; if(r.maxLength){str+='-'+r.maxLength;}  return str; }">属性长度</th>
			<th field="regex">验证正则</th>
			<th field="enumValue">枚举值</th>
            <th field="defaultValue">默认值</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_eavAttribute_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_eavAttribute_action" style="display: none;">
      <a onclick="$.acooly.framework.edit({url:'/manage/module/eav/eavAttribute/edit.html',id:'{0}',entity:'eavAttribute',width:800,height:600});" href="#" title="编辑"><i
              class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.show('/manage/module/eav/eavAttribute/show.html?id={0}',600,600);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.remove('/manage/module/eav/eavAttribute/deleteJson.html','{0}','manage_eavAttribute_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_eavAttribute_toolbar">
        <input type="hidden" id="manage_eavAttribute_search_schemeId" name="search_EQ_schemeId"/>
        <%--<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false" onclick="$.acooly.framework.search('manage_eavAttribute_searchform','manage_eavAttribute_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>--%>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="manage_eavAttribute_create()"><i
              class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/module/eav/eavAttribute/deleteJson.html','manage_eavAttribute_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>
      <%--<a href="#" class="easyui-menubutton" data-options="menu:'#manage_eavAttribute_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>--%>
      <%--<div id="manage_eavAttribute_exports_menu" style="width:150px;">--%>
        <%--<div onclick="$.acooly.framework.exports('/manage/module/eav/eavAttribute/exportXls.html','manage_eavAttribute_searchform','eav_attribute')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>--%>
        <%--<div onclick="$.acooly.framework.exports('/manage/module/eav/eavAttribute/exportCsv.html','manage_eavAttribute_searchform','eav_attribute')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>--%>
      <%--</div>--%>
      <%--<a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/module/eav/eavAttribute/importView.html',uploader:'manage_eavAttribute_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a>--%>
    </div>
  </div>
    <%--<script src="/manage/asset/eav/acooly.admin.eav.js" />--%>
    <script type="text/javascript">
        $(function() {
            manage_eavAttribute_loadTree();
            $.acooly.framework.registerKeydown('manage_eavAttribute_toolbar','manage_eavAttribute_datagrid');
        });

    </script>
</div>
