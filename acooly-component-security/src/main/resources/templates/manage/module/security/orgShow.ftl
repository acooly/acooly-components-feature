<div class="card-body">
    <dl class="row">
        <dt class="col-sm-3">ID:</dt>
        <dd class="col-sm-9">${org.id}</dd>
        <dt class="col-sm-3">上级机构ID:</dt>
        <dd class="col-sm-9">${org.parentId}</dd>
        <dt class="col-sm-3">机构名称:</dt>
        <dd class="col-sm-9">${org.contacts}</dd>
        <dt class="col-sm-3">联系人:</dt>
        <dd class="col-sm-9">${org.id}</dd>
        <dt class="col-sm-3">地区:</dt>
        <dd class="col-sm-9">${org.province} - ${org.city} - ${org.county}</dd>
        <dt class="col-sm-3">地址:</dt>
        <dd class="col-sm-9">${org.address}</dd>
        <dt class="col-sm-3">手机号码:</dt>
        <dd class="col-sm-9">${org.mobileNo}</dd>
        <dt class="col-sm-3">邮件:</dt>
        <dd class="col-sm-9">${org.email}</dd>
        <dt class="col-sm-3">固定电话:</dt>
        <dd class="col-sm-9">${org.telephone}</dd>
        <dt class="col-sm-3">状态:</dt>
        <dd class="col-sm-9">${org.status.message()}</dd>
        <dt class="col-sm-3">创建时间:</dt>
        <dd class="col-sm-9">${(org.createTime?string('yyyy-MM-dd'))!}</dd>
        <dt class="col-sm-3">更新时间:</dt>
        <dd class="col-sm-9">${(org.updateTime?string('yyyy-MM-dd'))!}</dd>
        <dt class="col-sm-3">备注:</dt>
        <dd class="col-sm-9">${org.memo}</dd>
    </dl>
</div>