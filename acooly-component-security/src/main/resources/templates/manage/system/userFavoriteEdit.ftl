<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_userFavorite_editform" class="form-horizontal" action="/manage/system/userFavorite/<#if action=='create'>saveJson<#else>updateJson</#if>.html" method="post" >
		<@jodd.form bean="userFavorite" scope="request">
        <input name="id" type="hidden" />
		<div class="card-body">
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">用户ID</label>
				<div class="col-sm-9">
					<input type="text" name="userId" placeholder="请输入用户ID..." class="easyui-validatebox form-control" data-options="validType:['number[0,2147483646]'],required:true"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">资源ID</label>
				<div class="col-sm-9">
					<input type="text" name="rescId" placeholder="请输入资源ID..." class="easyui-validatebox form-control" data-options="validType:['number[0,2147483646]'],required:true"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">排序值</label>
				<div class="col-sm-9">
					<input type="text" name="sortTime" placeholder="请输入排序值..." class="easyui-validatebox form-control" data-options="validType:['number[0,2147483646]'],required:true"/>
				</div>
			</div>
        </div>
      </@jodd.form>
    </form>
</div>
