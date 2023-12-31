<#if ssoEnable><#include "/manage/common/ssoInclude.ftl"></#if>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
        <form id="manage_smsBlackList_searchform" class="form-inline ac-form-search" onsubmit="return false">
            <div class="form-group">
                <label class="col-form-label">手机号码：</label>
                <input type="text" class="form-control form-control-sm" name="search_LIKE_mobile"/>
            </div>
            <div class="form-group">
                <button class="btn btn-sm btn-primary" type="button" onclick="$.acooly.framework.search('manage_smsBlackList_searchform','manage_smsBlackList_datagrid');"><i class="fa fa-search fa-fw fa-col"></i> 查询</button>
                <button class="btn btn-sm btn-primary ml-2" type="button" onclick="$.acooly.framework.create({url:'/manage/smsend/smsBlackList/create.html',entity:'smsBlackList',width:500,height:300});"><i class="fa fa-plus-circle fa-fw fa-col"></i> 添加</button>
            </div>
        </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_smsBlackList_datagrid" class="easyui-datagrid" url="/manage/smsend/smsBlackList/listJson.html"
               fit="true" border="false" fitColumns="false"
               pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc"
               checkOnSelect="true" selectOnCheck="true" singleSelect="true">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
                <th field="id" sum="true">ID</th>
                <th field="mobile">手机号</th>
                <th field="status"
                    data-options="formatter:function(value){ return formatRefrence('manage_smsBlackList_datagrid','allStatuss',value);} ">状态
                </th>
                <th field="description" formatter="contentFormatter">描述</th>
                <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
                <th field="updateTime" formatter="dateTimeFormatter">修改时间</th>
                <th field="rowActions"
                    data-options="formatter:function(value, row, index){return formatAction('manage_smsBlackList_action',value,row)}">动作
                </th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_smsBlackList_action" style="display: none;">
            <a onclick="$.acooly.framework.edit({url:'/manage/smsend/smsBlackList/edit.html',id:'{0}',entity:'smsBlackList',width:500,height:300});"
               href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.remove('/manage/smsend/smsBlackList/deleteJson.html','{0}','manage_smsBlackList_datagrid');"
               href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
        </div>
    </div>
    <script type="text/javascript">
        $(function () {
            $.acooly.framework.initPage('manage_smsBlackList_searchform', 'manage_smsBlackList_datagrid');
        });
    </script>
</div>
