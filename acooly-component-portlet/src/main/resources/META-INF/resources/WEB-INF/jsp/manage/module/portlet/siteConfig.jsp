<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>

<script type="text/javascript">
    $(function () {
        $.acooly.framework.registerKeydown('manage_siteConfig_searchform', 'manage_siteConfig_datagrid');
    });

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
        <form id="manage_siteConfig_searchform" onsubmit="return false">
            <table class="tableForm" width="100%">
                <tr>
                    <td align="left">
                        <div>
                            类型: <select style="width:80px;height:27px;" name="search_EQ_type" editable="false" panelHeight="auto"
                                        class="easyui-combobox">
                            <option value="">所有</option>
                            <c:forEach var="e" items="${allTypes}">
                                <option value="${e.key}" ${param.search_EQ_type == e.key?'selected':''}>${e.value}</option>
                            </c:forEach></select>
                            标题: <input type="text" class="text" size="15" name="search_LIKE_title"/>
                            参数键: <input type="text" class="text" size="15" name="search_LIKE_key"/>
                            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false"
                               onclick="$.acooly.framework.search('manage_siteConfig_searchform','manage_siteConfig_datagrid');"><i
                                    class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_siteConfig_datagrid" class="easyui-datagrid"
               url="${pageContext.request.contextPath}/manage/module/portlet/siteConfig/listJson.html" toolbar="#manage_siteConfig_toolbar"
               fit="true" border="false" fitColumns="false"
               pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc"
               checkOnSelect="true" selectOnCheck="true" singleSelect="true">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
                <th field="id" sum="true">主键</th>
                <th field="type"
                    data-options="formatter:function(value){ return formatRefrence('manage_siteConfig_datagrid','allTypes',value);} ">类型
                </th>
                <th field="title">标题</th>
                <th field="name">参数键</th>
                <th field="value">参数值</th>
                <th field="comments">备注</th>
                <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
                <th field="updateTime" formatter="dateTimeFormatter">更新时间</th>
                <th field="rowActions"
                    data-options="formatter:function(value, row, index){return formatAction('manage_siteConfig_action',value,row)}">动作
                </th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_siteConfig_action" style="display: none;">
            <a onclick="$.acooly.framework.edit({url:'/manage/module/portlet/siteConfig/edit.html',id:'{0}',entity:'siteConfig',width:500,height:400});"
               href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.show('/manage/module/portlet/siteConfig/show.html?id={0}',500,400);" href="#" title="查看"><i
                    class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.remove('/manage/module/portlet/siteConfig/deleteJson.html','{0}','manage_siteConfig_datagrid');"
               href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_siteConfig_toolbar">
            <a href="#" class="easyui-linkbutton" plain="true"
               onclick="$.acooly.framework.create({url:'/manage/module/portlet/siteConfig/create.html',entity:'siteConfig',width:500,height:400})"><i
                    class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
            <a href="#" class="easyui-linkbutton" plain="true"
               onclick="$.acooly.framework.removes('/manage/module/portlet/siteConfig/deleteJson.html','manage_siteConfig_datagrid')"><i
                    class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>
        </div>
    </div>

</div>
