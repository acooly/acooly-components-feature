<div class="card-body">
	<dl class="row">
		<dt class="col-sm-3">ID:</dt>
		<dd class="col-sm-9">${smsTemplateProvider.id}</dd>
		<dt class="col-sm-3">模板编码:</dt>
		<dd class="col-sm-9">${smsTemplateProvider.templateCode}</dd>
		<dt class="col-sm-3">渠道:</dt>
		<dd class="col-sm-9">${smsTemplateProvider.provider}</dd>
		<dt class="col-sm-3">渠道模板编码:</dt>
		<dd class="col-sm-9">${smsTemplateProvider.providerTemplateCode}</dd>
		<dt class="col-sm-3">创建时间:</dt>
		<dd class="col-sm-9">${smsTemplateProvider.createTime?string('yyyy-MM-dd HH:mm:ss')}</dd>
		<dt class="col-sm-3">更新时间:</dt>
		<dd class="col-sm-9">${smsTemplateProvider.updateTime?string('yyyy-MM-dd HH:mm:ss')}</dd>
		<dt class="col-sm-3">备注:</dt>
		<dd class="col-sm-9">${smsTemplateProvider.comments}</dd>
	</dl>
</div>
