<#if ssoEnable><#include "/manage/common/ssoInclude.ftl"></#if>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_org_searchform" class="form-inline ac-form-search" onsubmit="return false">
                    <div class="form-group">
                        <label class="col-form-label">机构编码：</label>
                        <input type="text" class="form-control form-control-sm" name="search_EQ_code"/>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">机构名称：</label>
                        <input type="text" class="form-control form-control-sm" name="search_EQ_name"/>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">省：</label>
                        <input type="text" class="form-control form-control-sm" name="search_EQ_province"/>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">市：</label>
                        <input type="text" class="form-control form-control-sm" name="search_EQ_city"/>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">区/县：</label>
                        <input type="text" class="form-control form-control-sm" name="search_EQ_district"/>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">手机号码：</label>
                        <input type="text" class="form-control form-control-sm" name="search_EQ_mobileNo"/>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">联系人：</label>
                        <input type="text" class="form-control form-control-sm" name="search_EQ_contacts"/>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">固定电话：</label>
                        <input type="text" class="form-control form-control-sm" name="search_EQ_telephone"/>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">邮件：</label>
                        <input type="text" class="form-control form-control-sm" name="search_EQ_email"/>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">状态：</label>
                        <select name="search_EQ_status" class="form-control input-sm select2bs4">
                            <option value="">所有</option><#list allStatuss as k,v><option value="${k}">${v}</option></#list>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">创建时间：</label>
                        <input type="text" class="form-control form-control-sm" id="search_GTE_createTime" name="search_GTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
                        <span class="mr-1 ml-1">至</span> <input type="text" class="form-control form-control-sm" id="search_LTE_createTime" name="search_LTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">最后修改时间：</label>
                        <input type="text" class="form-control form-control-sm" id="search_GTE_updateTime" name="search_GTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
                        <span class="mr-1 ml-1">至</span> <input type="text" class="form-control form-control-sm" id="search_LTE_updateTime" name="search_LTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
                    </div>
            <div class="form-group">
                <button class="btn btn-sm btn-primary" type="button" onclick="$.acooly.framework.search('manage_org_searchform','manage_org_datagrid');"><i class="fa fa-search fa-fw fa-col"></i> 查询</button>
            </div>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_org_datagrid" class="easyui-datagrid" url="/manage/rbac/org/listJson.html" toolbar="#manage_org_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" formatter="idFormatter">编号</th>
            <th field="id" sortable="true" >主键id</th>
            <th field="parentId" sortable="true" sum="true">父类id</th>
			<th field="code">机构编码</th>
			<th field="name">机构名称</th>
			<th field="path" formatter="contentFormatter">搜索路径</th>
            <th field="orderTime" sortable="true" sum="true">排序值</th>
			<th field="province">省</th>
			<th field="city">市</th>
			<th field="district">区/县</th>
			<th field="mobileNo">手机号码</th>
			<th field="address" formatter="contentFormatter">地址</th>
			<th field="contacts">联系人</th>
			<th field="telephone">固定电话</th>
			<th field="email">邮件</th>
			<th field="status" formatter="mappingFormatter">状态</th>
			<th field="memo" formatter="contentFormatter">备注</th>
		    <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
		    <th field="updateTime" formatter="dateTimeFormatter">最后修改时间</th>
        </tr>
      </thead>
      <thead frozen="true">
        <tr>
            <th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_org_action',value,row)}">动作</th>
        </tr>
      </thead>

    </table>

      <!-- 每行的Action动作模板 -->
      <div id="manage_org_action" style="display: none;">
          <div class="btn-group btn-group-xs">
              <button onclick="$.acooly.framework.show('/manage/rbac/org/show.html?id={0}',500,500);" class="btn btn-outline-primary btn-xs" type="button"><i class="fa fa-info fa-fw fa-col"></i>查看</button>
              <button onclick="$.acooly.framework.edit({url:'/manage/rbac/org/edit.html',id:'{0}',entity:'org',width:500,height:500});" class="btn btn-outline-primary btn-xs" type="button"><i class="fa fa-pencil fa-fw fa-col"></i>编辑</button>
              <button onclick="$.acooly.framework.remove('/manage/rbac/org/deleteJson.html','{0}','manage_org_datagrid');" class="btn btn-outline-primary btn-xs" type="button"><i class="fa fa-trash fa-fw fa-col"></i>删除</button>
          </div>
      </div>

      <!-- 表格的工具栏 -->
      <div id="manage_org_toolbar">
          <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/rbac/org/create.html',entity:'org',width:500,height:500})"><i class="fa fa-plus-circle fa-fw fa-col"></i>添加</a>
          <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/rbac/org/deleteJson.html','manage_org_datagrid')"><i class="fa fa-trash fa-fw fa-col"></i>批量删除</a>
          <a href="#" class="easyui-menubutton" data-options="menu:'#manage_org_exports_menu'"><i class="fa fa-cloud-download fa-fw fa-col"></i>批量导出</a>
          <div id="manage_org_exports_menu" style="width:150px;">
              <div onclick="$.acooly.framework.exports('/manage/rbac/org/exportXls.html','manage_org_searchform','组织机构')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>
              <div onclick="$.acooly.framework.exports('/manage/rbac/org/exportCsv.html','manage_org_searchform','组织机构')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>
          </div>
          <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/rbac/org/importView.html',uploader:'manage_org_import_uploader_file'});"><i class="fa fa-cloud-upload fa-fw fa-col"></i>批量导入</a>
      </div>
  </div>
    <script type="text/javascript">
        $(function () {
            $.acooly.framework.initPage('manage_org_searchform', 'manage_org_datagrid');
        });
    </script>
</div>
