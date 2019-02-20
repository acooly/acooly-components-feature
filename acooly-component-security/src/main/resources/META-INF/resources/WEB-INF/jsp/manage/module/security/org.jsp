<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>
<c:if test="${initParam['ssoEnable']=='true'}">
    <%@ include file="/WEB-INF/jsp/manage/common/ssoInclude.jsp" %>
</c:if>
<script type="text/javascript">
    $(function () {
        $("#manage_org_datagrid").treegrid({
            loadFilter: function (result) {
                return result.rows;
            }
        });
        $.acooly.framework.registerKeydown('manage_org_searchform', 'manage_org_datagrid');

    });
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_org_datagrid" class="easyui-treegrid"
               url="${pageContext.request.contextPath}/manage/module/security/org/listTree.html" toolbar="#manage_org_toolbar" fit="true"
               border="false" fitColumns="true"
               rownumbers="true" idField="id" treeField="name" collapsible="true" checkOnSelect="true" selectOnCheck="true">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
                <th field="id" sum="true">id</th>
                <th field="name">机构名称</th>
                <th field="statusView"
                    data-options="formatter:function(value){ return formatRefrence('manage_org_datagrid','allStatuss',value);} ">状态
                </th>
                <th field="province">省</th>
                <th field="city">市</th>
                <th field="county">区</th>
                <th field="mobileNo">手机号</th>
                <%-- <th field="address">地址</th>--%>
                <th field="contacts">联系人</th>
                <%--
                                <th field="telephone">固定电话</th>
                --%>
                <th field="email">联系邮箱</th>
                <th field="createTime" formatter="formatDate">创建时间</th>
                <th field="updateTime" formatter="formatDate">修改时间</th>
                <th field="memo">备注</th>
                <th field="rowActions"
                    data-options="formatter:function(value, row, index){return formatAction('manage_org_action',value,row)}">动作
                </th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_org_action" style="display: none;">
            <a onclick="$.acooly.framework.create({url:'/manage/module/security/org/create.html?parentId={0}',entity:'org',width:500,height:500,reload:true});"
               href="#" title="添加"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.edit({url:'/manage/module/security/org/edit.html',id:'{0}',entity:'org',width:500,height:500,reload:true});"
               href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.show('/manage/module/security/org/show.html?id={0}',500,500);" href="#" title="查看"><i
                    class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.remove('/manage/module/security/org/deleteJson.html','{0}','manage_org_datagrid');" href="#"
               title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_org_toolbar">
            <a href="#" class="easyui-linkbutton" plain="true"
               onclick="$.acooly.framework.create({url:'/manage/module/security/org/create.html?parentId=0',entity:'org',width:500,height:500})"><i
                    class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
        </div>
    </div>

</div>
