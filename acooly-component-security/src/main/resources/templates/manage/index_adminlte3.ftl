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
    <#if Session.securityConfig.icon??><link rel="shortcut icon" href="${Session.securityConfig.icon}" /></#if>

    <!-- layui -->
    <link rel="stylesheet" href="/manage/assert/plugin/layui/css/layui.css">
    <!-- zTree -->
    <link rel="stylesheet" href="/manage/assert/plugin/jquery-ztree/css/zTreeStyle/bootstrapStyle.css"/>
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
    <!-- easyui及自定义 -->
    <link id="easyuiTheme" rel="stylesheet" href="/manage/assert/plugin/jquery-easyui/themes/acooly/easyui.css" type="text/css"/>
    <link id="easyuiThemeBasic" rel="stylesheet" href="/manage/assert/plugin/jquery-easyui/themes/acooly/basic.css">
    <link rel="stylesheet" type="text/css" href="/manage/assert/style/icon.css">
    <link rel="stylesheet" href="/manage/assert/plugin/jquery-easyui/themes/icon.css" type="text/css"/>
    <!-- videoJs -->
    <link rel="stylesheet" href="/manage/assert/plugin/jquery-plugin/videojs/video-js.css" type="text/css"/>
    <!-- daterangepicker -->
    <link rel="stylesheet" href="/manage/assert/plugin/daterangepicker/daterangepicker.css" type="text/css"/>
    <!-- 扩展css -->
    ${extendStyles}

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body class="hold-transition sidebar-mini layout-fixed">
<div class="wrapper">

    <!-- Navbar -->
    <nav class="main-header navbar navbar-expand navbar-white navbar-light">
        <!-- Left navbar links -->
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fa fa-bars"></i></a>
            </li>
        </ul>
        <!-- Right navbar links -->
        <ul class="navbar-nav ml-auto">
            <li class="nav-item dropdown user-menu">
                <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown">
                    <i class="fa fa-user-circle-o fa-lg" aria-hidden="true"></i> <span class="d-none d-md-inline"><@shiroPrincipal/></span>
                </a>
                <ul class="dropdown-menu dropdown-menu-lg dropdown-menu-right">
                    <!-- User image -->
                    <li class="user-header bg-primary">
                        <span style="font-size: 60px;"><i class="fa fa-user-circle-o fa-lg" aria-hidden="true"></i></span>
                        <p>
                            ${Session.user.username} - ${Session.user.realName}
                        <div style="font-size:14px;">${roleName}</div>
                        </p>
                    </li>
                    <!-- Menu Footer-->
                    <li class="user-footer">
                        <a href="javascript:;" style="font-size:14px;" onclick="$.acooly.framework.changePassword();" class="btn btn-default btn-flat">修改密码</a>
                        <a href="javascript:;" style="font-size:14px;" onclick="$.acooly.framework.logout();" class="btn btn-default btn-flat float-right">安全退出</a>
                    </li>
                </ul>
            </li>
            <!-- choose theme -->
            <li class="nav-item dropdown">
                <a class="nav-link" data-toggle="dropdown" href="#">
                    <i class="fa fa-cube fa-lg"></i>
                </a>
                <div class="dropdown-menu dropdown-menu-lg dropdown-menu-right" style="font-size: 14px;">
                    <div class="dropdown-divider"></div>
                    <a href="/manage/index.html?acoolyTheme=acooly" class="dropdown-item<#if acoolyTheme='acooly'> active</#if>">
                        <i class="fa fa-pie-chart fa-lg mr-2"></i> 创新素雅
                        <span class="float-right text-sm">AdminLte3</span>
                    </a>
                    <a href="/manage/index.html?acoolyTheme=paiggio" class="dropdown-item<#if acoolyTheme='paiggio'> active</#if>">
                        <i class="fa fa-product-hunt fa-lg mr-2"></i> 蓝色天空
                        <span class="float-right text-sm">Paiggio</span>
                    </a>
                    <div class="dropdown-divider"></div>
                    <a href="/manage/index.html?acoolyTheme=easyui" class="dropdown-item">
                        <i class="fa fa-bandcamp fa-lg mr-2"></i> 原滋原味
                        <span class="float-right text-sm">EasyUI1.9</span>
                    </a>
                    <div class="dropdown-divider"></div>
                </div>
            </li>
            <!-- 帮组文档按钮 -->
            <#if isOnline == false>
                <li class="nav-item">
                    <a class="nav-link" href="https://acooly.cn/docs/core.html" target="_blank" title="帮助" role="button"><i class="fa fa-question-circle fa-lg"></i></a>
                </li>
            </#if>
            <li class="nav-item">
                <a class="nav-link" data-widget="control-sidebar" data-slide="true" href="#" role="button"><i class="fa fa-th-large fa-lg"></i></a>
            </li>
        </ul>
    </nav>
    <!-- /.navbar -->

    <!-- Main Sidebar Container sidebar-blue-primary -->
    <aside class="main-sidebar sidebar-<#if acoolyTheme == 'acooly'>dark<#else>blue</#if>-primary elevation-5">
        <!-- Brand Logo -->
        <a href="javascript:;" class="logo brand-link" style="display: none;">
            <span><img id="logo_image" alt="AdminLTE Logo" class="brand-image img-circle elevation-3" style="opacity: .8;width: 33px;"></span>
            <span id="logo_title" class="ac-logo-title brand-text font-weight-light"></span>
        </a>

        <!-- Sidebar -->
        <div class="sidebar">
            <!-- Sidebar user panel (optional) -->
            <#--            <div class="user-panel mt-3 pb-3 mb-3 d-flex">-->
            <#--                <div class="image">-->
            <#--                    <img src="/manage/assert/plugin/adminlte3/img/user2-160x160.jpg" class="img-circle elevation-2" alt="User Image">-->
            <#--                </div>-->
            <#--                <div class="info">-->
            <#--                    <a href="#" class="d-block"><@shiroPrincipal/></a>-->
            <#--                </div>-->
            <#--            </div>-->

            <!-- Sidebar Menu -->
            <nav class="mt-2">
                <ul class="nav nav-pills nav-sidebar flex-column nav-child-indent" data-widget="treeview" role="menu" data-accordion="false">
                    <!-- Add icons to the links using the .nav-icon class
                         with font-awesome or any other icon font library -->
                    <li class="nav-header">功能菜单</li>
                    <#list menu as e1>
                    <#--第一层-->
                        <li class="nav-item has-treeview<#if e1?index==0> menu-open</#if>">
                            <a href="#" class="nav-link<#if e1?index==0> active</#if>" style="padding-left: 0.5rem;">
                                <#if e1.iconSkin??><i class="nav-icon fa ${e1.iconSkin}"></i><#else><span class="line-action ${e1.icon}"></span></#if>
                                <p>
                                    ${e1.name}
                                    <i class="right fa fa-angle-left"></i>
                                </p>
                            </a>
                            <ul class="nav nav-treeview">
                                <#if e1.children??>
                                    <#list e1.children as e2>
                                        <#if e2.children?? && (e2.children?size > 0)>
                                            <li class="nav-item has-treeview">
                                                <a href="#" class="nav-link" style="padding-left: 0.5rem;">
                                                    <#if e2.iconSkin??><i class="nav-icon fa ${e2.iconSkin}"></i><#else><span class="line-action ${e2.icon}"></span></#if>
                                                    <p>
                                                        ${e2.name}
                                                        <i class="right fa fa-angle-left"></i>
                                                    </p>
                                                </a>
                                                <ul class="nav nav-treeview">
                                                    <#list e2.children as e3>
                                                        <li class="nav-item">
                                                            <a href="javascript:;" onclick="$.acooly.layout.accessResource({type:'URL',name:'${e3.name}',value:'${e3.value}',showMode:'${e3.showMode}',icon:'${e3.icon}'})" class="nav-link" style="padding-left: 0.5rem;">
                                                                <#if e3.iconSkin??><i class="nav-icon fa ${e3.iconSkin}"></i><#else><span class="line-action ${e3.icon}"></span></#if><p>${e3.name}</p></a>
                                                        </li>
                                                    </#list>
                                                </ul>
                                            </li>
                                        <#else>
                                            <li class="nav-item">
                                                <a href="javascript:;" onclick="$.acooly.layout.accessResource({type:'URL',name:'${e2.name}',value:'${e2.value}',showMode:'${e2.showMode}',icon:'${e2.icon}'})" class="nav-link" style="padding-left: 0.5rem;">
                                                    <#if e2.iconSkin??><i class="nav-icon fa ${e2.iconSkin}"></i><#else><span class="line-action ${e2.icon}"></span></#if>
                                                    <p>${e2.name}</p>
                                                </a>
                                            </li>
                                        </#if>
                                    </#list>
                                </#if>
                            </ul>
                        </li>
                    </#list>
                </ul>
            </nav>
            <!-- /.sidebar-menu -->
        </div>
        <!-- /.sidebar -->
    </aside>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <div id="layout_center_tabs" style="overflow: hidden;background-color: #ecf0f5;" data-options="tabHeight:32">
            <div title="首页" data-options="href:'/manage/layout/portal.html'"></div>
        </div>
        <div id="layout_center_tabsMenu" style="width: 120px;display:none;">
            <div type="refresh">刷新</div>
            <div class="menu-sep"></div>
            <div type="close">关闭</div>
            <div type="closeOther">关闭其他</div>
            <div type="closeAll">关闭所有</div>
        </div>
    </div>
    <!-- /.content-wrapper -->

    <!-- Control Sidebar -->
    <aside class="control-sidebar control-sidebar-dark">

        <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
            <li class="active"><a href="#control-sidebar-options-tab" data-toggle="tab" aria-expanded="true"><i class="fa fa-wrench"></i></a></li>
            <li class=""><a href="#control-sidebar-devdocs-tab" data-toggle="tab" aria-expanded="false"><i class="fa fa-book"></i></a></li>
        </ul>

        <div class="tab-content control-sidebar-content">
            <div class="tab-pane control-sidebar-options-tab active" id="control-sidebar-options-tab"></div>
            <#if isOnline == false>
                <!-- docs tab content -->
                <div class="tab-pane" id="control-sidebar-devdocs-tab">
                    <h3 class="control-sidebar-heading">开发文档</h3>
                    <ul class="control-sidebar-menu">
                        <li>
                            <a href="http://www.fontawesome.com.cn/faicons/" target="_blank">
                                <i class="sidebar-menu-icon fa fa-font-awesome bg-gradient-green"></i>
                                <div class="menu-info">
                                    <h4 class="control-sidebar-subheading">Awesome图标库</h4>
                                    <p>推荐使用的文字图标库查询</p>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="https://ionicons.com/v2/" target="_blank">
                                <i class="sidebar-menu-icon ico ion-ionic bg-gradient-lightblue"></i>
                                <div class="menu-info">
                                    <h4 class="control-sidebar-subheading">Ionicons图标库</h4>
                                    <p>推荐使用的文字图标库查询</p>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="http://www.jeasyui.com/" target="_blank">
                                <i class="sidebar-menu-icon fa fa-etsy bg-purple"></i>

                                <div class="menu-info">
                                    <h4 class="control-sidebar-subheading">EasyUI文档</h4>
                                    <p>EasyUI升级到1.9了，新的控件</p>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="https://acooly.cn/resource/AdminLTE-3.0.4/" target="_blank">
                                <i class="sidebar-menu-icon fa fa-user bg-yellow"></i>

                                <div class="menu-info">
                                    <h4 class="control-sidebar-subheading">AdminLte3</h4>
                                    <p>升级为V3，可用其漂亮组件</p>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="http://freemarker.foofun.cn/" target="_blank">
                                <i class="sidebar-menu-icon fa fa-file-code-o bg-gradient-blue"></i>
                                <div class="menu-info">
                                    <h4 class="control-sidebar-subheading">Freemarker文档</h4>
                                    <p>Freemarker中文文档，方便查询</p>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="https://acooly.cn/docs/core.html" target="_blank">
                                <i class="sidebar-menu-icon fa fa-book bg-gradient-danger"></i>
                                <div class="menu-info">
                                    <h4 class="control-sidebar-subheading">Acooly官方文档</h4>
                                    <p>Acooly框架所有的文档</p>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="https://bantikyan.github.io/icheck-bootstrap/" target="_blank">
                                <i class="sidebar-menu-icon fa fa-file-code-o bg-gradient-blue"></i>
                                <div class="menu-info">
                                    <h4 class="control-sidebar-subheading">复选框icheck-bootstrap</h4>
                                    <p>icheck-bootstrap的演示和文档</p>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="https://select2.org/" target="_blank">
                                <i class="sidebar-menu-icon fa fa-list bg-gradient-blue"></i>
                                <div class="menu-info">
                                    <h4 class="control-sidebar-subheading">下拉框Select2</h4>
                                    <p>Select2官方文档</p>
                                </div>
                            </a>
                        </li>

                    </ul>
                    <!-- /.control-sidebar-menu -->
                </div>
                <!-- / docs tab content -->
            </#if>
        </div>

    </aside>
    <!-- /.control-sidebar -->
