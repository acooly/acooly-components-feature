<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_chatUser_editform" action="${pageContext.request.contextPath}/manage/component/chat/chatUser/passwordModifyJson.html" method="post">
      <jodd:form bean="chatUser" scope="request">
        <input name="id" type="hidden" value="${chatUser.id}" />
        <table class="tableForm" width="100%">
			<tr>
				<th width="25%">用户名：</th>
				<td>
					${chatUser.userName}
				</td>
			
			</tr>					
			<tr>
				<th>新密码：</th>
				<td><input type="text" name="newPassword" size="20" placeholder="请输入新密码..." class="easyui-validatebox text" data-options="validType:['length[1,64]'],required:true"/></td>
			</tr>					
        </table>
      </jodd:form>
    </form>
</div>
