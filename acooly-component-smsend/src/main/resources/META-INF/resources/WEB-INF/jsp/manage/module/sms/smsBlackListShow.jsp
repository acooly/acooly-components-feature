<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>
<div style="padding: 5px;font-family:微软雅黑;">
    <table class="tableForm" width="100%">
        <tr>
            <th>ID:</th>
            <td>${smsBlackList.id}</td>
        </tr>
        <tr>
            <th width="25%">手机号:</th>
            <td>${smsBlackList.mobile}</td>
        </tr>
        <tr>
            <th>状态:</th>
            <td>${smsBlackList.status.message}</td>
        </tr>
        <tr>
            <th>描述:</th>
            <td>${smsBlackList.description}</td>
        </tr>
        <tr>
            <th>创建时间:</th>
            <td><fmt:formatDate value="${smsBlackList.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
        <tr>
            <th>修改时间:</th>
            <td><fmt:formatDate value="${smsBlackList.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
    </table>
</div>
