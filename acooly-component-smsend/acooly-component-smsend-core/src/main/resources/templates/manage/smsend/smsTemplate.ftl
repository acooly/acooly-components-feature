<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
        <form id="manage_smsTemplate_searchform" class="form-inline ac-form-search" onsubmit="return false">
            <div class="form-group">
                <label class="col-form-label">模板编码：</label>
                <input type="text" class="form-control form-control-sm" name="search_EQ_templateCode"/>
            </div>
            <div class="form-group">
                <label class="col-form-label">模板名称：</label>
                <input type="text" class="form-control form-control-sm" name="search_EQ_templateName"/>
            </div>
            <div class="form-group">
                <label class="col-form-label">模板内容：</label>
                <input type="text" class="form-control form-control-sm" name="search_EQ_templateContent"/>
            </div>
            <div class="form-group">
                <label class="col-form-label">创建时间: </label>
                <input type="text" class="form-control form-control-sm" id="search_GTE_createTime" name="search_GTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
                <span class="mr-1 ml-1">至</span> <input type="text" class="form-control form-control-sm" id="search_LTE_createTime" name="search_LTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
            </div>
            <div class="form-group">
                <label class="col-form-label">更新时间: </label>
                <input type="text" class="form-control form-control-sm" id="search_GTE_updateTime" name="search_GTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
                <span class="mr-1 ml-1">至</span> <input type="text" class="form-control form-control-sm" id="search_LTE_updateTime" name="search_LTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
            </div>
            <div class="form-group">
                <label class="col-form-label">备注：</label>
                <input type="text" class="form-control form-control-sm" name="search_EQ_comments"/>
            </div>
            <div class="form-group">
                <button class="btn btn-sm btn-primary" type="button" onclick="$.acooly.framework.search('manage_smsTemplate_searchform','manage_smsTemplate_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i> 查询</button>
            </div>
        </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_smsTemplate_datagrid" class="easyui-datagrid" url="/manage/smsend/smsTemplate/listJson.html" toolbar="#manage_smsTemplate_toolbar" fit="true" border="false" fitColumns="false"
               pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true"
               data-options="onSelect:manage_smsTemplate_onSelect">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" formatter="idFormatter">编号</th>
                <th field="id" sortable="true">ID</th>
                <th field="appId">AppId</th>
                <th field="templateCode">模板编码</th>
                <th field="templateName">模板名称</th>
                <th field="templateContent">模板内容</th>
                <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
                <th field="updateTime" formatter="dateTimeFormatter">更新时间</th>
                <th field="comments">备注</th>
                <th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_smsTemplate_action',value,row)}">动作</th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_smsTemplate_action" style="display: none;">
            <a onclick="$.acooly.framework.edit({url:'/manage/smsend/smsTemplate/edit.html',id:'{0}',entity:'smsTemplate',width:500,height:500});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.show('/manage/smsend/smsTemplate/show.html?id={0}',500,500);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.remove('/manage/smsend/smsTemplate/deleteJson.html','{0}','manage_smsTemplate_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_smsTemplate_toolbar">
            <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/smsend/smsTemplate/create.html',entity:'smsTemplate',width:500,height:500})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
            <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/smsend/smsTemplate/deleteJson.html','manage_smsTemplate_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>
            <a href="#" class="easyui-menubutton" data-options="menu:'#manage_smsTemplate_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>
            <div id="manage_smsTemplate_exports_menu" style="width:150px;">
                <div onclick="$.acooly.framework.exports('/manage/smsend/smsTemplate/exportXls.html','manage_smsTemplate_searchform','短信模板')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>
                <div onclick="$.acooly.framework.exports('/manage/smsend/smsTemplate/exportCsv.html','manage_smsTemplate_searchform','短信模板')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>
            </div>
            <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/smsend/smsTemplate/importView.html',uploader:'manage_smsTemplate_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a>
        </div>
    </div>

    <div data-options="region:'south',border:false" style="height:46%;">
        <div id="manage_smsTemplate_sub_tab" class="easyui-tabs" fit="true">

            <div title="渠道模板管理" style="margin-left: 0px;">
                <div id="manage_smsTemplateProvider_searchform"></div>
                <table id="manage_smsTemplateProvider_datagrid" class="easyui-datagrid" fit="true" border="false" fitColumns="false" toolbar="#manage_smsTemplateProvider_toolbar"
                       pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
                    <thead>
                    <tr>
                        <th field="showCheckboxWithId" checkbox="true" formatter="idFormatter">编号</th>
                        <th field="id" sortable="true">ID</th>
                        <th field="templateCode">模板编码</th>
                        <th field="provider" formatter="mappingFormatter">渠道</th>
                        <th field="providerTemplateCode">渠道模板编码</th>
                        <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
                        <th field="updateTime" formatter="dateTimeFormatter">更新时间</th>
                        <th field="comments">备注</th>
                        <th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_smsTemplateProvider_action',value,row)}">动作</th>
                    </tr>
                    </thead>
                </table>

                <!-- 每行的Action动作模板 -->
                <div id="manage_smsTemplateProvider_action" style="display: none;">
                    <a onclick="$.acooly.framework.edit({url:'/manage/smsend/smsTemplateProvider/edit.html',id:'{0}',entity:'smsTemplateProvider',width:500,height:500});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
                    <a onclick="$.acooly.framework.show('/manage/smsend/smsTemplateProvider/show.html?id={0}',500,500);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
                    <a onclick="$.acooly.framework.remove('/manage/smsend/smsTemplateProvider/deleteJson.html','{0}','manage_smsTemplateProvider_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
                </div>

                <!-- 表格的工具栏 -->
                <div id="manage_smsTemplateProvider_toolbar">
                    <a href="#" class="easyui-linkbutton" plain="true" onclick="manage_smsTemplateProvider_create()"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
                    <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/smsend/smsTemplateProvider/deleteJson.html','manage_smsTemplateProvider_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>
                </div>
            </div>
        </div>
    </div>
