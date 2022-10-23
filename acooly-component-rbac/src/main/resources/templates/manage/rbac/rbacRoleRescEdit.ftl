<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_rbacRoleResc_editform" class="form-horizontal" action="/manage/rbac/rbacRoleResc/<#if action=='create'>saveJson<#else>updateJson</#if>.html" method="post" >
		<@jodd.form bean="rbacRoleResc" scope="request">
        <input name="id" type="hidden" />
		<div class="card-body">
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">role_id</label>
				<div class="col-sm-9">
					<input type="text" name="roleId" placeholder="请输入role_id..." class="easyui-validatebox form-control" data-options="validType:['number[0,2147483646]'],required:true"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">resc_id</label>
				<div class="col-sm-9">
					<input type="text" name="rescId" placeholder="请输入resc_id..." class="easyui-validatebox form-control" data-options="validType:['number[0,2147483646]'],required:true"/>
				</div>
			</div>
        </div>
      </@jodd.form>
    </form>
</div>
