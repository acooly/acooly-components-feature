<#if ssoEnable><#include "/manage/common/ssoInclude.ftl"></#if>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_rbacUserRole_searchform" class="form-inline ac-form-search" onsubmit="return false">
            <div class="form-group">
                <button class="btn btn-sm btn-primary" type="button" onclick="$.acooly.framework.search('manage_rbacUserRole_searchform','manage_rbacUserRole_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i> 查询</button>
            </div>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_rbacUserRole_datagrid" class="easyui-datagrid" url="/manage/rbac/rbacUserRole/listJson.html" toolbar="#manage_rbacUserRole_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" formatter="idFormatter">编号</th>
            <th field="roleId" sortable="true" sum="true">role_id</th>
            <th field="userId" sortable="true" sum="true">user_id</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_rbacUserRole_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_rbacUserRole_action" style="display: none;">
      <a onclick="$.acooly.framework.edit({url:'/manage/rbac/rbacUserRole/edit.html',id:'{0}',entity:'rbacUserRole',width:500,height:500});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.show('/manage/rbac/rbacUserRole/show.html?id={0}',500,500);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.remove('/manage/rbac/rbacUserRole/deleteJson.html','{0}','manage_rbacUserRole_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_rbacUserRole_toolbar">
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/rbac/rbacUserRole/create.html',entity:'rbacUserRole',width:500,height:500})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/rbac/rbacUserRole/deleteJson.html','manage_rbacUserRole_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>
      <a href="#" class="easyui-menubutton" data-options="menu:'#manage_rbacUserRole_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>
      <div id="manage_rbacUserRole_exports_menu" style="width:150px;">
        <div onclick="$.acooly.framework.exports('/manage/rbac/rbacUserRole/exportXls.html','manage_rbacUserRole_searchform','用户角色表')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>
        <div onclick="$.acooly.framework.exports('/manage/rbac/rbacUserRole/exportCsv.html','manage_rbacUserRole_searchform','用户角色表')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>
      </div>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/rbac/rbacUserRole/importView.html',uploader:'manage_rbacUserRole_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a>
    </div>
  </div>
    <script type="text/javascript">
        $(function () {
            $.acooly.framework.initPage('manage_rbacUserRole_searchform', 'manage_rbacUserRole_datagrid');
        });
    </script>
</div>
