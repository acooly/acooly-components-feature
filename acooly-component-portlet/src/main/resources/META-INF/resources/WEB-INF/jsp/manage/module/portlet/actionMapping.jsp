<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>

<script type="text/javascript">
    $(function () {
        $.acooly.framework.registerKeydown('manage_actionMapping_searchform', 'manage_actionMapping_datagrid');
    });

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
        <form id="manage_actionMapping_searchform" onsubmit="return false">
            <table class="tableForm" width="100%">
                <tr>
                    <td align="left">
                        <div>
                            链接: <input type="text" class="text" size="15" name="search_LIKE_url"/>
                            名称: <input type="text" class="text" size="15" name="search_LIKE_title"/>
                            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false"
                               onclick="$.acooly.framework.search('manage_actionMapping_searchform','manage_actionMapping_datagrid');"><i
                                    class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_actionMapping_datagrid" class="easyui-datagrid"
               url="${pageContext.request.contextPath}/manage/module/portlet/actionMapping/listJson.html"
               toolbar="#manage_actionMapping_toolbar" fit="true" border="false" fitColumns="false"
               pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc"
               checkOnSelect="true" selectOnCheck="true" singleSelect="true">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
                <th field="id" sum="true">主键</th>
                <th field="url">链接</th>
                <th field="title">名称</th>
                <th field="comments">备注</th>
                <th field="rowActions"
                    data-options="formatter:function(value, row, index){return formatAction('manage_actionMapping_action',value,row)}">动作
                </th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_actionMapping_action" style="display: none;">
            <a onclick="$.acooly.framework.edit({url:'/manage/module/portlet/actionMapping/edit.html',id:'{0}',entity:'actionMapping',width:500,height:400});"
               href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.show('/manage/module/portlet/actionMapping/show.html?id={0}',500,400);" href="#" title="查看"><i
                    class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.remove('/manage/module/portlet/actionMapping/deleteJson.html','{0}','manage_actionMapping_datagrid');"
               href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_actionMapping_toolbar">
            <a href="#" class="easyui-linkbutton" plain="true"
               onclick="$.acooly.framework.create({url:'/manage/module/portlet/actionMapping/create.html',entity:'actionMapping',width:500,height:400})"><i
                    class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
            <a href="#" class="easyui-linkbutton" plain="true"
               onclick="$.acooly.framework.removes('/manage/module/portlet/actionMapping/deleteJson.html','manage_actionMapping_datagrid')"><i
                    class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>
        </div>
    </div>

</div>
