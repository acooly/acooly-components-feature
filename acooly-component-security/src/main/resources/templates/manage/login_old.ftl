<!DOCTYPE html>
<html>
<head>
    <title>${Session.securityConfig.title }</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">

    <!-- bootstrap -->
    <link rel="stylesheet" href="/manage/assert/plugin/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="/manage/assert/plugin/awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="/manage/assert/style/login.css">

</head>

<body>
<!-- Top content -->
<div class="top-content">
    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="text" style="text-align: center;">
                    <#if Session.securityConfig.logo??>
                        <img alt="logo" width="300" src="${Session.securityConfig.logo}">
                    <#else>
                        <h1>${Session.securityConfig.title}</h1>
                    </#if>
                    <div class="description">${Session.securityConfig.subtitle}</div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-4 col-sm-offset-4 form-box">
                    <div class="form-top">
                        <div class="form-top-left">
                            <h3>登录您的账号</h3>
                            <p id="message" class="text-danger"><i class="fa fa-exclamation"></i>
                                <#if Request.message??>
                                    ${Request.message}
                                <#else>
                                    请注意账户安全
                                </#if>
                            </p>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-lock"></i>
                        </div>
                    </div>
                    <div class="form-bottom">
                        <form role="form" action="" method="post" class="login-form" autocomplete="off">
                            <input type="hidden" id="_csrf" name="_csrf"
                                   value="${Request['org.springframework.security.web.csrf.CsrfToken'].token}"/>

                            <#if Session.TENANT_FLAG?exists>
                            <div class="form-group input-group">
                                <span class="input-group-addon" id="username-addon"><i
                                        class="fa fa-user fa-fw"></i></span>
                                <label class="sr-only" for="form-username">租户号</label>
                                <select id="tenant_id" name="tenantId" editable="false" aria-describedby="username-addon" panelHeight="auto" class="form-control" style="width: 100%">
                                    <#list Session.TENANT_FLAG?keys as k><option value="${k}">${Session.TENANT_FLAG[k]}</option></#list></select>
                            </div>
                            </#if>


                            <div class="form-group input-group">
                                <span class="input-group-addon" id="username-addon"><i
                                            class="fa fa-user fa-fw"></i></span>
                                <label class="sr-only" for="form-username">用户名</label>
                                <input type="text" name="username" placeholder="账号..." aria-describedby="username-addon" autocomplete="new-password"
                                       class="form-username form-control" id="form-username">
                            </div>
                            <input style="display:none">
                            <div class="form-group input-group">
                                <span class="input-group-addon" id="password-addon"><i
                                            class="fa fa-key fa-fw"></i></span>
                                <label class="sr-only" for="form-password">密码</label>
                                <input type="password" name="password" placeholder="密码..." autocomplete="new-password"
                                       aria-describedby="password-addon" class="form-password form-control"
                                       id="form-password">
                            </div>

                            <#if Application.loginSmsEnable?exists>
                                <div class="form-group input-group" style="white-space:nowrap;">
                                   <span class="input-group-addon" id="captcha-addon"><i
                                               class="fa fa-check fa-fw"></i></span>
                                    <#-- <input id="phoneNumber" name="phoneNumber" value="" size="100"
                                            class="form-captcha form-control"
                                            type="text" placeholder="手机号码"/>-->

                                    <input id="validCode" name="validCode" type="text" value="" size="30"
                                           class="form-smscaptcha form-control"
                                           placeholder="验证码"/>
                                    <input
                                            id="getValidCodeaa" class="btn btn-info" width="20" type="button"
                                            style="font-size: small" value="获取验证码"
                                    />
                                    <input style="display: none;" id="smsSendInterval"
                                           value="${Session.securityConfig.smsSendInterval}"/>
                                </div>
                            <#else>
                                <div class="form-group input-group" id="captchaPanel" style="display: none;">
                                <span class="input-group-addon" id="captcha-addon"><i
                                            class="fa fa-check fa-fw"></i></span>
                                    <label class="sr-only" for="form-captcha">验证码</label>
                                    <input type="text" name="captcha" placeholder="验证码..." aria-describedby="captcha-addon"
                                           class="form-captcha form-control" id="form-captcha">
                                    <span class="ml5">
                                        <img id="jcaptchaImage" onclick="refreshCaptcha()" src="/jcaptcha.jpg"
                                             height="34"
                                             width="65" align="top" style="cursor: pointer;" title="看不清楚？点击更换">
                                        <a href="javascript:;" onclick="refreshCaptcha()"
                                           style="font-size: 12px;">看不清,换一下！</a>
                                </span>
                                </div>
                            </#if>

                            <button type="button" id="loginButton" onclick="loginForm()" class="btn btn-warning">登 录</button>
                        </form>
                        <div style="margin-top: 10px;font-size: 18px;">
                            <span style="font-size: 14px;">推荐浏览器：</span>
                            <a href="https://www.google.cn/chrome/" class="text-success" target="_blank" title="Google Chrome浏览器"><i class="fa fa-chrome fa-lg" aria-hidden="true"></i></a>
                            <a href="http://www.firefox.com.cn/" class="text-danger" target="_blank" title="Firefox火狐浏览器"><i class="fa fa-firefox fa-lg" aria-hidden="true"></i></a>
                            <a href="javascript:;" onclick="alert('苹果自带...');return false;" target="_blank" title="Safari苹果浏览器"><i class="fa fa-safari fa-lg" aria-hidden="true"></i></a>
                        </div>
                    </div>

                </div>
            </div>
            <!-- footer -->
            <div class="row ">
                <div class="login-footer">${Session.securityConfig.copyright}</div>
            </div>

        </div>
    </div>
