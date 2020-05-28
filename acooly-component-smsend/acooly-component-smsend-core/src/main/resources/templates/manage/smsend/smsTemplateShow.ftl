<div class="card-body">
	<dl class="row">
		<dt class="col-sm-3">ID:</dt>
		<dd class="col-sm-9">${smsTemplate.id}</dd>
		<dt class="col-sm-3">模板编码:</dt>
		<dd class="col-sm-9">${smsTemplate.templateCode}</dd>
		<dt class="col-sm-3">模板名称:</dt>
		<dd class="col-sm-9">${smsTemplate.templateName}</dd>
		<dt class="col-sm-3">模板内容:</dt>
		<dd class="col-sm-9">${smsTemplate.templateContent}</dd>
		<dt class="col-sm-3">创建时间:</dt>
		<dd class="col-sm-9">${smsTemplate.createTime?string('yyyy-MM-dd HH:mm:ss')}</dd>
		<dt class="col-sm-3">更新时间:</dt>
		<dd class="col-sm-9">${smsTemplate.updateTime?string('yyyy-MM-dd HH:mm:ss')}</dd>
		<dt class="col-sm-3">备注:</dt>
		<dd class="col-sm-9">${smsTemplate.comments}</dd>
	</dl>
</div>
