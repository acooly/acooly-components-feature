<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_resource_editform" class="form-horizontal" action="/manage/rbac/rbacResource/<#if action=='create'>saveJson<#else>updateJson</#if>.html" method="post" >
		<@jodd.form bean="rbacResource" scope="request">
        <input name="id" type="hidden" />
		<div class="card-body">
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">父主键</label>
				<div class="col-sm-9">
					<input type="text" name="parentId" placeholder="请输入父主键..." class="easyui-validatebox form-control" data-options="validType:['number[0,2147483646]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">资源名称</label>
				<div class="col-sm-9">
					<input type="text" name="name" placeholder="请输入资源名称..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,64]']" required="true"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">资源值</label>
				<div class="col-sm-9">
					<textarea rows="3" cols="40" placeholder="请输入资源值..." name="value" class="easyui-validatebox form-control" ></textarea>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">资源类型</label>
				<div class="col-sm-9">
					<select name="type" class="form-control select2bs4" data-options="required:true">
						<#list allTypes as k,v><option value="${k}">${v}</option></#list>
					</select>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">是否显示</label>
				<div class="col-sm-9">
					<select name="showStatus" class="form-control select2bs4" data-options="required:true">
						<#list allShowStatuss as k,v><option value="${k}">${v}</option></#list>
					</select>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">加载方式</label>
				<div class="col-sm-9">
					<select name="showMode" class="form-control select2bs4" >
						<#list allShowModes as k,v><option value="${k}">${v}</option></#list>
					</select>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">排序时间</label>
				<div class="col-sm-9">
					<input type="text" name="orderTime" placeholder="请输入排序时间..." class="easyui-validatebox form-control" data-options="validType:['number[0,2147483646]'],required:true"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">图标</label>
				<div class="col-sm-9">
					<input type="text" name="icon" placeholder="请输入图标..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,64]']"/>
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
