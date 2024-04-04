<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_emailSender_editform" class="form-horizontal" action="/manage/module/mail/emailSender/<#if action=='create'>saveJson<#else>updateJson</#if>.html" method="post" >
		<@jodd.form bean="emailSender" scope="request">
        <input name="id" type="hidden" />
		<div class="card-body">
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">boss用户id</label>
				<div class="col-sm-9">
					<input type="text" name="sysUserId" placeholder="请输入boss用户id..." class="easyui-validatebox form-control" data-options="validType:['number[0,999999999]']" required="true"/>
				</div>
			</div>
<#--			<div class="form-group row">-->
<#--				<label class="col-sm-3 col-form-label">boss登录名</label>-->
<#--				<div class="col-sm-9">-->
<#--					<input type="text" name="sysUserName" placeholder="请输入boss登录名..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,64]']"/>-->
<#--				</div>-->
<#--			</div>-->
<#--			<div class="form-group row">-->
<#--				<label class="col-sm-3 col-form-label">boss真实姓名</label>-->
<#--				<div class="col-sm-9">-->
<#--					<input type="text" name="sysRealName" placeholder="请输入boss真实姓名..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,64]']"/>-->
<#--				</div>-->
<#--			</div>-->
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">发件人邮箱</label>
				<div class="col-sm-9">
					<input type="text" placeholder="请输入发件人邮箱..." name="fromAddress" class="easyui-validatebox form-control form-words" required="true"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">发件人名称</label>
				<div class="col-sm-9">
					<input type="text" placeholder="请输入发件人名称..." name="fromName" class="easyui-validatebox form-control form-words" />
				</div>
			</div>
			

			<div class="form-group row">
				<label class="col-sm-3 col-form-label">邮箱用户名</label>
				<div class="col-sm-9">
					<input type="text"  placeholder="请输入邮箱用户名..." name="emailName" class="easyui-validatebox form-control form-words" required="true"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">邮箱密码</label>
				<div class="col-sm-9">
					<input type="text" placeholder="请输入邮箱密码..." name="emailPassword" class="easyui-validatebox form-control form-words" required="true"/>
				</div>
			</div>

			<div class="form-group row">
				<label class="col-sm-3 col-form-label">SMTP服务器地址</label>
				<div class="col-sm-9">
					<input type="text" name="hostName" placeholder="请输入SMTP服务器地址..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,128]']" required="true"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">SMTP服务器端口</label>
				<div class="col-sm-9">
					<input type="text" name="hostPort" placeholder="请输入SMTP服务器端口..." class="easyui-validatebox form-control" data-options="validType:['number[0,999999999]']" required="true"/>
				</div>
			</div>
			
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">SSL链接的端口</label>
				<div class="col-sm-9">
					<input type="text" name="sslPort" placeholder="请输入SSL链接的端口..." class="easyui-validatebox form-control" data-options="validType:['number[0,999999999]']" required="true"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">启动tls</label>
				<div class="col-sm-9">
					<select name="startTls" class="form-control select2bs4" >
						<#list allStartTlss as k,v><option value="${k}">${v}</option></#list>
					</select>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">使用SSL连接</label>
				<div class="col-sm-9">
					<select name="sslConnect" class="form-control select2bs4" >
						<#list allSslConnects as k,v><option value="${k}">${v}</option></#list>
					</select>
				</div>
			</div>
        </div>
      </@jodd.form>
    </form>
</div>
