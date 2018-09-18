<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>
<div>
    <form id="manage_emailTemplate_editform"
          action="${pageContext.request.contextPath}/manage/module/mail/emailTemplate/${action=='create'?'saveJson':'updateJson'}.html"
          method="post">
        <jodd:form bean="emailTemplate" scope="request">
            <input name="id" type="hidden"/>
            <table class="tableForm" width="100%">
                <tr>
                    <th width="25%">模版名称：</th>
                    <td><input type="text" name="name" rows="1" size="70" class="easyui-validatebox text" data-options="required:true"
                               validType="byteLength[1,45]"/></td>
                </tr>
                <tr>
                    <th>模板邮件主题：</th>
                    <td><input type="text" name="subject" rows="1" size="70" class="easyui-validatebox text" data-options="required:true"
                               validType="byteLength[1,1024]"/></td>
                </tr>
                <tr>
                    <th>模板邮件内容：</th>
                    <td><textarea rows="28" cols="70" name="content" class="easyui-validatebox" data-options="required:true"></textarea>
                    </td>
                </tr>

            </table>
        </jodd:form>
    </form>
</div>
