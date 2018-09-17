<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>主键ID:</th>
		<td>${chatUser.id}</td>
	</tr>					
	<tr>
		<th width="25%">用户名:</th>
		<td>${chatUser.userName}</td>
	</tr>					
	<tr>
		<th>密码:</th>
		<td>${chatUser.password}</td>
	</tr>					
	<tr>
		<th>昵称:</th>
		<td>${chatUser.nickName}</td>
	</tr>					
	<tr>
		<th>个性签名:</th>
		<td>${chatUser.signature}</td>
	</tr>					
	<tr>
		<th>生日:</th>
		<td>${chatUser.birthday}</td>
	</tr>					
	<tr>
		<th>性别:</th>
		<td>${chatUser.gender}</td>
	</tr>					
	<tr>
		<th>类型:</th>
		<td>${chatUser.type.message}</td>
	</tr>					
	<tr>
		<th>客服置顶消息模板id:</th>
		<td>${chatUser.infoTempId}</td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${chatUser.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>修改时间:</th>
		<td><fmt:formatDate value="${chatUser.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>备注:</th>
		<td>${chatUser.comments}</td>
	</tr>					
</table>
</div>
