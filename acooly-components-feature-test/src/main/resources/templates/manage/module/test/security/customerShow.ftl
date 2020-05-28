<div class="card-body">
    <dl class="row">
        <dt class="col-sm-3">ID:</dt>
        <dd class="col-sm-9">${customer.id}</dd>

        <dt class="col-sm-3">用户名:</dt>
        <dd class="col-sm-9">${customer.username}</dd>

        <dt class="col-sm-3">年龄:</dt>
        <dd class="col-sm-9">${customer.age}</dd>

        <dt class="col-sm-3">生日:</dt>
        <dd class="col-sm-9">${customer.birthday?string('yyyy-MM-dd HH:mm:ss')}</dd>

        <dt class="col-sm-3">性别:</dt>
        <dd class="col-sm-9">${customer.gender.message()}</dd>

        <dt class="col-sm-3">姓名:</dt>
        <dd class="col-sm-9">${customer.realName}</dd>

        <dt class="col-sm-3">证件类型:</dt>
        <dd class="col-sm-9">${customer.idcardType.message()}</dd>

        <dt class="col-sm-3">身份证号码:</dt>
        <dd class="col-sm-9">${customer.idcardNo}</dd>

        <dt class="col-sm-3">手机号码:</dt>
        <dd class="col-sm-9">${customer.mobileNo}</dd>

        <dt class="col-sm-3">邮件:</dt>
        <dd class="col-sm-9">${customer.mail}</dd>

        <dt class="col-sm-3">照片缩略图:</dt>
        <dd class="col-sm-9">${customer.photoThum}</dd>

        <dt class="col-sm-3">摘要:</dt>
        <dd class="col-sm-9">${customer.subject}</dd>

        <dt class="col-sm-3">客户类型:</dt>
        <dd class="col-sm-9">${customer.customerType.message()}</dd>

        <dt class="col-sm-3">手续费:</dt>
        <dd class="col-sm-9">${customer.fee}</dd>

        <dt class="col-sm-3">测试Text类型:</dt>
        <dd class="col-sm-9">${customer.content}</dd>

        <dt class="col-sm-3">薪水:</dt>
        <dd class="col-sm-9">${customer.salary}</dd>

        <dt class="col-sm-3">状态:</dt>
        <dd class="col-sm-9">${allStatuss?api.get(customer.status)}</dd>

        <dt class="col-sm-3">创建时间:</dt>
        <dd class="col-sm-9">${customer.createTime?string('yyyy-MM-dd HH:mm:ss')}</dd>

        <dt class="col-sm-3">update_time:</dt>
        <dd class="col-sm-9">${customer.updateTime?string('yyyy-MM-dd HH:mm:ss')}</dd>

        <dt class="col-sm-3">备注:</dt>
        <dd class="col-sm-9">${customer.comments}</dd>
    </dl>
</div>

