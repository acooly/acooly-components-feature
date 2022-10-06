<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_org_editform" class="form-horizontal" action="/manage/rbac/org/<#if action=='create'>saveJson<#else>updateJson</#if>.html" method="post" >
		<@jodd.form bean="org" scope="request">
        <input name="id" type="hidden" />
		<div class="card-body">
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">父类id</label>
				<div class="col-sm-9">
					<input type="text" name="parentId" placeholder="请输入父类id..." class="easyui-validatebox form-control" data-options="validType:['number[0,2147483646]'],required:true"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">机构编码</label>
				<div class="col-sm-9">
					<input type="text" name="code" placeholder="请输入机构编码..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,64]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">机构名称</label>
				<div class="col-sm-9">
					<input type="text" name="name" placeholder="请输入机构名称..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,32]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">搜索路径</label>
				<div class="col-sm-9">
					<input type="text" name="path" placeholder="请输入搜索路径..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,128]']" required="true"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">排序值</label>
				<div class="col-sm-9">
					<input type="text" name="orderTime" placeholder="请输入排序值..." class="easyui-validatebox form-control" data-options="validType:['number[0,2147483646]'],required:true"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">省</label>
				<div class="col-sm-9">
					<input type="text" name="province" placeholder="请输入省..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,64]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">市</label>
				<div class="col-sm-9">
					<input type="text" name="city" placeholder="请输入市..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,64]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">区/县</label>
				<div class="col-sm-9">
					<input type="text" name="district" placeholder="请输入区/县..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,64]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">手机号码</label>
				<div class="col-sm-9">
					<input type="text" name="mobileNo" placeholder="请输入手机号码..." class="easyui-validatebox form-control" data-inputmask="'alias':'mobile'" data-mask data-options="validType:['mobile','length[1,20]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">地址</label>
				<div class="col-sm-9">
					<textarea rows="3" cols="40" placeholder="请输入地址..." name="address" class="easyui-validatebox form-control" ></textarea>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">联系人</label>
				<div class="col-sm-9">
					<input type="text" name="contacts" placeholder="请输入联系人..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,64]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">固定电话</label>
				<div class="col-sm-9">
					<input type="text" name="telephone" placeholder="请输入固定电话..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,20]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">邮件</label>
				<div class="col-sm-9">
					<input type="text" name="email" placeholder="请输入邮件..." class="easyui-validatebox form-control" data-inputmask="'alias':'email'" data-mask data-options="validType:['email','length[1,64]']"/>
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
					<textarea rows="3" cols="40" placeholder="请输入备注..." name="memo" class="easyui-validatebox form-control" ></textarea>
				</div>
			</div>
        </div>
      </@jodd.form>
    </form>
</div>
