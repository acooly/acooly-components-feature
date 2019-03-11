<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>
<style>
    .tableForm .checkInput { padding: 0; width: 27px; height: 18px; font-size: 1px; text-align: center; overflow: hidden;}
</style>
<div>
    <form id="manage_eavAttribute_editform"
          action="${pageContext.request.contextPath}/manage/module/eav/eavAttribute/${action=='create'?'saveJson':'updateJson'}.html" method="post">
        <jodd:form bean="eavAttribute" scope="request">
            <input name="id" type="hidden"/>
            <table class="tableForm" width="100%">
                <tr>
                    <th width="15%">方案：</th>
                    <td colspan="3">
                        <c:if test="${eavScheme!=null}">
                            <input type="hidden" name="schemeId" value="${eavScheme.id}">
                            <input type="hidden" name="schemeName" value="${eavScheme.name}" />
                            ${eavScheme.name}/${eavScheme.title}
                        </c:if>
                        <c:if test="${eavScheme==null}">
                            <select name="schemeId" id="manage_eavAttribute_editform_schemeId" editable="false" panelHeight="auto" class="easyui-combobox">
                                <c:forEach items="${allSchemes}" var="e">
                                    <option name="${e.name}" value="${e.id}">${e.name}/${e.title}</option>
                                </c:forEach>
                            </select>
                            <input name="schemeName" id="manage_eavAttribute_editform_schemeName" type="hidden"/>
                        </c:if>

                    </td>
                </tr>
                <tr>
                    <th width="15%">属性名称：</th>
                    <td><input type="text" name="name" size="20" placeholder="请输入属性名称..." style="width: 200px;"
                               class="easyui-validatebox text" data-options="validType:['length[1,128]'],required:true"/></td>
                    <th width="15%">展示名称：</th>
                    <td><input type="text" name="displayName" size="20" placeholder="请输入展示名称..." style="width: 200px;"
                               class="easyui-validatebox text" data-options="validType:['length[1,128]'],required:true"/></td>
                </tr>
                <tr>
                    <th>属性类型：</th>
                    <td><select id="manage_eavAttribute_editform_attributeType" style="width:200px;" name="attributeType" editable="false" panelHeight="auto" class="easyui-combobox">
                        <c:forEach items="${allAttributeTypes}" var="e">
                            <option value="${e.key}">${e.value}</option>
                        </c:forEach>
                    </select></td>
                    <th>分组标签：</th>
                    <td><select name="tag" id="manage_eavAttribute_editform_tag" editable="true" style="width:200px;" panelHeight="auto" class="easyui-combobox">
                        <option value=""></option>
                        <c:forEach items="${tags}" var="e">
                            <option value="${e.tag}">${e.tag}</option>
                        </c:forEach>
                    </select></td>
                </tr>
                <tr>
                    <th>是否为空：</th>
                    <td><select name="nullable" editable="false" panelHeight="auto" class="easyui-combobox">
                        <c:forEach items="${allWhetherStatus}" var="e">
                            <option value="${e.key}">${e.value}</option>
                        </c:forEach>
                    </select>
                    </td>
                    <th>默认值：</th>
                    <td><input type="text" name="defaultValue" size="48" placeholder="请输入属性默认值..." style="width: 200px;"
                               class="easyui-validatebox text" data-options="validType:['length[1,256]']"/>
                    </td>
                </tr>
                <tr>
                    <th>显示权限：</th>
                    <td>
                        <c:forEach items="${allAttributeShowTypes}" var="e">
                            <input type="checkbox" name="showType" checked value="${e.key}"> ${e.value}
                        </c:forEach>
                    </td>
                    <th>显示格式：</th>
                    <td><select name="showFormat" style="width:200px;" editable="false" panelHeight="auto" class="easyui-combobox">
                        <c:forEach items="${allAttributeFormats}" var="e">
                            <option value="${e.key}">${e.value}</option>
                        </c:forEach>
                    </select></td>
                </tr>

                <tbody id="manage_eavAttribute_editform_number_label" style="display: none">
                <tr>
                    <th>最小值：</th>
                    <td><input type="text" name="minimum" size="20" placeholder="请输入最小值..." style="width: 200px;line-height: 30px; height: 30px;"
                               class="easyui-numberbox text" data-options="validType:['length[1,19]']"/></td>
                    <th>最大值：</th>
                    <td><input type="text" name="maximum" size="20" placeholder="请输入最大值..." style="width: 200px;line-height: 30px; height: 30px;"
                               class="easyui-numberbox text" data-options="validType:['length[1,19]']"/></td>
                </tr>
                </tbody>
                <tbody id="manage_eavAttribute_editform_string_label" style="display: none">
                <tr>
                    <th>最小长度：</th>
                    <td><input type="text" name="minLength" size="20" placeholder="请输入最小长度..." style="width: 200px; line-height: 30px; height: 30px;"
                               class="easyui-numberbox text" data-options="validType:['length[1,10]']"/></td>
                    <th>最大长度：</th>
                    <td><input type="text" name="maxLength" size="20" placeholder="请输入最大长度..." style="width: 200px;line-height: 30px; height: 30px;"
                               class="easyui-numberbox text" data-options="validType:['length[1,10]']"/></td>
                </tr>
                </tbody>
                <tr id="manage_eavAttribute_editform_enum_label" style="display:none;">
                    <th>枚举值：</th>
                    <td colspan="3">
                        <select name="enumValue" editable="false" style="width:200px;" panelHeight="auto" class="easyui-combobox">
                            <c:forEach items="${options}" var="e">
                                <option value="${e.key}">${e.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>正则表达式：</th>
                    <td><input type="text" name="regex" size="20" placeholder="请输入正则表达式..." style="width: 200px;"
                               class="easyui-validatebox text" data-options="validType:['length[1,256]']"/></td>
                    <th>正则错误消息：</th>
                    <td><input type="text" name="regexMessage" size="20" placeholder="请输入正则验证错误的消息..." style="width: 200px;"
                               class="easyui-validatebox text" data-options="validType:['length[1,128]']"/></td>
                </tr>
                <tr>
                    <th>备注：</th>
                    <td colspan="3"><input type="text" name="memo" size="48" placeholder="请输入备注..." style="width: 600px;"
                                           class="easyui-validatebox text" data-options="validType:['length[1,128]']"/></td>
                </tr>
            </table>
        </jodd:form>
    </form>
    <script>


        function manage_eavAttribute_editform_beforeSubmit(){
            $.each($("#manage_eavAttribute_editform .combo-text"),function(i,o){
                if($(o).parent().prev().attr('comboname') == 'tag'){
                    var val = $(o).val();
                    $('#manage_eavAttribute_editform_tag').combobox('setValue',val);
                    // $('#manage_eavAttribute_editform :input[name=tag]').val(val);
                    console.info("set tag value:",val);
                }
            });
            return true;
        }


        // todo: easyui根据表单名选择和值
        /**
         * 根据属性类型，动态显示需要填写的字段
         */
        function initAttributeForm() {
            var selectedAttributeType = $('#manage_eavAttribute_editform_attributeType').combo("getValue");
            $('#manage_eavAttribute_editform_number_label').hide();
            $('#manage_eavAttribute_editform_string_label').hide();
            $('#manage_eavAttribute_editform_enum_label').hide();

            if (selectedAttributeType == 'ENUM') {
                $('#manage_eavAttribute_editform_enum_label').show();
            } else if (selectedAttributeType.startsWith("NUMBER")) {
                $('#manage_eavAttribute_editform_number_label').show();
            } else {
                $('#manage_eavAttribute_editform_string_label').show();
            }
        }

        /**
         * 根据选择的方案设置方案名称隐藏字段
         */
        function initSchemeAttribute() {
            var selectedSchemeId = $('#manage_eavAttribute_editform_schemeId').combo("getValue");
            var options = $('#manage_eavAttribute_editform_schemeId option');
            $(options).each(function () {
                if (selectedSchemeId == $(this).attr('value')) {
                    $('#manage_eavAttribute_editform_schemeName').val($(this).attr('name'));
                }
            })
        }

        function initEavAttributeShowType(){
            var showType = "${eavAttribute.showType}";
            if(showType == ''){
                return;
            }
            var perms = parseInt(showType);
            $('#manage_eavAttribute_editform :input[name=showType]').each(function(index){
                var v = parseInt($(this).val());
                if((perms & v) == v){
                    $(this).attr("checked","true");
                }else{
                    $(this).removeAttr("checked");
                }
            });
        }

        $(function () {

            /**
             * OnChange事件绑定
             */
            $('#manage_eavAttribute_editform_attributeType').combobox({
                onChange: function (n, o) {
                    initAttributeForm();
                }
            });
            $('#manage_eavAttribute_editform_schemeId').combobox({
                onChange: function (n, o) {
                    initSchemeAttribute();
                }
            });

            initAttributeForm();
            // initSchemeAttribute();
            initEavAttributeShowType();
        });

    </script>
</div>
