<#if ssoEnable><#include "/manage/common/ssoInclude.ftl"></#if>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
<#--    <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;">-->
<#--        <form id="manage_userFavorite_searchform" class="form-inline ac-form-search" onsubmit="return false">-->
<#--            <div class="form-group">-->
<#--                <button class="btn btn-sm btn-primary" type="button" onclick="$.acooly.framework.search('manage_userFavorite_searchform','manage_userFavorite_datagrid');"><i class="fa fa-search fa-fw fa-col"></i> 查询</button>-->
<#--            </div>-->
<#--    </form>-->
<#--    </div>-->

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_userFavorite_datagrid" class="easyui-datagrid" url="/manage/system/userFavorite/favorites.html" fit="true" fitColumns="false"
                pagination="false" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" formatter="idFormatter">编号</th>
                <th field="rescId" sortable="true" sum="true">资源ID</th>
                <th field="rescName" sortable="true" sum="true">资源名称</th>
<#--                <th field="createTime" formatter="dateTimeFormatter">创建时间</th>-->
            </tr>
            </thead>
            <thead frozen="true">
            <tr>
                <th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_userFavorite_action',value,row)}">动作</th>
            </tr>
            </thead>
        </table>
        <!-- 每行的Action动作模板 -->
        <div id="manage_userFavorite_action" style="display: none;">
            <div class="btn-group btn-group-xs">
              <button onclick="$.acooly.framework.move('/manage/system/userFavorite/upJson.html','{0}','manage_userFavorite_datagrid');" class="btn btn-outline-primary btn-xs" type="button"><i class="fa fa-arrow-up fa-fw fa-col"></i>上移</button>
              <button onclick="$.acooly.framework.move('/manage/system/userFavorite/topJson.html','{0}','manage_userFavorite_datagrid');" class="btn btn-outline-primary btn-xs" type="button"><i class="fa fa-step-forward fa-rotate-270 fa-fw fa-col"></i>置顶</button>
              <button onclick="$.acooly.framework.remove('/manage/system/userFavorite/deleteJson.html','{0}','manage_userFavorite_datagrid');" class="btn btn-outline-primary btn-xs" type="button"><i class="fa fa-trash fa-fw fa-col"></i>删除</button>
          </div>
        </div>
        <!-- 表格的工具栏 -->
        <div id="manage_userFavorite_toolbar">
            <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/system/userFavorite/create.html',entity:'userFavorite',width:500,height:500})"><i class="fa fa-plus-circle fa-fw fa-col"></i>添加</a>
            <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/system/userFavorite/deleteJson.html','manage_userFavorite_datagrid')"><i class="fa fa-trash fa-fw fa-col"></i>批量删除</a>
            <a href="#" class="easyui-menubutton" data-options="menu:'#manage_userFavorite_exports_menu'"><i class="fa fa-cloud-download fa-fw fa-col"></i>批量导出</a>
            <div id="manage_userFavorite_exports_menu" style="width:150px;">
              <div onclick="$.acooly.framework.exports('/manage/system/userFavorite/exportXls.html','manage_userFavorite_searchform','sys_user_favorite')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>
              <div onclick="$.acooly.framework.exports('/manage/system/userFavorite/exportCsv.html','manage_userFavorite_searchform','sys_user_favorite')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>
            </div>
            <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/system/userFavorite/importView.html',uploader:'manage_userFavorite_import_uploader_file'});"><i class="fa fa-cloud-upload fa-fw fa-col"></i>批量导入</a>
        </div>
    </div>
    <script type="text/javascript">
        $(function () {
            $.acooly.framework.initPage('manage_userFavorite_searchform', 'manage_userFavorite_datagrid');
        });
    </script>
</div>
