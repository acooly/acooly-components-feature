<div class="card-body">
	<dl class="row">
		<dt class="col-sm-3">主键id:</dt>
		<dd class="col-sm-9">${org.id}</dd>
		<dt class="col-sm-3">父类id:</dt>
		<dd class="col-sm-9">${org.parentId}</dd>
		<dt class="col-sm-3">机构编码:</dt>
		<dd class="col-sm-9">${org.code}</dd>
		<dt class="col-sm-3">机构名称:</dt>
		<dd class="col-sm-9">${org.name}</dd>
		<dt class="col-sm-3">搜索路径:</dt>
		<dd class="col-sm-9">${org.path}</dd>
		<dt class="col-sm-3">排序值:</dt>
		<dd class="col-sm-9">${org.orderTime}</dd>
		<dt class="col-sm-3">省:</dt>
		<dd class="col-sm-9">${org.province}</dd>
		<dt class="col-sm-3">市:</dt>
		<dd class="col-sm-9">${org.city}</dd>
		<dt class="col-sm-3">区/县:</dt>
		<dd class="col-sm-9">${org.district}</dd>
		<dt class="col-sm-3">手机号码:</dt>
		<dd class="col-sm-9">${org.mobileNo}</dd>
		<dt class="col-sm-3">地址:</dt>
		<dd class="col-sm-9">${org.address}</dd>
		<dt class="col-sm-3">联系人:</dt>
		<dd class="col-sm-9">${org.contacts}</dd>
		<dt class="col-sm-3">固定电话:</dt>
		<dd class="col-sm-9">${org.telephone}</dd>
		<dt class="col-sm-3">邮件:</dt>
		<dd class="col-sm-9">${org.email}</dd>
		<dt class="col-sm-3">状态:</dt>
		<dd class="col-sm-9">${org.status.message()}</dd>
		<dt class="col-sm-3">备注:</dt>
		<dd class="col-sm-9">${org.memo}</dd>
		<dt class="col-sm-3">创建时间:</dt>
		<dd class="col-sm-9">${org.createTime?string('yyyy-MM-dd HH:mm:ss')}</dd>
		<dt class="col-sm-3">最后修改时间:</dt>
		<dd class="col-sm-9">${org.updateTime?string('yyyy-MM-dd HH:mm:ss')}</dd>
	</dl>
</div>
