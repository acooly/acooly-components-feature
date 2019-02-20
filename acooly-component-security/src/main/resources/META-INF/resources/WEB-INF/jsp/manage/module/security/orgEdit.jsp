<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>
<script type="text/javascript" src="/manage/assert/plugin/area/area.js" charset="utf-8"></script>

<script type="text/javascript">
    _init_area();
    $(function () {
        setAreaValue();
    });

    function setAreaValue() {
        <c:if test="${org != null}">
        $('#s_province').val('${org.province}');
        change(1);
        $('#s_city').val('${org.city}');
        change(2);
        $('#s_county').val('${org.county}');
        $('#parentId').val('${org.parentId}');
        </c:if>
    }
</script>


<div>
    <form id="manage_org_editform"
          action="${pageContext.request.contextPath}/manage/module/security/org/${action=='create'?'saveJson':'updateJson'}.html"
          method="post">
        <jodd:form bean="org" scope="request">
            <input name="id" type="hidden"/>
            <table class="tableForm" width="100%">
                <input name="parentId" id="parentId" type="hidden" value="${parentId}"/>

                <tr>
                    <th>机构名称：</th>
                    <td><input type="text" name="name" size="48" class="easyui-validatebox text" validType="byteLength[1,32]"
                               data-options="required:true"/></td>
                </tr>

                <tr>
                    <th>状态：</th>
                    <td><select name="status" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox">
                        <c:forEach items="${allStatuss}" var="e">
                            <option value="${e.key}">${e.value}</option>
                        </c:forEach>
                    </select></td>
                </tr>
                <tr>
                    <th>省/市/县：</th>
                    <td>
                        <select id="s_province" name="province"></select> <select id="s_city" name="city"></select> <select id="s_county"
                                                                                                                            name="county"></select>
                    </td>
                </tr>
                <tr>
                    <th>手机号：</th>
                    <td><input type="text" name="mobileNo" size="48" class="easyui-validatebox text" validType="mobile"/></td>
                </tr>
                <tr>
                    <th>邮箱：</th>
                    <td><input type="text" name="email" size="48" class="easyui-validatebox text" validType="email"/></td>
                </tr>
                <tr>
                    <th>地址：</th>
                    <td><textarea rows="3" cols="40" style="width:300px;" name="address" class="easyui-validatebox"
                                  validType="byteLength[1,255]"></textarea></td>
                </tr>
                <tr>
                    <th>联系人：</th>
                    <td><input type="text" name="contacts" size="48" class="easyui-validatebox text" validType="byteLength[1,64]"/></td>
                </tr>
                <tr>
                    <th>固定电话：</th>
                    <td><input type="text" name="telephone" size="48" class="easyui-validatebox text" validType="byteLength[1,20]"/></td>
                </tr>

                <tr>
                    <th>备注：</th>
                    <td><textarea rows="3" cols="40" style="width:300px;" name="memo" class="easyui-validatebox"
                                  validType="byteLength[1,255]"></textarea></td>
                </tr>
            </table>
        </jodd:form>
    </form>
</div>
