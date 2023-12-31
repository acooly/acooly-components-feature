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
    <link href="/manage/assert/plugin/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <!-- Theme style -->
    <link rel="stylesheet" href="/manage/assert/plugin/adminlte/css/AdminLTE.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins
     folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="/manage/assert/plugin/adminlte/css/skins/_all-skins.min.css">
    <!-- zTree -->
    <link rel="stylesheet" href="/manage/assert/plugin/jquery-ztree/css/zTreeStyle/bootstrapStyle.css"/>
    <!-- icons -->
    <link href="/manage/assert/plugin/icon/Ionicons/4.4.7/ionicons.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/manage/assert/plugin/awesome/4.7.0/css/font-awesome.min.css">
    <#--    <link rel="stylesheet" href="/manage/assert/plugin/Ionicons/css/ionicons.min.css">-->

    <!-- Google Font -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">

    <!-- easyui及自定义 -->
    <link id="easyuiTheme" rel="stylesheet" href="/manage/assert/plugin/jquery-easyui/themes/acooly/easyui.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="/manage/assert/style/icon.css">
    <link rel="stylesheet" href="/manage/assert/plugin/jquery-easyui/themes/icon.css" type="text/css"/>
    <link id="easyuiThemeBasic" rel="stylesheet" href="/manage/assert/plugin/jquery-easyui/themes/acooly/basic.css">
    <!-- videoJs -->
    <link rel="stylesheet" href="/manage/assert/plugin/jquery-plugin/videojs/video-js.css" type="text/css"/>

    <!-- 扩展css -->
    ${extendStyles}

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->


    <script src="/manage/assert/plugin/jquery/jquery-3.3.1.min.js"></script>
    <script src="/manage/assert/plugin/jquery/jquery-ui-1.11.4.min.js"></script>
    <!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
    <script>
        $.widget.bridge('uibutton', $.ui.button);
    </script>
    <script src="/manage/assert/plugin/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!-- Slimscroll -->
    <script src="/manage/assert/plugin/jquery-plugin/jquery.slimscroll-1.3.8.min.js"></script>
    <!-- FastClick -->
    <script src="/manage/assert/plugin/jquery-plugin/fastclick-1.0.6.min.js"></script>
    <!-- AdminLTE App -->
    <script src="/manage/assert/plugin/adminlte/js/adminlte.js"></script>
    <!-- 模板引擎：baidu -->
    <script src="/manage/assert/plugin/template/baiduTemplate.js"></script>
    <!-- AdminLTE for -->
    <script src="/manage/assert/plugin/adminlte/js/demo.js"></script>
    <script src="/manage/assert/plugin/jquery-plugin/jquery.resize.js"></script>
    <script type="text/javascript" src="/manage/assert/plugin/jquery/jquery-migrate-3.1.0.min.js" charset="utf-8"></script>
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
    <!-- acooly -->
    <script src="/manage/assert/script/acooly.min.js" charset="utf-8"></script>
    <!-- 扩展script -->
    ${extendScripts}
    <script type="text/javascript">
        var contextPath = '';
        $.acooly.system.init();
    </script>
</head>

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
        $('#loading').fadeOut('normal', function () {
            $(this).remove();
        });
    }
</script>
