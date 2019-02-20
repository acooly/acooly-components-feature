<div align="center" style="padding-top: 5px;">
    <form id="user_changePassword_form" action="${rc.contextPath}/manage/system/changePassword.html" method="post">
        <input type="hidden" name="_csrf" value="${Request['org.springframework.security.web.csrf.CsrfToken'].token}"/>
        <table class="tableForm">
            <tr>
                <th style="width: 100px;">用户</th>
                <td>${user}</td>
            </tr>
            <tr>
                <th>旧密码</th>
                <td><input name="password" type="password" class="easyui-validatebox"
                           data-options="required:'true',missingMessage:'请填写登录密码'"/></td>
            </tr>
            <tr>
                <th>新密码</th>
                <td><input name="newPassword" id="system_newPassword" type="password"
                           validType="commonRegExp['${PASSWORD_REGEX}','${PASSWORD_ERROR}']" class="easyui-validatebox"
                           data-options="required:'true'"/></td>
            </tr>
            <tr>
                <th>确认新密码</th>
                <td><input name="confirmNewPassword" id="system_confirmNewPassword" type="password"
                           validType="equals['#system_newPassword']" class="easyui-validatebox"
                           data-options="required:'true',missingMessage:'请再次填写新登录密码'"/></td>
            </tr>
        </table>
    </form>
</div>
