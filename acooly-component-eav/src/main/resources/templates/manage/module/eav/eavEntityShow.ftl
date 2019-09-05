<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<script type="text/javascript">
    <#--for (idx in window.data.eavSchemas) {-->
        <#--eavSchema = window.data.eavSchemas[idx];-->
        <#--if (eavSchema.id == window.data.eavSchemaId) {-->
            <#--$("#schmeaName").text(eavSchema.text);-->
        <#--}-->
    <#--}-->
    <#--$.ajax({-->
        <#--url: "/eav/getEavSchema?id=" + window.data.eavSchemaId,-->
        <#--async: false,-->
        <#--success: function (result) {-->
            <#--var attrs = result.data.attributes;-->
            <#--var trs = "";-->
            <#--var entity = ${entityJson};-->
            <#--console.info(entity)-->
            <#--for (key in attrs) {-->
               <#--var attr = attrs[key]-->
                <#--trs += "<tr><th>" + attr.displayName + ":</th><td>" + entity[attr.name] + "</td></tr>";-->
            <#--}-->

            <#--$("#eavShowTable tr:eq(1)").after(trs);-->
        <#--}-->
    <#--});-->
</script>
<div style="padding: 5px;font-family:微软雅黑;">
    <table class="tableForm" id="eavShowTable" width="100%">
        <tr>
            <th width="20%">编码:</th>
            <td>${eavEntityInfo.id}</td>
        </tr>
        <tr>
            <th>方案:</th>
            <td>${eavEntityInfo.schemeName}</td>
        </tr>
        <#assign prevTag=null>
        <#list eavEntityInfo.eavAttributeInfos as e>
            <#if prevTag != e.tag && e.tag != ''>
            <tr><td colspan="2"><div style="border-bottom: 1px solid #ddd;padding: 5px;margin-bottom: 10px;">
                <i class="fa fa-tag" aria-hidden="true"></i> ${e.tag}
            </div></td></tr>
            </#if>
          <tr>
              <th>${e.displayName}</th>
              <td>${e.formatValue}</td>
          </tr>
            <#assign prevTag=e.tag>
        </#list>


        <#--<tr>-->
            <#--<th>创建时间:</th>-->
            <#--<td><fmt:formatDate value="${eavEntity.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>-->
        <#--</tr>-->
        <#--<tr>-->
            <#--<th>修改时间:</th>-->
            <#--<td><fmt:formatDate value="${eavEntity.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>-->
        <#--</tr>-->
    </table>
</div>
