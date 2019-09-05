<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;min-height: 50px;" align="left">
        <div id="manage_eavEntity_searchform_container${schemeId}"></div>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false" id="manage_eavEntity_datagrid_container${schemeId}"></div>

    <#--<script type="text/html" id="manage_eavEntity_searchform_template">-->
    <#--<form id="<%=elementIds.searchform%>" onsubmit="return false">-->
        <#--<table class="tableForm" width="100%">-->
            <#--<tr>-->
                <#--<td align="left">-->
                    <#--<div>-->
                        <#--<% if(schemes != null){ %>-->
                        <#--方案:<select name="search_EQ_schemeId" id="search_EQ_schemeId">-->
                            <#--<%for(var i=0;i<schemes.length;i++){ var e = schemes[i]; %>-->
                            <#--<option value="<%=e.id%>"<%if(e.id==entity.id){%> selected<%}%>><%=e.title%></option>-->
                            <#--<%}%>-->
                        <#--<select>-->
                        <#--<%}else{%>-->
                        <#--<input type="hidden" name="search_EQ_schemeId" value="<%=entity.id%>">-->
                        <#--<%}%>-->

                        <#--<%-->
                        <#--for (var key in entity.attributes) {-->
                            <#--var attr = entity.attributes[key];-->
                            <#--if(!$.acooly.eav.hasPermission(attr.showType,$.acooly.eav.showType.SEARCH)){ continue; }-->
                        <#--%>-->

                        <#--<%-->
                            <#--if (attr.attributeType.startsWith('NUMBER')) {-->
                        <#--%>-->
                            <#--<%=attr.displayName%>: <input type="text" name="search_EQ_<%=name%>" class="easyui-numberbox <%=attr.cssClass%>" style="<%=attr.cssStyle%>">-->
                        <#--<%-->
                            <#--} else if (attr.attributeType == 'DATE') {-->
                        <#--%>-->
                            <#--<%=attr.displayName%>: <input type="text" name="search_GTE_<%=attr.name%>" onFocus="WdatePicker({readOnly:true,dateFmt:'<%=$.acooly.eav.formatDate(attr.showFormat)%>'})" class="easyui-validatebox <%=attr.cssClass%>" style="<%=attr.cssStyle%>">-->
                            <#--至 <input type="text" name="search_LTE_<%=attr.name%>" onFocus="WdatePicker({readOnly:true,dateFmt:'<%=$.acooly.eav.formatDate(attr.showFormat)%>'})" class="easyui-validatebox <%=attr.cssClass%>" style="<%=attr.cssStyle%>">-->
                        <#--<%-->
                            <#--} else if (attr.attributeType == 'ENUM') {-->
                        <#--%>-->
                        <#--<%=attr.displayName%>: <select name="search_EQ_<%=attr.name%>" editable="false" panelHeight="auto" class="easyui-combobox <%=attr.cssClass%>" style="<%=attr.cssStyle%>">-->
                            <#--<option value="">所有</option>-->
                            <#--<% for(var i=0;i<attr.options.length;i++){ var e=attr.options[i]; %>-->
                            <#--<option value="<%=e.code%>"><%=e.name%></option>-->
                            <#--<% } %>-->
                            <#--</select>-->
                        <#--<%-->
                            <#--} else {-->
                        <#--%>-->
                            <#--<%=attr.displayName%>: <input type="text" name="search_LIKE_<%=attr.name%>" class="easyui-validatebox <%=attr.cssClass%>" style="<%=attr.cssStyle%>">-->
                        <#--<%-->
                            <#--}-->
                        <#--}-->
                        <#--%>-->
                        <#--<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false" onclick="$.acooly.framework.search('<%=elementIds.searchform%>','<%=elementIds.datagrid%>');"><i class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>-->
                    <#--</div>-->
                <#--</td>-->
            <#--</tr>-->
        <#--</table>-->
    <#--</form>-->
    <#--</script>-->

    <script src="/manage/asset/eav/acooly.eav.entity.js" />
    <script>
        $(function () {
            $.acooly.eav.loadAllSchemes("manage_eavEntity_searchform_container${schemeId}",
                    "manage_eavEntity_datagrid_container${schemeId}","${schemeId}");
        });
    </script>
</div>
