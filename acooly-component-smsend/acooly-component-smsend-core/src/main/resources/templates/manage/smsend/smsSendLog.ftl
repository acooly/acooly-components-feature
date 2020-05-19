<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
        <form id="manage_smsSendLog_searchform" class="form-inline ac-form-search" onsubmit="return false">
            <div class="form-group">
                <label class="col-form-label">手机号码：</label>
                <input type="text" class="form-control form-control-sm" name="search_EQ_mobileNo"/>
            </div>
            <div class="form-group">
                <label class="col-form-label">应用标志：</label>
                <input type="text" class="form-control form-control-sm" name="search_EQ_appId"/>
            </div>
            <div class="form-group">
                <label class="col-form-label">批次号：</label>
                <input type="text" class="form-control form-control-sm" name="search_EQ_batchNo"/>
            </div>
<#--            <div class="form-group">-->
<#--                <label class="col-form-label">发送类型：</label>-->
<#--                <select name="search_EQ_sendType" class="form-control input-sm select2bs4">-->
<#--                    <option value="">所有</option><#list allSendTypes as k,v>-->
<#--                    <option value="${k}">${v}</option></#list>-->
<#--                </select>-->
<#--            </div>-->
            <div class="form-group">
                <label class="col-form-label">发送时间：</label>
                <input type="text" class="form-control form-control-sm" id="search_GTE_sendTime" name="search_GTE_sendTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
                <span class="mr-1 ml-1">至</span> <input type="text" class="form-control form-control-sm" id="search_LTE_sendTime" name="search_LTE_sendTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
            </div>
            <div class="form-group">
                <label class="col-form-label">提供方：</label>
                <select name="search_EQ_provider" class="form-control input-sm select2bs4">
                    <option value="">所有</option><#list allProviders as k,v>
                    <option value="${k}">${v}</option></#list>
                </select>
            </div>
<#--            <div class="form-group">-->
<#--                <label class="col-form-label">客户IP：</label>-->
<#--                <input type="text" class="form-control form-control-sm" name="search_EQ_clientIp"/>-->
<#--            </div>-->
            <div class="form-group">
                <button class="btn btn-sm btn-primary" type="button" onclick="$.acooly.framework.search('manage_smsSendLog_searchform','manage_smsSendLog_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i> 查询</button>
            </div>
        </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_smsSendLog_datagrid" class="easyui-datagrid" url="/manage/smsend/smsSendLog/listJson.html" fit="true" border="false" fitColumns="false"
               pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" formatter="idFormatter">编号</th>
                <th field="id" sortable="true">ID</th>
                <th field="mobileNo">手机号码</th>
                <th field="appId">应用标志</th>
                <th field="provider" formatter="mappingFormatter">提供方</th>
                <th field="sendType" formatter="mappingFormatter">发送类型</th>
                <th field="templateCode">模板编码</th>
                <th field="templateJsonParams">模板参数</th>
                <th field="content" formatter="contentFormatter">短信内容</th>
                <th field="clientIp">客户IP</th>
                <th field="sendTime" formatter="dateTimeFormatter">发送时间</th>
                <th field="status" formatter="mappingFormatter">状态</th>
                <th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_smsSendLog_action',value,row)}">动作</th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_smsSendLog_action" style="display: none;">
            <#--      <a onclick="$.acooly.framework.edit({url:'/manage/smsend/smsSendLog/edit.html',id:'{0}',entity:'smsSendLog',width:500,height:500});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>-->
            <a onclick="$.acooly.framework.show('/manage/smsend/smsSendLog/show.html?id={0}',500,500);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
            <#--      <a onclick="$.acooly.framework.remove('/manage/smsend/smsSendLog/deleteJson.html','{0}','manage_smsSendLog_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>-->
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_smsSendLog_toolbar">
            <#--      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/smsend/smsSendLog/create.html',entity:'smsSendLog',width:500,height:500})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>-->
            <#--      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/smsend/smsSendLog/deleteJson.html','manage_smsSendLog_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>-->
            <#--      <a href="#" class="easyui-menubutton" data-options="menu:'#manage_smsSendLog_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>-->
            <#--      <div id="manage_smsSendLog_exports_menu" style="width:150px;">-->
            <#--        <div onclick="$.acooly.framework.exports('/manage/smsend/smsSendLog/exportXls.html','manage_smsSendLog_searchform','短信发送记录')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>-->
            <#--        <div onclick="$.acooly.framework.exports('/manage/smsend/smsSendLog/exportCsv.html','manage_smsSendLog_searchform','短信发送记录')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>-->
            <#--      </div>-->
            <#--      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/smsend/smsSendLog/importView.html',uploader:'manage_smsSendLog_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a>-->
        </div>
    </div>
    <script type="text/javascript">
        $(function () {
            $.acooly.framework.initPage('manage_customer_searchform', 'manage_customer_datagrid');
        });
    </script>
</div>
