<script type="text/javascript" src="/manage/assert/plugin/jquery-easyui/plugins/treegrid-dnd.js" charset="utf-8"></script>
<div class="easyui-layout" data-options="fit : true,border : false">
    <style>
        #manage_org_toolbar {
            background: #fff;
        }

        .datagrid-row-track {
            background: #ffc107;
            color: #1f2d3d !important;
        }

    </style>
    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_org_datagrid" class="easyui-treegrid" toolbar="#manage_org_toolbar" fit="true"
               fitColumns="true" idField="id" treeField="name" collapsible="true" checkOnSelect="true" selectOnCheck="true">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
                <th field="id" sum="true">id</th>
                <th field="name">名称</th>
                <th field="username">负责人</th>
                <th field="contacts">联系人</th>
                <th field="mobileNo">手机号</th>
                <th field="telephone">固定电话</th>
                <th field="email">联系邮箱</th>
                <th field="statusView">状态</th>
                <th field="rowActions" data-options="formatter:function(value, row, index){return formatString($('#manage_org_action').html(),row.id,row.parentId);}">动作</th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_org_action" style="display: none;">
            <button onclick="$.acooly.framework.create({url:'/manage/module/security/org/create.html?parentId={0}',entity:'org',width:600,height:500,reload:true});" class="btn btn-outline-primary btn-xs" title="添加子部门"><i class="fa fa-plus-circle fa-fw fa-col"></i></button>
            <button onclick="$.acooly.framework.edit({url:'/manage/module/security/org/edit.html',id:'{0}',entity:'org',width:600,height:500,reload:true});" class="btn btn-outline-primary btn-xs" title="编辑"><i class="fa fa-pencil fa-fw fa-col"></i></button>
            <button onclick="manage_org_datagrid_move('{0}','{1}')" class="btn btn-outline-primary btn-xs" title="同级上移" type="button"><i class="fa fa-arrow-circle-up fa-fw fa-col"></i></button>
            <button onclick="$.acooly.framework.show('/manage/module/security/org/show.html?id={0}',600,500);" class="btn btn-outline-primary btn-xs" title="查看"><i class="fa fa-file-o fa-fw fa-col"></i></button>
            <button onclick="$.acooly.framework.remove('/manage/module/security/org/deleteJson.html','{0}','manage_org_datagrid');" class="btn btn-outline-primary btn-xs" title="删除"><i class="fa fa-trash-o fa-fw fa-col"></i></button>
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_org_toolbar">
            <a href="#" class="easyui-linkbutton" plain="true"
               onclick="$.acooly.framework.create({url:'/manage/module/security/org/create.html?parentId=0',entity:'org',width:600,height:500})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
        </div>
    </div>
    <script type="text/javascript">
        // 最新的移动的行的ID
        var manage_org_datagrid_move_row_pid = null;

        function manage_org_datagrid_move(id, pid) {
            $.acooly.framework.move('/manage/module/security/org/upJson.html?id=' + id, id, 'manage_org_datagrid');
            manage_org_datagrid_move_row_pid = id;
        }

        $(function () {
            $("#manage_org_datagrid").treegrid({
                idField: 'id',
                treeField: 'name',
                url: '/manage/module/security/org/listTree.html',
                onLoadSuccess: function (row, data) {
                    // console.info("onLoadSuccess data:", data);
                    // // 合并所有节点
                    // $(this).treegrid('collapseAll');
                    // // 展开的节点
                    // if (data && data.rows) {
                    //     let expandId = manage_org_datagrid_move_row_pid;
                    //     if (!expandId && data.rows.length > 0) {
                    //         expandId = data.rows[0].id;
                    //     }
                    //     console.info("expandId:", expandId);
                    //     $(this).treegrid('expandTo', expandId);
                    // }
                    // $.acooly.framework.renderDynamic("manage_org_datagrid");
                }
            });
            $.acooly.framework.initPage('manage_org_searchform', 'manage_org_datagrid');
        });
    </script>
</div>
