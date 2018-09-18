<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>
<script>
    //加载方案下拉菜单
    $.ajax({
        url: "/eav/getEavSchemas",
        async: false,
        success: function (result) {
            var data = [];
            var first = true;
            $.each(result.data, function (i, val) {
                data.push({"text": val.name, "id": val.id, "selected": first});
                if (first) {
                    window.data.eavSchemaId = val.id;
                }
                first = false
                $("#schemaId").append("<option value='" + val.id + "'>" + val.name + "</option>");
            })
            window.data.eavSchemas = data;
        }
    });

    <c:if test="${action=='edit'}">
        var nullable =${eavAttribute.nullable};
        if (nullable) {
            nullable = 1;
        } else {
            nullable = 0;
        }
        $("select[name='nullable']").attr('value', nullable);
        $("select[name='schemaId']").attr('value', ${eavAttribute.schemaId}).attr("disabled", "disabled");
    </c:if>

</script>
<div>
    <form id="manage_eavAttribute_editform"
          action="${pageContext.request.contextPath}/manage/module/eav/eavAttribute/${action=='create'?'saveJson':'updateJson'}.html"
          method="post">
        <jodd:form bean="eavAttribute" scope="request">
            <input name="id" type="hidden"/>
            <table class="tableForm" width="100%">
                <tr>
                    <th width="25%">方案：</th>
                    <td>
                        <select name="schemaId" style="width:100px;"
                                id="schemaId">
                        </select>
                    </td>
                        <%--<td><input type="text" name="schemaId" size="48" placeholder="请输入方案id..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]'],required:true"/></td>--%>
                </tr>
                <tr>
                    <th>属性名称：</th>
                    <td><input type="text" name="name" size="48" placeholder="请输入属性名称..."
                               class="easyui-validatebox text"
                               data-options="validType:['length[1,128]'],required:true"/></td>
                </tr>
                <tr>
                    <th>展示名称：</th>
                    <td><input type="text" name="displayName" size="48" placeholder="请输入展示名称..."
                               class="easyui-validatebox text"
                               data-options="validType:['length[1,128]'],required:true"/></td>
                </tr>
                <tr>
                    <th>备注：</th>
                    <td><input type="text" name="memo" size="48" placeholder="请输入备注..." class="easyui-validatebox text"
                               data-options="validType:['length[1,128]']"/></td>
                </tr>
                <tr>
                    <th>是否可以为空：</th>
                    <td>
                        <select name="nullable">
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select>
                            <%--<input type="text" name="nullable" size="48" placeholder="请输入是否可以为空..." class="easyui-validatebox text" data-options="validType:['length[1,1]']"/></td>--%>
                </tr>
                <tr>
                    <th>最小值：</th>
                    <td><input type="text" name="minimum" size="48" placeholder="请输入最小值..."
                               style="height: 27px;line-height: 27px;" class="easyui-numberbox text"
                               data-options="validType:['length[1,19]']"/></td>
                </tr>
                <tr>
                    <th>最大值：</th>
                    <td><input type="text" name="maximum" size="48" placeholder="请输入最大值..."
                               style="height: 27px;line-height: 27px;" class="easyui-numberbox text"
                               data-options="validType:['length[1,19]']"/></td>
                </tr>
                <tr>
                    <th>最小长度：</th>
                    <td><input type="text" name="minLength" size="48" placeholder="请输入最小长度..."
                               style="height: 27px;line-height: 27px;" class="easyui-numberbox text"
                               data-options="validType:['length[1,10]']"/></td>
                </tr>
                <tr>
                    <th>最大长度：</th>
                    <td><input type="text" name="maxLength" size="48" placeholder="请输入最大长度..."
                               style="height: 27px;line-height: 27px;" class="easyui-numberbox text"
                               data-options="validType:['length[1,10]']"/></td>
                </tr>
                <tr>
                    <th>正则表达式：</th>
                    <td><input type="text" name="regex" size="48" placeholder="请输入正则表达式..."
                               class="easyui-validatebox text" data-options="validType:['length[1,30]']"/></td>
                </tr>
                <tr>
                    <th>枚举值：</th>
                    <td><input type="text" name="enumValue" size="48" placeholder="请输入枚举值..."
                               class="easyui-validatebox text" data-options="validType:['length[1,128]']"/></td>
                </tr>
                <tr>
                    <th>属性类型：</th>
                    <td><select name="attributeType" editable="false" style="height:27px;" panelHeight="auto"
                                class="easyui-combobox">
                        <c:forEach items="${allAttributeTypes}" var="e">
                            <option value="${e.key}">${e.value}</option>
                        </c:forEach>
                    </select></td>
                </tr>
            </table>
        </jodd:form>
    </form>
</div>
