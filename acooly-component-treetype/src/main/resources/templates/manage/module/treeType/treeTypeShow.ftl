<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>主键:</th>
		<td>${treeType.id}</td>
	</tr>					
	<tr>
		<th width="25%">主题:</th>
		<td>${treeType.theme}</td>
	</tr>					
	<tr>
		<th>父类型ID:</th>
		<td>${treeType.parentId}</td>
	</tr>					
	<tr>
		<th>搜索路径:</th>
		<td>${treeType.path}</td>
	</tr>					
	<tr>
		<th>排序值:</th>
		<td>${treeType.sortTime}</td>
	</tr>					
	<tr>
		<th>类型编码:</th>
		<td>${treeType.code}</td>
	</tr>					
	<tr>
		<th>类型名称:</th>
		<td>${treeType.name}</td>
	</tr>					
	<tr>
		<th>子节点数量:</th>
		<td>${treeType.subCount}</td>
	</tr>					
	<tr>
		<th>备注:</th>
		<td>${treeType.comments}</td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td>${treeType.createTime?string('yyyy-MM-dd HH:mm:ss')}</td>
	</tr>					
	<tr>
		<th>修改时间:</th>
		<td>${treeType.updateTime?string('yyyy-MM-dd HH:mm:ss')}</td>
	</tr>					
</table>
</div>