</div>
<!-- ./wrapper -->


<script src="/manage/assert/plugin/jquery/3.4.1/jquery.min.js"></script>
<script src="/manage/assert/plugin/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- pace-progress -->
<script src="/manage/assert/plugin/pace-progress/pace.min.js"></script>
<!-- Slimscroll -->
<script src="/manage/assert/plugin/jquery-plugin/jquery.slimscroll-1.3.8.min.js"></script>
<!-- FastClick -->
<script src="/manage/assert/plugin/jquery-plugin/fastclick-1.0.6.min.js"></script>
<!-- AdminLTE App -->
<script src="/manage/assert/plugin/adminlte3/js/adminlte.js"></script>
<!-- 模板引擎：baidu -->
<script src="/manage/assert/plugin/template/baiduTemplate.js"></script>
<!-- AdminLTE for -->
<script src="/manage/assert/plugin/adminlte3/js/adminlte_acooly.js"></script>
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
<!-- select2 -->
<script src="/manage/assert/plugin/select2/js/select2.min.js"></script>
<!-- ztree -->
<script type="text/javascript" src="/manage/assert/plugin/jquery-ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="/manage/assert/plugin/jquery-ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="/manage/assert/plugin/jquery-ztree/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="/manage/assert/plugin/jquery-ztree/js/jquery.ztree.exhide-3.5.js"></script>
<!-- select2ztree -->
<script type="text/javascript" src="/manage/assert/plugin/jquery-plugin/select2ztree/jquery.select2ztree.search.js"></script>
<script type="text/javascript" src="/manage/assert/plugin/jquery-plugin/select2ztree/jquery.select2ztree.js"></script>
<!--kindEditor插件库 -->
<link rel="stylesheet" type="text/css" href="/manage/assert/plugin/kindeditor/plugins/multi_image/diyUpload/css/webuploader.css">
<link rel="stylesheet" type="text/css" href="/manage/assert/plugin/kindeditor/plugins/multi_image/diyUpload/css/diyUpload.css">
<script charset="utf-8" src="/manage/assert/plugin/kindeditor/plugins/multi_image/diyUpload/js/webuploader.html5only.min.js"></script>
<script charset="utf-8" src="/manage/assert/plugin/kindeditor/plugins/multi_image/diyUpload/js/diyUpload.js"></script>
<script charset="utf-8" src="/manage/assert/plugin/kindeditor/htmlminifier.min.js"></script>
<script charset="utf-8" src="/manage/assert/plugin/kindeditor/kindeditor-all.js"></script>
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
<!-- daterangepicker -->
<script src="/manage/assert/plugin/daterangepicker/moment.min.js"></script>
<script src="/manage/assert/plugin/daterangepicker/daterangepicker.js"></script>
<!-- js xss -->
<script src="/manage/assert/plugin/jquery-plugin/xss.min.js"></script>

<!-- 自己定义的样式和JS扩展 -->
<!-- acooly -->
<script src="/manage/assert/script/acooly.js" charset="utf-8"></script>
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

<!-- 扩展script -->
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
