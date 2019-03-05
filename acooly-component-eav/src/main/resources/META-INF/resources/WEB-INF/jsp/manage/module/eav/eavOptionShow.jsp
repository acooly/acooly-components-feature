<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>id:</th>
		<td>${eavOption.id}</td>
	</tr>					
	<tr>
		<th width="25%">父ID:</th>
		<td>${eavOption.parentId}</td>
	</tr>					
	<tr>
		<th>path:</th>
		<td>${eavOption.path}</td>
	</tr>					
	<tr>
		<th>编码:</th>
		<td>${eavOption.code}</td>
	</tr>					
	<tr>
		<th>名称:</th>
		<td>${eavOption.name}</td>
	</tr>					
	<tr>
		<th>排序值:</th>
		<td>${eavOption.sortTime}</td>
	</tr>					
	<tr>
		<th>状态:</th>
		<td>${eavOption.status}</td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${eavOption.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>更新时间:</th>
		<td><fmt:formatDate value="${eavOption.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>备注:</th>
		<td>${eavOption.memo}</td>
	</tr>					
</table>
</div>
