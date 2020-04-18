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
                <div class="layui-btn-group" style="float: right;padding-right: 10px;">
                    <button class="layui-btn layui-btn-sm layui-btn-normal" onclick="$.acooly.framework.changePassword();">
                        <i class="fa fa-key fa-fw fa-col" aria-hidden="true"></i>密码
                    </button>
                    <button class="layui-btn layui-btn-sm layui-btn-normal" onclick="$.acooly.framework.logout();">
                        <i class="fa fa-sign-out fa-fw fa-col" aria-hidden="true"></i>注销
                    </button>
                    <button class="layui-btn layui-btn-sm layui-btn-normal" onclick="location.href='/manage/index' + '.html?acoolyTheme=adminlte'">
                        <i class="fa fa-thumbs-o-up fa-fw fa-col" aria-hidden="true"></i> 新版
                    </button>
                </div>
            </div>
        </div>
        <div id="mainMenu" class="nav"></div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $.acooly.layout.loadMenu();

        $.ajax({
            url: contextPath + "/security/config/index.html",
            success: function (data, textStatus) {
                if (typeof (data) == 'string') {
                    data = eval('(' + data + ')');
                }
                if (data.logo) {
                    $('#acooly_logo img').attr('src', data.logo)
                    $('#acooly_title').hide();
                } else {
                    if (data.title) {
                        $('#acooly_title').text(data.title);
                    }
                }
            }
        });
    });
</script>