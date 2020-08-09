<#if ssoEnable><#include "/manage/common/ssoInclude.ftl"></#if>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
        <form id="manage_smsSendPeriod_searchform" class="form-inline ac-form-search" onsubmit="return false">
            <div class="form-group">
                <label class="col-form-label">统计单位：</label>
                <select name="search_EQ_dateUnit" class="form-control input-sm select2bs4">
                    <#list allDateUnits as k,v><option value="${k}">${v}</option></#list>
                </select>
            </div>
            <div class="form-group">
                <label class="col-form-label">日期：</label>
                <input type="text" class="easyui-validatebox form-control form-control-sm" required="true" name="search_GTE_start" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
                <span class="mr-1 ml-1">至</span> <input type="text" class="easyui-validatebox form-control form-control-sm" required="true" name="search_LTE_end" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
            </div>
            <div class="form-group">
                <button class="btn btn-sm btn-primary" type="button" onclick="$.acooly.framework.search('manage_smsSendPeriod_searchform','manage_smsSendDPeriod_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i> 查询</button>
            </div>
        </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_smsSendDPeriod_datagrid" class="easyui-datagrid" url="/manage/analysis/smsSendDay/periodData.html" toolbar="#manage_smsSendDPeriod_toolbar" fit="true" border="false" fitColumns="false"
               showFooter="true" data-options="onLoadSuccess : function() {$('#manage_smsSendDPeriod_datagrid').datagrid('statistics');}">
            <thead>
            <tr>
                <th field="period" sortable="true">日期</th>
                <th field="appId">应用ID</th>
                <th field="count" sortable="true" sum="true">发送成功数</th>
                <th field="amount" sortable="true" sum="true" formatter="moneyFormatter">费用</th>
            </tr>
            </thead>
        </table>
        <!-- 每行的Action动作模板 -->
        <div id="manage_smsSendDPeriod_action" style="display: none;">
            <a onclick="$.acooly.framework.show('/manage/analysis/smsSendDay/show.html?id={0}',500,500);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
        </div>
        <!-- 表格的工具栏 -->
        <div id="manage_smsSendDPeriod_toolbar">
            <a href="#" class="easyui-menubutton" data-options="menu:'#manage_smsSendDPeriod_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>
            <div id="manage_smsSendDPeriod_exports_menu" style="width:150px;">
                <div onclick="$.acooly.framework.exports('/manage/analysis/smsSendDay/exportXls.html','manage_smsSendPeriod_searchform','发送统计')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>
                <div onclick="$.acooly.framework.exports('/manage/analysis/smsSendDay/exportCsv.html','manage_smsSendPeriod_searchform','发送统计')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        $(function () {
            $.acooly.framework.initPage('manage_smsSendPeriod_searchform', 'manage_smsSendPeriod_datagrid');
        });

        function manage_smsSendPeriod_searchform_beforeSubmit() {
            return $('#manage_smsSendPeriod_searchform').form('validate');
        }
    </script>
</div>
