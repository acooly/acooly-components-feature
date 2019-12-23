<!DOCTYPE html>
<html>
<head>
    <title>${Session.securityConfig.title}</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="acooly">
    <meta http-equiv="description" content="spring+jpa+hibernate+easyui+springmvc+jstl/freemarker">
    <meta name="X-CSRF-TOKEN" content="${Request['org.springframework.security.web.csrf.CsrfToken'].token}"/>
    <!-- easyui控件 -->
    <link id="easyuiTheme" rel="stylesheet" href="/manage/assert/plugin/jquery-easyui/themes/acooly4/easyui.css" type="text/css"/>
    <link rel="stylesheet" href="/manage/assert/plugin/layui/css/layui.css">
    <link id="easyuiThemeBasic" rel="stylesheet"  href="/manage/assert/plugin/jquery-easyui/themes/acooly4/basic.css">
    <link rel="stylesheet" type="text/css" href="/manage/assert/style/icon.css">
    <link rel="stylesheet" href="/manage/assert/plugin/jquery-easyui/themes/icon.css" type="text/css"/>
    <!-- 扩展css -->
    ${extendStyles}

    <script src="/manage/assert/plugin/jquery/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="/manage/assert/plugin/jquery/jquery-migrate-1.1.0.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/plugin/jquery-easyui/jquery.easyui.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/plugin/jquery-easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/plugin/jquery-easyui/plugins/datagrid-detailview.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/plugin/jquery-easyui/plugins/datagrid-groupview.js" charset="utf-8"></script>
    <!-- easyui portal插件 -->
    <link rel="stylesheet" href="/manage/assert/plugin/jquery-easyui-portal/portal.css" type="text/css"/>
    <script type="text/javascript" src="/manage/assert/plugin/jquery-easyui-portal/jquery.portal.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/plugin/jquery-plugin/jquery.cookie.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/plugin/jquery-plugin/jquery.form.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/plugin/jquery-easyui/plugins/easyui.statistics.js" charset="utf-8"></script>

    <script type="text/javascript" src="/manage/assert/plugin/layui/layui.all.js"></script>
    <!-- 模板引擎：baidu -->
    <script src="/manage/assert/plugin/template/baiduTemplate.js"></script>

    <!-- 自己定义的样式和JS扩展 -->
    <script src="/manage/assert/script/acooly.admin.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/script/acooly.framework.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/script/acooly.format.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/script/acooly.verify.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/script/acooly.easyui.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/script/acooly.layout.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/script/acooly.system.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/script/acooly.portal.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/script/acooly.ui.layer.js" charset="utf-8"></script>
    <script src="/manage/assert/script/acooly.template.js" charset="utf-8"></script>

    <!-- 扩展script -->
${extendScripts}

    <!-- my97日期控件 -->
    <script type="text/javascript" src="/manage/assert/plugin/My97DatePicker/WdatePicker.js" charset="utf-8"></script>
    <!-- uploadify -->
<#--<script type="text/javascript" src="/manage/assert/plugin/jquery-uploadify/jquery.uploadify.min.js"></script>-->
<#--<link rel="stylesheet" type="text/css" href="/manage/assert/plugin/jquery-uploadify/uploadify.css"/>-->
    <script type="text/javascript" src="/manage/assert/plugin/uploadifive/jquery.uploadifive.js"></script>
    <link rel="stylesheet" type="text/css" href="/manage/assert/plugin/uploadifive/uploadifive.css"/>
    <!-- ztree -->
    <script type="text/javascript" src="/manage/assert/plugin/jquery-ztree/js/jquery.ztree.core-3.5.js"></script>
    <link rel="stylesheet" type="text/css" href="/manage/assert/plugin/jquery-ztree/css/zTreeStyle/zTreeStyle.css"/>
    <script type="text/javascript" src="/manage/assert/plugin/jquery-ztree/js/jquery.ztree.excheck-3.5.js"></script>
    <script type="text/javascript" src="/manage/assert/plugin/jquery-ztree/js/jquery.ztree.exedit-3.5.js"></script>
    <!--kindEditor插件库 -->
    <script charset="utf-8" src="/manage/assert/plugin/kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="/manage/assert/plugin/kindeditor/lang/zh_CN.js"></script>
    <!-- fancybox 图片展示插件 -->
    <script type="text/javascript" src="//cdn.bootcss.com/fancybox/2.1.5/jquery.fancybox.js"></script>
    <link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/fancybox/2.1.5/jquery.fancybox.css"/>

    <link rel="stylesheet" href="/manage/assert/plugin/awesome/4.7.0/css/font-awesome.min.css">


    <script type="text/javascript">
        var contextPath = '';
    </script>

    <script type="text/javascript">
        $(function () {
            $.acooly.system.init();
            // 新旧版本的风格(访问到旧版页面，则设置版本cookies)
            $.acooly.admin.theme.saveTheme($.acooly.admin.theme.acoolyThemeKey, "easyui");
        });
    </script>
</head>

<body id="mainLayout" class="easyui-layout" style="margin-left: 2px; margin-right: 2px;">
<div data-options="region:'north',border:false,href:'/manage/north.html'" style="height: 66px; overflow: hidden;"></div>
<div id="mainWestLayout" title="功能菜单"
     data-options="headerCls:'westHeader',tools: [{ iconCls:'icon-refresh',handler:function(){$.acooly.layout.reloadMenu();}}],region:'west',href:'/manage/layout/west.html',onLoad:function(){$.acooly.layout.loadTree();}"
     style="width: 210px; overflow: hidden;"></div>
<div data-options="region:'center',href:'/manage/layout/center.html'" class="centerHeader" style="overflow: hidden;"></div>
</body>
</html>
