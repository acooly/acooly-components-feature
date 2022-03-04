<!DOCTYPE html>
<html>
<head>
    <title>${Session.securityConfig.title }</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <#if Session.securityConfig.icon??><link rel="shortcut icon" href="${Session.securityConfig.icon}" /></#if>
    <!-- icons -->
    <link rel="stylesheet" href="/manage/assert/plugin/icon/Ionicons/2.0.1/css/ionicons.min.css">
    <link rel="stylesheet" href="/manage/assert/plugin/awesome/4.7.0/css/font-awesome.min.css">
    <!-- icheck bootstrap -->
    <link rel="stylesheet" href="/manage/assert/plugin/icheck-bootstrap/icheck-bootstrap.min.css">
    <!-- adminlte -->
    <link rel="stylesheet" href="/manage/assert/plugin/adminlte3/css/adminlte.min.css">

<#--    <link rel="stylesheet" href="/manage/assert/style/login.css">-->

</head>

<body class="hold-transition login-page" style="height:80vh;background: ${securityConfig.loginViewBackColor} url(${securityConfig.loginViewBackImage}) no-repeat center bottom;">

<div class="login-logo">
    <#if Session.securityConfig.logo?? && Session.securityConfig.logo != "">
        <img alt="logo" width="200" src="${Session.securityConfig.logo}">
    <#else>
        <a href="javascript:;" class="text-white">${Session.securityConfig.title}</a>
    </#if>
    <div class="text-center text-white" style="font-size: 14px;margin-top: 10px;">${Session.securityConfig.subtitle}</div>
</div>
<div class="login-box">

    <!-- /.login-logo -->
    <div class="card" style="font-size: 14px;">
        <div class="card-body login-card-body">
            <p id="message" class="login-box-msg text-danger">
                <#if Request.message??>
                    ${Request.message}
                <#else>
                    请注意账户安全
                </#if>
            </p>

            <form role="form" action="" method="post" autocomplete="off">
                <input type="hidden" id="_csrf" name="_csrf" value="${Request['org.springframework.security.web.csrf.CsrfToken'].token}"/>
                <#if Session.TENANT_FLAG?exists>
                    <div class="input-group mb-3">
                        <select id="tenant_id" name="tenantId" placeholder="租户号..."  class="form-control" style="width: 100%">
                            <#list Session.TENANT_FLAG?keys as k><option value="${k}">${Session.TENANT_FLAG[k]}</option></#list>
                        </select>
                    </div>
                </#if>

                <div class="input-group mb-3">
                    <input type="text" name="username" placeholder="用户名..." class="form-control" id="form-username">
                    <div class="input-group-append">
                        <div class="input-group-text">
                            <span class="fa fa-user"></span>
                        </div>
                    </div>
                </div>
                <div class="input-group mb-3">
                    <input type="password" name="password" placeholder="密码..." class="form-control" id="form-password" autocomplete="off">
                    <div class="input-group-append">
                        <div class="input-group-text">
                            <span class="fa fa-lock"></span>
                        </div>
                    </div>
                </div>

                <#if Application.enableSmsAuth??>
                <!-- sms -->
                <div class="input-group mb-3">
                    <input id="validCode" name="validCode" type="text" value="" size="25" class="form-smscaptcha form-control" placeholder="短信验证码"/>
                    <div class="input-group-append">
                        <div class="input-group-text">
                            <span class="fa fa-mobile fa-lg"></span>
                        </div>
                    </div>
                    <button id="getValidCodeaa" type="button" class="btn btn-secondary" style="margin-left: 5px;font-size: 14px;">获取验证码</button>
                    <input style="display: none;" id="smsSendInterval" value="${securityProperties.smsSendInterval}"/>
                </div>
                <#else>
                <!-- human验证码 -->
                <div class="input-group mb-3" id="captchaPanel" style="display: none;">
                    <input type="text" name="captcha" placeholder="验证码..." class="form-captcha form-control" id="form-captcha">
                    <div class="input-group-append">
                        <div class="input-group-text">
                            <span class="fa fa-male"></span>
                        </div>
                    </div>
                    <span class="ml5">
                        <img id="jcaptchaImage" onclick="refreshCaptcha()" src="/jcaptcha.jpg" height="34" width="65" align="top" style="cursor: pointer;" title="看不清楚？点击更换">
                        <a href="javascript:;" onclick="refreshCaptcha()" style="font-size: 12px;">看不清,换一下！</a>
                    </span>
                </div>
                </#if>
                <div class="row mb-3">
                    <div class="col-12"><button type="button" id="loginButton" onclick="loginForm()" class="btn btn-primary btn-block">登 录</button></div>
                </div>
<#--                <div class="row mb-3">-->
<#--                    <div class="col-6">-->
<#--                        <div class="icheck-primary">-->
<#--                            <input type="checkbox" id="remember">-->
<#--                            <label for="remember">记住用户名</label>-->
<#--                        </div>-->
<#--                    </div>-->
<#--                    <div class="col-6" style="line-height: 36px;text-align: right;">-->
<#--                        <a href="forgot-password.html">找回密码</a>-->
<#--                    </div>-->
<#--                </div>-->
                <div class="row">
                    <div class="col-12" style="font-size: 18px;">
                        <span style="font-size: 14px;">推荐浏览器：</span>
                        <a href="https://www.google.cn/chrome/" class="text-success" target="_blank" title="Google Chrome浏览器"><i class="fa fa-chrome fa-lg" aria-hidden="true"></i></a>
                        <a href="http://www.firefox.com.cn/" class="text-danger" target="_blank" title="Firefox火狐浏览器"><i class="fa fa-firefox fa-lg" aria-hidden="true"></i></a>
                        <a href="javascript:;" onclick="alert('苹果自带...');return false;" target="_blank" title="Safari苹果浏览器"><i class="fa fa-safari fa-lg" aria-hidden="true"></i></a>
                    </div>
                </div>
            </form>
        </div>
        <!-- /.login-card-body -->
    </div>
    <#if Session.securityConfig.copyright?? && Session.securityConfig.copyright != "">
    <!-- footer -->
    <div class="row">
        <div class="col-12 login-footer text-center text-white" style="font-size: 14px;">${Session.securityConfig.copyright}</div>
    </div>
    </#if>
