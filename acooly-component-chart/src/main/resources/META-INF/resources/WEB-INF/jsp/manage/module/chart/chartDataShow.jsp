<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>id:</th>
		<td>${chartData.id}</td>
	</tr>					
	<tr>
		<th width="25%">主题id:</th>
		<td>${chartData.chartId}</td>
	</tr>					
	<tr>
		<th>图表选项id:</th>
		<td>${chartData.itemsId}</td>
	</tr>					
	<tr>
		<th>sql表达式:</th>
		<td>${chartData.sqlData}</td>
	</tr>					
	<tr>
		<th>数据字段:</th>
		<td>${chartData.fieldMapped}</td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${chartData.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>更新时间:</th>
		<td><fmt:formatDate value="${chartData.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>备注:</th>
		<td>${chartData.comments}</td>
	</tr>					
</table>
</div>
