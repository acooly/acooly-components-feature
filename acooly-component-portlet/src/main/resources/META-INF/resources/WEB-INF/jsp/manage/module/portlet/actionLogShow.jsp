<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>
<div style="padding: 5px;font-family:微软雅黑;">
    <table class="tableForm" width="100%">
        <tr>
            <th>ID:</th>
            <td>${actionLog.id}</td>
        </tr>
        <tr>
            <th width="25%">操作:</th>
            <td>${actionLog.actionKey}</td>
        </tr>
        <tr>
            <th>操作名称:</th>
            <td>${actionLog.actionName}</td>
        </tr>
        <tr>
            <th>URL连接:</th>
            <td>${actionLog.actionUrl}</td>
        </tr>
        <tr>
            <th>用户名:</th>
            <td>${actionLog.userName}</td>
        </tr>
        <tr>
            <th>渠道:</th>
            <td>${actionLog.channel.message}</td>
        </tr>
        <tr>
            <th>渠道信息:</th>
            <td>${actionLog.channelInfo}</td>
        </tr>
        <tr>
            <th>渠道版本:</th>
            <td>${actionLog.channelVersion}</td>
        </tr>
        <tr>
            <th>访问IP:</th>
            <td>${actionLog.userIp}</td>
        </tr>
        <tr>
            <th>创建时间:</th>
            <td><fmt:formatDate value="${actionLog.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
        <tr>
            <th>更新时间:</th>
            <td><fmt:formatDate value="${actionLog.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
        <tr>
            <th>备注:</th>
            <td>${actionLog.comments}</td>
        </tr>
    </table>
</div>
