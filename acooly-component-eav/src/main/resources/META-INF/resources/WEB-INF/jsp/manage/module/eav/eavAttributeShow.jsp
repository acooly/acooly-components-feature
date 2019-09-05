<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>id:</th>
		<td>${eavAttribute.id}</td>
	</tr>					
	<tr>
		<th width="25%">方案:</th>
		<td>${eavAttribute.schemeId}/${eavAttribute.schemeName}</td>
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
		<th>分组标签:</th>
		<td>${eavAttribute.tag}</td>
	</tr>
	<tr>
		<th>是否可以为空:</th>
		<td>${eavAttribute.nullable}</td>
	</tr>
	<tr>
		<th>属性类型:</th>
		<td>${eavAttribute.attributeType.message}</td>
	</tr>
	<c:if test="${fn:startsWith(eavAttribute.attributeType.code,'NUMBER')}">
		<tr>
			<th>最小值:</th>
			<td>${eavAttribute.minimum}</td>
		</tr>
		<tr>
			<th>最大值:</th>
			<td>${eavAttribute.maximum}</td>
		</tr>
	</c:if>
	<c:if test="${fn:startsWith(eavAttribute.attributeType.code,'STRING')}">
	<tr>
		<th>最小长度:</th>
		<td>${eavAttribute.minLength}</td>
	</tr>					
	<tr>
		<th>最大长度:</th>
		<td>${eavAttribute.maxLength}</td>
	</tr>
	</c:if>
	<c:if test="${fn:startsWith(eavAttribute.attributeType.code,'ENUM')}">
	<tr>
		<th>选项组:</th>
		<td>${eavAttribute.enumValue}</td>
	</tr>
	</c:if>
	<tr>
		<th>正则表达式:</th>
		<td>${eavAttribute.regex}</td>
	</tr>
	<tr>
		<th>正则验证消息:</th>
		<td>${eavAttribute.regexMessage}</td>
	</tr>

	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${eavAttribute.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>修改时间:</th>
		<td><fmt:formatDate value="${eavAttribute.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
	<tr>
		<th>备注:</th>
		<td>${eavAttribute.memo}</td>
	</tr>
</table>
</div>
