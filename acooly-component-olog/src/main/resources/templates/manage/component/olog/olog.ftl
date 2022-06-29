<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
        <form id="manage_olog_searchform" class="form-inline ac-form-search" onsubmit="return false">
            <div class="form-group">
                <input type="text" placeholder="开始日期" class="form-control form-control-sm" id="search_GTE_operateTime" name="search_GTE_operateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
                <span class="mr-1 ml-1">至</span> <input type="text" placeholder="结束日期" class="form-control form-control-sm" id="search_LTE_operateTime" name="search_LTE_operateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
            </div>
            <div class="form-group">
                <input type="text" placeholder="操作员" class="form-control form-control-sm" name="search_LIKE_operateUser"/>
            </div>
            <div class="form-group">
                <input type="text" placeholder="系统" class="form-control form-control-sm" name="search_EQ_system"/>
            </div>
            <div class="form-group">
                <input type="text" placeholder="模块" class="form-control form-control-sm" name="search_LIKE_module"/>
            </div>
            <div class="form-group">
                <input type="text" placeholder="功能" class="form-control form-control-sm" name="search_LIKE_actionName"/>
            </div>
            <div class="form-group">
                <button class="btn btn-sm btn-primary" type="button" onclick="$.acooly.framework.search('manage_olog_searchform','manage_olog_datagrid');"><i class="fa fa-search fa-fw fa-col"></i> 查询</button>
            </div>
        </form>
    </div>


    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_olog_datagrid" class="easyui-datagrid" url="/manage/component/olog/olog/listJson.html" toolbar="" fit="true"
               border="false" fitColumns="true" pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc"
               checkOnSelect="true" selectOnCheck="true">
            <thead>
            <tr>
                <th field="id">ID</th>
                <th field="system">系统</th>
                <th field="moduleName">模块</th>
                <th field="actionName">功能</th>
                <th field="operateUser">操作员</th>
                <th field="operateTime" formatter="dateTimeFormatter">操作时间</th>
                <th field="executeMilliseconds" formatter="millisecondFormatter">执行时间</th>
                <th field="clientInformations">客户端信息</th>
            </tr>
            </thead>
            <thead frozen="true">
            <tr>
                <th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_olog_action',value,row)}">动作</th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_olog_action" style="display: none;">
            <div class="btn-group btn-group-xs">
                <a onclick="$.acooly.framework.show('/manage/component/olog/olog/show.html?id={0}',800,600);" class="btn btn-outline-primary btn-xs" href="#" title="查看"><i class="fa fa-file-o"></i> 查看</a>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        $(function () {
            $.acooly.framework.initPage('manage_olog_searchform', 'manage_olog_datagrid');
        });
    </script>
</div>
