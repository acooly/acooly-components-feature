<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>ID:</th>
		<td>${eavSchemeTag.id}</td>
	</tr>					
	<tr>
		<th width="25%">方案ID:</th>
		<td>${eavSchemeTag.schemeid}</td>
	</tr>					
	<tr>
		<th>标签:</th>
		<td>${eavSchemeTag.tag}</td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${eavSchemeTag.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>更新时间:</th>
		<td><fmt:formatDate value="${eavSchemeTag.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>备注:</th>
		<td>${eavSchemeTag.memo}</td>
	</tr>					
</table>
</div>
