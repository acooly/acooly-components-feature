<!DOCTYPE html>
<html>
<head>
    <title>${securityConfig.title}</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="acooly">
    <meta http-equiv="description" content="spring+jpa+hibernate+easyui+springmvc+jstl/freemarker">
    <meta name="X-CSRF-TOKEN" content="${Request['org.springframework.security.web.csrf.CsrfToken'].token}"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <#if securityConfig.icon??>
        <link rel="shortcut icon" href="${securityConfig.icon}" /></#if>

    <!-- easyui -->
    <link rel="stylesheet" href="/manage/assert/plugin/layui/css/layui.css">
    <!-- ztree -->
    <link rel="stylesheet" type="text/css" href="/manage/assert/plugin/jquery-ztree/css/zTreeStyle/zTreeStyle.css"/>
    <!-- icons -->
    <link rel="stylesheet" href="/manage/assert/plugin/icon/Ionicons/2.0.1/css/ionicons.min.css">
    <link rel="stylesheet" href="/manage/assert/plugin/awesome/4.7.0/css/font-awesome.min.css">
    <!-- adminlte -->
    <link rel="stylesheet" href="/manage/assert/plugin/adminlte3/css/adminlte.min.css">
    <!-- Select2 -->
    <link rel="stylesheet" href="/manage/assert/plugin/select2/css/select2.min.css">
    <!-- easyui控件 -->
    <link id="easyuiTheme" rel="stylesheet" href="/manage/assert/plugin/jquery-easyui/themes/bootstrap/easyui.css" type="text/css"/>
    <link id="easyuiThemeBasic" rel="stylesheet" href="/manage/assert/plugin/jquery-easyui/themes/default/basic.css">
    <link rel="stylesheet" type="text/css" href="/manage/assert/style/icon.css">
    <link rel="stylesheet" href="/manage/assert/plugin/jquery-easyui/themes/icon.css" type="text/css"/>
    <link rel="stylesheet" href="/manage/assert/plugin/jquery-easyui/themes/color.css" type="text/css"/>
    <link rel="stylesheet" href="/manage/assert/plugin/jquery-easyui-portal/portal.css" type="text/css"/>
    <#if securityConfig.plugin.icheck>
        <!-- icheck bootstrap -->
        <link rel="stylesheet" href="/manage/assert/plugin/icheck-bootstrap/icheck-bootstrap.min.css">
    </#if>
    <#if securityConfig.plugin.videoJs>
        <!-- videoJs -->
        <link rel="stylesheet" href="/manage/assert/plugin/jquery-plugin/videojs/video-js.css" type="text/css"/>
    </#if>
    <!-- 扩展css -->
    ${extendStyles}
    <!-- 根据配置设置全局样式 -->
    <style>
        .datagrid-header .datagrid-cell span {
            font-size: ${securityConfig.fontSize}px;
        }

        .combobox-item, .combobox-group, .combobox-stick, .combo input[type='text'], .tableForm input, .tableForm textarea, .ztree *,
        .input-group-text, .tabs-title {
            font-size: ${securityConfig.fontSize}px;
        }

        .datagrid-header-row, .datagrid-row {
            height: <#if securityConfig.fontSize lt 14>35<#else>40</#if>px;
        }

        .datagrid-cell, .datagrid-cell-group, .datagrid-header-rownumber, .datagrid-cell-rownumber {
            font-size: ${securityConfig.fontSize}px;
        }

        .l-btn-text {
            font-size: ${securityConfig.fontSize}px;
        }

        .panel-body {
            font-size: ${securityConfig.fontSize}px;
        }

        body {
            font-size: ${securityConfig.fontSize}px;
        }

        .main-sidebar, .main-sidebar::before {
            width: ${securityConfig.mainSidebarWidth}px;
        }

        .content-wrapper, body:not(.sidebar-mini-md) .main-footer, body:not(.sidebar-mini-md) .main-header {
            margin-left: ${securityConfig.mainSidebarWidth}px;
        }

        body:not(.sidebar-mini-md) .content-wrapper, body:not(.sidebar-mini-md) .main-footer, body:not(.sidebar-mini-md) .main-header {
            margin-left: ${securityConfig.mainSidebarWidth}px;
        }

        .tree-title {
            font-size: ${securityConfig.fontSize}px;
        }

        .layout-fixed .brand-link {
            width: ${securityConfig.mainSidebarWidth}px;
        }

        .ac-logo-title {
            font-size: <#if securityConfig.fontSize lt 14>18<#else>20</#if>px;
        }
    </style>
</head>

