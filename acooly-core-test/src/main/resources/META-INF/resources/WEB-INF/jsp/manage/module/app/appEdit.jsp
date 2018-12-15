<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>
<div>
    <form id="manage_app_editform"
          action="${pageContext.request.contextPath}/manage/module/app/app/${action=='create'?'saveJson':'updateJson'}.html" method="post">
        <jodd:form bean="app" scope="request">
            <input name="id" type="hidden"/>
            <table class="tableForm" width="100%">
                <tr>
                    <th width="25%">display_name：</th>
                    <td><textarea rows="3" cols="40" style="width:300px;" name="displayName" class="easyui-validatebox"
                                  validType="byteLength[1,255]"></textarea></td>
                </tr>
                <tr>
                    <th>name：</th>
                    <td><textarea rows="3" cols="40" style="width:300px;" name="name" class="easyui-validatebox"
                                  validType="byteLength[1,255]"></textarea></td>
                </tr>
                <tr>
                    <th>parent_app_id：</th>
                    <td><input type="text" name="parentAppId" size="48" class="easyui-numberbox text" validType="byteLength[1,19]"/></td>
                </tr>
                <tr>
                    <th>type：</th>
                    <td><textarea rows="3" cols="40" style="width:300px;" name="type" class="easyui-validatebox"
                                  validType="byteLength[1,255]"></textarea></td>
                </tr>
                <tr>
                    <th>user_id：</th>
                    <td><input type="text" name="userId" size="48" class="easyui-numberbox text" validType="byteLength[1,19]"/></td>
                </tr>
                <tr>
                    <th>创建时间：</th>
                    <td><input type="text" name="rawAddTime" size="20" class="easyui-validatebox text"
                               value="<fmt:formatDate value="${app.rawAddTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                               onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" data-options="required:true"/></td>
                </tr>
                <tr>
                    <th>修改时间：</th>
                    <td><input type="text" name="rawUpdateTime" size="20" class="easyui-validatebox text"
                               value="<fmt:formatDate value="${app.rawUpdateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                               onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/></td>
                </tr>
                <tr>
                    <th>parent_id：</th>
                    <td><input type="text" name="parentId" size="48" class="easyui-numberbox text" validType="byteLength[1,19]"/></td>
                </tr>
            </table>
        </jodd:form>
    </form>
</div>
