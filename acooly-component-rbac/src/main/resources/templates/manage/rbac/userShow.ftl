<div class="card-body">
	<dl class="row">
		<dt class="col-sm-3">主键:</dt>
		<dd class="col-sm-9">${rbacUser.id}</dd>
		<dt class="col-sm-3">用户名:</dt>
		<dd class="col-sm-9">${rbacUser.username}</dd>
		<dt class="col-sm-3">姓名:</dt>
		<dd class="col-sm-9">${rbacUser.realName}</dd>
		<dt class="col-sm-3">姓名拼音:</dt>
		<dd class="col-sm-9">${rbacUser.pinyin}</dd>
		<dt class="col-sm-3">登录密码:</dt>
		<dd class="col-sm-9">${rbacUser.password}</dd>
		<dt class="col-sm-3">密码加盐:</dt>
		<dd class="col-sm-9">${rbacUser.salt}</dd>
		<dt class="col-sm-3">用户类型:</dt>
		<dd class="col-sm-9">${allUserTypes?api.get(rbacUser.userType)}</dd>
		<dt class="col-sm-3">邮件:</dt>
		<dd class="col-sm-9">${rbacUser.email}</dd>
		<dt class="col-sm-3">手机号码:</dt>
		<dd class="col-sm-9">${rbacUser.mobileNo}</dd>
		<dt class="col-sm-3">组织ID:</dt>
		<dd class="col-sm-9">${rbacUser.orgId}</dd>
		<dt class="col-sm-3">组织名称:</dt>
		<dd class="col-sm-9">${rbacUser.orgName}</dd>
		<dt class="col-sm-3">密码过期时间:</dt>
		<dd class="col-sm-9">${rbacUser.expireTime?string('yyyy-MM-dd HH:mm:ss')}</dd>
		<dt class="col-sm-3">解锁时间:</dt>
		<dd class="col-sm-9">${rbacUser.unlockTime?string('yyyy-MM-dd HH:mm:ss')}</dd>
		<dt class="col-sm-3">登录失败次数:</dt>
		<dd class="col-sm-9">${rbacUser.loginFailCount}</dd>
		<dt class="col-sm-3">最后登录时间:</dt>
		<dd class="col-sm-9">${rbacUser.loginTime?string('yyyy-MM-dd HH:mm:ss')}</dd>
		<dt class="col-sm-3">状态:</dt>
		<dd class="col-sm-9">${rbacUser.status.message()}</dd>
		<dt class="col-sm-3">描述:</dt>
		<dd class="col-sm-9">${rbacUser.memo}</dd>
		<dt class="col-sm-3">创建时间:</dt>
		<dd class="col-sm-9">${rbacUser.createTime?string('yyyy-MM-dd HH:mm:ss')}</dd>
		<dt class="col-sm-3">最后修改时间:</dt>
		<dd class="col-sm-9">${rbacUser.updateTime?string('yyyy-MM-dd HH:mm:ss')}</dd>
	</dl>
</div>
