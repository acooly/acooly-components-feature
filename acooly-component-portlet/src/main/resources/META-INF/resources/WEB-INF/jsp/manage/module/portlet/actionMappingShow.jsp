<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>
<div style="padding: 5px;font-family:微软雅黑;">
    <table class="tableForm" width="100%">
        <tr>
            <th>主键:</th>
            <td>${actionMapping.id}</td>
        </tr>
        <tr>
            <th width="25%">连接:</th>
            <td>${actionMapping.url}</td>
        </tr>
        <tr>
            <th>名称:</th>
            <td>${actionMapping.title}</td>
        </tr>
        <tr>
            <th>create_time:</th>
            <td><fmt:formatDate value="${actionMapping.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
        <tr>
            <th>update_time:</th>
            <td><fmt:formatDate value="${actionMapping.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
        <tr>
            <th>备注:</th>
            <td>${actionMapping.comments}</td>
        </tr>
    </table>
</div>
