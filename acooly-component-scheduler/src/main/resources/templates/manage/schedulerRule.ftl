<#if ssoEnable><#include "/manage/common/ssoInclude.ftl"></#if>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
        <form id="manage_schedulerRule_searchform" class="form-inline ac-form-search" onsubmit="return false">
            <div class="form-group">
                <label class="col-form-label">任务名称:</label>
                <input type="text" placeholder="任务名称" class="form-control form-control-sm"  name="search_LIKE_memo"/>
            </div>
            <div class="form-group">
                <label class="col-form-label">类型:</label>
                <select name="search_EQ_actionType" class="form-control input-sm select2bs4">
                    <option value="">所有</option><#list allTaskTypes as k,v><option value="${k}">${v}</option></#list>
                </select>
            </div>
            <div class="form-group">
                <label class="col-form-label">状态:</label>
                <select name="search_EQ_status" class="form-control input-sm select2bs4">
                    <option value="">所有</option><#list allStatuss as k,v><option value="${k}">${v}</option></#list>
                </select>
            </div>
            <div class="form-group">
                <button class="btn btn-sm btn-primary" type="button" onclick="$.acooly.framework.search('manage_schedulerRule_searchform','manage_schedulerRule_datagrid');"><i class="fa fa-search fa-fw fa-col"></i> 查询</button>
            </div>
        </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_schedulerRule_datagrid" class="easyui-datagrid"
               url="${pageContext.request.contextPath}/manage/schedulerRule/listJson.html" toolbar="#manage_schedulerRule_toolbar"
               fit="true" fitColumns="false" pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc"
               checkOnSelect="true" selectOnCheck="true" singleSelect="true">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
                <th field="id" sum="true">id</th>
                <th field="memo">任务名</th>
                <th field="status" formatter="mappingFormatter">状态</th>
                <th field="cronString">cron_string</th>
                <th field="actionType" formatter="mappingFormatter">任务类型</th>
                <th field="properties">HTTP地址</th>
                <th field="className">类名</th>
                <th field="methodName" formatter="contentFormatter">方法名</th>
                <th field="dubboGroup">dubbo组名</th>
                <th field="dubboVersion">dubbo版本</th>
                <th field="dubboParam">dubbo参数</th>
                <th field="lastExecuteTime" formatter="dateTimeFormatter">上次执行时间</th>
                <th field="exceptionAtLastExecute" formatter="contentFormatter">上次执行结果</th>
                <th field="creater">任务创建人</th>
            </tr>
            </thead>
            <thead frozen="true">
            <tr>
                <th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_schedulerRule_action',value,row)}">动作</th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_schedulerRule_action" style="display: none;">

            <div class="btn-group btn-group-xs">
                <a onclick="$.acooly.framework.confirmSubmit('/manage/schedulerRule/runjob.html','{0}','manage_schedulerRule_datagrid');" class="btn btn-outline-primary btn-xs" href="#" title="立即执行"><i class="fa fa-play"></i> 立即执行</a>
                <a onclick="$.acooly.framework.edit({url:'/manage/schedulerRule/edit.html',id:'{0}',entity:'schedulerRule',width:620,height:520});" class="btn btn-outline-primary btn-xs" href="#" title="编辑"><i class="fa fa-pencil"></i> 编辑</a>
                <a onclick="$.acooly.framework.show('/manage/schedulerRule/show.html?id={0}',580,600);" class="btn btn-outline-primary btn-xs" href="#" title="查看"><i class="fa fa-file-o"></i> 查看</a>
                <a onclick="$.acooly.framework.remove('/manage/schedulerRule/deleteJson.html','{0}','manage_schedulerRule_datagrid');" class="btn btn-outline-primary btn-xs" href="#" title="删除"><i class="fa fa-trash-o"></i> 删除</a>
            </div>




        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_schedulerRule_toolbar">
            <a href="#" class="easyui-linkbutton" plain="true"
               onclick="$.acooly.framework.create({url:'/manage/schedulerRule/create.html',entity:'schedulerRule',width:620,height:520})"><i
                    class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
        </div>
    </div>
    <script type="text/javascript">
        $(function () {
            $.acooly.framework.initPage('manage_schedulerRule_searchform', 'manage_schedulerRule_datagrid');
        });
    </script>
</div>
