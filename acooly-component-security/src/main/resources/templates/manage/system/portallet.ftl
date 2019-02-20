<script type="text/javascript">
    $(function () {
        $.acooly.framework.registerKeydown('manage_portallet_searchform', 'manage_portallet_datagrid');
    });

    function manage_portallet_datagrid_content_formatter(v, r, i) {
        if (r.loadMode == 1) {
            return formatRefrence('manage_portallet_datagrid', 'allShowModes', r.showMode) + ':' + r.href;
        } else {
            return formatContent(r.content);
        }
    }
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding: 5px; overflow: hidden;" align="left">
        <form id="manage_portallet_searchform" onsubmit="return false">
            <table class="tableForm" width="100%">
                <tr>
                    <td align="left" style="color: orange">
                        <input type="button" style="color: orange;font-size: 14px" onclick="window.open('/manage/tableSchema/exportExcel')" name="exportSchema" value="导出数据库表结构"/>
                    </td>
                </tr>
                <tr>
                    <td align="left">
                        所属用户:<input type="text" name="search_LIKE_userName"/>
                        标题:<input type="text" name="search_LIKE_title"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_portallet_datagrid" class="easyui-datagrid" url="${rc.contextPath}/manage/system/portallet/listJson.html"
               toolbar="#manage_portallet_toolbar" fit="true" border="false" fitColumns="true" pagination="true" idField="id" pageSize="20"
               pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
                <th field="id">ID</th>
                <th field="userName">所属用户</th>
                <th field="title">标题</th>
                <th field="height">高度</th>
                <th field="collapsible"
                    data-options="formatter:function(value){ return formatRefrence('manage_portallet_datagrid','allCollapsibles',value);} ">
                    可否折叠
                </th>
                <th field="loadMode"
                    data-options="formatter:function(value){ return formatRefrence('manage_portallet_datagrid','allLoadModes',value);}">内容类型
                </th>
                <th field="content" formatter="manage_portallet_datagrid_content_formatter">内容</th>
                <th field="rowActions"
                    data-options="formatter:function(value, row, index){return formatAction('manage_portallet_action',value,row)}">动作
                </th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_portallet_action" style="display: none;">
            <a class="line-action icon-edit"
               onclick="$.acooly.framework.edit({url:'/manage/system/portallet/edit.html',id:'{0}',entity:'portallet',height:350,width:600});"
               href="#" title="编辑"></a>
            <a class="line-action icon-delete"
               onclick="$.acooly.framework.remove('/manage/system/portallet/deleteJson.html','{0}','manage_portallet_datagrid',null,null,cleanPortalletCookie);"
               href="#" title="删除"></a>
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_portallet_toolbar">
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
               onclick="$.acooly.framework.create({url:'/manage/system/portallet/create.html',entity:'portallet',height:350,width:600,onSuccess:cleanPortalletCookie})">添加</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
               onclick="$.acooly.framework.removes('/manage/system/portallet/deleteJson.html','manage_portallet_datagrid',null,null,cleanPortalletCookie)">批量删除</a>
        </div>
    </div>

</div>