<body id="mainLayout" class="easyui-layout content-wrapper" style="margin-left: 2px; margin-right: 2px;">
<div data-options="region:'north',border:false" style="overflow: hidden;">
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
                    <div class="userinfo"><@shiroPrincipal/></div>
                    <div style="float: right;padding-right: 10px;">
                        <a href="#" onclick="$.acooly.framework.changePassword();" data-options="plain:true" class="easyui-linkbutton c9"><i class="fa fa-key fa-fw fa-col" aria-hidden="true"></i> 密码</a>
                        <a href="#" onclick="$.acooly.framework.logout();" data-options="plain:true" class="easyui-linkbutton c9"><i class="fa fa-sign-out fa-fw fa-col" aria-hidden="true"></i> 注销</a>
                        <a href="/manage/index.html?acoolyTheme=acooly" data-options="plain:true" class="easyui-linkbutton c9"><i class="fa fa-thumbs-o-up fa-fw fa-col" aria-hidden="true"></i> 新版</a>
                    </div>
                </div>
            </div>
            <div id="mainMenu" class="nav"></div>
        </div>
    </div>
</div>
<div id="mainWestLayout" title="功能菜单"
     data-options="headerCls:'westHeader',tools: [{iconCls:'icon_pagination_reload',handler:function(){$.acooly.layout.reloadMenu();}}], region:'west',onLoad:function(){$.acooly.layout.loadTree();}"
     style="width: 210px; overflow: hidden;">
    <div id="menuLayout" class="easyui-layout" data-options="fit:true,border:false">
        <div data-options="region:'center',border:false">
            <ul id="layout_west_tree" class="ztree" style="margin-top: 5px; margin-left: 5px;">加载中...
            </ul>
        </div>
    </div>
</div>
<div data-options="region:'center'" class="centerHeader" style="overflow: hidden;">
    <div id="layout_center_tabs" style="overflow: hidden;">
<#--        <div title="首页" data-options="href:'/manage/layout/portal.html'"></div>-->
    </div>
    <div id="layout_center_tabsMenu" style="width: 120px;display:none;">
        <div type="refresh">刷新</div>
        <div class="menu-sep"></div>
        <div type="close">关闭</div>
        <div type="closeOther">关闭其他</div>
        <div type="closeAll">关闭所有</div>
    </div>
</div>

<!-- jquery -->
<script src="/manage/assert/plugin/jquery/3.4.1/jquery.min.js"></script>
<script src="/manage/assert/plugin/jquery/jquery-migrate-3.1.0.min.js" type="text/javascript" charset="utf-8"></script>
<script src="/manage/assert/plugin/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- select2 -->
<script src="/manage/assert/plugin/select2/js/select2.min.js"></script>
<!-- Jquery plugins  -->
<script src="/manage/assert/plugin/template/baiduTemplate.js"></script>
<script src="/manage/assert/plugin/jquery-plugin/jquery.form.cookie.resize.min.js" type="text/javascript" charset="utf-8"></script>
<!-- easyui -->
<script type="text/javascript" src="/manage/assert/plugin/jquery-easyui/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="/manage/assert/plugin/jquery-easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<script type="text/javascript" src="/manage/assert/plugin/jquery-easyui-portal/jquery.portal.js" charset="utf-8"></script>
<#if securityConfig.plugin.easyuiExtension>
    <script type="text/javascript" src="/manage/assert/plugin/jquery-easyui/plugins/datagrid-extensions.min.js" charset="utf-8"></script>
</#if>

<#if securityConfig.plugin.clipboard>
    <script src="/manage/assert/plugin/jquery-plugin/clipboard.min.js"></script>
</#if>
<#if securityConfig.plugin.media>
    <script src="/manage/assert/plugin/jquery-plugin/jquery.media.js"></script>
</#if>
<#if securityConfig.plugin.xss>
    <script src="/manage/assert/plugin/jquery-plugin/xss.min.js" type="text/javascript" charset="utf-8"></script>
