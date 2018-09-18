<#if ssoEnable>
    <#include "*/include.ftl">
</#if>
<script type="text/javascript">
    $(function () {
        $.acooly.framework.registerKeydown('manage_feedback_searchform', 'manage_feedback_datagrid');
    });
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
        <form id="manage_feedback_searchform" onsubmit="return false">
            <table class="tableForm" width="100%">
                <tr>
                    <td align="left">
                        类型:<select style="width:80px;height:27px;" name="search_EQ_type" editable="false" panelHeight="auto"
                                   class="easyui-combobox">
                        <option value="">所有</option><#list allTypes as k,v>
                        <option value="${k}">${v}</option></#list>
                    </select>
                        标题:<input type="text" class="text" size="15" name="search_LIKE_title"/>
                        日期:<input size="15" class="text" id="search_GTE_createTime" name="search_GTE_createTime" size="15"
                                  onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                        至<input size="15" class="text" id="search_LTE_createTime" name="search_LTE_createTime" size="15"
                                onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false"
                           onclick="$.acooly.framework.search('manage_feedback_searchform','manage_feedback_datagrid');">查询</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_feedback_datagrid" class="easyui-datagrid"
               url="${pageContext.request.contextPath}/manage/module/portlet/feedback/listJson.html" toolbar="#manage_feedback_toolbar"
               fit="true" border="false" fitColumns="true"
               pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc"
               checkOnSelect="true" selectOnCheck="true">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
                <th field="id">id</th>
                <th field="type"
                    data-options="formatter:function(value){ return formatRefrence('manage_feedback_datagrid','allTypes',value);} ">类型
                </th>
                <th field="title">标题</th>
                <th field="userName">用户</th>
                <th field="telephone">联系电话</th>
                <th field="address">联系地址</th>
                <th field="contactInfo">联系信息</th>
                <th field="createTime" formatter="dateTimeFormatter">提交时间</th>
                <th field="status"
                    data-options="formatter:function(value){ return formatRefrence('manage_feedback_datagrid','allStatuss',value);} ">状态
                </th>
                <th field="rowActions"
                    data-options="formatter:function(value, row, index){return formatAction('manage_feedback_action',value,row)}">动作
                </th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_feedback_action" style="display: none;">
            <a onclick="$.acooly.framework.edit({url:'/manage/module/portlet/feedback/edit.html',id:'{0}',entity:'feedback',width:500,height:400});"
               href="#" title="处理"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.show('/manage/module/portlet/feedback/show.html?id={0}',500,400);" href="#" title="查看"><i
                    class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_feedback_toolbar">
            <a href="#" class="easyui-menubutton" data-options="menu:'#manage_feedback_exports_menu',iconCls:'icon-export'">批量导出</a>
            <div id="manage_feedback_exports_menu" style="width:150px;">
                <div data-options="iconCls:'icon-excel'"
                     onclick="$.acooly.framework.exports('/manage/module/portlet/feedback/exportXls.html','manage_feedback_searchform','客户反馈')">
                    Excel
                </div>
                <div data-options="iconCls:'icon-csv'"
                     onclick="$.acooly.framework.exports('/manage/module/portlet/feedback/exportCsv.html','manage_feedback_searchform','客户反馈')">
                    CSV
                </div>
            </div>
        </div>
    </div>

</div>
