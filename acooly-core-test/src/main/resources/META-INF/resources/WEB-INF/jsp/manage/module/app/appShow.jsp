<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>
<div style="padding: 5px;font-family:微软雅黑;">
    <table class="tableForm" width="100%">
        <tr>
            <th>id:</th>
            <td>${app.id}</td>
        </tr>
        <tr>
            <th width="25%">display_name:</th>
            <td>${app.displayName}</td>
        </tr>
        <tr>
            <th>name:</th>
            <td>${app.name}</td>
        </tr>
        <tr>
            <th>parent_app_id:</th>
            <td>${app.parentAppId}</td>
        </tr>
        <tr>
            <th>type:</th>
            <td>${app.type}</td>
        </tr>
        <tr>
            <th>user_id:</th>
            <td>${app.userId}</td>
        </tr>
        <tr>
            <th>创建时间:</th>
            <td><fmt:formatDate value="${app.rawAddTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
        <tr>
            <th>修改时间:</th>
            <td><fmt:formatDate value="${app.rawUpdateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
        <tr>
            <th>parent_id:</th>
            <td>${app.parentId}</td>
        </tr>
    </table>
</div>
