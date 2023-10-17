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
                    <a href="#" onclick="$.acooly.framework.changePassword();" data-options="plain:true,height:28" class="easyui-linkbutton c9"><i class="fa fa-key fa-fw fa-col" aria-hidden="true"></i> 密码</a>
                    <a href="#" onclick="$.acooly.framework.logout();" data-options="plain:true,height:28" class="easyui-linkbutton c9"><i class="fa fa-sign-out fa-fw fa-col" aria-hidden="true"></i> 注销</a>
                    <a href="/manage/index.html?acoolyTheme=acooly" data-options="plain:true,height:28" class="easyui-linkbutton c9"><i class="fa fa-thumbs-o-up fa-fw fa-col" aria-hidden="true"></i> 新版</a>
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
            url: "/security/config/index.html",
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
