<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_eavSchema_editform" action="${pageContext.request.contextPath}/manage/module/eav/eavSchema/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="eavSchema" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th width="25%">名称：</th>
				<td><input type="text" name="name" size="48" placeholder="请输入名称..." class="easyui-validatebox text" data-options="validType:['length[1,128]'],required:true"/></td>
			</tr>					
			<tr>
				<th>备注：</th>
				<td><input type="text" name="memo" size="48" placeholder="请输入备注..." class="easyui-validatebox text" data-options="validType:['length[1,128]']"/></td>
			</tr>					
        </table>
      </jodd:form>
    </form>
</div>
