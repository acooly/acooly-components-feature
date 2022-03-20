<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">

        <form id="manage_appConfig_searchform" class="form-inline ac-form-search" onsubmit="return false" style="padding-left: 5px;">
            <div class="form-group">
                <label class="col-form-label">配置名称：</label>
                <input type="text" class="form-control form-control-sm" name="search_LIKE_configName"/>
            </div>
            <div class="form-group">
                <label class="col-form-label">配置项值：</label>
                <input type="text" class="form-control form-control-sm" name="search_LIKE_configValue"/>
            </div>
            <div class="form-group">
                <button class="btn btn-sm btn-primary" type="button" onclick="$.acooly.framework.search('manage_appConfig_searchform','manage_appConfig_datagrid');"><i class="fa fa-search fa-fw fa-col"></i> 查询</button>
            </div>
        </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_appConfig_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/module/config/appConfig/listJson.html" toolbar="#manage_appConfig_toolbar" fit="true" border="false" fitColumns="false"
               pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
                <th field="id">ID</th>
                <th field="title">标题</th>
                <th field="configName">配置名称</th>
                <th field="configValue" width="200px">配置值</th>
                <th field="localCacheExpire" formatter="millisecondFormatter">本地缓存过期时长</th>
                <th field="redisCacheExpire" formatter="millisecondFormatter">Redis缓存过期时长</th>
                <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
                <th field="updateTime" formatter="dateTimeFormatter">修改时间</th>
                <th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_appConfig_action',value,row)}">动作</th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_appConfig_action" style="display: none;">
            <a onclick="$.acooly.framework.edit({url:'/manage/module/config/appConfig/edit.html',id:'{0}',entity:'appConfig',width:600,height:600});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.remove('/manage/module/config/appConfig/deleteJson.html','{0}','manage_appConfig_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_appConfig_toolbar">
            <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/module/config/appConfig/create.html',entity:'appConfig',width:600,height:600})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
            <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/module/config/appConfig/deleteJson.html','manage_appConfig_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>
        </div>
    </div>


    <script type="text/javascript">
        $(function () {
            $.acooly.framework.initPage('manage_appConfig_searchform', 'manage_appConfig_datagrid');
        });
    </script>
</div>
