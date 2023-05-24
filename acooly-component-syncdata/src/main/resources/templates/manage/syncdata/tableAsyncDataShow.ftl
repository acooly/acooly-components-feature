<div class="card-body">
	<dl class="row">
		<dt class="col-sm-3">ID:</dt>
		<dd class="col-sm-9">${tableAsyncData.id}</dd>
		<dt class="col-sm-3">类型:</dt>
		<dd class="col-sm-9">${tableAsyncData.type}</dd>
		<dt class="col-sm-3">表名:</dt>
		<dd class="col-sm-9">${tableAsyncData.tableName}</dd>
		<dt class="col-sm-3">查询类型:</dt>
		<dd class="col-sm-9">${tableAsyncData.queryType.message()}</dd>
		<dt class="col-sm-3">字段类型:</dt>
		<dd class="col-sm-9">${tableAsyncData.queryColumnType.message()}</dd>
		<dt class="col-sm-3">字段名称:</dt>
		<dd class="col-sm-9">${tableAsyncData.queryColumnName}</dd>
		<dt class="col-sm-3">字段值:</dt>
		<dd class="col-sm-9">${tableAsyncData.queryColumnValue}</dd>
		<dt class="col-sm-3">查询条数:</dt>
		<dd class="col-sm-9">${tableAsyncData.queryRows}</dd>
		<dt class="col-sm-3">最后字段值:</dt>
		<dd class="col-sm-9">${tableAsyncData.lastColumeValue}</dd>
		<dt class="col-sm-3">排序时间:</dt>
		<dd class="col-sm-9">${tableAsyncData.sortTime?string('yyyy-MM-dd HH:mm:ss')}</dd>
		<dt class="col-sm-3">创建时间:</dt>
		<dd class="col-sm-9">${tableAsyncData.createTime?string('yyyy-MM-dd HH:mm:ss')}</dd>
		<dt class="col-sm-3">更新时间:</dt>
		<dd class="col-sm-9">${tableAsyncData.updateTime?string('yyyy-MM-dd HH:mm:ss')}</dd>
	</dl>
</div>
