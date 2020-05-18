<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_smsSendLog_editform" class="form-horizontal" action="/manage/smsend/smsSendLog/<#if action=='create'>saveJson<#else>updateJson</#if>.html" method="post">
		<@jodd.form bean="smsSendLog" scope="request">
        <input name="id" type="hidden" />
		<div class="card-body">
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">批次号</label>
				<div class="col-sm-9">
					<input type="text" name="batchNo" placeholder="请输入批次号..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,32]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">手机号码</label>
				<div class="col-sm-9">
					<input type="text" name="mobileNo" placeholder="请输入手机号码..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,16]']" required="true"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">发送类型</label>
				<div class="col-sm-9">
					<select name="sendType" class="form-control select2bs4" data-options="required:true">
						<#list allSendTypes as k,v><option value="${k}">${v}</option></#list>
					</select>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">短信内容</label>
				<div class="col-sm-9">
					<textarea rows="3" cols="40" placeholder="请输入短信内容..." name="content" class="easyui-validatebox form-control" data-options="required:true"></textarea>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">发送时间</label>
				<div class="col-sm-9">
					<input type="text" name="sendTime" placeholder="请输入发送时间..." class="easyui-validatebox form-control" value="<#if smsSendLog.sendTime??>${smsSendLog.sendTime?string('yyyy-MM-dd HH:mm:ss')}</#if>" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" data-options="required:true" />
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">提供方</label>
				<div class="col-sm-9">
					<select name="provider" class="form-control select2bs4" >
						<#list allProviders as k,v><option value="${k}">${v}</option></#list>
					</select>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">request_id</label>
				<div class="col-sm-9">
					<input type="text" name="requestId" placeholder="请输入request_id..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,64]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">重试次数</label>
				<div class="col-sm-9">
					<input type="text" name="retryCount" placeholder="请输入重试次数..." class="easyui-validatebox form-control" data-options="validType:['number[0,999999999]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">消息编码</label>
				<div class="col-sm-9">
					<input type="text" name="resultCode" placeholder="请输入消息编码..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,32]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">消息内容</label>
				<div class="col-sm-9">
					<textarea rows="3" cols="40" placeholder="请输入消息内容..." name="resultMessage" class="easyui-validatebox form-control" ></textarea>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">客户IP</label>
				<div class="col-sm-9">
					<input type="text" name="clientIp" placeholder="请输入客户IP..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,16]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">状态</label>
				<div class="col-sm-9">
					<select name="status" class="form-control select2bs4" data-options="required:true">
						<#list allStatuss as k,v><option value="${k}">${v}</option></#list>
					</select>
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
