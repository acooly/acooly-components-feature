<div class="card-body">
	<dl class="row">
		<dt class="col-sm-3">id:</dt>
		<dd class="col-sm-9">${emailRecord.id}</dd>
		<dt class="col-sm-3">模板名称:</dt>
		<dd class="col-sm-9">${emailRecord.templateName}</dd>
		<dt class="col-sm-3">模板标题:</dt>
		<dd class="col-sm-9">${emailRecord.templateTitle}</dd>
		<dt class="col-sm-3">标题:</dt>
		<dd class="col-sm-9">${emailRecord.title}</dd>
		<dt class="col-sm-3">主题:</dt>
		<dd class="col-sm-9">${emailRecord.subject}</dd>
		<dt class="col-sm-3">内容:</dt>
		<dd class="col-sm-9">${emailRecord.content}</dd>
		<dt class="col-sm-3">发送人地址:</dt>
		<dd class="col-sm-9">${emailRecord.fromAddress}</dd>
		<dt class="col-sm-3">发送人:</dt>
		<dd class="col-sm-9">${emailRecord.from}</dd>
		<dt class="col-sm-3">收件人地址列表:</dt>
		<dd class="col-sm-9">${emailRecord.toAddressList}</dd>
		<dt class="col-sm-3">创建时间:</dt>
		<dd class="col-sm-9">${emailRecord.createTime?string('yyyy-MM-dd HH:mm:ss')}</dd>
		<dt class="col-sm-3">修改时间:</dt>
		<dd class="col-sm-9">${emailRecord.updateTime?string('yyyy-MM-dd HH:mm:ss')}</dd>
		<dt class="col-sm-3">备注:</dt>
		<dd class="col-sm-9">${emailRecord.comments}</dd>
	</dl>
</div>
