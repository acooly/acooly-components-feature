<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_chatUser_editform" action="${pageContext.request.contextPath}/manage/component/chat/chatUser/statusUpdateJson.html" method="post">
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
				<th>类型：</th>
				<td><select name="status" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" >
					<c:forEach items="${allStatuss}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
        </table>
      </jodd:form>
    </form>
</div>
