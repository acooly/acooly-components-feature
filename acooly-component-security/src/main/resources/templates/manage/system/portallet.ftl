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
        <form id="manage_portallet_searchform" class="form-inline ac-form-search" onsubmit="return false">
            <div class="form-group">
                <label class="col-form-label">标题：</label>
                <input type="text" class="form-control form-control-sm" name="search_LIKE_title"/>
            </div>
            <div class="form-group">
                <label class="col-form-label">用户：</label>
                <input type="text" class="form-control form-control-sm" name="search_LIKE_userName"/>
            </div>
            <div class="form-group">
                <button class="btn btn-sm btn-primary" type="button"
                        onclick="$.acooly.framework.search('manage_portallet_searchform','manage_portallet_datagrid');">
                    <i class="fa fa-search fa-fw fa-col"></i> 查询
                </button>
            </div>
        </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_portallet_datagrid" class="easyui-datagrid"
               url="${rc.contextPath}/manage/system/portallet/listJson.html"
               toolbar="#manage_portallet_toolbar" fit="true" border="false" fitColumns="true" pagination="true"
               idField="id" pageSize="20"
               pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true"
               selectOnCheck="true">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true"
                    data-options="formatter:function(value, row, index){ return row.id }">编号
                </th>
                <th field="id">ID</th>
                <th field="userName">所属用户</th>
                <th field="title">标题</th>
                <th field="height">高度</th>
                <th field="collapsible" formatter="mappingFormatter">可否折叠</th>
                <th field="loadMode" formatter="mappingFormatter">内容类型</th>
                <th field="rowActions"
                    data-options="formatter:function(value, row, index){return formatAction('manage_portallet_action',value,row)}">
                    动作
                </th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_portallet_action" style="display: none;">
            <div class="btn-group btn-group-xs">
                <a onclick="$.acooly.framework.edit({url:'/manage/system/portallet/edit.html',id:'{0}',entity:'portallet',height:450,width:600});"
                   class="btn btn-outline-primary btn-xs" href="#" title="编辑"><i class="fa fa-pencil"></i> 编辑</a>
                <a onclick="$.acooly.framework.remove('/manage/system/portallet/deleteJson.html','{0}','manage_portallet_datagrid',null,null,cleanPortalletCookie);"
                   class="btn btn-outline-primary btn-xs" href="#" title="删除"><i class="fa fa-trash-o"></i> 删除</a>
            </div>
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_portallet_toolbar">
            <a href="#" class="easyui-linkbutton" plain="true"
               onclick="$.acooly.framework.create({url:'/manage/system/portallet/create.html',entity:'portallet',height:450,width:600,reload:true,onSuccess:cleanPortalletCookie})"><i
                        class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
            <a href="#" class="easyui-linkbutton" plain="true"
               onclick="$.acooly.framework.removes('/manage/system/portallet/deleteJson.html','manage_portallet_datagrid',null,null,cleanPortalletCookie)"><i
                        class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>
        </div>
    </div>

</div>
