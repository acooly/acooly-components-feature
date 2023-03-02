<head>
    <title>${securityConfig.title}</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta http-equiv="cache-control" content="max-age=86400">
    <meta http-equiv="expires" content="1440">
    <meta http-equiv="keywords" content="acooly">
    <meta http-equiv="description" content="spring+jpa+hibernate+easyui+springmvc+jstl/freemarker">
    <meta name="X-CSRF-TOKEN" content="${Request['org.springframework.security.web.csrf.CsrfToken'].token}"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

    <#if securityConfig.plugin.layui>
        <!-- layui -->
        <link rel="stylesheet" href="/manage/assert/plugin/layui/css/layui.css">
    </#if>
    <!-- zTree -->
    <link rel="stylesheet" href="/manage/assert/plugin/jquery-ztree/css/zTreeStyle/zTreeStyle_bootstrip.min.css"/>
    <!-- icons -->
    <link rel="stylesheet" href="/manage/assert/plugin/awesome/4.7.0/css/font-awesome.min.css">
    <!-- adminlte -->
    <link rel="stylesheet" href="/manage/assert/plugin/adminlte3/css/adminlte.min.css">
    <!-- Select2 -->
    <link rel="stylesheet" href="/manage/assert/plugin/select2/css/select2.min.css">
    <!-- easyui及自定义 -->
    <link rel="stylesheet" href="/manage/assert/plugin/jquery-easyui/themes/acooly/easyui.min.css" id="easyuiTheme" type="text/css"/>
    <link rel="stylesheet" href="/manage/assert/plugin/jquery-easyui/themes/acooly/basic.min.css" id="easyuiThemeBasic">
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

        body, .l-btn-text,.panel-body, .tableForm, .editForm {
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
        .tree-title { font-size: ${securityConfig.fontSize}px;}
        .layout-fixed .brand-link { width: ${securityConfig.mainSidebarWidth}px;}
        .ac-logo-title { font-size: <#if securityConfig.fontSize lt 14>18<#else>20</#if>px;}
    </style>

    <script src="/manage/assert/plugin/jquery/3.4.1/jquery.min.js"></script>
    <script src="/manage/assert/plugin/jquery/jquery-migrate-3.1.0.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="/manage/assert/plugin/bootstrap/js/bootstrap.bundle.min.js"></script>
    <!-- AdminLTE -->
    <script src="/manage/assert/plugin/adminlte3/js/adminlte.min.js"></script>
    <script src="/manage/assert/plugin/adminlte3/js/adminlte_acooly.min.js"></script>
    <!-- select2 -->
    <script src="/manage/assert/plugin/select2/js/select2.min.js"></script>
    <!-- Jquery plugins  -->
    <script src="/manage/assert/plugin/template/baiduTemplate.js"></script>
    <script src="/manage/assert/plugin/jquery-plugin/jquery.form.cookie.resize.min.js" type="text/javascript" charset="utf-8"></script>
    <#if securityConfig.plugin.clipboard>
        <script src="/manage/assert/plugin/jquery-plugin/clipboard.min.js"></script>
    </#if>
    <#if securityConfig.plugin.media>
        <script src="/manage/assert/plugin/jquery-plugin/jquery.media.js"></script>
    </#if>
    <#if securityConfig.plugin.xss>
        <script src="/manage/assert/plugin/jquery-plugin/xss.min.js" type="text/javascript" charset="utf-8"></script>
    </#if>
    <!-- easyui -->
    <script type="text/javascript" src="/manage/assert/plugin/jquery-easyui/jquery.easyui.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="/manage/assert/plugin/jquery-easyui-portal/jquery.portal.min.js" charset="utf-8"></script>
    <#if securityConfig.plugin.easyuiExtension>
        <script type="text/javascript" src="/manage/assert/plugin/jquery-easyui/plugins/datagrid-extensions.min.js" charset="utf-8"></script>
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
    <script src="/manage/assert/script/acooly.min.js" charset="utf-8"></script>
    <!-- 扩展script -->
    ${extendScripts}
    <script type="text/javascript">
        var contextPath = '';
        $.acooly.system.init();
    </script>
</head>
<div id="loading" style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#ecf0f5;text-align :center;padding-top:20%;">
    <h1><span style="font-size: 16px;"><i class="fa fa-refresh fa-spin fa-fw"></i><span class="sr-only">Loading...</span>单点登录远程加载中....</span></h1>
</div>
<script>
    var pc;
    $.parser.onComplete = function () {
        if (pc) clearTimeout(pc);
        pc = setTimeout(function () {
            $.acooly.admin.theme.loadTheme();
            $('#loading').fadeOut('normal', function () {
                $(this).remove();
            });
        }, 500);
    }
</script>
