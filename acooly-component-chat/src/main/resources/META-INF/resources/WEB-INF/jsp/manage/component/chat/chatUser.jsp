<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<c:if test="${initParam['ssoEnable']=='true'}">
    <%@ include file="/WEB-INF/jsp/manage/common/ssoInclude.jsp" %>
</c:if>
<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_chatUser_searchform','manage_chatUser_datagrid');
});




/**
 * 强制聊天
 */
function manage_force_chat_show(urlStr,height,width){
	var row = $.acooly.framework.getSelectedRow("manage_chatUser_datagrid");
	if(!row){
		$.messager.show({
			title : '提示',
			msg : "请先选择操作管理员账户"
		});
		return;
	}
    $.acooly.framework.edit({url:urlStr,id:row.id,entity:'chatUser',width:500,height:400,title:'强制聊天',editButton:'发送'});
};

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_chatUser_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
          	<div>
					用户名: <input type="text" class="text" size="15" name="search_LIKE_userName"/>
					昵称: <input type="text" class="text" size="15" name="search_LIKE_nickName"/>
				类型: <select style="width:80px;height:27px;" name="search_EQ_type" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allTypes}"><option value="${e.key}" ${param.search_EQ_type == e.key?'selected':''}>${e.value}</option></c:forEach></select>
				状态: <select style="width:80px;height:27px;" name="search_EQ_status" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allStatuss}"><option value="${e.key}" ${param.search_EQ_type == e.key?'selected':''}>${e.value}</option></c:forEach></select>
					创建时间: <input size="15" class="text" id="search_GTE_createTime" name="search_GTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" class="text" id="search_LTE_createTime" name="search_LTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false" onclick="$.acooly.framework.search('manage_chatUser_searchform','manage_chatUser_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
          	</div>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_chatUser_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/component/chat/chatUser/listJson.html" toolbar="#manage_chatUser_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="id" sum="true">主键ID</th>
			<th field="userName">用户名</th>
			<th field="nickName">昵称</th>
			<th field="signature">个性签名</th>
			<th field="birthday">生日</th>
			<th field="gender" >性别</th>
			<th field="type" formatter="mappingFormatter">类型</th>
			<th field="status" formatter="mappingFormatter">状态</th>
			<th field="infoTempId" sum="true">客服消息id</th>
		    <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
		    <th field="updateTime" formatter="dateTimeFormatter">修改时间</th>
			<th field="comments">备注</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_chatUser_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_chatUser_action" style="display: none;">
      <a onclick="$.acooly.framework.edit({url:'/manage/component/chat/chatUser/edit.html',id:'{0}',entity:'chatUser',width:600,height:500});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.edit({url:'/manage/component/chat/chatUser/passwordModify.html',id:'{0}',entity:'chatUser',width:400,height:300});" href="#" title="修改密码"><i class="fa fa-key fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.edit({url:'/manage/component/chat/chatUser/statusUpdate.html',id:'{0}',entity:'chatUser',width:400,height:300});" href="#" title="修改状态"><i class="fa fa-power-off fa-lg fa-fw fa-col"></i></a>
<!--       <a onclick="$.acooly.framework.remove('/manage/component/chat/chatUser/deleteJson.html','{0}','manage_chatUser_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a> -->
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_chatUser_toolbar">
    
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/component/chat/chatUser/create.html',entity:'chatUser',width:600,height:500})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="manage_force_chat_show('/manage/component/chat/chatUser/sendMessage.html','700','500');"><i class="fa fa-snapchat fa-lg fa-fw fa-col"></i>强制聊天</a> 
      <a href="/im/index.html#/login" target="_blank"><i class="fa fa-wechat fa-lg fa-fw fa-col"></i>聊天</a> 
<!--       <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/component/chat/chatUser/deleteJson.html','manage_chatUser_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a> -->
<!--       <a href="#" class="easyui-menubutton" data-options="menu:'#manage_chatUser_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a> -->
<!--       <div id="manage_chatUser_exports_menu" style="width:150px;"> -->
<!--         <div onclick="$.acooly.framework.exports('/manage/component/chat/chatUser/exportXls.html','manage_chatUser_searchform','IM聊天-用户名')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div> -->
<!--         <div onclick="$.acooly.framework.exports('/manage/component/chat/chatUser/exportCsv.html','manage_chatUser_searchform','IM聊天-用户名')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div> -->
<!--       </div> -->
<!--       <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/component/chat/chatUser/importView.html',uploader:'manage_chatUser_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a> -->
    </div>
  </div>

</div>
