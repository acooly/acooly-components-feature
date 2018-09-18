<#if ssoEnable>
    <#include "*/include.ftl">
</#if>
<script type="text/javascript">
    $(function () {
        $.acooly.framework.registerKeydown('manage_content${RequestParameters.code}_searchform', 'manage_content${RequestParameters.code}_datagrid');
    });


    function manage_content${RequestParameters.code}_size() {
        var cmsType = '${RequestParameters.cmsType}';
        var scale = {w: 1000, h: 600};
        if (cmsType == 'banner') {
            scale.w = 500;
            scale.h = 400;
        }
        return scale;
    }


    function manage_content${RequestParameters.code}_show() {
        var rows = $.acooly.framework._getCheckedRows("manage_content${RequestParameters.code}_datagrid");
        var id = "";
        if (rows.length == 1) {
            id = rows[0].id;
            $.acooly.framework.show('/manage/module/cms/content/show.html?id=' + id, '900', '600');
        } else if (rows.length > 1) {
            $.messager.show({title: '提示', msg: '请勾选一条记录'});
        } else {
            $.messager.show({title: '提示', msg: '请勾选要查看的记录'});
        }
    }

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
        <form id="manage_content${RequestParameters.code}_searchform" onsubmit="return false">
            <table class="tableForm" width="100%">
                <tr>
                    <td align="left">
                        标题:<input type="text" class="text" name="search_LIKE_title"/>
                        关键字:<input type="text" class="text" name="search_LIKE_keywords" "/>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
                           onclick="$.acooly.framework.search('manage_content${RequestParameters.code}_searchform', 'manage_content${RequestParameters.code}_datagrid');">查询</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_content${RequestParameters.code}_datagrid" class="easyui-datagrid"
               url="/manage/module/cms/content/listJson.html?code=${RequestParameters.code}&cmsType=${RequestParameters.cmsType}"
               toolbar="#manage_content${RequestParameters.code}_toolbar" fit="true" border="false" fitColumns="false"
               pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id"
               sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
                <th field="id">主键</th>
                <th field="contentType" data-options="formatter:function(e){return e.name;}">类型</th>
                <th field="title">标题</th>
                <#if RequestParameters.cmsType = 'banner'>
                <th field="cover"
                    data-options="formatter:function(v,r,i){ if(v){return '<img src=\'${mediaRoot}/'+v+'\' width=\'100\'>';}else{return '';} }">
                    pc图片
                </th>
                <th field="appcover"
                    data-options="formatter:function(v,r,i){ if(v){return '<img src=\'${mediaRoot}/'+v+'\' width=\'100\'>';}else{return '';} }">
                    app图片
                </th>

                <th field="link" formatter="linkFormatter">链接</th>
                <#else >
                <th field="keywords">关键字</th>
                <th field="keycode">编码</th>
                </#if>
                <th field="pubDate" formatter="dateTimeFormatter">发布时间</th>
                <th field="rowActions"
                    data-options="formatter:function(value, row, index){return formatAction('manage_content${RequestParameters.code}_action',value,row)}">
                    动作
                </th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_content${RequestParameters.code}_action" style="display: none;">
            <a class="line-action icon-edit"
               onclick="$.acooly.framework.edit({url:'/manage/module/cms/content/edit.html?code=${RequestParameters.code}&cmsType=${RequestParameters.cmsType}',id:{0},entity:'content${RequestParameters.code}',width:manage_content${RequestParameters.code}_size().w,height:manage_content${RequestParameters.code}_size().h,maximizable:true});"
               href="#"></a>
            <a class="line-action icon-delete"
               onclick="$.acooly.framework.remove('/manage/module/cms/content/deleteJson.html',{0},'manage_content${RequestParameters.code}_datagrid');"
               href="#"></a>
            <a class="line-action icon-movetop"
               onclick="$.acooly.framework.move('/manage/module/cms/content/moveTop.html',{0},'manage_content${RequestParameters.code}_datagrid');"
               href="#"></a>
            <a class="line-action icon-moveup"
               onclick="$.acooly.framework.move('/manage/module/cms/content/moveUp.html',{0},'manage_content${RequestParameters.code}_datagrid');"
               href="#"></a>
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_content${RequestParameters.code}_toolbar">
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
               onclick="$.acooly.framework.create({url:'/manage/module/cms/content/create.html?code=${RequestParameters.code}&cmsType=${RequestParameters.cmsType}',entity:'content${RequestParameters.code}',width:manage_content${RequestParameters.code}_size().w,height:manage_content${RequestParameters.code}_size().h,maximizable:true})">添加</a>
            <!--
            <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true"
               onclick="manage_content${RequestParameters.code}_show();"> 详情 </a>
            -->
        </div>
    </div>

</div>
