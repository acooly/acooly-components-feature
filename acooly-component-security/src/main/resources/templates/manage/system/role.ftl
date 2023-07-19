<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="overflow: hidden;" align="left">
        <form id="manage_role_searchform" class="form-inline ac-form-search" onsubmit="return false" style="padding-left: 5px;">
            <div class="form-group">
                <label class="col-form-label">角色：</label>
                <input type="text" style="width: 200px;" class="form-control form-control-sm" name="search_LIKE_name"/>
            </div>
            <div class="form-group">
                <label class="col-form-label">角色说明：</label>
                <input type="text" style="width: 200px;" class="form-control form-control-sm" name="search_LIKE_descn"/>
            </div>

            <div class="form-group">
                <button class="btn btn-sm btn-primary" type="button" onclick="$.acooly.framework.search('manage_role_searchform','manage_role_datagrid');"><i class="fa fa-search fa-fw fa-col"></i> 查询</button>
            </div>
        </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_role_datagrid" class="easyui-datagrid" url="/manage/system/role/listJson.html"
               toolbar="#manage_role_toolbar" fit="true" border="false" fitColumns="true"
               pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc"
               checkOnSelect="true" selectOnCheck="true" singleSelect="true">
            <thead>
            <tr>
                <th field="formatId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
                <th field="id">编号</th>
                <th field="name">角色</th>
                <th field="descn" formatter="formatContent">说明</th>
                <th field="action" data-options="formatter:function(value,row,index){return formatAction('manage_role_action',value,row);}">动作</th>
            </tr>
            </thead>
        </table>

        <div id="manage_role_action" style="display: none;">
            <a title="编辑" onclick="$.acooly.framework.edit({url:'/manage/system/role/edit.html',id:'{0}',entity:'role',width:550,height:500});" href="#"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
            <a title="删除" onclick="$.acooly.framework.remove('/manage/system/role/deleteJson.html','{0}','manage_role_datagrid');" href="#"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
        </div>

        <div id="manage_role_toolbar">
            <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/system/role/create.html',entity:'role',width:550,height:500});">
                <i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i> 添加
            </a>
            <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.edit({url:'/manage/system/role/edit.html',entity:'role',width:550,height:500});">
                <i class="fa fa-pencil fa-lg fa-fw fa-col"></i> 编辑
            </a>
            <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/system/role/deleteJson.html','manage_role_datagrid')">
                <i class="fa fa-trash-o fa-lg fa-fw fa-col"></i> 批量删除
            </a>
        </div>
    </div>
    <script type="text/javascript">
        $(function () {
            $.acooly.framework.initPage('manage_role_searchform', 'manage_role_datagrid');
        });
    </script>
</div>
