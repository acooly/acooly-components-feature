<!DOCTYPE html>
<html>
<head>
    <title>${Session.securityConfig.title}</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta http-equiv="cache-control" content="max-age=86400">
    <meta http-equiv="expires" content="1440">
    <meta http-equiv="keywords" content="acooly">
    <meta http-equiv="description" content="spring+jpa+hibernate+easyui+springmvc+jstl/freemarker">
    <meta name="X-CSRF-TOKEN" content="${Request['org.springframework.security.web.csrf.CsrfToken'].token}"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

    <!-- layui -->
    <link rel="stylesheet" href="/manage/assert/plugin/layui/css/layui.css">
    <!-- adminlte -->
    <link rel="stylesheet" href="/manage/assert/plugin/adminlte3/css/adminlte.min.css">
    <!-- zTree -->
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
    <#--    <link rel="stylesheet" href="/manage/assert/plugin/pace-progress/themes/black/pace-theme-flat-top.css">-->
    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
    <!-- easyui及自定义 -->
    <link id="easyuiTheme" rel="stylesheet" href="/manage/assert/plugin/jquery-easyui/themes/acooly/easyui.css" type="text/css"/>
    <link id="easyuiThemeBasic" rel="stylesheet" href="/manage/assert/plugin/jquery-easyui/themes/acooly/basic.css">
    <link rel="stylesheet" type="text/css" href="/manage/assert/style/icon.css">
    <link rel="stylesheet" href="/manage/assert/plugin/jquery-easyui/themes/icon.css" type="text/css"/>


    <!-- 扩展css -->
    ${extendStyles}

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->


    <script src="/manage/assert/plugin/jquery/3.4.1/jquery.min.js"></script>
    <script src="/manage/assert/plugin/bootstrap/js/bootstrap.bundle.min.js"></script>
    <!-- pace-progress -->
    <#--    <script src="/manage/assert/plugin/pace-progress/pace.min.js"></script>-->
    <!-- Slimscroll -->
    <script src="/manage/assert/plugin/jquery-plugin/jquery.slimscroll-1.3.8.min.js"></script>
    <!-- FastClick -->
    <script src="/manage/assert/plugin/jquery-plugin/fastclick-1.0.6.min.js"></script>
    <!-- AdminLTE App -->
    <script src="/manage/assert/plugin/adminlte3/js/adminlte.js"></script>
    <!-- 模板引擎：baidu -->
    <script src="/manage/assert/plugin/template/baiduTemplate.js"></script>
    <!-- AdminLTE for -->
    <script src="/manage/assert/plugin/jquery-plugin/jquery.resize.js"></script>
    <script type="text/javascript" src="/manage/assert/plugin/jquery/jquery-migrate-3.1.0.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/plugin/jquery-easyui/jquery.easyui.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/plugin/jquery-easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/plugin/jquery-easyui/plugins/datagrid-detailview.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/plugin/jquery-easyui/plugins/datagrid-groupview.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/plugin/jquery-easyui/plugins/datagrid-statistics.js" charset="utf-8"></script>
    <!-- easyui portal插件 -->
    <link rel="stylesheet" href="/manage/assert/plugin/jquery-easyui-portal/portal.css" type="text/css"/>
    <script type="text/javascript" src="/manage/assert/plugin/jquery-easyui-portal/jquery.portal.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/plugin/jquery-plugin/jquery.cookie.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/plugin/jquery-plugin/jquery.form.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/plugin/layui/layui.all.js"></script>
    <!-- my97日期控件 -->
    <script type="text/javascript" src="/manage/assert/plugin/My97DatePicker/WdatePicker.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/plugin/uploadifive/jquery.uploadifive.js"></script>
    <link rel="stylesheet" type="text/css" href="/manage/assert/plugin/uploadifive/uploadifive.css"/>
    <!-- ztree -->
    <script type="text/javascript" src="/manage/assert/plugin/jquery-ztree/js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="/manage/assert/plugin/jquery-ztree/js/jquery.ztree.excheck-3.5.js"></script>
    <script type="text/javascript" src="/manage/assert/plugin/jquery-ztree/js/jquery.ztree.exedit-3.5.js"></script>
    <!--kindEditor插件库 -->
    <script charset="utf-8" src="/manage/assert/plugin/kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="/manage/assert/plugin/kindeditor/lang/zh_CN.js"></script>
    <!-- select2 -->
    <script src="/manage/assert/plugin/select2/js/select2.min.js"></script>
    <!-- inputmask -->
    <script src="/manage/assert/plugin/jquery-plugin/inputmask/min/jquery.inputmask.bundle.min.js"></script>

    <!-- 自己定义的样式和JS扩展 -->
    <!-- acooly -->
    <script src="/manage/assert/script/acooly.js" charset="utf-8"></script>
    <script src="/manage/assert/script/acooly.template.js" charset="utf-8"></script>
    <script src="/manage/assert/script/acooly.format.js" charset="utf-8"></script>
    <script src="/manage/assert/script/acooly.verify.js" charset="utf-8"></script>
    <script src="/manage/assert/script/acooly.verify.js" charset="utf-8"></script>
    <script src="/manage/assert/script/acooly.admin.js" charset="utf-8"></script>
    <script src="/manage/assert/script/acooly.framework.js" charset="utf-8"></script>
    <script src="/manage/assert/script/acooly.easyui.js" charset="utf-8"></script>
    <script src="/manage/assert/script/acooly.layout.js" charset="utf-8"></script>
    <script src="/manage/assert/script/acooly.system.js" charset="utf-8"></script>
    <script src="/manage/assert/script/acooly.portal.js" charset="utf-8"></script>
    <script src="/manage/assert/script/acooly.ui.messager.js" charset="utf-8"></script>
    <script src="/manage/assert/script/acooly.inputmask.js"></script>

    <!-- 扩展script -->
    ${extendScripts}
    <script type="text/javascript">
        var contextPath = '';
        $.acooly.system.init();
    </script>
</head>
<body>
<div id="loading" style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#ecf0f5;text-align :center;padding-top:20%;">
    <h1><span style="font-size: 16px;"><i class="fa fa-refresh fa-spin fa-fw"></i>
<span class="sr-only">Loading...</span>单点登录远程加载中....</span></h1>
</div>
<script>
    var pc;
    //不要放在$(function(){});中
    $.parser.onComplete = function () {
        if (pc) clearTimeout(pc);
        pc = setTimeout(closes, 1000);
    }

    function closes() {
        loadTheme();
        $('#loading').fadeOut('normal', function () {
            $(this).remove();
        });
    }
</script>
