/**
 * 后台管理（基于adminlte）
 */
(function ($) {
    var adminClass = {

        /**
         * 初始化
         */
        init: function () {
            this.initMenus();
            this.initTab();

            // 新旧版本的风格(访问到旧版页面，则设置版本cookies)
            $.acooly.admin.theme.saveTheme($.acooly.admin.theme.acoolyThemeKey, "adminlte")
        },


        /**
         * 初始化左侧功能菜单
         */
        initMenus: function () {
            $.ajax({
                url: '/manage/system/authorisedMenus.html',
                success: function (result) {
                    var data = {resources: result};
                    $.acooly.template.render("acooly_admin_menu_container", "acooly_admin_menu_template", data);


                    // 注册点击主菜单（.treeview）的选中效果(.active)
                    $(document).on("click", '.treeview', function (e) {
                        $(".sidebar-menu .treeview").removeClass("active");
                        $(this).addClass("active");
                    });
                    $(document).on("click", '.sidebar-menu ul li', function (e) {
                        $(".sidebar-menu li").removeClass("active");
                        $(this).addClass("active");
                        if ($(this).parent() && $(this).parent().parent()) {
                            $(this).parent().parent().addClass("active");
                        }
                    });

                    // fix bug: Dynamic rendering menu cannot be expanded.
                    var ua = navigator.userAgent.toLocaleLowerCase();
                    if (ua.match(/chrome/) != null && ua.match(/edge/) == null) {
                        return;
                    }

                    $(document).on("click", ".sidebar-menu li a", function (e) {
                        var firstParent = $(this).parent("li");
                        var firstChildUl = $(this).next("ul");
                        if (firstParent.hasClass("menu-open")) {
                            firstParent.removeClass("menu-open");
                            firstChildUl.hide();
                        } else {
                            firstParent.addClass("menu-open");
                            firstChildUl.show();
                        }
                    });
                }
            });
        },
        /**
         * 初始化中间内容区（基于easyui，保持兼容）
         */
        initTab: function () {
            $('#layout_center_tabsMenu').menu({
                onClick: function (item) {
                    var curTabTitle = $(this).data('tabTitle');
                    var type = $(item.target).attr('type');

                    if (type === 'refresh') {
                        $('#layout_center_tabs').tabs('getTab', curTabTitle).panel('refresh');
                        return;
                    }

                    if (type === 'close') {
                        var t = $('#layout_center_tabs').tabs('getTab', curTabTitle);
                        if (t.panel('options').closable) {
                            $('#layout_center_tabs').tabs('close', curTabTitle);
                        }
                        return;
                    }

                    var allTabs = $('#layout_center_tabs').tabs('tabs');
                    var closeTabsTitle = [];

                    $.each(allTabs, function () {
                        var opt = $(this).panel('options');
                        if (opt.closable && opt.title != curTabTitle && type === 'closeOther') {
                            closeTabsTitle.push(opt.title);
                        } else if (opt.closable && type === 'closeAll') {
                            closeTabsTitle.push(opt.title);
                        }
                    });

                    for (var i = 0; i < closeTabsTitle.length; i++) {
                        $('#layout_center_tabs').tabs('close', closeTabsTitle[i]);
                    }
                }
            });
            $('#layout_center_tabs').tabs({
                fit: true,
                border: false,
                onContextMenu: function (e, title) {
                    e.preventDefault();
                    $('#layout_center_tabsMenu').menu('show', {
                        left: e.pageX,
                        top: e.pageY
                    }).data('tabTitle', title);
                },
                tools: [{
                    text: '<i class="fa fa-refresh"></i>',
                    handler: function () {
                        var href = $('#layout_center_tabs').tabs('getSelected').panel('options').href;
                        if (href) {/*说明tab是以href方式引入的目标页面*/
                            var index = $('#layout_center_tabs').tabs('getTabIndex', $('#layout_center_tabs').tabs('getSelected'));
                            $('#layout_center_tabs').tabs('getTab', index).panel('refresh');
                        } else {/*说明tab是以content方式引入的目标页面*/
                            var panel = $('#layout_center_tabs').tabs('getSelected').panel('panel');
                            var frame = panel.find('iframe');
                            try {
                                if (frame.length > 0) {
                                    for (var i = 0; i < frame.length; i++) {
                                        //跨域情况会报错，注释掉
                                        //frame[i].contentWindow.document.write('');
                                        //frame[i].contentWindow.close();
                                        frame[i].src = frame[i].src;
                                    }
                                    if ($.browser.msie) {
                                        CollectGarbage();
                                    }
                                }
                            } catch (e) {
                            }
                        }
                    }
                }, {
                    text: '<i class="fa fa-close"></i>',
                    handler: function () {
                        var index = $('#layout_center_tabs').tabs('getTabIndex', $('#layout_center_tabs').tabs('getSelected'));
                        if (index <= 0) {
                            return;
                        }
                        var tab = $('#layout_center_tabs').tabs('getTab', index);
                        if (tab.panel('options').closable) {
                            $('#layout_center_tabs').tabs('close', index);
                        } else {
                            $.messager.alert('提示', '[' + tab.panel('options').title + ']不可以被关闭', 'error');
                        }
                    }
                }]
            });

            // 通过jquery-resize插件实现：父节点大小改变后，resize tabs
            $('.content-wrapper').resize(function () {
                $('#layout_center_tabs').tabs({
                    width: $("#_tabs").parent().width(),
                    height: "auto"
                }).tabs('resize');
            });
        },
    };

    var themeClass = {

        defaultExpires: 7,
        acoolyThemeKey: "acoolyTheme",

        getTheme: function (key) {
            if (!key) {
                key = this.acoolyThemeKey;
            }
            return $.cookie(key)
        },

        /**
         * 保持主题
         *
         * 可选：easyui,adminlte
         * @param theme
         */
        saveTheme: function (key, value) {
            if (!key) {
                key = this.acoolyThemeKey;
            }
            $.cookie(key, value, {
                expires: this.defaultExpires
            });
        }
    }


    if (!$.acooly) {
        $.acooly = {};
    }

    $.extend($.acooly, {
        admin: adminClass
    });

    $.extend($.acooly.admin, {
        theme: themeClass
    })
})(jQuery);
