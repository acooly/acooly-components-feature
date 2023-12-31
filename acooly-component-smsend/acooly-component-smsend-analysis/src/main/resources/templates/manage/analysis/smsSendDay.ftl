<#if ssoEnable><#include "/manage/common/ssoInclude.ftl"></#if>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
        <form id="manage_smsSendDay_searchform" class="form-inline ac-form-search" onsubmit="return false">
            <div class="form-group">
                <label class="col-form-label">应用ID：</label>
                <input type="text" class="form-control form-control-sm" name="search_EQ_appId"/>
            </div>
            <div class="form-group">
                <label class="col-form-label">提供方：</label>
                <select name="search_EQ_provider" class="form-control input-sm select2bs4">
                    <option value="">所有</option><#list allProviders as k,v>
                    <option value="${k}">${v}</option></#list>
                </select>
            </div>
            <div class="form-group">
                <label class="col-form-label">日期：</label>
                <input type="text" class="form-control form-control-sm" name="search_GTE_period" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
                <span class="mr-1 ml-1">至</span> <input type="text" class="form-control form-control-sm" name="search_LT_period" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
            </div>
            <div class="form-group">
                <button class="btn btn-sm btn-primary" type="button" onclick="$.acooly.framework.search('manage_smsSendDay_searchform','manage_smsSendDay_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i> 查询</button>
            </div>
        </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_smsSendDay_datagrid" class="easyui-datagrid" url="/manage/analysis/smsSendDay/listJson.html" toolbar="#manage_smsSendDay_toolbar" fit="true" border="false" fitColumns="false"
               pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true"
               showFooter="true" data-options="onLoadSuccess : function() {$('#manage_smsSendDay_datagrid').datagrid('statistics');}">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" formatter="idFormatter">编号</th>
                <th field="id" sortable="true">ID</th>
                <th field="period" sortable="true" formatter="dateFormatter">日期</th>
                <th field="appId">应用ID</th>
                <th field="provider" formatter="mappingFormatter">提供方</th>
                <th field="count" sortable="true" sum="true">发送成功数</th>
                <th field="amount" sortable="true" sum="true" formatter="centMoneyFormatter">费用</th>
                <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
            </tr>
            </thead>
        </table>
        <!-- 每行的Action动作模板 -->
        <div id="manage_smsSendDay_action" style="display: none;">
            <a onclick="$.acooly.framework.show('/manage/analysis/smsSendDay/show.html?id={0}',500,500);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
        </div>
        <!-- 表格的工具栏 -->
        <div id="manage_smsSendDay_toolbar">
            <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/analysis/smsSendDay/summaryView.html',form:'manage_smsSendDaySummary_editform',datagrid:'manage_smsSendDay_datagrid',width:500,height:300,reload:true,addButton:'执行'})"><i class="fa fa-moon-o fa-lg fa-fw fa-col"></i>手动执行日终</a>
            <a href="#" class="easyui-menubutton" data-options="menu:'#manage_smsSendDay_exports_menu'"><i class="fa fa-cloud-download fa-lg fa-fw fa-col"></i>批量导出</a>
            <div id="manage_smsSendDay_exports_menu" style="width:150px;">
                <div onclick="$.acooly.framework.exports('/manage/analysis/smsSendDay/exportXls.html','manage_smsSendDay_searchform','发送统计')"><i class="fa fa-file-excel-o green"></i> Excel</div>
                <div onclick="$.acooly.framework.exports('/manage/analysis/smsSendDay/exportCsv.html','manage_smsSendDay_searchform','发送统计')"><i class="fa fa-file-text-o"></i> CSV</div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        $(function () {
            $.acooly.framework.initPage('manage_smsSendDay_searchform', 'manage_smsSendDay_datagrid');
        });
    </script>
</div>
