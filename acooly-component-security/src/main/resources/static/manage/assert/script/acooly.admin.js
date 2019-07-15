/**
 * 后台管理（基于adminlte）
 */
(function ($) {
    var adminClass = {

        /**
         * 初始化
         */
        init: function () {
            this.initLogo();
            this.initMenus();
            this.initTab();
            // 新旧版本的风格(访问到旧版页面，则设置版本cookies)
            $.acooly.admin.theme.saveTheme($.acooly.admin.theme.acoolyThemeKey, "adminlte")
        },

        /**
         * 初始化LOGO
         */
        initLogo: function () {
            if ($.acooly.system.config.logo) {
                $('.logo-lg').html("<img src='" + $.acooly.system.config.logo + "' width='200'>")
            } else {
                if ($.acooly.system.config.title) {
                    $('.logo-lg').text($.acooly.system.config.title);
                } else {
                    $('.logo-lg').text("<b>Accoly</b> Sys V4.x");
                }
            }
            if ($.acooly.system.config.shorttitle) {
                $('.logo-mini').html($.acooly.system.config.shorttitle);
            }
        },

        /**
         * 初始化左侧功能菜单
         */
        initMenus: function () {

            // 注册点击主菜单（.treeview）的选中效果(.active)
            $(document).on("click", '.sidebar-menu ul li', function (e) {
                $(".sidebar-menu li").removeClass("active");
                $(this).addClass("active");
                if ($(this).parent() && $(this).parent().parent()) {
                    $(this).parent().parent().addClass("active");
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
                tools: [
                    {
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
                },
                    {
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

        /**
         * 顶部隐藏和显示切换
         */
        headerToggle: function () {

            var header = $('.main-header');
            if (header.is(':hidden')) {
                header.show();
                $(".main-sidebar").css("padding-top", "50px");
                $('.user-panel').hide();
                $('#menu-toggle-icon').removeClass("fa-compress")
                $('#menu-toggle-icon').addClass("fa-expand");
            } else {
                header.hide();
                $(".main-sidebar").css("padding-top", "0");
                $('.user-panel').show();
                $('#menu-toggle-icon').removeClass("fa-expand")
                $('#menu-toggle-icon').addClass("fa-compress");
            }
        }
    };


    var tabClass = {
        MAIN_TABS_ID: "layout_center_tabs",

        getTabs: function () {
            return $('#' + this.MAIN_TABS_ID);
        },

        /**
         * 添加
         * @param opts {*title,closable,iconCls,loadMode[1:ajax,2:iframe],*url}
         */
        add: function (opts) {
            var options = $.extend({closable: true, iconCls: 'fa fa-circle-o', loadMode: 1}, opts);
            var t = this.getTabs();
            if (t.tabs('exists', options.title)) {
                t.tabs('select', options.title);
            } else {
                if (options.loadMode == 1) {
                    options.href = contextPath + options.url;
                } else {
                    options.content = '<iframe src="' + options.url + '" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>'
                }
                var That = this;
                $.extend(options, {
                    onLoadError: function (e, x, y) {
                        That.close();
                        $.acooly.alert('错误', "请求的功能不存在");
                    }
                })
                t.tabs('add', options);
            }
        },

        /**
         * 添加ajax-load内容
         * @param title
         * @param iconCls
         * @param url
         */
        addPage: function (title, iconCls, url) {
            this.add({title: title, iconCls: iconCls, loadMode: 1, url: url});
        },

        /**
         * 添加iframe内容
         * @param title
         * @param iconCls
         * @param url
         */
        addIframe: function (title, iconCls, url) {
            this.add({title: title, iconCls: iconCls, loadMode: 2, url: url});
        },

        /**
         * 选中
         * @param title
         */
        select: function (title) {
            this.getTabs().tabs('select', title);
        },

        /**
         * 获得当前选中的tab
         * @returns {*}
         */
        getSelect: function () {
            return this.getTabs().tabs('getSelected').panel('options');
        },

        refresh: function (title) {
            this.getTabs().tabs('getTab', title).panel('refresh');
        },

        /**
         * 关闭当前tab
         */
        close: function () {
            var t = this.getTabs();
            var index = t.tabs('getTabIndex', t.tabs('getSelected'));
            if (index <= 0) {
                return;
            }
            var tab = t.tabs('getTab', index);
            if (tab.panel('options').closable) {
                t.tabs('close', index);
            } else {
                $.messager.alert('提示', '[' + tab.panel('options').title + ']不可以被关闭', 'error');
            }
        }


    }


    /**
     * 主题
     * @type {{defaultExpires: number, acoolyThemeKey: string, getTheme: (function(*=): (*|String)), saveTheme: saveTheme}}
     */
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
    });

    $.extend($.acooly.admin, {tab: tabClass});
})(jQuery);