</div>

<script src="/manage/assert/plugin/jquery/jquery-3.3.1.min.js"></script>
<script src="/manage/assert/plugin/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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


        <#if Application.loginSmsEnable?exists>
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
                    <#if Application.loginSmsEnable?exists>
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
//            if (!checkPhone()) {
//                return;
//            }
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
                btn.val(timeLeft + "秒后重发");
                timeCount();
            } else {
                btn.val("重新发送");
                btn.removeAttr('disabled');
                timeLeft = SEND_INTERVAL;
            }
        }, 1000);
    };

    /**
     * 验证手机号。
     * @param input 手机号输入域
     * @returns {boolean}
     */
//        function checkPhone(input) {
//            input = input || this.inputPhone;
//            var value = input.val();
//            if (!value) {
//                $('#message').html('手机号码不能为空!');
//                return false;
//            }
//            var reg = /(^0{0,1}[13|15|18|14|17]{2}[0-9]{9}$)/;
//            if (!reg.test(value)) {
//                $('#message').html('手机号码格式错误!');
//                return false;
//            } else {
//                //$('#message').html('手机号码格式正确');
//                $('#message').html('');
//                return true;
//            }
//        }

    function checkCodee() {
//            if (!this.checkPhone()) {
//                return false;
//            }
        if (!this.validCode.val()) {
            $('#message').html('验证码不能为空!');
            return false;
        }
        return true;
    }
    //
    // $.IEVersion = function() {
    //     var userAgent = navigator.userAgent.toLowerCase(); //取得浏览器的userAgent字符串
    //     var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("msie") > -1; //判断是否IE<11浏览器
    //     var isEdge = userAgent.indexOf("edge") > -1 && !isIE; //判断是否IE的Edge浏览器
    //     var isIE11 = userAgent.indexOf('trident') > -1 && userAgent.indexOf("rv:11.0") > -1;
    //     //alert("userAgent = "+userAgent+" isIE = "+isIE+"  isEdge = "+isEdge+" isIE11 = "+isIE11);
    //     if (isIE) {
    //         var reIE = new RegExp("msie (\\d+\\.\\d+);");
    //         reIE.test(userAgent);
    //         var fIEVersion = parseFloat(RegExp["$1"]);
    //         if (fIEVersion == 7) {
    //             return 7;
    //         } else if (fIEVersion == 8) {
    //             return 8;
    //         } else if (fIEVersion == 9) {
    //             return 9;
    //         } else if (fIEVersion == 10) {
    //             return 10;
    //         } else {
    //             return 6; //IE版本<=6
    //         }
    //     } else if (isEdge) {
    //         return 'edge'; //edge
    //     } else if (isIE11) {
    //         return 11; //IE11
    //     }
    //     return -1; //不是ie浏览器
    // };
    //
    // if($.IEVersion() != -1){
    //     $('#message').html("请使用非IE浏览器");
    // }
</script>

</body>
</html>