</#if>
<!-- my97日期控件 -->
<script type="text/javascript" src="/manage/assert/plugin/My97DatePicker/WdatePicker.js" charset="utf-8"></script>
<!-- uploadifive -->
<link rel="stylesheet" type="text/css" href="/manage/assert/plugin/uploadifive/uploadifive.css"/>
<script type="text/javascript" src="/manage/assert/plugin/uploadifive/jquery.uploadifive.min.js"></script>
<!-- inputmask -->
<script src="/manage/assert/plugin/jquery-plugin/inputmask/min/jquery.inputmask.bundle.min.js"></script>
<!-- AdminLTE bs-custom-file-input -->
<script src="/manage/assert/plugin/bs-custom-file-input/bs-custom-file-input.min.js"></script>
<!-- ztree -->
<script type="text/javascript" src="/manage/assert/plugin/jquery-ztree/js/jquery.ztree.allinone.min.js"></script>
<!-- select2ztree -->
<script type="text/javascript" src="/manage/assert/plugin/jquery-plugin/select2ztree/jquery.select2ztree.search.js"></script>
<script type="text/javascript" src="/manage/assert/plugin/jquery-plugin/select2ztree/jquery.select2ztree.min.js"></script>
<#if securityConfig.plugin.layui>
    <script type="text/javascript" src="/manage/assert/plugin/layui/layui.all.js"></script>
</#if>
<#if securityConfig.plugin.kindEditor>
    <!--kindEditor插件库 -->
    <link rel="stylesheet" type="text/css" href="/manage/assert/plugin/kindeditor/plugins/multi_image/diyUpload/css/webuploader.css">
    <link rel="stylesheet" type="text/css" href="/manage/assert/plugin/kindeditor/plugins/multi_image/diyUpload/css/diyUpload.css">
    <script charset="utf-8" src="/manage/assert/plugin/kindeditor/plugins/multi_image/diyUpload/js/webuploader.html5only.min.js"></script>
    <script charset="utf-8" src="/manage/assert/plugin/kindeditor/plugins/multi_image/diyUpload/js/diyUpload.js"></script>
    <script charset="utf-8" src="/manage/assert/plugin/kindeditor/htmlminifier.min.js"></script>
    <script charset="utf-8" src="/manage/assert/plugin/kindeditor/kindeditor-all.js"></script>
    <script charset="utf-8" src="/manage/assert/plugin/kindeditor/lang/zh_CN.js"></script>
</#if>
<#if securityConfig.plugin.videoJs>
    <!-- videoJs -->
    <script src="/manage/assert/plugin/jquery-plugin/videojs/video.min.js"></script>
    <script src="/manage/assert/plugin/jquery-plugin/videojs/videojs-zh-CN.js"></script>
</#if>
<#if securityConfig.plugin.dateRangePicker>
    <!-- daterangepicker -->
    <link rel="stylesheet" href="/manage/assert/plugin/daterangepicker/daterangepicker.css" type="text/css"/>
    <script src="/manage/assert/plugin/daterangepicker/moment.min.js"></script>
    <script src="/manage/assert/plugin/daterangepicker/daterangepicker.min.js"></script>
    <script src="/manage/assert/plugin/daterangepicker/daterangepicker_acooly.min.js"></script>
</#if>

<!-- acooly -->
<#if securityConfig.plugin.acoolyDebug>
    <script src="/manage/assert/script/acooly.js"></script>
    <script src="/manage/assert/script/acooly.template.js" charset="utf-8"></script>
    <script src="/manage/assert/script/acooly.format.js" charset="utf-8"></script>
    <script src="/manage/assert/script/acooly.verify.js" charset="utf-8"></script>
    <script src="/manage/assert/script/acooly.file.js" charset="utf-8"></script>
    <script src="/manage/assert/script/acooly.editor.js" charset="utf-8"></script>
    <script src="/manage/assert/script/acooly.admin.js" charset="utf-8"></script>
    <script src="/manage/assert/script/acooly.framework.js" charset="utf-8"></script>
    <script src="/manage/assert/script/acooly.easyui.js" charset="utf-8"></script>
    <script src="/manage/assert/script/acooly.layout.js" charset="utf-8"></script>
    <script src="/manage/assert/script/acooly.system.js" charset="utf-8"></script>
    <script src="/manage/assert/script/acooly.portal.js" charset="utf-8"></script>
    <script src="/manage/assert/script/acooly.ui.messager.js" charset="utf-8"></script>
    <script src="/manage/assert/script/acooly.inputmask.js"></script>
<#else>
    <script src="/manage/assert/script/acooly.min.js" charset="utf-8"></script>
</#if>

<!-- 通过配置扩展script -->
${extendScripts}
<script type="text/javascript">
    var contextPath = '';
    $(function () {
        var acoolyTheme = '${acoolyTheme}';
        if (acoolyTheme == '') {
            acoolyTheme = 'acooly';
        }
        $.acooly.admin.theme.saveTheme($.acooly.admin.theme.acoolyThemeKey, acoolyTheme);
        $.acooly.admin.theme.loadTheme();
        $.acooly.system.init();
    });
</script>

</body>
</html>
