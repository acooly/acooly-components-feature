<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_chatUser_editform" action="${pageContext.request.contextPath}/manage/component/chat/chatUser/sendMessageJson.html" method="post">
      <jodd:form bean="chatUser" scope="request">
      
        <input name="id" type="hidden" value="${chatUser.id}" />
      
        <table class="tableForm" width="100%">
			<tr>
				<th width="25%">发送者用户名：</th>
				<td>${chatUser.userName}</td>
			</tr>					
			<tr>
				<th>接收者用户名：</th>
				<td><input type="text" name="targetUserName" size="20" placeholder="请输入接收者用户名..." class="easyui-validatebox text" data-options="validType:['length[1,64]'],required:true"/></td>
			</tr>					
			<tr>
				<th>内容：</th>
				<td><textarea rows="15" cols="40" placeholder="请输入聊天内容..." style="width:300px;" name="messages" class="easyui-validatebox" data-options="validType:['length[1,500]'],required:true"></textarea></td>
			</tr>					
        </table>
      </jodd:form>
    </form>
</div>
