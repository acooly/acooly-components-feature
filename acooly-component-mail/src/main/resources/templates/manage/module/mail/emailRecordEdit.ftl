<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_emailRecord_editform" class="form-horizontal" action="/manage/module/mail/emailRecord/<#if action=='create'>saveJson<#else>updateJson</#if>.html" method="post" >
		<@jodd.form bean="emailRecord" scope="request">
        <input name="id" type="hidden" />
		<div class="card-body">
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">模板名称</label>
				<div class="col-sm-9">
					<input type="text" name="templateName" placeholder="请输入模板名称..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,32]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">模板标题</label>
				<div class="col-sm-9">
					<input type="text" name="templateTitle" placeholder="请输入模板标题..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,32]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">标题</label>
				<div class="col-sm-9">
					<input type="text" name="title" placeholder="请输入标题..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,128]']" required="true"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">主题</label>
				<div class="col-sm-9">
					<textarea rows="3" cols="40" placeholder="请输入主题..." name="subject" class="easyui-validatebox form-control" ></textarea>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">内容</label>
				<div class="col-sm-9">
					<textarea rows="3" cols="40" placeholder="请输入内容..." name="content" class="easyui-validatebox form-control" data-options="required:true"></textarea>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">发送人地址</label>
				<div class="col-sm-9">
					<input type="text" name="fromAddress" placeholder="请输入发送人地址..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,64]']" required="true"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">发送人</label>
				<div class="col-sm-9">
					<input type="text" name="from" placeholder="请输入发送人..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,32]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">收件人地址列表</label>
				<div class="col-sm-9">
					<textarea rows="3" cols="40" placeholder="请输入收件人地址列表..." name="toAddressList" class="easyui-validatebox form-control" data-options="required:true"></textarea>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">备注</label>
				<div class="col-sm-9">
					<textarea rows="3" cols="40" placeholder="请输入备注..." name="comments" class="easyui-validatebox form-control" ></textarea>
				</div>
			</div>
        </div>
      </@jodd.form>
    </form>
</div>
