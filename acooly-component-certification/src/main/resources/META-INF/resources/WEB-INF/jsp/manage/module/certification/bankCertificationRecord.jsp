<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>
<c:if test="${initParam['ssoEnable']=='true'}">
    <%@ include file="/WEB-INF/jsp/manage/common/ssoInclude.jsp" %>
</c:if>
<script type="text/javascript">
    $(function () {
        $.acooly.framework.registerKeydown('manage_bankCertificationRecord_searchform', 'manage_bankCertificationRecord_datagrid');
    });

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
        <form id="manage_bankCertificationRecord_searchform" onsubmit="return false">
            <table class="tableForm" width="100%">
                <tr>
                    <td align="left">
                        <div>
                            持卡人姓名: <input type="text" class="text" size="5" name="search_LIKE_realName"/>
                            银行卡帐号: <input type="text" class="text" size="20" name="search_LIKE_cardNo"/>
                            开卡使用的证件号码: <input type="text" class="text" size="20" name="search_LIKE_certId"/>
                            绑定手机号: <input type="text" class="text" size="10" name="search_LIKE_phoneNum"/>
                            状态: <select style="width:100px;height:27px;" name="search_EQ_status" editable="false" panelHeight="auto"
                                        class="easyui-combobox">
                            <option value="">所有</option>
                            <c:forEach var="e" items="${allStatuss}">
                                <option value="${e.key}" ${param.search_EQ_status == e.key?'selected':''}>${e.value}</option>
                            </c:forEach></select>
                            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false"
                               onclick="$.acooly.framework.search('manage_bankCertificationRecord_searchform','manage_bankCertificationRecord_datagrid');"><i
                                    class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_bankCertificationRecord_datagrid" class="easyui-datagrid"
               url="${pageContext.request.contextPath}/manage/module/certification/bankCertificationRecord/listJson.html"
               toolbar="#manage_bankCertificationRecord_toolbar" fit="true" border="false" fitColumns="false"
               pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc"
               checkOnSelect="true" selectOnCheck="true" singleSelect="true">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
                <th field="id" sum="true">ID</th>
                <th field="realName">持卡人姓名</th>
                <th field="cardNo">银行卡帐号</th>
                <th field="certId">证件号码</th>
                <th field="phoneNum">绑定手机号</th>
                <th field="certType"
                    data-options="formatter:function(value){ return formatRefrence('manage_bankCertificationRecord_datagrid','allCertTypes',value);} ">
                    证件类型
                </th>
                <th field="belongArea">银行卡归属地</th>
                <th field="brand">银行卡产品名称</th>
                <th field="bankName">银行名称</th>
                <th field="cardType">银行卡种</th>
                <th field="status"
                    data-options="formatter:function(value){ return formatRefrence('manage_bankCertificationRecord_datagrid','allStatuss',value);} ">
                    状态
                </th>
                <th field="failReason">验证失败原因</th>
                <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
                <th field="rowActions"
                    data-options="formatter:function(value, row, index){return formatAction('manage_bankCertificationRecord_action',value,row)}">
                    动作
                </th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_bankCertificationRecord_action" style="display: none;">
            <a onclick="$.acooly.framework.show('/manage/module/certification/bankCertificationRecord/show.html?id={0}',500,480);" href="#"
               title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_bankCertificationRecord_toolbar">
        </div>
    </div>

</div>
