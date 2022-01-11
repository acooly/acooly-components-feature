<div class="card-body">
	<dl class="row">
		<dt class="col-sm-3">id:</dt>
		<dd class="col-sm-9">${emailRecordContent.id}</dd>
		<dt class="col-sm-3">邮件内容:</dt>
		<dd class="col-sm-9">${emailRecordContent.content}</dd>
		<dt class="col-sm-3">创建时间:</dt>
		<dd class="col-sm-9">${emailRecordContent.createTime?string('yyyy-MM-dd HH:mm:ss')}</dd>
		<dt class="col-sm-3">修改时间:</dt>
		<dd class="col-sm-9">${emailRecordContent.updateTime?string('yyyy-MM-dd HH:mm:ss')}</dd>
	</dl>
</div>
