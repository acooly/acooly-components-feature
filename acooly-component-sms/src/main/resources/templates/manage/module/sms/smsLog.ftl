<#if ssoEnable>
    <#include "*/include.ftl">
</#if>
<script type="text/javascript">
    $(function () {
        $.acooly.framework.registerKeydown('manage_smsLog_searchform', 'manage_smsLog_datagrid');
    });
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_smsLog_datagrid" class="easyui-datagrid"
               url="${pageContext.request.contextPath}/manage/module/sms/smsLog/listJson.html" toolbar="#manage_smsLog_toolbar" fit="true"
               border="false" fitColumns="false"
               pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc"
               checkOnSelect="true" selectOnCheck="true" singleSelect="true">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
                <th field="id">ID</th>
                <th field="mobileNo">手机号码</th>
                <th field="content">短信内容</th>
                <th field="provider">提供商</th>
                <th field="clientIp">客户Ip</th>
                <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
                <th field="sendTime" formatter="dateTimeFormatter">发送时间</th>
                <th field="comments">备注</th>
                <th field="status"
                    data-options="formatter:function(value){ return formatRefrence('manage_smsLog_datagrid','allStatuss',value);} ">状态
                </th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_smsLog_action" style="display: none;">
            <a class="line-action icon-show" onclick="$.acooly.framework.show('/manage/module/sms/smsLog/show.html?id={0}',500,450);"
               href="#"></a>
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_smsLog_toolbar">
            <div id="manage_smsLog_searchform" class="tableForm" style="padding:3px;">
                手机号:<input type="text" size="15" class="text" name="search_LIKE_mobileNo"/>
                发送时间:<input size="15" id="search_GTE_sendTime" class="text" name="search_GTE_sendTime" size="15"
                            onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                至<input size="15" id="search_LTE_sendTime" class="text" name="search_LTE_sendTime" size="15"
                        onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                状态:<select style="width:80px;height:27px;" name="search_EQ_status" editable="false" panelHeight="auto"
                           class="easyui-combobox">
                <option value="">所有</option>
            <#list allStatuss as k,v>
                <option value="${k}">${v}</option></#list>

            </select>
                <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
                   onclick="$.acooly.framework.search('manage_smsLog_searchform','manage_smsLog_datagrid');">查询</a>
            </div>
        </div>

    </div>
