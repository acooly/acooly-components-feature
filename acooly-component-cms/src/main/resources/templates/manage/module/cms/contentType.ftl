<script type="text/javascript">
    $(function () {
        $.acooly.framework.registerKeydown('manage_contentType_searchform', 'manage_contentType_datagrid');
    });

    function manage_contentType_create() {
        var row = $.acooly.framework.getSelectedRow("manage_contentType_datagrid");
        if (!row || row == null) {
            $.acooly.framework.create({
                url: '/manage/module/cms/contentType/create.html',
                entity: 'contentType',
                height: 300
            })
        } else {
            $.acooly.framework.create({
                url: '/manage/module/cms/contentType/create.html?parentId=' + row.id,
                entity: 'contentType',
                height: 300
            })
        }
    }

    function manage_contentType_datagrid_loadFilter(data) {
        return data.rows;
    }


</script>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_contentType_datagrid" class="easyui-treegrid"
               url="${rc.contextPath}/manage/module/cms/contentType/loadTree.html"
               toolbar="#manage_contentType_toolbar" fit="true" border="false" fitColumns="true"
               rownumbers="true" idField="id" treeField="name" checkOnSelect="true" selectOnCheck="true"
               data-options="loadFilter:manage_contentType_datagrid_loadFilter">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true"
                    data-options="formatter:function(value, row, index){ return row.id }">编号
                </th>
                <th field="id">主键</th>
                <th field="code">类型编码</th>
                <th field="name">类型名称</th>
                <th field="comments">备注</th>
                <th field="rowActions"
                    data-options="formatter:function(value, row, index){return formatAction('manage_contentType_action',value,row)}">
                    动作
                </th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_contentType_action" style="display: none;">
            <a class="line-action icon-edit"
               onclick="$.acooly.framework.edit({url:'/manage/module/cms/contentType/edit.html',id:{0},entity:'contentType',height:300});"
               href="#"></a>&nbsp
            <a class="line-action icon-delete"
               onclick="$.acooly.framework.remove('/manage/module/cms/contentType/deleteJson.html',{0},'manage_contentType_datagrid');"
               href="#"></a>
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_contentType_toolbar">
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
               onclick="manage_contentType_create();">添加</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-delete" plain="true"
               onclick="$.acooly.framework.removes('/manage/module/cms/contentType/deleteJson.html','manage_contentType_datagrid')">删除</a>
        </div>

    </div>
