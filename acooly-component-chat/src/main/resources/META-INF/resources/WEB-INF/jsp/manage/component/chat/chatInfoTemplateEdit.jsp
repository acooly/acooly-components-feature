<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_chatInfoTemplate_editform" action="${pageContext.request.contextPath}/manage/component/chat/chatInfoTemplate/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="chatInfoTemplate" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th width="25%">标题：</th>
				<td><input type="text" name="title" size="30" placeholder="请输入标题..." class="easyui-validatebox text" data-options="validType:['length[1,64]'],required:true"/></td>
			</tr>					
			<tr>
				<th>内容：</th>
				<td><textarea rows="10" cols="40" placeholder="请输入内容..." style="width:300px;" name="text" class="easyui-validatebox" data-options="validType:['length[1,5000]'],required:true"></textarea></td>
			</tr>					
			<tr>
				<th>备注：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入备注..." style="width:300px;" name="comments" class="easyui-validatebox" data-options="validType:['length[1,255]']"></textarea></td>
			</tr>					
        </table>
      </jodd:form>
    </form>
</div>
