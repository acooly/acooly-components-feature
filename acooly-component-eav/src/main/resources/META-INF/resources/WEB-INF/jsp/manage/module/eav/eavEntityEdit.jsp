<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>
<script src="asset/eav/eav.js"></script>


<div>
    <script type="text/javascript">
        $.ajax({
            url: "/eav/getEavSchema?id=" + window.data.eavSchemaId,
            async: false,
            success: function (result) {
                console.info(result);
                $("#schemaName").text(result.data.name);
                $("#eavSchemaId").val(window.data.eavSchemaId);
                var attrs = result.data.attributes;
                for (var key in attrs) {
                    var attr = attrs[key];
                    var template = '<tr><th>{{attr.displayName}}：</th><td>{{{input}}}</td></tr>';
                    attr.forQueryCondition=false;
                    $("#eavCreateTable tr:eq(0)").after(appendTable(template,attr));
                }
            }
        });

        <c:if test="${action=='edit'}">
        var entity =${entityJson};
        $("#manage_eavEntity_editform").unserialize(entity);
        </c:if>

    </script>
    <form id="manage_eavEntity_editform"
          action="${pageContext.request.contextPath}/manage/module/eav/eavEntity/${action=='create'?'saveJson':'updateJson'}.html"
          method="post">
        <input name="eavEntityId" type="hidden" value="${eavEntityId}"/>
        <input name="schemaId" id="eavSchemaId" type="hidden"/>


        <table class="tableForm" id="eavCreateTable" width="100%">
            <tr>
                <th width="25%">方案：</th>
                <td><span id="schemaName"></span></td>
            </tr>
        </table>

        <table class="tableForm" width="100%">
            <tr>
                <th width="25%">方案：</th>
                <td><span id="schemaName"></span></td>
            </tr>

            <%
                for(var i=0;i<attributes.length;i++){
                    var e = attributes[i];
            %>
            <tr>
                <th width="25%"><%e.displayName%>：</th>
                <td>
                 <% if(e.attributeType.startsWith("NUMBER")){ %>
                    <input type="text" name="<%=e.name%>" size="20"  style="width: 200px;line-height: 30px; height: 30px;<%=e.cssStyle%>"
                           class="easyui-numberbox <%=e.cssClass%>" <%if(e.nullable=="no"){%>required="true"<%}%> data-options="validType:['length[<%=e.minimum%>,<%=e.maximum%>]']"/>
                 <%}else if(e.attributeType == "DATE"){%>
                    <input type="text" name="search_GTE_<%=e.name%>" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
                           class="easyui-validatebox <%=e.cssClass%>" style="<%=e.cssStyle%>">
                 <%}else if(e.attributeType == "ENUM"){%>

                 <%}else{%>

                 <%}%>
                </td>
            </tr>
            <%
                }
            %>

        </table>

    </form>
</div>
