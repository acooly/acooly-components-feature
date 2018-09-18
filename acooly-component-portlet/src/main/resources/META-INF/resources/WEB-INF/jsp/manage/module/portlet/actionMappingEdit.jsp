<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>
<div>
    <form id="manage_actionMapping_editform"
          action="${pageContext.request.contextPath}/manage/module/portlet/actionMapping/${action=='create'?'saveJson':'updateJson'}.html"
          method="post">
        <jodd:form bean="actionMapping" scope="request">
            <input name="id" type="hidden"/>
            <table class="tableForm" width="100%">
                <tr>
                    <th width="25%">连接：</th>
                    <td><input type="text" name="url" size="48" class="easyui-validatebox text" data-options="required:true"
                               validType="byteLength[1,128]"/></td>
                </tr>
                <tr>
                    <th>名称：</th>
                    <td><input type="text" name="title" size="48" class="easyui-validatebox text" data-options="required:true"
                               validType="byteLength[1,32]"/></td>
                </tr>
                <tr>
                    <th>备注：</th>
                    <td><input type="text" name="comments" size="48" class="easyui-validatebox text" validType="byteLength[1,128]"/></td>
                </tr>
            </table>
        </jodd:form>
    </form>
</div>
