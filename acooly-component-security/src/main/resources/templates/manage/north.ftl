<div class="acooly-top">
    <div class="bg">
        <div class="header">
            <div class="header-left">
                <div id="acooly_title" class="text-logo"></div>
                <div id="acooly_logo" style="border-bottom: 1px solid #99BBE8;">
                    <img src="" style="height: 35px;">
                </div>
            </div>
            <div class="header-right">
                <div class="userinfo">
                    <@shiroPrincipal/>
                </div>
                <div style="float: right;padding-right: 10px;">
                    <button class="layui-btn layui-btn-primary layui-btn-xs" onclick="$.acooly.framework.changePassword();"><i class="fa fa-key fa-fw
                    fa-lg" aria-hidden="true"></i>修改密码
                    </button>
                    <button class="layui-btn layui-btn-primary layui-btn-xs" onclick="$.acooly.framework.logout();"><i
                            class="fa fa-sign-out fa-fwfa-lg" aria-hidden="true"></i>注销
                    </button>
                    <button class="layui-btn layui-btn-danger layui-btn-xs" onclick="location.href='/manage/index' +
                     '.html?acoolyTheme=adminlte'">
                        <i class="fa fa-sign-out fa-fwfa-lg" aria-hidden="true"></i> 新版
                    </button>
                </div>
            </div>
        </div>
        <div id="mainMenu" class="nav"></div>
        <div class="top-menu" style="position: absolute; right: 0px; bottom: 0px;">
            <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_menu_theme'">
                <i class="fa fa-laptop fa-fw fa-lg fa-col" aria-hidden="true"></i>主题</a>
        </div>
        <div id="layout_menu_theme" style="width: 100px; display: none;">
            <div id="theme_acooly" onclick="changeTheme('acooly');">acooly</div>
            <div id="theme_acooly4" onclick="changeTheme('acooly4');">acooly4</div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $.acooly.layout.loadMenu();
        // 旧版风格内的切换
        loadTheme();
        if ($.acooly.system.config.logo) {
            $('#acooly_logo img').attr('src', $.acooly.system.config.logo)
            $('#acooly_title').hide();
        } else {
            if ($.acooly.system.config.title) {
                $('#acooly_title').text($.acooly.system.config.title);
            }
        }
    });
</script>