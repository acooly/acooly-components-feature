<div align="center" style="padding-top: 5px;">
    <form id="manage_user_changePassword_form" action="${rc.contextPath}/manage/system/user/changePassword.html" method="post">
        <table class="tableForm">
            <input type="hidden" name="id" value="${user.id}"/>
            <input type="hidden" name="_csrf" value="${Request['org.springframework.security.web.csrf.CsrfToken'].token}"/>
            <tr>
                <th style="width: 35%;">登录名：</th>
                <td>${user}</td>
            </tr>

            <tr>
                <th>设置新密码：</th>
                <td><input name="newPassword" id="manage_user_newPassword" type="password" style="width: 220px;"
                           validType="commonRegExp['${PASSWORD_REGEX}','${PASSWORD_ERROR}']" class="easyui-validatebox text"
                           data-options="required:'true'"/></td>
            </tr>
            <tr>
                <th>确认新密码：</th>
                <td><input name="confirmNewPassword" id="manage_user_confirmNewPassword" type="password" style="width: 220px;"
                           validType="equals['#manage_user_newPassword']" class="easyui-validatebox text"
                           data-options="required:'true',missingMessage:'请再次填写新登录密码'"/></td>
            </tr>

            <tr>
                <th>当前操作员密码：</th>
                <td><input name="adminPassword" type="password" style="width: 220px;"
                           validType="commonRegExp['${PASSWORD_REGEX}','${PASSWORD_ERROR}']" class="easyui-validatebox text"
                           data-options="required:'true'"/></td>
            </tr>
        </table>
    </form>
</div>
