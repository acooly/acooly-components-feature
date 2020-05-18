<div class="card-body">
	<dl class="row">
		<dt class="col-sm-3">ID:</dt>
		<dd class="col-sm-9">${smsSendLog.id}</dd>
		<dt class="col-sm-3">批次号:</dt>
		<dd class="col-sm-9">${smsSendLog.batchNo}</dd>
		<dt class="col-sm-3">手机号码:</dt>
		<dd class="col-sm-9">${smsSendLog.mobileNo}</dd>
		<dt class="col-sm-3">发送类型:</dt>
		<dd class="col-sm-9">${smsSendLog.sendType.message()}</dd>
		<dt class="col-sm-3">短信内容:</dt>
		<dd class="col-sm-9">${smsSendLog.content}</dd>
		<dt class="col-sm-3">发送时间:</dt>
		<dd class="col-sm-9">${smsSendLog.sendTime?string('yyyy-MM-dd HH:mm:ss')}</dd>
		<dt class="col-sm-3">提供方:</dt>
		<dd class="col-sm-9">${smsSendLog.provider.message()}</dd>
		<dt class="col-sm-3">request_id:</dt>
		<dd class="col-sm-9">${smsSendLog.requestId}</dd>
		<dt class="col-sm-3">重试次数:</dt>
		<dd class="col-sm-9">${smsSendLog.retryCount}</dd>
		<dt class="col-sm-3">消息编码:</dt>
		<dd class="col-sm-9">${smsSendLog.resultCode}</dd>
		<dt class="col-sm-3">消息内容:</dt>
		<dd class="col-sm-9">${smsSendLog.resultMessage}</dd>
		<dt class="col-sm-3">客户IP:</dt>
		<dd class="col-sm-9">${smsSendLog.clientIp}</dd>
		<dt class="col-sm-3">状态:</dt>
		<dd class="col-sm-9">${allStatuss?com.acooly.module.smsend.facade.api.get(smsSendLog.status)}</dd>
		<dt class="col-sm-3">创建时间:</dt>
		<dd class="col-sm-9">${smsSendLog.createTime?string('yyyy-MM-dd HH:mm:ss')}</dd>
		<dt class="col-sm-3">修改时间:</dt>
		<dd class="col-sm-9">${smsSendLog.updateTime?string('yyyy-MM-dd HH:mm:ss')}</dd>
		<dt class="col-sm-3">备注:</dt>
		<dd class="col-sm-9">${smsSendLog.comments}</dd>
	</dl>
</div>
