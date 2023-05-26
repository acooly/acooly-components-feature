<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_tableAsyncData_editform" class="form-horizontal" action="/manage/syncdata/tableAsyncData/<#if action=='create'>saveJson<#else>updateJson</#if>.html" method="post" >
		<@jodd.form bean="tableAsyncData" scope="request">
        <input name="id" type="hidden" />
		<div class="card-body">
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">类型</label>
				<div class="col-sm-9">
					<select name="type" class="form-control select2bs4" >
						<#list allTypes as k,v><option value="${k}">${v}</option></#list>
					</select>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">表名（英文）</label>
				<div class="col-sm-9">
					<input type="text" name="tableName" placeholder="请输入表名..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,64]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">表名描述</label>
				<div class="col-sm-9">
					<input type="text" name="tableTitle" placeholder="请输入表名描述..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,64]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">主键字段（英文）</label>
				<div class="col-sm-9">
					<input type="text" name="primaryColumnName" placeholder="请输入主键字段..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,64]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">查询方式</label>
				<div class="col-sm-9">
					<select name="queryType" class="form-control select2bs4" >
						<#list allQueryTypes as k,v><option value="${k}">${v}</option></#list>
					</select>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">查询字段类型</label>
				<div class="col-sm-9">
					<select name="queryColumnType" class="form-control select2bs4" >
						<#list allQueryColumnTypes as k,v><option value="${k}">${v}</option></#list>
					</select>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">查询字段名称（英文）</label>
				<div class="col-sm-9">
					<input type="text" name="queryColumnName" placeholder="请输入字段名称..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,64]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">最后字段值</label>
				<div class="col-sm-9">
					<input type="text" name="queryColumnValue" placeholder="请输入字段值..." class="easyui-validatebox form-control"  data-options="validType:['text','length[1,64]']"/>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label">查询条数</label>
				<div class="col-sm-9">
					<input type="text" name="queryRows" placeholder="请输入查询条数..." class="easyui-validatebox form-control" data-options="validType:['number[0,2147483646]']"/>
				</div>
			</div>
        </div>
      </@jodd.form>
    </form>
</div>
