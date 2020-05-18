<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>
<div>
    <form id="manage_smsBlackList_editform"
          action="${pageContext.request.contextPath}/manage/module/sms/smsBlackList/${action=='create'?'saveJson':'updateJson'}.html"
          method="post">
        <jodd:form bean="smsBlackList" scope="request">
            <input name="id" type="hidden"/>
            <table class="tableForm" width="100%">
                <br>
                <tr>
                    <th width="25%">手机号：</th>
                    <td><input type="text" name="mobile" size="38" class="easyui-validatebox text" data-options="required:true"
                               validType="mobile"/></td>
                </tr>
                <tr>
                    <th>状态：</th>
                    <td><select name="status" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox"
                                data-options="required:true">
                        <c:forEach items="${allStatuss}" var="e">
                            <option value="${e.key}">${e.value}</option>
                        </c:forEach>
                    </select></td>
                </tr>
                <tr>
                    <th>描述：</th>
                    <td><textarea rows="3" cols="40" style="width:300px;" name="description" class="easyui-validatebox"
                                  validType="byteLength[1,512]"></textarea></td>
                </tr>
            </table>
        </jodd:form>
    </form>
</div>
