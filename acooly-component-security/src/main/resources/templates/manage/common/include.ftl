<!DOCTYPE html>
<html>
<head>
    <title>${Session.securityConfig.title}</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta http-equiv="keywords" content="acooly">
    <meta http-equiv="description" content="spring+jpa+hibernate+easyui+springmvc+jstl/freemarker">
    <meta name="X-CSRF-TOKEN" content="${Request['org.springframework.security.web.csrf.CsrfToken'].token}"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- layui -->
    <link rel="stylesheet" href="/manage/assert/plugin/layui/css/layui.css">
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <!-- Theme style -->
    <link rel="stylesheet" href="/manage/assert/plugin/adminlte/css/AdminLTE.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins
     folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="/manage/assert/plugin/adminlte/css/skins/_all-skins.min.css">
    <!-- zTree -->
    <link rel="stylesheet" type="text/css" href="/manage/assert/plugin/jquery-ztree/css/zTreeStyle/zTreeStyle.css"/>
    <!-- icons -->
    <link href="https://cdn.bootcss.com/ionicons/4.4.8/css/ionicons.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/manage/assert/plugin/awesome/4.7.0/css/font-awesome.min.css">
    <#--<link rel="stylesheet" href="/manage/assert/plugin/Ionicons/css/ionicons.min.css">-->

    <!-- AdminLte 扩展 -->
    <!-- jvectormap: 地图 -->
    <link href="https://cdn.bootcss.com/jvectormap/2.0.4/jquery-jvectormap.min.css" rel="stylesheet">
    <#--<link rel="stylesheet" href="/manage/assert/plugin/jvectormap/jquery-jvectormap.css">-->
    <!-- Date Picker 日期-->
    <link href="https://cdn.bootcss.com/bootstrap-datepicker/1.8.0/css/bootstrap-datepicker.min.css" rel="stylesheet">
    <#--<link rel="stylesheet" href="/manage/assert/plugin/bootstrap-datepicker/css/bootstrap-datepicker.min.css">-->
    <!-- Daterange picker 日期段 -->
    <link href="https://cdn.bootcss.com/bootstrap-daterangepicker/2.1.27/daterangepicker.min.css" rel="stylesheet">
    <#--<link rel="stylesheet" href="/manage/assert/plugin/bootstrap-daterangepicker/daterangepicker.css">-->
    <!-- bootstrap wysihtml5 - text editor -->
    <link rel="stylesheet" href="/manage/assert/plugin/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">
    <!-- Google Font -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">

    <!-- easyui及自定义 -->
    <link id="easyuiTheme" rel="stylesheet" href="/manage/assert/plugin/jquery-easyui/themes/acooly4/easyui.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="/manage/assert/style/icon.css">
    <link rel="stylesheet" href="/manage/assert/plugin/jquery-easyui/themes/icon.css" type="text/css"/>
    <link id="easyuiThemeBasic" rel="stylesheet" href="/manage/assert/plugin/jquery-easyui/themes/acooly4/basic_new.css">
    <!-- 扩展css -->
    ${extendStyles}

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/jqueryui/1.11.4/jquery-ui.min.js"></script>
    <!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
    <script>
        $.widget.bridge('uibutton', $.ui.button);
    </script>
    <script src="https://cdn.bootcss.com/twitter-bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <!-- Sparkline 图表 -->
    <script src="https://cdn.bootcss.com/jquery-sparklines/2.1.2/jquery.sparkline.min.js"></script>
    <!-- jvectormap -->
    <script src="https://cdn.bootcss.com/jvectormap/2.0.4/jquery-jvectormap.min.js"></script>
    <!-- 矢量图 -->
    <script src="https://cdn.bootcss.com/raphael/2.2.0/raphael.min.js"></script>
    <!-- jQuery Knob Chart -->
    <script src="https://cdn.bootcss.com/jQuery-Knob/1.2.12/jquery.knob.min.js"></script>
    <!-- daterangepicker -->
    <script src="https://cdn.bootcss.com/bootstrap-daterangepicker/2.1.27/moment.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap-daterangepicker/2.1.27/daterangepicker.min.js"></script>
    <!-- datepicker -->
    <script src="https://cdn.bootcss.com/bootstrap-datepicker/1.8.0/js/bootstrap-datepicker.min.js"></script>
    <!-- Bootstrap WYSIHTML5 -->
    <script src="/manage/assert/plugin/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
    <!-- Slimscroll -->
    <script src="https://cdn.bootcss.com/jQuery-slimScroll/1.3.8/jquery.slimscroll.min.js"></script>
    <#--<script src="/manage/assert/plugin/jquery-slimscroll/jquery.slimscroll.min.js"></script>-->
    <!-- FastClick -->
    <script src="https://cdn.bootcss.com/fastclick/1.0.6/fastclick.min.js"></script>
    <#--<script src="/manage/assert/plugin/fastclick/lib/fastclick.js"></script>-->
    <!-- AdminLTE App -->
    <script src="/manage/assert/plugin/adminlte/js/adminlte.js"></script>
    <!-- 模板引擎：baidu -->
    <script src="/manage/assert/plugin/template/baiduTemplate.js"></script>

    <!-- AdminLTE dashboard demo (This is only for demo purposes) -->
    <#--<script src="/manage/assert/plugin/adminlte/js/pages/dashboard.js"></script>-->
    <!-- AdminLTE for demo purposes -->
    <script src="/manage/assert/plugin/adminlte/js/demo.js"></script>
    <script src="https://cdn.bootcss.com/javascript-detect-element-resize/0.5.3/jquery.resize.min.js"></script>


    <#--<script type="text/javascript" src="//cdn.bootcss.com/jquery/1.9.1/jquery.min.js" charset="utf-8"></script>-->
    <script type="text/javascript" src="//cdn.staticfile.org/jquery-migrate/1.1.0/jquery-migrate-1.1.0.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/plugin/jquery-easyui/jquery.easyui.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/plugin/jquery-easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/plugin/jquery-easyui/plugins/datagrid-detailview.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/plugin/jquery-easyui/plugins/datagrid-groupview.js" charset="utf-8"></script>
    <!-- easyui portal插件 -->
    <link rel="stylesheet" href="/manage/assert/plugin/jquery-easyui-portal/portal.css" type="text/css"/>
    <script type="text/javascript" src="/manage/assert/plugin/jquery-easyui-portal/jquery.portal.js" charset="utf-8"></script>
    <script type="text/javascript" src="//cdn.bootcss.com/jquery-cookie/1.0/jquery.cookie.js" charset="utf-8"></script>
    <script type="text/javascript" src="//cdn.bootcss.com/jquery.form/3.32.0-2013.04.09/jquery.form.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/plugin/jquery-easyui/plugins/easyui.statistics.js" charset="utf-8"></script>
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
    <script src="/manage/assert/script/acooly.ui.layer.js" charset="utf-8"></script>
    <!-- 扩展script -->
    ${extendScripts}
    <script type="text/javascript">
        var contextPath = '';
        $(function () {
            $.acooly.system.init();
        });
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