</div>

</div>
<script type="text/javascript">
    $(function () {
        $.acooly.framework.initPage('manage_customer_searchform', 'manage_customer_datagrid');
        $.acooly.framework.initPage('manage_smsTemplateProvider_searchform', 'manage_smsTemplateProvider_datagrid');
    });


    function manage_smsTemplate_sub_tab_onSelect(title, index) {
        manage_smsTemplate_selectTab(index);
    }

    /**
     * 点击客户主表格行，加载显示所有关联只tab数据
     */
    function manage_smsTemplate_onSelect() {
        var tab = $('#manage_smsTemplate_sub_tab').tabs('getSelected');
        var index = $('#manage_smsTemplate_sub_tab').tabs('getTabIndex', tab);
        manage_smsTemplate_selectTab(index);
    }

    function manage_smsTemplate_selectTab(index) {
        var row = $.acooly.framework.getSelectedRow("manage_smsTemplate_datagrid");
        if (!row) return;
        if (index == 0) {
            manage_smsTemplateProvider_datagrid_load();
        }
    }

    /**
     * 加载渠道模板的列表
     * 使用ajax查询和加载
     */
    function manage_smsTemplateProvider_datagrid_load() {
        $.acooly.framework.fireSelectRow('manage_smsTemplate_datagrid', function (row) {
            $.acooly.framework.loadGrid({
                gridId: "manage_smsTemplateProvider_datagrid",
                url: '/manage/smsend/smsTemplateProvider/listJson.html',
                ajaxData: {"search_EQ_templateCode": row.templateCode}
            });
        }, '请先选择模板数据行');
    }

    /**
     * 添加客户基本信息
     */
    function manage_smsTemplateProvider_create() {
        //判断是否选择客户主表行，如果选择，则回调传入当前选中行的数据。
        $.acooly.framework.fireSelectRow('manage_smsTemplate_datagrid', function (row) {
            $.acooly.framework.create({
                url: '/manage/smsend/smsTemplateProvider/create.html',
                entity: 'smsTemplateProvider',
                width: 500, height: 400,
                ajaxData: {'templateCode': row.templateCode, 'templateName': row.templateName}
            });
        }, '请先选择操作数据行');
    }

</script>