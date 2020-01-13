<div align="center" style="padding-top: 5px;">
    <form id="user_changePassword_form" action="${rc.contextPath}/manage/system/changePassword.html" method="post">
        <input type="hidden" name="_csrf" value="${Request['org.springframework.security.web.csrf.CsrfToken'].token}"/>
        <table class="tableForm">
            <tr>
                <th style="width: 35%;">用户：</th>
                <td>${user}</td>
            </tr>
            <tr>
                <th>旧密码：</th>
                <td><input name="password" type="password" class="easyui-validatebox" style="width: 220px;"
                           data-options="required:'true',missingMessage:'请填写登录密码'"/></td>
            </tr>
            <tr>
                <th>新密码：</th>
                <td><input name="newPassword" id="system_newPassword" type="password" style="width: 220px;"
                           validType="commonRegExp['${PASSWORD_REGEX}','${PASSWORD_ERROR}']" class="easyui-validatebox"
                           data-options="required:'true'"/></td>
            </tr>
            <tr>
                <th>确认新密码：</th>
                <td><input name="confirmNewPassword" id="system_confirmNewPassword" type="password" style="width: 220px;"
                           validType="equals['#system_newPassword']" class="easyui-validatebox"
                           data-options="required:'true',missingMessage:'请再次填写新登录密码'"/></td>
            </tr>
            <tr>
                <th>验证码：</th>
                <td><input name="passwordCaptcha" id="user_changePassword_passwordCaptcha" style="width:80px;" class="easyui-validatebox" data-options="required:'true',validType:['length[4,6]']"/>
                    <span style="vertical-align: super;">
                        <img id="user_changePassword_jcaptchaImage" onclick="user_changePassword_refreshCaptcha()" src="/jcaptcha.jpg"  style="cursor: pointer;" title="看不清楚？点击更换">
                    </span>
                    <a href="javascript:;" onclick="user_changePassword_refreshCaptcha()" style="font-size: 12px;">换一下</a>
                </td>
            </tr>
            <script>
                /**
                 * 刷新验证码
                 */
                function user_changePassword_refreshCaptcha() {
                    $('#user_changePassword_jcaptchaImage').show();
                    $('#user_changePassword_jcaptchaImage').attr("src", "/jcaptcha.jpg?" + new Date());
                    $('#user_changePassword_passwordCaptcha').val('');
                }
                user_changePassword_refreshCaptcha();
            </script>
        </table>
    </form>
</div>
