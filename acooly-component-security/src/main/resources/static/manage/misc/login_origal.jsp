<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${sessionScope.securityConfig.title }</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta name="X-CSRF-TOKEN" content="${requestScope["org.springframework.security.web.csrf.CsrfToken"].token}"/>
    <link id="easyuiTheme" rel="stylesheet"
          href="/manage/assert/plugin/jquery-easyui/themes/<c:out value="${cookie.easyuiThemeName.value}" default="default"/>/easyui.css"
          type="text/css"/>
    <link rel="stylesheet" href="/manage/assert/plugin/jquery-easyui/themes/icon.css" type="text/css"/>
    <script type="text/javascript" src="//cdn.bootcss.com/jquery/1.9.1/jquery.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/plugin/jquery-easyui/jquery.easyui.min.js" charset="utf-8"></script>
    <link rel="stylesheet" type="text/css" href="/manage/assert/style/basic.css">
    <link rel="stylesheet" type="text/css" href="/manage/assert/style/icon.css">

    <script type="text/javascript">
        function refreshCaptcha() {
            $('#captchaPanel').show();
            $('#jcaptchaImage').attr("src", "/jcaptcha.jpg?" + new Date());
            $('#captcha').val('');
        }

        $(function () {
            var formParam = {
                url: '${pageContext.request.contextPath}/manage/login.html',
                method: 'POST',
                success: function (result) {
                    var r = $.parseJSON(result);
                    if (r.success) {
                        $('#user_login_loginDialog').dialog('close');
                        window.location.href = "${pageContext.request.contextPath}/manage/index.jsp";
                    } else {
                        refreshCaptcha();
                        $('#message').html(r.message);
                    }
                }
            };
            $('#loginForm').form(formParam);
            $('#user_login_loginDialog').show().dialog({
                modal: true,
                noheader: true,
                style: {
                    borderWidth: 1
                },
                closable: false,
                buttons: [{
                    text: '登  录',
                    iconCls: 'icon-login',
                    handler: function () {
                        $("#loginForm").submit();
                    }
                }]
            });

            $('#captcha').keydown(function (event) {
                if (event.keyCode == 13) {
                    $("#loginForm").submit();
                }
            });
            $('#password').keydown(function (event) {
                if (event.keyCode == 13) {
                    $("#loginForm").submit();
                }
            });

        });
    </script>
</head>

<body>
<div id="user_login_loginDialog" style="display: none; width: 400px; overflow: hidden;">
    <div id="user_login_loginTab">
        <div title="系统登录" style="overflow: hidden;">
            <div style="background-color: #E0ECFF;">
                <c:if test="${sessionScope.securityConfig.logo == ''}">
                    <div class="panel-title acooly-header">
                        <h1>${sessionScope.securityConfig.title}</h1>
                    </div>
                </c:if>
                <c:if test="${sessionScope.securityConfig.logo != ''}">
                    <div style="height: 50px; border-bottom: 1px solid #99BBE8;">
                        <img src="${sessionScope.securityConfig.logo}">
                    </div>
                </c:if>
            </div>
            <div>
                <div class="info" style="border-bottom: 1px dotted #ccc;">
                    <div class="tip icon-tip"></div>
                    <div id="message">
                        请注意账户安全。
                        <c:if test="${sessionScope.securityConfig.loginLock == 'true' }"> 连续错误${sessionScope.securityConfig.loginLockErrorTimes}次将锁定账户！</c:if>
                    </div>
                </div>
            </div>
            <div align="center" style="margin-top: 10px;">
                <form id="loginForm" method="post">
                    <input type="hidden" name="_csrf" value="${requestScope['org.springframework.security.web.csrf.CsrfToken'].token}"/>
                    <table class="tableForm" width="100%" style="margin-bottom: 20px">
                        <tr>
                            <th width="32%">登录名</th>
                            <td><input name="username" class="easyui-validatebox" data-options="required:true"
                                       style="width: 150px; height: 25px; padding-left: 2px;"/></td>
                        </tr>
                        <tr>
                            <th>密&nbsp;&nbsp;码</th>
                            <td><input type="password" id="password" name="password" class="easyui-validatebox" data-options="required:true"
                                       style="width: 150px; height: 25px; padding-left: 2px;"/></td>
                        </tr>
                        <tr id="captchaPanel" style="display: none;">
                            <th>验证码</th>
                            <td><img id="jcaptchaImage" onclick="refreshCaptcha()" src="/jcaptcha.jpg" height="30px" width="70px"
                                     align="top"> <input type="text" id="captcha" name="captcha" class="easyui-validatebox"
                                                         data-options="required:false"
                                                         style="width: 73px; height: 25px; padding-left: 2px;"/></td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>