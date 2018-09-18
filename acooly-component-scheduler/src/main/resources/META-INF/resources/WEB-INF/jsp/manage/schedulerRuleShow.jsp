<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>
<div style="padding: 5px;font-family:微软雅黑;">
    <table class="tableForm" width="100%">
        <tr>
            <th>id:</th>
            <td>${schedulerRule.id}</td>
        </tr>
        <tr>
            <th>任务名:</th>
            <td>${schedulerRule.memo}</td>
        </tr>
        <tr>
            <th>状态:</th>
            <td>${schedulerRule.status}</td>
        </tr>
        <tr>
            <th>cron表达式:</th>
            <td>${schedulerRule.cronString}</td>
        </tr>
        <tr>
            <th>任务类型:</th>
            <td>${schedulerRule.actionType}</td>
        </tr>
        <tr>
            <th>HTTP地址:</th>
            <td>${schedulerRule.properties}</td>
        </tr>
        <tr>
            <th>类名:</th>
            <td>${schedulerRule.className}</td>
        </tr>
        <tr>
            <th>方法名:</th>
            <td>${schedulerRule.methodName}</td>
        </tr>
        <tr>
            <th>dubbo组名:</th>
            <td>${schedulerRule.DGroup}</td>
        </tr>
        <tr>
            <th>dubbo版本:</th>
            <td>${schedulerRule.DVersion}</td>
        </tr>
        <tr>
            <th>dubbo参数:</th>
            <td>${schedulerRule.DParam}</td>
        </tr>
        <tr>
            <th width="25%">创建时间:</th>
            <td><fmt:formatDate value="${schedulerRule.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
        <tr>
            <th>修改时间:</th>
            <td><fmt:formatDate value="${schedulerRule.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
        <tr>
            <th>创建人:</th>
            <td>${schedulerRule.creater}</td>
        </tr>
        <tr>
            <th>修改者:</th>
            <td>${schedulerRule.modifyer}</td>
        </tr>
        <tr>
            <th>上次执行结果:</th>
            <td>${schedulerRule.exceptionAtLastExecute}</td>
        </tr>
        <tr>
            <th>执行次数:</th>
            <td>${schedulerRule.executeNum}</td>
        </tr>
        <tr>
            <th>上次执行时间:</th>
            <td><fmt:formatDate value="${schedulerRule.lastExecuteTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
        <tr>
            <th>失败重试次数:</th>
            <td>${schedulerRule.retryTimeOnException}</td>
        </tr>
        <tr>
            <th>开始时间:</th>
            <td><fmt:formatDate value="${schedulerRule.validityStart}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
        <tr>
            <th>结束时间:</th>
            <td><fmt:formatDate value="${schedulerRule.validityEnd}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
    </table>
</div>
