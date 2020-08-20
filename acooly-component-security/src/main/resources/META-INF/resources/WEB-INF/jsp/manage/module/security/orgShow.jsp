<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>
<div style="padding: 5px;font-family:微软雅黑;">
    <table class="tableForm" width="100%">
        <tr>
            <th>id:</th>
            <td>${org.id}</td>
        </tr>
        <tr>
            <th width="25%">上级机构id:</th>
            <td>${org.parentId}</td>
        </tr>
        <tr>
            <th>机构名称:</th>
            <td>${org.name}</td>
        </tr>
        <tr>
            <th>状态:</th>
            <td>${org.status.message()}</td>
        </tr>
        <tr>
            <th>省:</th>
            <td>${org.province}</td>
        </tr>
        <tr>
            <th>市:</th>
            <td>${org.city}</td>
        </tr>
        <tr>
            <th>县:</th>
            <td>${org.county}</td>
        </tr>
        <tr>
            <th>手机号:</th>
            <td>${org.mobileNo}</td>
        </tr>
        <tr>
            <th>地址:</th>
            <td>${org.address}</td>
        </tr>
        <tr>
            <th>联系人:</th>
            <td>${org.contacts}</td>
        </tr>
        <tr>
            <th>固定电话:</th>
            <td>${org.telephone}</td>
        </tr>
        <tr>
            <th>创建时间:</th>
            <td><fmt:formatDate value="${org.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
        <tr>
            <th>修改时间:</th>
            <td><fmt:formatDate value="${org.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
        <tr>
            <th>备注:</th>
            <td>${org.memo}</td>
        </tr>
    </table>
</div>
