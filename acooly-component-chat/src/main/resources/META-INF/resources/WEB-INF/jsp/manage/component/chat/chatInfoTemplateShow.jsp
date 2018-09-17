<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>主键ID:</th>
		<td>${chatInfoTemplate.id}</td>
	</tr>					
	<tr>
		<th width="25%">标题:</th>
		<td>${chatInfoTemplate.title}</td>
	</tr>					
	<tr>
		<th>内容:</th>
		<td>${chatInfoTemplate.text}</td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${chatInfoTemplate.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>修改时间:</th>
		<td><fmt:formatDate value="${chatInfoTemplate.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>备注:</th>
		<td>${chatInfoTemplate.comments}</td>
	</tr>					
</table>
</div>
