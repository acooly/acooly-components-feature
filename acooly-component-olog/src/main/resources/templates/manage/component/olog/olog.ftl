<script type="text/javascript">
    $(function () {
        $.acooly.framework.registerKeydown('manage_olog_searchform', 'manage_olog_datagrid');
    });
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
        <form id="manage_olog_searchform" onsubmit="return false">
            <table class="tableForm" width="100%">
                <tr>
                    <td align="left">
                        系统: <input type="text" size="10" name="search_EQ_system"/>
                        模块: <input type="text" size="10" name="search_LIKE_module"/>
                        功能: <input type="text" size="10" name="search_LIKE_actionName"/>
                        结果: <select name="search_EQ_operateResult" editable="false" style="width:100px;" panelHeight="auto" class="easyui-combobox">
                            <option value="">所有</option>
                            <#list allOperateResults as k,v><option value="${k}">${v}</option></#list>
                            </select>
                        操作时间: <input id="search_GTE_operateTime" name=" search_GTE_operateTime" size="10" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
                        至 <input id="search_LTE_operateTime" name=" search_LTE_operateTime" size="10" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
                        操作员: <input type="text" size="15" name="search_LIKE_operateUser"/>
                        <a href="javascript:void(0);" style="width:70px;" class="easyui-linkbutton" data-options="plain:false"
                           onclick="$.acooly.framework.search('manage_olog_searchform','manage_olog_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i> 查询</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_olog_datagrid" class="easyui-datagrid" url="/manage/component/olog/olog/listJson.html" toolbar="" fit="true"
               border="false" fitColumns="true"
               pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc"
               checkOnSelect="true" selectOnCheck="true" nowrap="false">
            <thead>
            <tr>
                <th field="id">ID</th>
                <th field="system">系统</th>
                <th field="moduleName">模块</th>
                <th field="actionName">功能</th>
                <th field="operateUser">操作员</th>
                <th field="operateTime" formatter="dateTimeFormatter">操作时间</th>
                <th field="executeMilliseconds" formatter="millisecondFormatter">执行时间(ms)</th>
                <th field="requestParameters" formatter="contentFormatter">请求参数</th>
                <th field="operateResult"
                    data-options="formatter:function(value){ return formatRefrence('manage_olog_datagrid','allOperateResults',value);} ">结果
                </th>
                <th field="operateMessage" formatter="contentFormatter">消息</th>
                <th field="rowActions"
                    data-options="formatter:function(value, row, index){return formatAction('manage_olog_action',value,row)}">动作
                </th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_olog_action" style="display: none;">
            <a onclick="$.acooly.framework.show('/manage/component/olog/olog/show.html?id={0}',700,500);" href="#" title="详情"><i
                        class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_olog_toolbar">
            <!--
            <a href="#" class="easyui-menubutton" data-options="menu:'#manage_olog_exports_menu',iconCls:'icon-export'">批量导出</a>
            <div id="manage_olog_exports_menu" style="width:150px;">
              <div data-options="iconCls:'icon-excel'" onclick="$.acooly.framework.exports('/manage/component/olog/olog/exportXls.html','manage_olog_searchform','操作日志')">Excel</div>
              <div data-options="iconCls:'icon-csv'" onclick="$.acooly.framework.exports('/manage/component/olog/olog/exportCsv.html','manage_olog_searchform','操作日志')">CSV</div>
            </div>
             -->
        </div>
    </div>

</div>
