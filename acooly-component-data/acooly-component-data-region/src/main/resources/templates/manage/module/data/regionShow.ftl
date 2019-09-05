<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>主键ID:</th>
		<td>${region.id}</td>
	</tr>					
	<tr>
		<th width="25%">区域编码:</th>
		<td>${region.pid}</td>
	</tr>					
	<tr>
		<th>区域名称:</th>
		<td>${region.name}</td>
	</tr>					
	<tr>
		<th>首字母拼音:</th>
		<td>${region.pinyin}</td>
	</tr>					
	<tr>
		<th>排序值:</th>
		<td>${region.sortTime}</td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td>${region.createTime?string('yyyy-MM-dd HH:mm:ss')}</td>
	</tr>					
	<tr>
		<th>更新时间:</th>
		<td>${region.updateTime?string('yyyy-MM-dd HH:mm:ss')}</td>
	</tr>					
	<tr>
		<th>备注:</th>
		<td>${region.comments}</td>
	</tr>					
</table>
</div>
