<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>

<script type="text/javascript">
    $(function () {
        $.acooly.framework.registerKeydown('manage_cmsCode_searchform', 'manage_cmsCode_datagrid');
    });

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
        <form id="manage_cmsCode_searchform" onsubmit="return false">
            <table class="tableForm" width="100%">
                <tr>
                    <td align="left">
                        <div>
                            编码: <input type="text" class="text" size="15" name="search_LIKE_keycode"/>

                            所属类型: <select style="width:100px;height:27px;" name="search_EQ_typeCode" editable="false" panelHeight="auto"
                                          class="easyui-combobox">
                            <option value="">所有</option>
                            <c:forEach var="e" items="${codeAndNames}">
                                <option value="${e.key}" ${param.search_EQ_typeCode == e.key?'selected':''}>${e.value}</option>
                            </c:forEach></select>

                            状态: <select style="width:100px;height:27px;" name="search_EQ_status" editable="false" panelHeight="auto"
                                        class="easyui-combobox">
                            <option value="">所有</option>
                            <c:forEach var="e" items="${allStatuss}">
                                <option value="${e.key}" ${param.search_EQ_status == e.key?'selected':''}>${e.value}</option>
                            </c:forEach></select>

                            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false"
                               onclick="$.acooly.framework.search('manage_cmsCode_searchform','manage_cmsCode_datagrid');"><i
                                    class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_cmsCode_datagrid" class="easyui-datagrid"
               url="${pageContext.request.contextPath}/manage/module/cms/cmsCode/listJson.html" toolbar="#manage_cmsCode_toolbar" fit="true"
               border="false" fitColumns="false"
               pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc"
               checkOnSelect="true" selectOnCheck="true" singleSelect="true">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
                <th field="id" sum="true">主键</th>
                <th field="keycode">编码</th>
                <th field="descn">描述</th>

                <th field="status"
                    data-options="formatter:function(value){ return formatRefrence('manage_cmsCode_datagrid','allStatuss',value);} ">状态
                </th>
                <th field="typeCode"
                    data-options="formatter:function(value){ return formatRefrence('manage_cmsCode_datagrid','codeAndNames',value);} ">所属类型
                </th>
                <th field="createTime" formatter="formatDate">创建时间</th>
                <th field="updateTime" formatter="formatDate">修改时间</th>
                <th field="rowActions"
                    data-options="formatter:function(value, row, index){return formatAction('manage_cmsCode_action',value,row)}">动作
                </th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_cmsCode_action" style="display: none;">
            <a onclick="$.acooly.framework.remove('/manage/module/cms/cmsCode/deleteJson.html','{0}','manage_cmsCode_datagrid');" href="#"
               title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_cmsCode_toolbar">
            <a href="#" class="easyui-linkbutton" plain="true"
               onclick="$.acooly.framework.create({url:'/manage/module/cms/cmsCode/create.html',entity:'cmsCode',width:500,height:240})"><i
                    class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
        </div>
    </div>

</div>
