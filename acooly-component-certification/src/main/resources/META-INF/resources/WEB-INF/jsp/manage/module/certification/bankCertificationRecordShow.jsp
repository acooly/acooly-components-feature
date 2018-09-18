<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>
<div style="padding: 5px;font-family:微软雅黑;">
    <table class="tableForm" width="100%">
        <tr>
            <th>ID:</th>
            <td>${bankCertificationRecord.id}</td>
        </tr>
        <tr>
            <th width="25%">持卡人姓名:</th>
            <td>${bankCertificationRecord.realName}</td>
        </tr>
        <tr>
            <th>银行卡帐号:</th>
            <td>${bankCertificationRecord.cardNo}</td>
        </tr>
        <tr>
            <th>开卡使用的证件号码:</th>
            <td>${bankCertificationRecord.certId}</td>
        </tr>
        <tr>
            <th>绑定手机号:</th>
            <td>${bankCertificationRecord.phoneNum}</td>
        </tr>
        <tr>
            <th>开卡使用的证件类型:</th>
            <td>${bankCertificationRecord.certType.message}</td>
        </tr>
        <tr>
            <th>银行卡归属地:</th>
            <td>${bankCertificationRecord.belongArea}</td>
        </tr>
        <tr>
            <th>银行客服:</th>
            <td>${bankCertificationRecord.bankTel}</td>
        </tr>
        <tr>
            <th>银行卡产品名称:</th>
            <td>${bankCertificationRecord.brand}</td>
        </tr>
        <tr>
            <th>银行名称:</th>
            <td>${bankCertificationRecord.bankName}</td>
        </tr>
        <tr>
            <th>银行卡种:</th>
            <td>${bankCertificationRecord.cardType}</td>
        </tr>
        <tr>
            <th>银行官网:</th>
            <td>${bankCertificationRecord.bankUrl}</td>
        </tr>
        <tr>
            <th>状态:</th>
            <td>${allStatuss[bankCertificationRecord.status]}</td>
        </tr>
        <tr>
            <th>创建时间:</th>
            <td><fmt:formatDate value="${bankCertificationRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
        <tr>
            <th>修改时间:</th>
            <td><fmt:formatDate value="${bankCertificationRecord.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
    </table>
</div>
