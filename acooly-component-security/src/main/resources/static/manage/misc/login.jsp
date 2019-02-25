<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${sessionScope.securityConfig.title }</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">

    <!-- bootstrap -->
    <link rel="stylesheet" href="/manage/assert/plugin/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="/manage/assert/plugin/bootstrap/3.3.5/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="/manage/assert/plugin/awesome/4.6.3/css/font-awesome.min.css">
    <link rel="stylesheet" href="/manage/assert/style/login.css">

</head>

<body>
<!-- Top content -->
<div class="top-content">
    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="text" style="text-align: center;">
                    <c:if test="${sessionScope.securityConfig.logo != ''}">
                        <img alt="logo" src="${sessionScope.securityConfig.logo}">
                    </c:if>
                    <c:if test="${sessionScope.securityConfig.logo == ''}">
                        <h1>${sessionScope.securityConfig.title}</h1>
                    </c:if>
                    <div class="description"><p>专注于<strong>业务</strong>开发，<strong>规范</strong>最佳实践，自动代码生成，提高<strong>70%</strong>效率！</p></div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-4 col-sm-offset-4 form-box">
                    <div class="form-top">
                        <div class="form-top-left">
                            <h3>登录您的账号</h3>
                            <p id="message" class="text-danger"><i class="fa fa-exclamation"></i> 请注意账户安全</p>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-lock"></i>
                        </div>
                    </div>
                    <div class="form-bottom">
                        <form role="form" action="" method="post" class="login-form">
                            <input type="hidden" id="_csrf" name="_csrf"
                                   value="${requestScope['org.springframework.security.web.csrf.CsrfToken'].token}"/>
                            <div class="form-group input-group">
                                <span class="input-group-addon" id="username-addon"><i class="fa fa-user"></i></span>
                                <label class="sr-only" for="form-username">用户名</label>
                                <input type="text" name="username" placeholder="账号..." aria-describedby="username-addon"
                                       class="form-username form-control" id="form-username">
                            </div>
                            <div class="form-group input-group">
                                <span class="input-group-addon" id="password-addon"><i class="fa fa-key"></i></span>
                                <label class="sr-only" for="form-password">密码</label>
                                <input type="password" name="password" placeholder="密码..." aria-describedby="password-addon"
                                       class="form-password form-control" id="form-password">
                            </div>
                            <div class="form-group input-group" id="captchaPanel" style="display: none;">
                                <span class="input-group-addon" id="captcha-addon"><i class="fa fa-check"></i></span>
                                <label class="sr-only" for="form-captcha">验证码</label>
                                <input type="text" name="captcha" placeholder="验证码..." aria-describedby="captcha-addon"
                                       class="form-captcha form-control" id="form-captcha">
                                <span class="ml5">
		                      	<img id="jcaptchaImage" onclick="refreshCaptcha()" src="/jcaptcha.jpg" height="34" width="65" align="top"
                                     style="cursor: pointer;" title="看不清楚？点击更换">
		                      	<a href="javascript:;" onclick="refreshCaptcha()" style="font-size: 12px;">看不清,换一下！</a>
		                </span>
                            </div>
                            <button type="button" id="loginButton" onclick="loginForm()" class="btn btn-warning">登 录</button>
                        </form>
                    </div>
                </div>
            </div>
            <!-- footer -->
            <div class="row ">
                <div class="login-footer">${sessionScope.securityConfig.copyright }</div>
            </div>

        </div>
    </div>
</div>

<script type="text/javascript" src="//cdn.bootcss.com/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="/manage/assert/plugin/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script type="text/javascript">

    /**
     * 刷新验证码
     */
    function refreshCaptcha() {
        $('#captchaPanel').show();
        $('#jcaptchaImage').attr("src", "/jcaptcha.jpg?" + new Date());
        $('#captcha').val('');
    }

    /**
     *  登录
     */
    function loginForm() {
        $('.login-form').find('input[type="text"], input[type="password"], textarea').each(function () {
            if ($(this).val() == "") {
                $(this).addClass('input-error');
            } else {
                $(this).removeClass('input-error');
            }
        });
        if ($('#form-username').val() == "" || $('#form-password').val() == "") {
            return;
        }
        var jsonData = {
            username: $('#form-username').val(),
            password: $('#form-password').val(),
            captcha: $('#form-captcha').val(),
            _csrf: $('#_csrf').val()
        };

        loading();
        $.ajax({
            url: '/manage/login.html',
            data: jsonData,
            method: 'POST',
            success: function (result) {
                if (result.success) {
                    window.location.href = "/manage/index.html";
                } else {
                    refreshCaptcha();
                    var message = result.message;
                    if (result.data.lastTimes) {
                        message += ". 再失败" + result.data.lastTimes + "次后锁定账户";
                    }
                    $('#message').html(message);
                    loaded();
                }
            },
            error: function (e) {
                $('#message').html("网络错误:" + e.status + ":" + e.statusText);
                loaded();
            }
        });
    }

    function loading() {
        $('#loginButton').html('<i class="fa fa-spinner fa-spin" style="font-size: 18px;"></i> 登录中...');
    }

    function loaded() {
        $('#loginButton').html('登录');
    }


    $(function () {
        $('.login-form input[type="text"], .login-form input[type="password"], .login-form textarea').on('focus', function () {
            $(this).removeClass('input-error');
        });

    });

</script>
</body>
</html>