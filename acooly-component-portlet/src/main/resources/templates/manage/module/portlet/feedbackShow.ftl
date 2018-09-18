<div style="padding: 5px;font-family:微软雅黑;">
    <table class="tableForm" width="100%">
        <tr>
            <th>标题:</th>
            <td>${feedback.title}</td>
        </tr>
        <tr>
            <th width="25%">类型:</th>
            <td>${feedback.type}</td>
        </tr>
        <tr>
            <th>内容:</th>
            <td>${feedback.content}</td>
        </tr>
        <tr>
            <th>用户名:</th>
            <td>${feedback.userName}</td>
        </tr>
        <tr>
            <th>联系电话:</th>
            <td>${feedback.telephone}</td>
        </tr>
        <tr>
            <th>联系地址:</th>
            <td>${feedback.address}</td>
        </tr>
        <tr>
            <th>联系信息:</th>
            <td>${feedback.contactInfo}</td>
        </tr>
        <tr>
            <th>提交时间:</th>
            <td>${feedback.createTime?datetime}</td>
        </tr>
        <tr>
            <th>处理时间:</th>
            <td>${feedback.updateTime?datetime}</td>
        </tr>
        <tr>
            <th width="25%">处理状态:</th>
            <td>${feedback.status}</td>
        </tr>
        <tr>
            <th>处理意见:</th>
            <td>${feedback.comments!}</td>
        </tr>
    </table>
</div>
