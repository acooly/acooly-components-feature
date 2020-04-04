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

    <!-- layui -->
    <link rel="stylesheet" href="/manage/assert/plugin/layui/css/layui.css">
    <link href="/manage/assert/plugin/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <!-- Theme style -->
    <link rel="stylesheet" href="/manage/assert/plugin/adminlte/css/AdminLTE.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins
     folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="/manage/assert/plugin/adminlte/css/skins/_all-skins.min.css">
    <!-- zTree -->
    <link rel="stylesheet" type="text/css" href="/manage/assert/plugin/jquery-ztree/css/zTreeStyle/zTreeStyle.css"/>
    <!-- icons -->
    <link href="/manage/assert/plugin/icon/Ionicons/4.4.7/ionicons.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/manage/assert/plugin/awesome/4.7.0/css/font-awesome.min.css">

    <!-- Google Font -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">

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

</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <header class="main-header">
        <!-- Logo -->
        <a href="javascript:;" class="logo">
            <!-- mini logo for sidebar mini 50x50 pixels -->
            <span class="logo-mini"><b>B</b>oss</span>
            <!-- logo for regular state and mobile devices -->
            <span class="logo-lg"></span>
        </a>
        <!-- Header Navbar: style can be found in header.less -->
        <nav class="navbar navbar-static-top">
            <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
                <span class="sr-only">Toggle navigation</span>
            </a>
            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
                    <!-- Notifications: style can be found in dropdown.less -->
                    <#--<li class="dropdown notifications-menu">-->
                    <#--<a href="#" class="dropdown-toggle" data-toggle="dropdown">-->
                    <#--<i class="fa fa-bell-o"></i>-->
                    <#--<span class="label label-warning">10</span>-->
                    <#--</a>-->
                    <#--<ul class="dropdown-menu">-->
                    <#--<li class="header">你有10个消息未读</li>-->
                    <#--<li>-->
                    <#--<!-- inner menu: contains the actual data &ndash;&gt;-->
                    <#--<ul class="top-menu">-->
                    <#--<li>-->
                    <#--<a href="#">-->
                    <#--<i class="fa fa-users text-aqua"></i> 5 new members joined today-->
                    <#--</a>-->
                    <#--</li>-->
                    <#--<li>-->
                    <#--<a href="#">-->
                    <#--<i class="fa fa-warning text-yellow"></i> Very long description here that may not fit into the-->
                    <#--page and may cause design problems-->
                    <#--</a>-->
                    <#--</li>-->
                    <#--<li>-->
                    <#--<a href="#">-->
                    <#--<i class="fa fa-users text-red"></i> 5 new members joined-->
                    <#--</a>-->
                    <#--</li>-->
                    <#--<li>-->
                    <#--<a href="#">-->
                    <#--<i class="fa fa-shopping-cart text-green"></i> 25 sales made-->
                    <#--</a>-->
                    <#--</li>-->
                    <#--<li>-->
                    <#--<a href="#">-->
                    <#--<i class="fa fa-user text-red"></i> You changed your username-->
                    <#--</a>-->
                    <#--</li>-->
                    <#--</ul>-->
                    <#--</li>-->
                    <#--<li class="footer"><a href="#">View all</a></li>-->
                    <#--</ul>-->
                    <#--</li>-->

                    <!-- User Account: style can be found in dropdown.less -->
                    <li class="dropdown user user-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <img src="/manage/assert/plugin/adminlte/img/avatar_def.jpg" class="user-image" alt="User Image">
                            <span class="hidden-xs"><@shiroPrincipal/></span>
                        </a>
                        <ul class="dropdown-menu">
                            <!-- User image -->
                            <li class="user-header">
                                <img src="/manage/assert/plugin/adminlte/img/avatar_def.jpg" class="img-circle" alt="User Image">

                                <p>
                                    ${Session.user.username} - ${Session.user.realName}
                                    <small>${roleName}</small>
                                    <small>创建：${Session.user.createTime?string('yyyy-MM-dd')}</small>
                                </p>
                            </li>
                            <!-- Menu Body -->
                            <#--<li class="user-body">-->
                            <#--<div class="row">-->
                            <#--<div class="col-xs-4 text-center">-->
                            <#--<a href="#">Followers</a>-->
                            <#--</div>-->
                            <#--<div class="col-xs-4 text-center">-->
                            <#--<a href="#">Sales</a>-->
                            <#--</div>-->
                            <#--<div class="col-xs-4 text-center">-->
                            <#--<a href="#">Friends</a>-->
                            <#--</div>-->
                            <#--</div>-->
                            <#--<!-- /.row &ndash;&gt;-->
                            <#--</li>-->
                            <!-- Menu Footer-->
                            <li class="user-footer">
                                <div class="pull-left">
                                    <a href="javascript:;" onclick="$.acooly.framework.changePassword();" class="btn btn-default btn-flat">修改密码</a>
                                </div>
                                <div class="pull-right">
                                    <a href="javascript:;" onclick="$.acooly.framework.logout();" class="btn btn-default btn-flat">安全退出</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                    <#if isOnline == false>
                        <li><a href="javascript:;" onclick="$.acooly.admin.tab.addIframe('帮助文档','fa fa-question-circle fa-lg','https://acooly.cn/docs/core.html')"
                               title="帮助" role="button"><i class="fa fa-question-circle fa-lg"></i></a></li>
                    </#if>
                    <li><a href="/manage/index.html?acoolyTheme=easyui" role="button">
                            <i class="fa fa-heart"></i>
                            <span>旧版</span>
                        </a></li>
                    <!-- Control Sidebar Toggle Button -->
                    <li>
                        <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
                    </li>
                </ul>
            </div>
        </nav>
    </header>
    <!-- Left side column. contains the logo and sidebar -->
    <aside class="main-sidebar" id="acooly_admin_menu_container">

        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">
            <!-- Sidebar user panel -->
            <div class="user-panel" style="display: none;">
                <div class="pull-left image">
                    <img src="/manage/assert/plugin/adminlte/img/avatar_def.jpg" class="img-circle" alt="User Image">
                </div>
                <div class="pull-left info">
                    <p><@shiroPrincipal/></p>
                    <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
                </div>
            </div>
            <!-- sidebar menu -->
            <ul class="sidebar-menu main-tree" data-widget="tree">
                <li class="header">
                    功能菜单
                    <span class="pull-right-container">
