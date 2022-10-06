<div class="card-body">
	<dl class="row">
		<dt class="col-sm-3">主键:</dt>
		<dd class="col-sm-9">${rbacRole.id}</dd>
		<dt class="col-sm-3">角色名称:</dt>
		<dd class="col-sm-9">${rbacRole.name}</dd>
		<dt class="col-sm-3">角色标题:</dt>
		<dd class="col-sm-9">${rbacRole.title}</dd>
		<dt class="col-sm-3">角色说明:</dt>
		<dd class="col-sm-9">${rbacRole.memo}</dd>
		<dt class="col-sm-3">创建时间:</dt>
		<dd class="col-sm-9">${rbacRole.createTime?string('yyyy-MM-dd HH:mm:ss')}</dd>
		<dt class="col-sm-3">修改时间:</dt>
		<dd class="col-sm-9">${rbacRole.updateTime?string('yyyy-MM-dd HH:mm:ss')}</dd>
	</dl>
</div>
