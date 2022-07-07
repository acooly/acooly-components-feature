<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>
<c:if test="${initParam['ssoEnable']=='true'}">
    <%@ include file="/WEB-INF/jsp/manage/common/ssoInclude.jsp" %>
</c:if>

<script type="text/javascript">
    $(function () {
        $.acooly.framework.registerKeydown('manage_schedulerRule_searchform', 'manage_schedulerRule_datagrid');
    });

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
        <form id="manage_schedulerRule_searchform" onsubmit="return false">
            <table class="tableForm" width="100%">
                <tr>
                    <td align="left">
                        <div>
                            任务id: <input type="text" class="text" size="2" name="search_EQ_id"/>
                            任务名: <input type="text" class="text" size="15" name="search_LIKE_memo"/>
                            任务类型: <select style="width:100px;height:27px;" name="search_EQ_actionType" editable="false" panelHeight="auto"
                                          class="easyui-combobox">
                            <option value="">所有</option>
                            <c:forEach var="e" items="${allTaskTypes}">
                                <option value="${e.key}" ${param.search_EQ_actionType == e.key?'selected':''}>${e.value}</option>
                            </c:forEach></select>
                            状态: <select style="width:100px;height:27px;" name="search_EQ_status" editable="false" panelHeight="auto"
                                        class="easyui-combobox">
                            <option value="">所有</option>
                            <c:forEach var="e" items="${allStatuss}">
                                <option value="${e.key}" ${param.search_EQ_status == e.key?'selected':''}>${e.value}</option>
                            </c:forEach></select>
                            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false"
                               onclick="$.acooly.framework.search('manage_schedulerRule_searchform','manage_schedulerRule_datagrid');"><i
                                    class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_schedulerRule_datagrid" class="easyui-datagrid"
               url="${pageContext.request.contextPath}/manage/schedulerRule/listJson.html" toolbar="#manage_schedulerRule_toolbar"
               fit="true" border="false" fitColumns="false"
               pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc"
               checkOnSelect="true" selectOnCheck="true" singleSelect="true">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
                <th field="id" sum="true">id</th>
                <th field="memo">任务名</th>
                <th field="status"
                    data-options="formatter:function(value){ return formatRefrence('manage_schedulerRule_datagrid','allStatuss',value);} ">
                    状态
                </th>
                <th field="cronString">cron_string</th>
                <th field="actionType"
                    data-options="formatter:function(value){ return formatRefrence('manage_schedulerRule_datagrid','allTaskTypes',value);} ">
                    任务类型
                </th>
                <th field="properties" formatter="contentFormatter">HTTP地址</th>
                <th field="className" formatter="contentFormatter">类名</th>
                <th field="methodName" formatter="contentFormatter">方法名</th>
                <th field="dgroup" formatter="contentFormatter">dubbo组名</th>
                <th field="dversion" formatter="contentFormatter">dubbo版本</th>
                <th field="dparam" formatter="contentFormatter">dubbo参数</th>
                <th field="lastExecuteTime" formatter="dateTimeFormatter">上次执行时间</th>
                <th field="exceptionAtLastExecute" formatter="contentFormatter">上次执行结果</th>
                <th field="creater">任务创建人</th>
                <th field="rowActions"
                    data-options="formatter:function(value, row, index){return formatAction('manage_schedulerRule_action',value,row)}">动作
                </th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_schedulerRule_action" style="display: none;">
            <a onclick="$.acooly.framework.confirmSubmit('/manage/schedulerRule/runjob.html','{0}','manage_schedulerRule_datagrid');"
               href="#" title="立即执行"><i class="fa fa-play fa-lg fa-fw fa-col"></i></a>

            <a onclick="$.acooly.framework.edit({url:'/manage/schedulerRule/edit.html',id:'{0}',entity:'schedulerRule',width:620,height:520});"
               href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.show('/manage/schedulerRule/show.html?id={0}',580,600);" href="#" title="查看"><i
                    class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.remove('/manage/schedulerRule/deleteJson.html','{0}','manage_schedulerRule_datagrid');" href="#"
               title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_schedulerRule_toolbar">
            <a href="#" class="easyui-linkbutton" plain="true"
               onclick="$.acooly.framework.create({url:'/manage/schedulerRule/create.html',entity:'schedulerRule',width:620,height:520})"><i
                    class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
        </div>
    </div>

</div>
