/**
 * 后台管理（基于adminlte）
 */
(function ($) {
    let adminClass = {
        /**
         * 初始化
         */
        init: function () {
            this.initLogo();
            this.initMenus();
            this.initTab();
            this.initPlugins();
            // 新旧版本的风格(访问到旧版页面，则设置版本cookies)
            // $.acooly.admin.theme.saveTheme($.acooly.admin.theme.acoolyThemeKey, "adminlte")
        },

        /**
         * 初始化LOGO
         */
        initLogo: function () {
            // adminlte2
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

            // adminlte3
            if ($.acooly.system.config.logoMini) {
                $('#logo_image').attr('src', $.acooly.system.config.logoMini);
            }
            if ($.acooly.system.config.title) {
                $('#logo_title').text($.acooly.system.config.title);
            } else {
                $('#logo_title').text("<b>Accoly</b> Sys V5.x");
            }
            $('.logo').show('normal');
        },

        /**
         * 初始化左侧功能菜单
         */
        initMenus: function () {

            // adminlte2: 注册点击主菜单（.treeview）的选中效果(.active)
            // $(document).on("click", '.sidebar-menu ul li', function (e) {
            //     $(".sidebar-menu li").removeClass("active");
            //     $(this).addClass("active");
            //     if ($(this).parent() && $(this).parent().parent()) {
            //         $(this).parent().parent().addClass("active");
            //     }
            // });

            // adminlte3: 注册点击主菜单（.nav-link）的选中效果(.active)
            $(document).on("click", '.sidebar .nav .nav-link', function (e) {
                // 选择效果
                $(".nav .nav-link").removeClass("active");
                $(this).addClass("active");
                var e = $(this).parent();
                while (e && e.parent() && e.attr('class').indexOf('nav-sidebar') == -1) {
                    e.parent().prev().addClass("active");
                    e = e.parent();
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
                height: $(window).height() - 52,
                border: false,
                pill: true,
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
            // 注册：通过jquery-resize插件实现：父节点大小改变后，resize tabs
            $('.content-wrapper').resize(function () {
                $('#layout_center_tabs').tabs('resize');
            });
        },

        /**
         * 初始化第三方插件
         */
        initPlugins: function () {

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

    let tabClass = {
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
                        $.acooly.toast('错误', "请求的功能不存在");
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
    let themeClass = {

        defaultExpires: 7,
        acoolyThemeKey: "acoolyTheme",

        /**
         * 从cookies加载主题并修改为对应的样式
         */
        loadTheme: function () {
            var themeName = $.acooly.admin.theme.getTheme($.acooly.admin.theme.acoolyThemeKey);
            if (!themeName) {
                themeName = 'acooly';
            }
            if (themeName == 'easyui') {
                themeName = 'default';
            }
            this.changeThemeStyle(themeName);
        },

        /**
         * 改变样式
         * @param themeName
         */
        changeTheme: function (themeName) {
            if (themeName) {
                $.acooly.admin.theme.saveTheme($.acooly.admin.theme.acoolyThemeKey, themeName);
            } else {
                themeName = $.acooly.admin.theme.getTheme($.acooly.admin.theme.acoolyThemeKey);
            }
            if (themeName == 'easyui') {
                themeName = 'default';
            }
            this.changeThemeStyle(themeName);
        },

        /**
         * 修改主题对应的样式
         * @param themeName
         */
        changeThemeStyle: function (themeName) {
            var $easyuiTheme = $('#easyuiTheme');
            var url = $easyuiTheme.attr('href');
            var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
            $easyuiTheme.attr('href', href);

            var $easyuiThemeBasic = $('#easyuiThemeBasic');
            var urlBasic = $easyuiThemeBasic.attr('href');
            var hrefBasic = url.substring(0, urlBasic.indexOf('themes')) + 'themes/' + themeName + '/basic.css';
            $easyuiThemeBasic.attr('href', hrefBasic);
        },


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
            var args = arguments;
            var ckey = args.length == 1 ? this.acoolyThemeKey : key;
            if (!ckey) {
                ckey = this.acoolyThemeKey;
            }
            var domain = this.getMainHost();
            $.cookie(ckey, value, {
                expires: this.defaultExpires,
                domain: domain
            });
        },

        getMainHost: function () {
            let key = `mh_${Math.random()}`;
            let keyR = new RegExp(`(^|;)\\s*${key}=12345`);
            let expiredTime = new Date(0);
            let domain = document.domain;
            let domainList = domain.split('.');

            let urlItems = [];
            // 主域名一定会有两部分组成
            urlItems.unshift(domainList.pop());
            // 慢慢从后往前测试
            while (domainList.length) {
                urlItems.unshift(domainList.pop());
                let mainHost = urlItems.join('.');
                let cookie = `${key}=${12345};domain=.${mainHost}`;
                document.cookie = cookie;
                //如果cookie存在，则说明域名合法
                if (keyR.test(document.cookie)) {
                    document.cookie = `${cookie};expires=${expiredTime}`;
                    return mainHost;
                }
            }
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
