<script type="text/javascript">
    $(function () {
        $.acooly.framework.registerKeydown('manage_treeType_searchform${theme}', 'manage_treeType_datagrid${theme}');
    });

    /**
     * onLoadSuccess:manage_treeType_datagrid_onLoadSuccess
     */
    function manage_treeType_datagrid_onLoadSuccess(row, data) {
        $(".tree-icon,.tree-file").removeClass("tree-file");
        $(".tree-icon,.tree-folder").removeClass("tree-folder-open tree-folder");
    }
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <form id="manage_treeType_searchform${theme}" onsubmit="return false"></form>
        <table id="manage_treeType_datagrid${theme}" class="easyui-treegrid" url="/manage/module/treeType/treeType/queryTree.html?theme=${theme}" toolbar="#manage_treeType_toolbar${theme}"
               fit="true" border="false" fitColumns="true" idField="id" treeField="code" checkOnSelect="true" selectOnCheck="true" singleSelect="true"
               data-options="loadFilter:function(data){ return data.rows;}">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
                <th field="id" sortable="true" sum="true">主键</th>
<#--                <th field="theme">主题</th>-->
                <th field="parentId" sortable="true" sum="true">PID</th>
                <th field="path" formatter="contentFormatter">目录</th>
                <th field="code">类型编码</th>
                <th field="name">类型名称</th>
                <th field="subCount" sortable="true">子节点数量</th>
<#--                <th field="comments" formatter="contentFormatter">备注</th>-->
                <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
                <th field="updateTime" formatter="dateTimeFormatter">修改时间</th>
                <th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_treeType_action${theme}',value,row)}">动作</th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_treeType_action${theme}" style="display: none;">
            <a onclick="$.acooly.framework.create({url: '/manage/module/treeType/treeType/create.html?parentId={0}&theme=${theme}', form: 'manage_treeType_editform${theme}', datagrid: 'manage_treeType_datagrid${theme}', height: 300});" href="#" title="添加子分类"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.edit({url:'/manage/module/treeType/treeType/edit.html&theme=${theme}',id:'{0}',form:'manage_treeType_editform${theme}',datagrid: 'manage_treeType_datagrid${theme}',width:500,height:500});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.move('/manage/module/treeType/treeType/topJson.html','{0}','manage_treeType_datagrid${theme}');" href="#" title="置顶"><i class="fa fa fa-chevron-circle-up fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.move('/manage/module/treeType/treeType/upJson.html','{0}','manage_treeType_datagrid${theme}');" href="#" title="上移动"><i class="fa fa-arrow-circle-up fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.show('/manage/module/treeType/treeType/show.html?id={0}',500,500);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.remove('/manage/module/treeType/treeType/deleteJson.html','{0}','manage_treeType_datagrid${theme}');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_treeType_toolbar${theme}">
            <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url: '/manage/module/treeType/treeType/create.html?theme=${theme}', form: 'manage_treeType_editform${theme}', datagrid: 'manage_treeType_datagrid${theme}', height: 300});"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加根分类</a>
            <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/module/treeType/treeType/deleteJson.html','manage_treeType_datagrid${theme}')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>
            <a href="#" title="客户端全数据缓存JSON树形结构" class="easyui-linkbutton" plain="true" onclick="window.open('/manage/module/treeType/treeType/loadTree.html?theme=${theme}');"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>下载树形数据</a>
            <#--      <a href="#" class="easyui-menubutton" data-options="menu:'#manage_treeType_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>-->
            <#--      <div id="manage_treeType_exports_menu" style="width:150px;">-->
            <#--        <div onclick="$.acooly.framework.exports('/manage/module/treeType/treeType/exportXls.html','manage_treeType_searchform','树形分类')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>-->
            <#--        <div onclick="$.acooly.framework.exports('/manage/module/treeType/treeType/exportCsv.html','manage_treeType_searchform','树形分类')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>-->
            <#--      </div>-->
            <#--      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/module/treeType/treeType/importView.html',uploader:'manage_treeType_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a>-->
        </div>
    </div>

</div>
