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
                <th field="sortTime">排序值</th>
                <th field="statusView">状态</th>
                <th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_org_action',value,row)}">动作</th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_org_action" style="display: none;">
            <a onclick="$.acooly.framework.create({url:'/manage/module/security/org/create.html?parentId={0}',entity:'org',width:600,height:500,reload:true});" href="#" title="添加"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.edit({url:'/manage/module/security/org/edit.html',id:'{0}',entity:'org',width:600,height:500,reload:true});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
            <button onclick="$.acooly.framework.move('/manage/module/security/org/upJson.html?id={0}','{0}','manage_org_datagrid');" class="btn btn-outline-primary btn-xs" title="上移" type="button"><i class="fa fa-arrow-circle-up fa-fw fa-col"></i></button>
            <a onclick="$.acooly.framework.show('/manage/module/security/org/show.html?id={0}',600,500);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.remove('/manage/module/security/org/deleteJson.html','{0}','manage_org_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_org_toolbar">
            <a href="#" class="easyui-linkbutton" plain="true"
               onclick="$.acooly.framework.create({url:'/manage/module/security/org/create.html?parentId=0',entity:'org',width:600,height:500})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
        </div>
    </div>
    <script type="text/javascript">
        $(function () {
            $("#manage_org_datagrid").treegrid({
                idField: 'id',
                treeField: 'name',
                url: '/manage/module/security/org/listTree.html',
                onLoadSuccess: function (row, data) {
                    // $(this).treegrid('enableDnd');
                    $(this).treegrid('collapseAll');
                    if(data && data.rows && data.rows.length > 0){
                        $(this).treegrid('expand', data.rows[0].id);
                    }
                },
                onBeforeDrop: function (targetRow, sourceRow, point) {
                    // console.info("onBeforeDrop:", targetRow, sourceRow, point);
                    // 拖动排序处理
                    if (!point || !targetRow || !sourceRow || targetRow == sourceRow) {
                        return false;
                    }
                    var result = true;
                    // $.ajax({
                    //     url: '/manage/pm/project/projectTask/move.html',
                    //     async: false,
                    //     data: {
                    //         sourceId: sourceRow.id,
                    //         targetId: targetRow.id,
                    //         point: point
                    //     },
                    //     success: function (data, status) {
                    //         if (data.success) {
                    //             $("#" + gridId).treegrid('reload');
                    //             console.info("save move success!");
                    //         } else {
                    //             $.acooly.messager("移动", data.message, "warning");
                    //             result = false;
                    //         }
                    //     }
                    // });
                    return result;
                }
            });
            $.acooly.framework.initPage('manage_org_searchform', 'manage_org_datagrid');
        });
    </script>
</div>
