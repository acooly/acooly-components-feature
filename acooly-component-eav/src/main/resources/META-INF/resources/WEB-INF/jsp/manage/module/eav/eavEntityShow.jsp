<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>

<script type="text/javascript">
    for (idx in window.data.eavSchemas) {
        eavSchema = window.data.eavSchemas[idx];
        if (eavSchema.id == window.data.eavSchemaId) {
            $("#schmeaName").text(eavSchema.text);
        }
    }
    $.ajax({
        url: "/eav/getEavSchema?id=" + window.data.eavSchemaId,
        async: false,
        success: function (result) {
            var attrs = result.data.attributes;
            var trs = "";
            var entity = ${entityJson};
            console.info(entity)
            for (key in attrs) {
               var attr = attrs[key]
                trs += "<tr><th>" + attr.displayName + ":</th><td>" + entity[attr.name] + "</td></tr>";
            }

            $("#eavShowTable tr:eq(1)").after(trs);
        }
    });
</script>
<div style="padding: 5px;font-family:微软雅黑;">
    <table class="tableForm" id="eavShowTable" width="100%">
        <tr>
            <th>id:</th>
            <td>${eavEntity.id}</td>
        </tr>
        <tr>
            <th width="25%">方案:</th>
            <td><span id="schmeaName"></span></td>
        </tr>
        <tr>
            <th>创建时间:</th>
            <td><fmt:formatDate value="${eavEntity.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
        <tr>
            <th>修改时间:</th>
            <td><fmt:formatDate value="${eavEntity.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
    </table>
</div>
