<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_smsTemplate_editform" class="form-horizontal" action="/manage/smsend/smsTemplate/<#if action=='create'>saveJson<#else>updateJson</#if>.html" method="post">
		<@jodd.form bean="smsTemplate" scope="request">
        <input name="id" type="hidden" />
		<div class="card-body">
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">应用</label>
				<div class="col-sm-9">
					<#if action=='create'>
					<select name="appId" class="form-control select2bs4" >
						<#list allApps as e><option value="${e.appId}">${e.appId}:${e.appName}</option></#list>
					</select>
					<#else>
						<span style="line-height: 35px;">${smsTemplate.appId}</span>
					</#if>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">模板编码</label>
				<div class="col-sm-9">
					<#if action=='create'><input type="text" name="templateCode" placeholder="模板编码, 例如: appName_login..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,32]']" required="true"/>
					<#else>
						<span style="line-height: 35px;">${smsTemplate.templateCode}</span>
					</#if>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">模板名称</label>
				<div class="col-sm-9">
					<input type="text" name="templateName" placeholder="请输入模板名称..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,45]']" required="true"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">模板内容</label>
				<div class="col-sm-9">
					<textarea rows="3" name="templateContent" placeholder="请输入模板内容..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,127]']"></textarea>
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
