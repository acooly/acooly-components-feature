<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div id="manage_eavEntity_form_container<#if single == "true">${schemeId}</#if>">
    <script id="manage_eavEntity_form_template" type="text/html">
        <form id="<%=elementIds.editform%>" action="/manage/module/eav/eavEntity/<%if(options.entityId != ''){%>save<%}else{%>update<%}%>Json.html" method="post">
            <input name="id" type="hidden" value="<%=options.entityId%>"/>
            <input name="schemeId" id="schemeId" type="hidden" value="<%=options.schemeId%>"/>
            <table class="tableForm" width="100%">
                <tr>
                    <th width="25%">方案：</th>
                    <td><%=entity.title%></td>
                </tr>
                <%
                var prevTag = null;
                for(var key in entity.attributes){
                var e = entity.attributes[key];
                var showType = (options.entityId != null && options.entityId != ''?$.acooly.eav.showType.UPDATE:$.acooly.eav.showType.CREATE);
                if(!$.acooly.eav.hasPermission(e.showType,showType)){ continue; }
                %>
                <%if(prevTag != e.tag && e.tag != ''){%>
                <tr><td colspan="2"><div style="border-bottom: 1px solid #ddd;padding: 5px;margin-bottom: 10px;"><%=e.tag%></div></td></tr>
                <%}%>
                <tr>
                    <th><%=e.displayName%>：</th>
                    <td>
                        <% if(e.attributeType.startsWith("NUMBER_INTEGER")){ %>
                        <input type="text" name="<%=e.name%>" size="20"  style="min-width:300px;line-height: 30px; height: 30px;<%=e.cssStyle%>"
                               class="easyui-numberbox <%=e.cssClass%>" <%if(e.nullable=="no"){%>required="true"<%}%>
                                <%if(e.minimum!=null){%>min="<%=e.minimum%>"<%}%> <%if(e.maximum!=null){%>max="<%=e.maximum%>"<%}%>/>
                        <%}else if(e.attributeType == "NUMBER_MONEY"){%>
                        <input type="text" name="<%=e.name%>" size="20"  style="min-width:300px;line-height: 30px; height: 30px;<%=e.cssStyle%>"
                               class="easyui-numberbox <%=e.cssClass%>" <%if(e.nullable=="no"){%>required="true"<%}%>
                            <%if(e.minimum!=null){%>min="<%=e.minimum/100%>"<%}%> <%if(e.maximum!=null){%>max="<%=e.maximum/100%>"<%}%>
                            data-options="precision:2"/>
                        <%}else if(e.attributeType == "DATE"){%>
                        <input type="text" <%if(e.nullable=="no"){%>required="true"<%}%> name="<%=e.name%>" onFocus="WdatePicker({readOnly:true,dateFmt:$.acooly.eav.formatDate('<%=e.showFormat%>')})"
                        class="easyui-validatebox <%=e.cssClass%>" style="min-width:300px; <%=e.cssStyle%>">
                        <%}else if(e.attributeType == "ENUM" && e.options && e.options.length > 0){%>
                        <select name="<%=e.name%>" <%if(e.nullable=="no"){%>required="true"<%}%> editable="false" panelHeight="auto" class="easyui-combobox <%=cssClass%>" style="min-width:300px;<%=cssStyle%>">
                        <% for(var j=0;j<e.options.length;j++){ var o=e.options[j]; %>
                        <option value="<%=o.code%>"><%=o.name%></option>
                        <% } %>
                        </select>
                        <%}else{%>
                            <%if(e.maxLength!=null&&e.maxLength>128){%><textarea rows="3" <%}else{%><input type="text" size="20" <%}%>
                               name="<%=e.name%>" style="min-width:300px;<%=e.cssStyle%>"
                               class="easyui-validatebox <%=e.cssClass%>" <%if(e.nullable=="no"){%>required="true"<%}%>
                                data-options="validType:['length[<%=e.minLength%>,<%=e.maxLength%>]'<%if(e.regex != null && e.regex != ''){%>,'regExp[\'<%=e.regex%>\', \'<%=e .regexMessage%>\']'<%}%>]"/>
                            <%if(e.maxLength!=null&&e.maxLength>128){%></textarea><%}%>
                        <%}%>
                    </td>
                </tr>
                <%
                    prevTag = e.tag;
                }
                %>
            </table>
        </form>
    </script>
</div>
<script>
    $(function(){
        var entity = <#if entityJson??>${entityJson}<#else>null</#if>;
        $.acooly.eav.initEdit({
            schemeId: "${schemeId}",
            entity: entity,                     // 编辑时不为空
            entityId: '${entityId}',            // 编辑时不为空
            single: '${single}',
            template: 'manage_eavEntity_form_template',
            container: 'manage_eavEntity_form_container<#if single == "true">${schemeId}</#if>'
        });
    });
</script>