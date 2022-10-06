<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_user_editform" class="form-horizontal" action="/manage/rbac/rbacUser/<#if action=='create'>saveJson<#else>updateJson</#if>.html" method="post" >
		<@jodd.form bean="rbacUser" scope="request">
        <input name="id" type="hidden" />
		<div class="card-body">
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">用户名</label>
				<div class="col-sm-9">
					<input type="text" name="username" placeholder="请输入用户名..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,16]']" required="true"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">姓名</label>
				<div class="col-sm-9">
					<input type="text" name="realName" placeholder="请输入姓名..." class="easyui-validatebox form-control" data-options="validType:['chinese','length[1,32]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">姓名拼音</label>
				<div class="col-sm-9">
					<input type="text" name="pinyin" placeholder="请输入姓名拼音..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,32]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">登录密码</label>
				<div class="col-sm-9">
					<input type="text" name="password" placeholder="请输入登录密码..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,128]']" required="true"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">密码加盐</label>
				<div class="col-sm-9">
					<input type="text" name="salt" placeholder="请输入密码加盐..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,32]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">用户类型</label>
				<div class="col-sm-9">
					<select name="userType" class="form-control select2bs4" data-options="required:true">
						<#list allUserTypes as k,v><option value="${k}">${v}</option></#list>
					</select>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">邮件</label>
				<div class="col-sm-9">
					<input type="text" name="email" placeholder="请输入邮件..." class="easyui-validatebox form-control" data-inputmask="'alias':'email'" data-mask data-options="validType:['email','length[1,64]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">手机号码</label>
				<div class="col-sm-9">
					<input type="text" name="mobileNo" placeholder="请输入手机号码..." class="easyui-validatebox form-control" data-inputmask="'alias':'mobile'" data-mask data-options="validType:['mobile','length[1,20]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">组织ID</label>
				<div class="col-sm-9">
					<input type="text" name="orgId" placeholder="请输入组织ID..." class="easyui-validatebox form-control" data-options="validType:['number[0,2147483646]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">组织名称</label>
				<div class="col-sm-9">
					<input type="text" name="orgName" placeholder="请输入组织名称..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,128]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">密码过期时间</label>
				<div class="col-sm-9">
					<input type="text" name="expireTime" placeholder="请输入密码过期时间..." class="easyui-validatebox form-control" value="<#if rbacUser.expireTime??>${rbacUser.expireTime?string('yyyy-MM-dd HH:mm:ss')}</#if>" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" onblur="$(this).validatebox('isValid');"  />
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">解锁时间</label>
				<div class="col-sm-9">
					<input type="text" name="unlockTime" placeholder="请输入解锁时间..." class="easyui-validatebox form-control" value="<#if rbacUser.unlockTime??>${rbacUser.unlockTime?string('yyyy-MM-dd HH:mm:ss')}</#if>" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" onblur="$(this).validatebox('isValid');"  />
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">登录失败次数</label>
				<div class="col-sm-9">
					<input type="text" name="loginFailCount" placeholder="请输入登录失败次数..." class="easyui-validatebox form-control" data-options="validType:['number[0,999999999]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">最后登录时间</label>
				<div class="col-sm-9">
					<input type="text" name="loginTime" placeholder="请输入最后登录时间..." class="easyui-validatebox form-control" value="<#if rbacUser.loginTime??>${rbacUser.loginTime?string('yyyy-MM-dd HH:mm:ss')}</#if>" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" onblur="$(this).validatebox('isValid');"  />
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
				<label class="col-sm-3 col-form-label">描述</label>
				<div class="col-sm-9">
					<textarea rows="3" cols="40" placeholder="请输入描述..." name="memo" class="easyui-validatebox form-control" ></textarea>
				</div>
			</div>
        </div>
      </@jodd.form>
    </form>
</div>
