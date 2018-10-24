<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>id:</th>
		<td>${chartItems.id}</td>
	</tr>					
	<tr>
		<th width="25%">主题id:</th>
		<td>${chartItems.chartId}</td>
	</tr>					
	<tr>
		<th>标题:</th>
		<td>${chartItems.title}</td>
	</tr>					
	<tr>
		<th>图表类型:</th>
		<td>${chartItems.type.message}</td>
	</tr>					
	<tr>
		<th>状态:</th>
		<td>${chartItems.status.message}</td>
	</tr>					
	<tr>
		<th>循环时间:</th>
		<td>${chartItems.loopTime /1000}</td>
	</tr>					
	<tr>
		<th>x轴:</th>
		<td>${chartItems.xShaft}</td>
	</tr>					
	<tr>
		<th>y轴:</th>
		<td>${chartItems.yShaft}</td>
	</tr>					
	<tr>
		<th>排序:</th>
		<td><fmt:formatDate value="${chartItems.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${chartItems.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>更新时间:</th>
		<td><fmt:formatDate value="${chartItems.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>备注:</th>
		<td>${chartItems.comments}</td>
	</tr>					
</table>
</div>
