<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>
<div>
    <form id="manage_siteConfig_editform"
          action="${pageContext.request.contextPath}/manage/module/portlet/siteConfig/${action=='create'?'saveJson':'updateJson'}.html"
          method="post">
        <jodd:form bean="siteConfig" scope="request">
            <input name="id" type="hidden"/>
            <table class="tableForm" width="100%">
                <tr>
                    <th width="25%">类型：</th>
                    <td><select name="type" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox">
                        <c:forEach items="${allTypes}" var="e">
                            <option value="${e.key}">${e.value}</option>
                        </c:forEach>
                    </select></td>
                </tr>
                <tr>
                    <th>标题：</th>
                    <td><input type="text" name="title" size="48" class="easyui-validatebox text" data-options="required:true"
                               validType="byteLength[1,45]"/></td>
                </tr>
                <tr>
                    <th>参数键：</th>
                    <td><input type="text" name="name" size="48" class="easyui-validatebox text" data-options="required:true"
                               validType="byteLength[1,45]"/></td>
                </tr>
                <tr>
                    <th>参数值：</th>
                    <td><textarea rows="3" cols="40" style="width:300px;" name="value" class="easyui-validatebox"
                                  validType="byteLength[1,4000]"></textarea></td>
                </tr>
                <tr>
                    <th>备注：</th>
                    <td><textarea rows="3" cols="40" style="width:300px;" name="comments" class="easyui-validatebox"
                                  validType="byteLength[1,255]"></textarea></td>
                </tr>
            </table>
        </jodd:form>
    </form>
</div>
