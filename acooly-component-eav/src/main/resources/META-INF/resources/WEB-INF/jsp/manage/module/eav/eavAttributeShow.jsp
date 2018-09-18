<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>id:</th>
		<td>${eavAttribute.id}</td>
	</tr>					
	<tr>
		<th width="25%">方案id:</th>
		<td>${eavAttribute.schemaId}</td>
	</tr>					
	<tr>
		<th>属性名称:</th>
		<td>${eavAttribute.name}</td>
	</tr>					
	<tr>
		<th>展示名称:</th>
		<td>${eavAttribute.displayName}</td>
	</tr>					
	<tr>
		<th>备注:</th>
		<td>${eavAttribute.memo}</td>
	</tr>					
	<tr>
		<th>是否可以为空:</th>
		<td>${eavAttribute.nullable}</td>
	</tr>					
	<tr>
		<th>最小值:</th>
		<td>${eavAttribute.minimum}</td>
	</tr>					
	<tr>
		<th>最大值:</th>
		<td>${eavAttribute.maximum}</td>
	</tr>					
	<tr>
		<th>最小长度:</th>
		<td>${eavAttribute.minLength}</td>
	</tr>					
	<tr>
		<th>最大长度:</th>
		<td>${eavAttribute.maxLength}</td>
	</tr>					
	<tr>
		<th>正则表达式:</th>
		<td>${eavAttribute.regex}</td>
	</tr>					
	<tr>
		<th>枚举值:</th>
		<td>${eavAttribute.enumValue}</td>
	</tr>					
	<tr>
		<th>属性类型:</th>
		<td>${eavAttribute.attributeType.message}</td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${eavAttribute.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>修改时间:</th>
		<td><fmt:formatDate value="${eavAttribute.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
</table>
</div>
