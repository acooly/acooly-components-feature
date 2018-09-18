<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>id:</th>
		<td>${appConfig.id}</td>
	</tr>					
	<tr>
		<th>配置项名称:</th>
		<td>${appConfig.configName}</td>
	</tr>					
	<tr>
		<th>配置值:</th>
		<td>${appConfig.configValue}</td>
	</tr>					
	<tr>
		<th>配置描述:</th>
		<td>${appConfig.comments}</td>
	</tr>					
	<tr>
		<th>本地缓存过期时间(ms):</th>
		<td>${appConfig.localCacheExpire}</td>
	</tr>					
	<tr>
		<th>redis缓存过期时间(ms):</th>
		<td>${appConfig.redisCacheExpire}</td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${appConfig.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>修改时间:</th>
		<td><fmt:formatDate value="${appConfig.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
</table>
</div>
