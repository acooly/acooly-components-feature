<#if ssoEnable><#include "/manage/common/ssoInclude.ftl"></#if>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
        <form id="manage_emailTemplate_searchform" class="form-inline ac-form-search" onsubmit="return false">
            <div class="form-group">
                <label class="col-form-label">模版：</label>
                <input type="text" class="form-control form-control-sm" name="search_LIKE_name"/>
            </div>
            <div class="form-group">
                <label class="col-form-label">默认主题：</label>
                <input type="text" class="form-control form-control-sm" name="search_LIKE_subject"/>
            </div>

            <div class="form-group">
                <button class="btn btn-sm btn-primary" type="button" onclick="$.acooly.framework.search('manage_emailTemplate_searchform','manage_emailTemplate_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i> 查询</button>
            </div>
        </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_emailTemplate_datagrid" class="easyui-datagrid"
               url="${pageContext.request.contextPath}/manage/module/mail/emailTemplate/listJson.html"
               toolbar="#manage_emailTemplate_toolbar" fit="true" border="false" fitColumns="false"
               pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc"
               checkOnSelect="true" selectOnCheck="true" singleSelect="true">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
                <th field="id" sum="true">id</th>
                <th field="name">模版名称</th>
                <th field="title">模版标题</th>
                <th field="subject">默认主题</th>
                <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
                <th field="updateTime" formatter="dateTimeFormatter">修改时间</th>
                <th field="rowActions"
                    data-options="formatter:function(value, row, index){return formatAction('manage_emailTemplate_action',value,row)}">动作
                </th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_emailTemplate_action" style="display: none;">
            <a onclick="$.acooly.framework.edit({url:'/manage/module/mail/emailTemplate/edit.html',id:'{0}',entity:'emailTemplate',width:800,height:600});"
               href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.show('/manage/module/mail/emailTemplate/show.html?id={0}',800,600);" href="#" title="查看模板"><i
                    class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.remove('/manage/module/mail/emailTemplate/deleteJson.html','{0}','manage_emailTemplate_datagrid');"
               href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_emailTemplate_toolbar">
            <a href="#" class="easyui-linkbutton" plain="true"
               onclick="$.acooly.framework.create({url:'/manage/module/mail/emailTemplate/create.html',entity:'emailTemplate',width:800,height:600})"><i
                    class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
        </div>
    </div>
    <script type="text/javascript">
        $(function () {
            $.acooly.framework.initPage('manage_emailTemplate_searchform', 'manage_emailTemplate_datagrid');
        });
    </script>
</div>
