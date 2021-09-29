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
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- easyui -->
    <link rel="stylesheet" href="/manage/assert/plugin/layui/css/layui.css">
    <!-- ztree -->
    <link rel="stylesheet" type="text/css" href="/manage/assert/plugin/jquery-ztree/css/zTreeStyle/zTreeStyle.css"/>
    <!-- icons -->
    <link rel="stylesheet" href="/manage/assert/plugin/icon/Ionicons/2.0.1/css/ionicons.min.css">
    <link rel="stylesheet" href="/manage/assert/plugin/awesome/4.7.0/css/font-awesome.min.css">
    <!-- icheck bootstrap -->
    <link rel="stylesheet" href="/manage/assert/plugin/icheck-bootstrap/icheck-bootstrap.min.css">
    <!-- adminlte -->
    <link rel="stylesheet" href="/manage/assert/plugin/adminlte3/css/adminlte.min.css">
    <!-- Select2 -->
    <link rel="stylesheet" href="/manage/assert/plugin/select2/css/select2.min.css">
    <link rel="stylesheet" href="/manage/assert/plugin/select2-bootstrap4-theme/select2-bootstrap4.min.css">
    <!-- pace-progress -->
    <link rel="stylesheet" href="/manage/assert/plugin/pace-progress/themes/black/pace-theme-flat-top.css">
    <!-- easyui控件 -->
    <link id="easyuiTheme" rel="stylesheet" href="/manage/assert/plugin/jquery-easyui/themes/default/easyui.css" type="text/css"/>
    <link id="easyuiThemeBasic" rel="stylesheet"  href="/manage/assert/plugin/jquery-easyui/themes/default/basic.css">
    <link rel="stylesheet" type="text/css" href="/manage/assert/style/icon.css">
    <link rel="stylesheet" href="/manage/assert/plugin/jquery-easyui/themes/icon.css" type="text/css"/>
    <link rel="stylesheet" href="/manage/assert/plugin/jquery-easyui-portal/portal.css" type="text/css"/>
    <!-- videoJs -->
    <link rel="stylesheet" href="/manage/assert/plugin/jquery-plugin/videojs/video-js.css" type="text/css"/>
    <!-- 扩展css -->
    ${extendStyles}
</head>

<body id="mainLayout" class="easyui-layout" style="margin-left: 2px; margin-right: 2px;">
<div data-options="region:'north',border:false,href:'/manage/north.html'" style="height: 66px; overflow: hidden;"></div>
<div id="mainWestLayout" title="功能菜单"
     data-options="headerCls:'westHeader',tools: [{ iconCls:'icon-refresh',handler:function(){$.acooly.layout.reloadMenu();}}],region:'west',href:'/manage/layout/west.html',onLoad:function(){$.acooly.layout.loadTree();}"
     style="width: 210px; overflow: hidden;"></div>
<div data-options="region:'center',href:'/manage/layout/center.html'" class="centerHeader" style="overflow: hidden;"></div>



