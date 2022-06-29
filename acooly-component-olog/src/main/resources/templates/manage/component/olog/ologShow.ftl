<div align="center">
    <table class="tableForm" width="100%">
        <tr>
            <th width="20%">ID：</th>
            <td>${ologEntity.id}</td>
        </tr>
        <tr>
            <th>操作员：</th>
            <td>${ologEntity.operateUser}</td>
        </tr>
        <tr>
            <th>操作时间：</th>
            <td>${ologEntity.operateTime}</td>
        </tr>
        <tr>
            <th>系统：</th>
            <td>${ologEntity.system}</td>
        </tr>
        <tr>
            <th>模块：</th>
            <td><#if ologEntity.module == ologEntity.moduleName>${ologEntity.module}<#else>${ologEntity.moduleName}[${ologEntity.module}]</#if></td>
        </tr>
        <tr>
            <th>功能：</th>
            <td>${ologEntity.actionName}[${ologEntity.action}]</td>
        </tr>
        <tr>
            <th>客户端：</th>
            <td>${ologEntity.clientInformations}</td>
        </tr>
        <tr>
            <th>执行时长：</th>
            <td>${ologEntity.executeMilliseconds}ms</td>
        </tr>
        <tr>
            <th>请求参数：</th>
            <td>
                <div style="word-wrap:break-word;max-width:550px" id="manage_ologEntity_show_parameter_container">${ologEntity.requestParameters}</div>
            </td>
        </tr>
        <#if (ologEntity.operateMessage)?? && ologEntity.operateMessage != "">
            <tr>
                <th>结果消息：</th>
                <td>
                    <div id="manage_ologEntity_show_result_container" style="word-wrap:break-word;max-width:550px">${ologEntity.operateMessage}</div>
                </td>
            </tr>
        </#if>
        <tr>
            <th>执行结果：</th>
            <td>${allOperateResults?api.get(ologEntity.operateResult)}</td>
        </tr>
    </table>
</div>
<script>
    $(function () {
        $('#manage_ologEntity_show_parameter_container').html($.acooly.format.json($('#manage_ologEntity_show_parameter_container').html()));
        $('#manage_ologEntity_show_result_container').html($.acooly.format.json($('#manage_ologEntity_show_result_container').html()));
    });
</script>
