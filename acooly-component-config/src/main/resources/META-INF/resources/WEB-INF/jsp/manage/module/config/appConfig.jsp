<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<c:if test="${initParam['ssoEnable']=='true'}">
    <%@ include file="/WEB-INF/jsp/manage/common/ssoInclude.jsp" %>
</c:if>
<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_appConfig_searchform','manage_appConfig_datagrid');
});

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_appConfig_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
          	<div>
                    配置项名称: <input type="text" class="text" size="15" name="search_LIKE_configName"/>
                   	配置项值: <input type="text" class="text" size="15" name="search_LIKE_configValue"/>
                   	配置项描述: <input type="text" class="text" size="15" name="search_LIKE_comments"/>
                <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false" onclick="$.acooly.framework.search('manage_appConfig_searchform','manage_appConfig_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
          	</div>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_appConfig_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/module/config/appConfig/listJson.html" toolbar="#manage_appConfig_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="id" sum="true">id</th>
			<th field="configName">配置项名称</th>
			<th field="configValue" width="200px">配置值</th>
			<th field="comments" width="400px"  formatter="contentFormatter">配置描述</th>
			<th field="localCacheExpire" >本地缓存过期时间</th>
			<th field="redisCacheExpire" >redis缓存过期时间</th>
		    <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
		    <th field="updateTime" formatter="dateTimeFormatter">修改时间</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_appConfig_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_appConfig_action" style="display: none;">
      <a onclick="$.acooly.framework.edit({url:'/manage/module/config/appConfig/edit.html',id:'{0}',entity:'appConfig',width:600,height:500});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.show('/manage/module/config/appConfig/show.html?id={0}',500,400);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.remove('/manage/module/config/appConfig/deleteJson.html','{0}','manage_appConfig_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_appConfig_toolbar">
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/module/config/appConfig/create.html',entity:'appConfig',width:600,height:500})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/module/config/appConfig/deleteJson.html','manage_appConfig_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>
<!--       <a href="#" class="easyui-menubutton" data-options="menu:'#manage_appConfig_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a> -->
<!--       <div id="manage_appConfig_exports_menu" style="width:150px;"> -->
<!--         <div onclick="$.acooly.framework.exports('/manage/module/config/appConfig/exportXls.html','manage_appConfig_searchform','sys_app_config')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div> -->
<!--         <div onclick="$.acooly.framework.exports('/manage/module/config/appConfig/exportCsv.html','manage_appConfig_searchform','sys_app_config')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div> -->
<!--       </div> -->
<!--       <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/module/config/appConfig/importView.html',uploader:'manage_appConfig_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a> -->
    </div>
  </div>

</div>
