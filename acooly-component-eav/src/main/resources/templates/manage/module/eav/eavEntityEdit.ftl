<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<script src="asset/eav/eav.js"></script>
<div>
    <script type="text/javascript">
        // $.ajax({
        //     url: "/eav/getEavSchema?id=" + window.data.eavSchemaId,
        //     async: false,
        //     success: function (result) {
        //         console.info(result);
        //         $("#schemaName").text(result.data.name);
        //         $("#eavSchemaId").val(window.data.eavSchemaId);
        //         var attrs = result.data.attributes;
        //         for (var key in attrs) {
        //             var attr = attrs[key];
        //             var template = '<tr><th>{{attr.displayName}}：</th><td>{{{input}}}</td></tr>';
        //             attr.forQueryCondition=false;
        //             $("#eavCreateTable tr:eq(0)").after(appendTable(template,attr));
        //         }
        //     }
        // });

        <#--<%--<c:if test="${action=='edit'}">--%>-->
        <#--<%--var entity =${entityJson};--%>-->
        <#--<%--$("#manage_eavEntity_editform").unserialize(entity);--%>-->
        <#--<%--</c:if>--%>-->

    </script>
    <form id="manage_eavEntity_editform"
          action="/manage/module/eav/eavEntity/<#if action == 'create'>save<#else>update</#if>Json.html"
          method="post">
        <input name="id" type="hidden" value="${id}"/>
        <input name="schemeId" id="eavSchemaId" type="hidden" value="${schemeId}"/>
        <div id="manage_eavEntity_form_container"></div>
    </form>
    <script id="manage_eavEntity_form_template" type="text/html">
        <table class="tableForm" width="100%">
            <tr>
                <th width="25%">方案：</th>
                <td><span id="schemaName"><%=entity.title%></span></td>
            </tr>
            <%
            for(var key in entity.attributes){
            var e = entity.attributes[key];
            console.info(e);
            %>
            <tr>
                <th width="30%"><%=e.displayName%>：</th>
                <td>
                    <% if(e.attributeType.startsWith("NUMBER_INTEGER")){ %>
                    <input type="text" name="<%=e.name%>" size="20"  style="min-width:200px;line-height: 30px; height: 30px;<%=e.cssStyle%>"
                           class="easyui-numberbox <%=e.cssClass%>" <%if(e.nullable=="no"){%>required="true"<%}%> data-options="validType:['length[<%=e.minimum%>,<%=e.maximum%>]']"/>
                    <%}else if(e.attributeType == "NUMBER_MONEY"){%>
                    <input type="text" name="<%=e.name%>" size="20"  style="min-width:200px;line-height: 30px; height: 30px;<%=e.cssStyle%>"
                           class="easyui-numberbox <%=e.cssClass%>" <%if(e.nullable=="no"){%>required="true"<%}%> data-options="precision:2,validType:['length[<%=e.minimum%>,<%=e.maximum%>]']"/>
                    <%}else if(e.attributeType == "DATE"){%>
                    <input type="text" <%if(e.nullable=="no"){%>required="true"<%}%> name="<%=e.name%>" onFocus="WdatePicker({readOnly:true,dateFmt:$.acooly.eav.formatDate('<%=e.showFormat%>')})"
                    class="easyui-validatebox <%=e.cssClass%>" style="min-width:200px; <%=e.cssStyle%>">
                    <%}else if(e.attributeType == "ENUM" && e.options && e.options.length > 0){%>
                    <select name="<%=e.name%>" <%if(e.nullable=="no"){%>required="true"<%}%> editable="false" panelHeight="auto" class="easyui-combobox <%=cssClass%>" style="min-width:200px;<%=cssStyle%>">
                    <% for(var j=0;j<e.options.length;j++){ var o=e.options[j]; %>
                    <option value="<%=o.code%>"><%=o.name%></option>
                    <% } %>
                    </select>
                    <%}else{%>
                    <input type="text" name="<%=e.name%>" size="20"  style="min-width:200px;line-height: 30px; height: 30px;<%=e.cssStyle%>" class="easyui-validatebox <%=e.cssClass%>"
                    <%if(e.nullable=="no"){%>required="true"<%}%> data-options="validType:['length[<%=e.minimum%>,<%=e.maximum%>]']"/>
                    <%}%>
                </td>
            </tr>
            <% } %>
        </table>
    </script>
    <script>
        $(function(){
            $.acooly.eav.loadScheme({
                schemeId: "${schemeId}",
                template: "manage_eavEntity_form_template",
                container: "manage_eavEntity_form_container",
                showType: $.acooly.eav.showType.CREATE
            });
        });
    </script>
</div>
