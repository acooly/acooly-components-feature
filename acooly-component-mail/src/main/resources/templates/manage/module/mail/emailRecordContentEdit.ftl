<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_emailRecordContent_editform" class="form-horizontal" action="/manage/module/mail/emailRecordContent/<#if action=='create'>saveJson<#else>updateJson</#if>.html" method="post" >
		<@jodd.form bean="emailRecordContent" scope="request">
        <input name="id" type="hidden" />
		<div class="card-body">
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">邮件内容</label>
				<div class="col-sm-9">
					<textarea rows="3" cols="40" placeholder="请输入邮件内容..." name="content" class="easyui-validatebox form-control" ></textarea>
				</div>
			</div>
        </div>
      </@jodd.form>
    </form>
</div>
