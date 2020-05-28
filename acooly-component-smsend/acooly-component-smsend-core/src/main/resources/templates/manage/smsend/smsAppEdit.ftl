<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_smsApp_editform" class="form-horizontal" action="/manage/smsend/smsApp/<#if action=='create'>saveJson<#else>updateJson</#if>.html" method="post">
		<@jodd.form bean="smsApp" scope="request">
        <input name="id" type="hidden" />
		<div class="card-body">
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">应用ID</label>
				<div class="col-sm-9">
					<input type="text" name="appId" placeholder="请输入应用ID,不填则自动生成..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,32]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">应用名称</label>
				<div class="col-sm-9">
					<input type="text" name="appName" placeholder="请输入应用名称..." class="easyui-validatebox form-control" required="true"  data-options="validType:['text','length[1,32]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">状态</label>
				<div class="col-sm-9">
					<select name="status" class="form-control select2bs4" >
						<#list allStatuss as k,v><option value="${k}">${v}</option></#list>
					</select>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">备注</label>
				<div class="col-sm-9">
					<input type="text" name="comments" placeholder="请输入备注..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,127]']"/>
				</div>
			</div>
        </div>
      </@jodd.form>
    </form>
</div>