</div>
<!-- /.login-box -->

<!-- jQuery -->
<script src="/manage/assert/plugin/jquery/jquery-3.3.1.min.js"></script>
<!-- Bootstrap 4 -->
<script src="/manage/assert/plugin/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- AdminLTE App -->
<script src="/manage/assert/plugin/adminlte3/js/adminlte.js"></script>
<script src="/manage/assert/script/acooly.admin.js" charset="utf-8"></script>
<script src="/manage/assert/plugin/jquery-plugin/jquery.cookie.js" charset="utf-8"></script>


<script type="text/javascript">

    var passwordRegex = "${passwordRegex}";
    var passwordMessage = "${securityConfig.passwordStrength.detail}";

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
        var username = $('#form-username').val();
        var password = $('#form-password').val();
        if (username == "" || password == "") {
            $('#message').html("用户名和密码不能为空");
            return;
        }
        <#if Session.TENANT_FLAG?exists>

        var tenantId = $('#tenant_id').val();
        if (tenantId == "" ) {
            $('#message').html("租户Id不能为空");
            return;
        }
        </#if>

        if(username.length > 64){
            $('#message').html("用户名长度不能操过64字符");
            return;
        }


        if(passwordRegex != ""){
            var regexPassword = new RegExp(passwordRegex);
            if(!regexPassword.test(password)){
                $('#message').html(passwordMessage);
                return;
            }
        }


        <#if Application.enableSmsAuth?exists>
        if (!checkCodee()) {
            return;
        }
        var captcha = $('#validCode').val();
        <#else>
        var captcha = $('#form-captcha').val();
        </#if>

        var targetUrl = getParameterByName('targetUrl');
        var cookiesAcoolyTheme = $.acooly.admin.theme.getTheme();
        var jsonData = {
            username: $('#form-username').val(),
            password: $('#form-password').val(),
            <#if Session.TENANT_FLAG?exists>
            tenantId: $('#tenant_id').val(),
            </#if>
            captcha: captcha,
            _csrf: $('#_csrf').val(),
            targetUrl: targetUrl
            , acoolyTheme: cookiesAcoolyTheme
        };

        loading();
        $.ajax({
            url: '/manage/login.html',
            data: jsonData,
            method: 'POST',
            success: function (result) {
                if (result.success) {
                    var isRedirect = result.data.isRedirect;
                    if (isRedirect) {
                        window.location.href = result.data.targetUrl;
                    } else {
                        window.location.href = "/manage/index.html";
                    }
                } else {
                    <#if Application.enableSmsAuth?exists>
                    <#else>
                    refreshCaptcha();
                    </#if>
                    var message = result.message;
//					if(result.data.lastTimes){
//						message += ". 再失败"+result.data.lastTimes+"次后锁定账户";
//					}
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

    function getParameterByName(name) {
        var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.search);
        return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
    }

    $(function () {
        $('.login-form input[type="text"], .login-form input[type="password"], .login-form textarea').on('focus', function () {
            $(this).removeClass('input-error');
        });
        $('#form-password').keydown(function (event) {
            if (event.keyCode == 13) {
                loginForm();
            }
        });
        $('#form-captcha').keydown(function (event) {
            if (event.keyCode == 13) {
                loginForm();
            }
        });
    });

    /**
     * 登录手机验证码
     * @author shuijing
     */
    var btn = $("#getValidCodeaa");
    //var inputPhone = $('#phoneNumber');
    var validCode = $('#validCode');
    var SEND_INTERVAL = $('#smsSendInterval').val();
    var timeLeft = SEND_INTERVAL;
    btn.bind('click', function () {
        if (!$('#form-username').val()) {
            $('#message').html('用户名不能为空!');
            return;
        }
        btn.attr('disabled', 'disabled');
        validCode.val('');
        var jsonData = {
            username: $('#form-username').val(),
            //phoneNumber: inputPhone.val(),
            _csrf: $('#_csrf').val()
        };

        $.ajax({
            url: '/sms/user/login/generateSmsCaptcha.json',
            data: jsonData,
            method: 'POST',
            success: function (result) {
                if (result.success) {
                    $('#message').html(result.message);
                    timeCount();
                } else {
                    $('#message').html(result.message);
                    btn.removeAttr('disabled');
                }
            },
            error: function (e) {
                $('#message').html("网络错误:" + e.status + ":" + e.statusText);
                btn.removeAttr('disabled');
            }
        });
    });

    /**
     * 重新发送计时
     **/
    var timeCount = function () {
        window.setTimeout(function () {
            if (timeLeft > 0) {
                timeLeft -= 1;
                btn.html(timeLeft + "秒后重发");
                timeCount();
            } else {
                btn.html("重新发送");
                btn.removeAttr('disabled');
                timeLeft = SEND_INTERVAL;
            }
        }, 1000);
    };

    function checkCodee() {
        if (!this.validCode.val()) {
            $('#message').html('验证码不能为空!');
            return false;
        }
        return true;
    }

    $(function () {
        <#if notFirstVerify>
        refreshCaptcha();
        </#if>
    });

</script>

</body>
</html>
