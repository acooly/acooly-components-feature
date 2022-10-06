<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_role_editform" class="form-horizontal" action="/manage/rbac/rbacRole/<#if action=='create'>saveJson<#else>updateJson</#if>.html" method="post" >
		<@jodd.form bean="rbacRole" scope="request">
        <input name="id" type="hidden" />
		<div class="card-body">
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">角色名称</label>
				<div class="col-sm-9">
					<input type="text" name="name" placeholder="请输入角色名称..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,64]']" required="true"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">角色标题</label>
				<div class="col-sm-9">
					<input type="text" name="title" placeholder="请输入角色标题..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,64]']" required="true"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">角色说明</label>
				<div class="col-sm-9">
					<textarea rows="3" cols="40" placeholder="请输入角色说明..." name="memo" class="easyui-validatebox form-control" ></textarea>
				</div>
			</div>
        </div>
      </@jodd.form>
    </form>
</div>