<!-- jquery -->
<script src="/manage/assert/plugin/jquery/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/manage/assert/plugin/jquery/jquery-migrate-3.1.0.min.js" charset="utf-8"></script>
<script type="text/javascript" src="/manage/assert/plugin/jquery-plugin/jquery.cookie.js" charset="utf-8"></script>
<script type="text/javascript" src="/manage/assert/plugin/jquery-plugin/jquery.form.min.js" charset="utf-8"></script>
<!-- easyui -->
<script type="text/javascript" src="/manage/assert/plugin/jquery-easyui/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="/manage/assert/plugin/jquery-easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<script type="text/javascript" src="/manage/assert/plugin/jquery-easyui/plugins/datagrid-detailview.js" charset="utf-8"></script>
<script type="text/javascript" src="/manage/assert/plugin/jquery-easyui/plugins/datagrid-groupview.js" charset="utf-8"></script>
<script type="text/javascript" src="/manage/assert/plugin/jquery-easyui/plugins/datagrid-statistics.js" charset="utf-8"></script>
<script type="text/javascript" src="/manage/assert/plugin/jquery-easyui-portal/jquery.portal.js" charset="utf-8"></script>
<!-- bootstrap-->
<script src="/manage/assert/plugin/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- layui -->
<script type="text/javascript" src="/manage/assert/plugin/layui/layui.all.js"></script>
<!-- 模板引擎：baidu -->
<script src="/manage/assert/plugin/template/baiduTemplate.js"></script>
<!-- my97日期控件 -->
<script type="text/javascript" src="/manage/assert/plugin/My97DatePicker/WdatePicker.js" charset="utf-8"></script>
<script type="text/javascript" src="/manage/assert/plugin/uploadifive/jquery.uploadifive.js"></script>
<link rel="stylesheet" type="text/css" href="/manage/assert/plugin/uploadifive/uploadifive.css"/>
<!-- select2 -->
<script src="/manage/assert/plugin/select2/js/select2.min.js"></script>
<!-- ztree -->
<script type="text/javascript" src="/manage/assert/plugin/jquery-ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="/manage/assert/plugin/jquery-ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="/manage/assert/plugin/jquery-ztree/js/jquery.ztree.exedit-3.5.js"></script>
<!-- select2ztree -->
<script type="text/javascript" src="/manage/assert/plugin/jquery-plugin/select2ztree/jquery.select2ztree.search.js"></script>
<script type="text/javascript" src="/manage/assert/plugin/jquery-plugin/select2ztree/jquery.select2ztree.js"></script>
<!--kindEditor插件库 -->
<script charset="utf-8" src="/manage/assert/plugin/kindeditor/kindeditor-all-min.js"></script>
<script charset="utf-8" src="/manage/assert/plugin/kindeditor/lang/zh_CN.js"></script>
<!-- inputmask -->
<script src="/manage/assert/plugin/jquery-plugin/inputmask/min/jquery.inputmask.bundle.min.js"></script>
<!-- AdminLTE bs-custom-file-input -->
<script src="/manage/assert/plugin/bs-custom-file-input/bs-custom-file-input.min.js"></script>
<!-- jquery.media for pdf view -->
<script src="/manage/assert/plugin/jquery-plugin/jquery.media.js"></script>
<!-- clipboard -->
<script src="/manage/assert/plugin/jquery-plugin/clipboard.min.js"></script>
<!-- videoJs -->
<script src="/manage/assert/plugin/jquery-plugin/videojs/video.min.js"></script>
<script src="/manage/assert/plugin/jquery-plugin/videojs/videojs-zh-CN.js"></script>


<!-- 自己定义的样式和JS扩展 -->
<script src="/manage/assert/script/acooly.js" charset="utf-8"></script>
<script src="/manage/assert/script/acooly.template.js" charset="utf-8"></script>
<script src="/manage/assert/script/acooly.format.js" charset="utf-8"></script>
<script src="/manage/assert/script/acooly.verify.js" charset="utf-8"></script>
<script src="/manage/assert/script/acooly.file.js" charset="utf-8"></script>
<script src="/manage/assert/script/acooly.admin.js" charset="utf-8"></script>
<script src="/manage/assert/script/acooly.framework.js" charset="utf-8"></script>
<script src="/manage/assert/script/acooly.easyui.js" charset="utf-8"></script>
<script src="/manage/assert/script/acooly.layout.js" charset="utf-8"></script>
<script src="/manage/assert/script/acooly.system.js" charset="utf-8"></script>
<script src="/manage/assert/script/acooly.portal.js" charset="utf-8"></script>
<script src="/manage/assert/script/acooly.ui.layer.js" charset="utf-8"></script>
<script src="/manage/assert/script/acooly.inputmask.js"></script>
<!-- 扩展script -->
${extendScripts}


<script type="text/javascript">
    var contextPath = '';
    $(function () {
        $.acooly.system.init();
        // 新旧版本的风格(访问到旧版页面，则设置版本cookies)
        $.acooly.admin.theme.saveTheme($.acooly.admin.theme.acoolyThemeKey, "easyui");
    });
</script>

</body>
</html>
