<div style="padding: 0 5px;">
    <form id="manage_user_changePassword_form" class="form-horizontal" action="/manage/system/user/changePassword.html" method="post">
        <input type="hidden" name="id" value="${user.id}"/>
        <input type="hidden" name="_csrf" value="${Request['org.springframework.security.web.csrf.CsrfToken'].token}"/>
        <div class="card-body">
            <div class="form-group row">
                <label class="col-sm-3 col-form-label">登录名</label>
                <div class="col-sm-9 col-form-content">${user}</div>
            </div>
            <div class="form-group row">
                <label for="system_newPassword" class="col-sm-3 col-form-label">设置新密码</label>
                <div class="col-sm-9">
                    <input type="password" name="newPassword" id="manage_user_newPassword" placeholder="设置新密码..." class="form-control easyui-validatebox"
                           validType="commonRegExp['${PASSWORD_REGEX}','${PASSWORD_ERROR}']" data-options="required:'true'"/>
                </div>
            </div>
            <div class="form-group row">
                <label for="system_confirmNewPassword" class="col-sm-3 col-form-label">确认新密码</label>
                <div class="col-sm-9">
                    <input name="confirmNewPassword" id="manage_user_confirmNewPassword" type="password" placeholder="请再次输入新密码..."
                           validType="equals['#manage_user_newPassword']" class="form-control easyui-validatebox"
                           data-options="required:'true',missingMessage:'请再次填写新登录密码'"/>
                </div>
            </div>
            <div class="form-group row">
                <label for="system_confirmNewPassword" class="col-sm-3 col-form-label">当前用户密码</label>
                <div class="col-sm-9">
                    <input name="adminPassword" type="password"
                           validType="commonRegExp['${PASSWORD_REGEX}','${PASSWORD_ERROR}']" class="form-control easyui-validatebox"
                           data-options="required:'true'"/>
                </div>
            </div>
        </div>
    </form>
</div>
