<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_eavOption_editform" action="${pageContext.request.contextPath}/manage/module/eav/eavOption/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="eavOption" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th width="15%">编码：</th>
				<td><input type="text" name="code" size="48" placeholder="请输入编码..." class="easyui-validatebox text" data-options="validType:['length[1,64]'],required:true"/></td>
			</tr>					
			<tr>
				<th>名称：</th>
				<td><input type="text" name="name" size="48" placeholder="请输入名称..." class="easyui-validatebox text" data-options="validType:['length[1,64]'],required:true"/></td>
			</tr>
			<tr>
				<th>备注：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入备注..." name="memo" class="easyui-validatebox" data-options="validType:['length[1,255]']"></textarea></td>
			</tr>					
        </table>
      </jodd:form>
    </form>
</div>
