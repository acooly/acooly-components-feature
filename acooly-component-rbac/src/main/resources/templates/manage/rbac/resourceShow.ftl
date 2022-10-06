<div class="card-body">
	<dl class="row">
		<dt class="col-sm-3">主键:</dt>
		<dd class="col-sm-9">${rbacResource.id}</dd>
		<dt class="col-sm-3">父主键:</dt>
		<dd class="col-sm-9">${rbacResource.parentId}</dd>
		<dt class="col-sm-3">资源名称:</dt>
		<dd class="col-sm-9">${rbacResource.name}</dd>
		<dt class="col-sm-3">资源值:</dt>
		<dd class="col-sm-9">${rbacResource.value}</dd>
		<dt class="col-sm-3">资源类型:</dt>
		<dd class="col-sm-9">${rbacResource.type.message()}</dd>
		<dt class="col-sm-3">是否显示:</dt>
		<dd class="col-sm-9">${rbacResource.showStatus.message()}</dd>
		<dt class="col-sm-3">加载方式:</dt>
		<dd class="col-sm-9">${rbacResource.showMode.message()}</dd>
		<dt class="col-sm-3">排序时间:</dt>
		<dd class="col-sm-9">${rbacResource.orderTime}</dd>
		<dt class="col-sm-3">图标:</dt>
		<dd class="col-sm-9">${rbacResource.icon}</dd>
		<dt class="col-sm-3">描述:</dt>
		<dd class="col-sm-9">${rbacResource.memo}</dd>
		<dt class="col-sm-3">创建时间:</dt>
		<dd class="col-sm-9">${rbacResource.createTime?string('yyyy-MM-dd HH:mm:ss')}</dd>
		<dt class="col-sm-3">修改时间:</dt>
		<dd class="col-sm-9">${rbacResource.updateTime?string('yyyy-MM-dd HH:mm:ss')}</dd>
	</dl>
</div>
