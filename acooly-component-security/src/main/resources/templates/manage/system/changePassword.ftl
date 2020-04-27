<div align="center" style="padding-top: 5px;">
    <form id="user_changePassword_form" class="form-horizontal" action="${rc.contextPath}/manage/system/changePassword.html" method="post">
        <input type="hidden" name="_csrf" value="${Request['org.springframework.security.web.csrf.CsrfToken'].token}"/>
        <div class="card-body">
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">用户</label>
                <div class="col-sm-10 text-left col-form-label">${user}</div>
            </div>
            <div class="form-group row">
                <label for="system_password" class="col-sm-2 col-form-label">旧密码</label>
                <div class="col-sm-10">
                    <input type="password" tabindex="0" name="password" id="system_password" placeholder="当前登录密码..." class="form-control easyui-validatebox" data-options="required:'true'"/>
                </div>
            </div>
            <div class="form-group row">
                <label for="system_newPassword" class="col-sm-2 col-form-label">新密码</label>
                <div class="col-sm-10">
                    <input type="password" name="newPassword" id="system_newPassword" placeholder="新密码..." class="form-control easyui-validatebox"
                           validType="commonRegExp['${PASSWORD_REGEX}','${PASSWORD_ERROR}']" data-options="required:'true'"/>
                </div>
            </div>
            <div class="form-group row">
                <label for="system_confirmNewPassword" class="col-sm-2 col-form-label">新密码</label>
                <div class="col-sm-10">
                    <input name="confirmNewPassword" id="system_confirmNewPassword" type="password" placeholder="请再次输入新密码..."
                           validType="equals['#system_newPassword']" class="form-control easyui-validatebox"
                           data-options="required:'true',missingMessage:'请再次填写新登录密码'"/>
                </div>
            </div>
            <div class="form-group row">
                <label for="system_confirmNewPassword" class="col-sm-2 col-form-label">验证码</label>
                <div class="col-sm-3">
                    <img id="user_changePassword_jcaptchaImage" onclick="$.acooly.framework.changePasswordCaptcha();" src="/jcaptcha.jpg"  style="cursor: pointer;" title="看不清楚？点击更换">
                </div>
                <div class="col-sm-7">
                    <input name="passwordCaptcha" placeholder="请输入显示的验证码..." id="user_changePassword_passwordCaptcha" class="form-control easyui-validatebox" data-options="required:'true',validType:['length[4,6]']"/>
                </div>

            </div>
        </div>

        <script>
            $.acooly.framework.changePasswordCaptcha();
        </script>
    </form>
</div>
