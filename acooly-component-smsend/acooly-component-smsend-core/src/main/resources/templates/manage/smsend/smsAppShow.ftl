<div class="card-body">
	<dl class="row">
		<dt class="col-sm-3">id:</dt>
		<dd class="col-sm-9">${smsApp.id}</dd>
		<dt class="col-sm-3">应用ID:</dt>
		<dd class="col-sm-9">${smsApp.appId}</dd>
		<dt class="col-sm-3">应用名称:</dt>
		<dd class="col-sm-9">${smsApp.appName}</dd>
		<dt class="col-sm-3">状态:</dt>
		<dd class="col-sm-9">${smsApp.status.message()}</dd>
		<dt class="col-sm-3">创建时间:</dt>
		<dd class="col-sm-9">${smsApp.createTime?string('yyyy-MM-dd HH:mm:ss')}</dd>
		<dt class="col-sm-3">更新时间:</dt>
		<dd class="col-sm-9">${smsApp.updateTime?string('yyyy-MM-dd HH:mm:ss')}</dd>
		<dt class="col-sm-3">备注:</dt>
		<dd class="col-sm-9">${smsApp.comments}</dd>
	</dl>
</div>
