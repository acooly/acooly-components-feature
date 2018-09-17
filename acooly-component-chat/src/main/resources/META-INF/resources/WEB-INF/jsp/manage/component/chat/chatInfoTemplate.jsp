<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<c:if test="${initParam['ssoEnable']=='true'}">
    <%@ include file="/WEB-INF/jsp/manage/common/ssoInclude.jsp" %>
</c:if>
<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_chatInfoTemplate_searchform','manage_chatInfoTemplate_datagrid');
});

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_chatInfoTemplate_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
          	<div>
					标题: <input type="text" class="text" size="15" name="search_LIKE_title"/>
					创建时间: <input size="15" class="text" id="search_GTE_createTime" name="search_GTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" class="text" id="search_LTE_createTime" name="search_LTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false" onclick="$.acooly.framework.search('manage_chatInfoTemplate_searchform','manage_chatInfoTemplate_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
          	</div>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_chatInfoTemplate_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/component/chat/chatInfoTemplate/listJson.html" toolbar="#manage_chatInfoTemplate_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="id" sum="true">主键ID</th>
			<th field="title">标题</th>
			<th field="text">内容</th>
		    <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
		    <th field="updateTime" formatter="dateTimeFormatter">修改时间</th>
			<th field="comments">备注</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_chatInfoTemplate_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_chatInfoTemplate_action" style="display: none;">
      <a onclick="$.acooly.framework.edit({url:'/manage/component/chat/chatInfoTemplate/edit.html',id:'{0}',entity:'chatInfoTemplate',width:500,height:400});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.show('/manage/component/chat/chatInfoTemplate/show.html?id={0}',500,400);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.remove('/manage/component/chat/chatInfoTemplate/deleteJson.html','{0}','manage_chatInfoTemplate_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_chatInfoTemplate_toolbar">
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/component/chat/chatInfoTemplate/create.html',entity:'chatInfoTemplate',width:500,height:400})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
<!--       <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/component/chat/chatInfoTemplate/deleteJson.html','manage_chatInfoTemplate_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a> -->
<!--       <a href="#" class="easyui-menubutton" data-options="menu:'#manage_chatInfoTemplate_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a> -->
<!--       <div id="manage_chatInfoTemplate_exports_menu" style="width:150px;"> -->
<!--         <div onclick="$.acooly.framework.exports('/manage/component/chat/chatInfoTemplate/exportXls.html','manage_chatInfoTemplate_searchform','IM聊天-客服信息模板')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div> -->
<!--         <div onclick="$.acooly.framework.exports('/manage/component/chat/chatInfoTemplate/exportCsv.html','manage_chatInfoTemplate_searchform','IM聊天-客服信息模板')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div> -->
<!--       </div> -->
<!--       <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/component/chat/chatInfoTemplate/importView.html',uploader:'manage_chatInfoTemplate_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a> -->
    </div>
  </div>

</div>
