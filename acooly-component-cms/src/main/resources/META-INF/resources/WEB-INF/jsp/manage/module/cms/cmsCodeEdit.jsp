<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>
<div>
    <form id="manage_cmsCode_editform"
          action="${pageContext.request.contextPath}/manage/module/cms/cmsCode/${action=='create'?'saveJson':'updateJson'}.html"
          method="post">
        <jodd:form bean="cmsCode" scope="request">
            <input name="id" type="hidden"/>
            <table class="tableForm" width="100%">
                <tr>
                    <th width="25%">编码：</th>
                    <td><input type="text" name="keycode" size="28" class="easyui-validatebox text" validType="byteLength[1,32]"/></td>
                </tr>
                <tr>
                    <th width="25%">描述：</th>
                    <td><input type="text" name="descn" size="28" class="easyui-validatebox text" validType="byteLength[1,32]"/></td>
                </tr>
                <tr>
                    <th>所属类型：</th>
                    <td><select name="typeCode" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox"
                                data-options="required:true">
                        <c:forEach items="${codeAndNames}" var="e">
                            <option value="${e.key}">${e.value}</option>
                        </c:forEach>
                    </select></td>
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
            </table>
        </jodd:form>
    </form>
</div>