<#--            <a title="刷新菜单(重新授权后)" href="javascript:;"><i style="margin-top: 0.3em;" class="fa fa-refresh pull-right"></i></a>-->
<#--            <a onclick="$.acooly.admin.headerToggle();" title="最大/小化菜单" href="javascript:;">-->
<#--                <i id="menu-toggle-icon" style="margin-top: 0.3em;" class="fa fa-expand pull-right"></i>-->
<#--            </a>-->
        </span>
                </li>

                <#list menu as e1>
                <#--第一层-->
                    <li class="treeview<#if e1?index==0> menu-open active</#if>">
                        <a href="javascript:;">
                            <#if e1.iconSkin??><i class="fa ${e1.iconSkin}"></i><#else><span class="line-action ${e1.icon}"></span></#if>
                            <span>${e1.name}</span>
                            <span class="pull-right-container"><i class="fa fa-angle-left pull-right"></i></span>
                        </a>
                        <ul class="treeview-menu">
                            <#if e1.children??>
                                <#list e1.children as e2>
                                    <#if e2.children??>
                                        <li class="treeview">
                                            <a href="javascript:;" onclick="$.acooly.layout.accessResource({type:'URL',name:'${e2.name}',value:'${e2.value}',showMode:'${e2.showMode}',icon:'${e2.icon}'})">
                                                <#if e2.iconSkin??><i class="fa ${e2.iconSkin}"></i><#else><span class="line-action ${e2.icon}"></span></#if>
                                                ${e2.name}
                                                <span class="pull-right-container"><i class="fa fa-angle-left pull-right"></i></span>
                                            </a>
                                            <ul class="treeview-menu">
                                                <#list e2.children as e3>
                                                    <li>
                                                        <a href="javascript:;" onclick="$.acooly.layout.accessResource({type:'URL',name:'${e3.name}',value:'${e3.value}',showMode:'${e3.showMode}',icon:'${e3.icon}'})">
                                                            <#if e3.iconSkin??><i class="fa ${e3.iconSkin}"></i><#else><span class="line-action ${e3.icon}"></span></#if>${e3.name}</a>
                                                    </li>
                                                </#list>
                                            </ul>
                                        </li>
                                    <#else>
                                        <li><a href="javascript:;" onclick="$.acooly.layout.accessResource({type:'URL',name:'${e2.name}',value:'${e2.value}',showMode:'${e2.showMode}',icon:'${e2.icon}'})">
                                                <#if e2.iconSkin??><i class="fa ${e2.iconSkin}"></i><#else><span class="line-action ${e2.icon}"></span></#if>${e2.name}
                                            </a></li>
                                    </#if>
                                </#list>
                            </#if>
                        </ul>
                    </li>
                </#list>
            </ul>
        </section>
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
        <!-- Create the tabs -->
        <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
            <li><a href="#control-sidebar-home-tab" data-toggle="tab"><i class="fa fa-book"></i></a></li>
            <#--<li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i class="fa fa-gears"></i></a></li>-->
        </ul>
        <!-- Tab panes -->
        <div class="tab-content">
            <!-- Home tab content -->
            <div class="tab-pane" id="control-sidebar-home-tab">
                <h3 class="control-sidebar-heading">文档中心</h3>
                <ul class="control-sidebar-menu">
                    <li>
                        <a href="http://www.fontawesome.com.cn/faicons/" target="_blank">
                            <i class="sidebar-menu-icon fa fa-fonticons bg-red"></i>
                            <div class="menu-info">
                                <h4 class="control-sidebar-subheading">文字图标库</h4>
                                <p>AdminLte支持的文字图标库查询，如：Font Awesome...</p>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="http://www.jeasyui.com/" target="_blank">
                            <i class="sidebar-menu-icon fa fa-child bg-purple"></i>

                            <div class="menu-info">
                                <h4 class="control-sidebar-subheading">EasyUI</h4>
                                <p>框架目前仍然以EasyUI作为主要功能开发框架，这里可以访问到官方网站和文档</p>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="https://www.layui.com/" target="_blank">
                            <i class="sidebar-menu-icon fa fa-user bg-orange"></i>

                            <div class="menu-info">
                                <h4 class="control-sidebar-subheading">Layui</h4>
                                <p>框架目前也导入了layui，你可以选择采用layui的开发方式</p>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="https://adminlte.io/" target="_blank">
                            <i class="sidebar-menu-icon fa fa-user bg-yellow"></i>

                            <div class="menu-info">
                                <h4 class="control-sidebar-subheading">AdminLte</h4>
                                <p>框架重构了结构，采用了2.4版本的AdminLte,所以你当然可以采用AdminLte方式开发界面</p>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="http://freemarker.foofun.cn/" target="_blank">
                            <i class="sidebar-menu-icon fa fa-file-text-o bg-light-blue"></i>
                            <div class="menu-info">
                                <h4 class="control-sidebar-subheading">Freemarker</h4>
                                <p>Freemarker中文文档，不叫全，不熟悉的可以查下命令</p>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:;" onclick="$.acooly.layout.accessResource({type:'URL',name:'Acooly文档',value:'https://acooly.cn/docs/core.html',
                    showMode:'2',icon:'fa-home'})">
                            <#--<a href="https://acooly.cn/docs/core.html" target="_blank">-->
                            <i class="sidebar-menu-icon fa fa-file-code-o bg-green"></i>
                            <div class="menu-info">
                                <h4 class="control-sidebar-subheading">Acooly官方文档</h4>
                                <p>Acooly框架的官方文档，包括核心框架介绍，从新手入门的引导，大量的组件库，OpenApi框架等</p>
                            </div>
                        </a>
                    </li>
                </ul>
                <!-- /.control-sidebar-menu -->
            </div>
            <!-- /.tab-pane -->
            <!-- Stats tab content -->
            <div class="tab-pane" id="control-sidebar-stats-tab">Stats Tab Content</div>
            <!-- /.tab-pane -->
        </div>
    </aside>
    <!-- /.control-sidebar -->
    <!-- Add the sidebar's background. This div must be placed
         immediately after the control sidebar -->
    <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->

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
        $.acooly.admin.theme.saveTheme($.acooly.admin.theme.acoolyThemeKey, "adminlte");
        $.acooly.system.init();
    });


</script>
</body>
</html>
