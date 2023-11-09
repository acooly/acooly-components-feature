(function ($) {
    $.extend($.acooly, {
        layout: {
            menus: [],
            selectedMenu: {
                id: null,
                name: null
            },
            loadMenu: function (defaultMenuId) {
                $("#mainMenu").empty();
                $.ajax({
                    url: contextPath + "/manage/system/authorisedMenus.html",
                    async: false,
                    success: function (data, textStatus) {
                        if (typeof (data) == 'string') {
                            data = eval('(' + data + ')');
                        }
                        // console.log("main menu", data);
                        // 如果开启了收藏夹功能，则加载收藏夹的菜单数据到第一个位置
                        if ($.acooly.system.config.enableFavorite) {
                            // 如果存在收藏夹的数据，则构建收藏夹菜单
                            var treeNodes = $.acooly.admin.favorite.getTreeNodes();
                            if (treeNodes && treeNodes.length > 0) {
                                for (let i = 0; i < treeNodes.length; i++) {
                                    treeNodes[i]["parentId"] = "0";
                                }
                                var favoriteMenu = {
                                    id: '0',
                                    icon: 'fa-star-o',
                                    iconSkin: 'fa-star-o',
                                    name: '收藏夹',
                                    orderTime: '2099-05-19 15:14:26',
                                    showMode: 1,
                                    showState: 0,
                                    type: 'MENU',
                                    value: '',
                                    children: treeNodes
                                }
                                data.unshift(favoriteMenu);
                            }
                        }

                        var a = '';
                        $(data).each(
                            function (i, domEle) {
                                if (domEle.children && domEle.children.length > 0) {
                                    // 遍历树形结构，设置主菜单ID
                                    $.acooly.layout._treeForeach(domEle, function (node) {
                                        node['menuId'] = domEle.id;
                                    });
                                    $.acooly.layout.menus.push(domEle);

                                    if (domEle.iconSkin != null) {
                                        a += "<a id='mainMenu_" + domEle.id + "' href=\"javascript:$.acooly.layout.loadTree('" + domEle.id + "','" + domEle.name + "');\" class=\"" + (i == 1 ? 'buttonaft' : 'button') + "\">" +
                                            "<span><i class='fa " + domEle.iconSkin + "'></i>  " + domEle.name + "</span></a>";
                                    } else {
                                        a += "<a id='mainMenu_" + domEle.id + "' href=\"javascript:$.acooly.layout.loadTree('" + domEle.id + "','" + domEle.name + "');\" class=\"" + (i == 1 ? 'buttonaft' : 'button') + "\">" +
                                            "<span><img src='" + $.acooly.layout.getIconPath(domEle.icon) + "'/>" + domEle.name + "</span></a>";
                                    }

                                    if (defaultMenuId && defaultMenuId == domEle.id) {
                                        $.acooly.layout.selectedMenu = domEle;
                                    }
                                    if (!defaultMenuId && i == 0) {
                                        $.acooly.layout.selectedMenu = domEle;
                                    }
                                }
                            });
                        $("#mainMenu").html(a);
                    }
                });
            },

            _treeForeach: function (tree, func) {
                if (tree && tree.children && tree.children.length > 0) {
                    for (let i in tree.children) {
                        let data = tree.children[i];
                        func(data);
                        data.children && data.children.length > 0 && $.acooly.layout._treeForeach(data, func) // 遍历子树
                    }
                }
            },

            /**
             * 显示子菜单
             *
             * 加载左边子菜单
             */
            loadTree: function (parentId, name) {
                if (!parentId) {
                    parentId = $.acooly.layout.selectedMenu.id
                }
                // 改变主菜单样式
                if (parentId) {
                    $("#mainMenu").children("a").attr("class", "button");
                    $("#mainMenu_" + parentId).attr("class", "buttonaft");
                }

                let setting = {
                    view: {
                        showLine: true
                    },
                    callback: {
                        onClick: function (event, treeId, treeNode, clickFlag) {
                            treeNode['rescId'] = treeNode.id;
                            $.acooly.layout.accessResource(treeNode);
                        }
                    }
                };
                var data = null, i = 0;
                while (data == null && i < 3) {
                    data = $.acooly.layout.getTree(parentId)
                    i++;
                }
                $.fn.zTree.init($("#layout_west_tree"), setting, data.children);
                var zTree = $.fn.zTree.getZTreeObj("layout_west_tree");
                zTree.expandAll(true);
            },

            reloadMenu: function () {
                $.acooly.layout.loadMenu($.acooly.layout.selectedMenu.id);
                $.acooly.layout.loadTree($.acooly.layout.selectedMenu.id);
            },

            getTree: function (id) {
                var selectMenu = $.acooly.layout.menus[0];
                $($.acooly.layout.menus).each(function (i, menu) {
                    if (menu.id == id) {
                        selectMenu = menu;
                    }
                });
                $.acooly.layout.selectedMenu = selectMenu;
                return selectMenu
            },

            accessResource: function (node) {
                var type = node.type
                if (type == 'MENU')
                    return;
                var url = node.value;
                if (!url || url == '')
                    return;

                var opts = {
                    rescId: node.rescId,
                    title: node.name,
                    menuId: node.menuId, // easyui专用
                    closable: true,
                    iconCls: node.icon != null && node.icon.indexOf("fa") != -1 ? "fa " + node.icon : node.icon
                };
                if (node.showMode == 1) {
                    opts.href = contextPath + url;
                } else {
                    opts.content = '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99%;" ></iframe>'
                }
                $.acooly.layout.addTab(opts);
            },

            createTree: function () {
                $.acooly.westTree = $('#layout_west_tree').tree({
                    parentField: 'id',
                    lines: true,
                    onClick: function (node) {
                        var url = node.attributes.url;
                        if (!url || url == '')
                            return;
                        var opts = {
                            title: node.text,
                            closable: true,
                            iconCls: node.iconCls
                        };
                        if (node.attributes.loadMode == 1) {
                            opts.href = contextPath + url;
                        } else {
                            opts.content = '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>'
                        }
                        $.acooly.layout.addTab(opts);
                    }
                });
                // $.acooly.layout.loadTree($.acooly.layout.selectedMenu.id,$.acooly.layout.selectedMenu.text);
            },

            addTab: function (opts) {
                //var jq = top.jQuery;
                var t = $('#layout_center_tabs');
                if (t.tabs('exists', opts.title)) {
                    t.tabs('select', opts.title);
                } else {
                    $.extend(opts, {
                        onLoadError: function (e, x, y) {
                            $.acooly.layout.closeTab();
                            $.acooly.messager("错误", "请求的功能不存在或没有权限", 'danger')
                        }
                    })
                    t.tabs('add', opts);
                }
                // console.info("add tab opts", opts);
                // $.acooly.admin.favorite.show(opts.rescId);
            },

            addTabAndRender: function (opts) {
                var t = $('#layout_center_tabs');
                if (t.tabs('exists', opts.title)) {
                    t.tabs('select', opts.title);
                } else {
                    $.extend(opts, {
                        onLoadError: function (e, x, y) {
                            $.acooly.layout.closeTab();
                            $.acooly.messager("错误", "请求的功能不存在或没有权限", 'danger')
                        }
                    })
                    let tab = t.tabs('add', opts);
                    if (tab && tab.length > 0) {
                        // 渲染
                        $.parser.onComplete = function () {
                            //要执行的操作
                            $.acooly.framework.extendCombobox($(tab));
                            //Initialize
                            $.acooly.framework.initPlugins($(tab));
                            //最后把坑爹的事件绑定解除
                            $.parser.onComplete = function () {
                            };
                        }
                    }
                }
                // console.info("add tab opts", opts);
                // $.acooly.admin.favorite.show(opts.rescId);
            },

            selectTab: function (title) {
                $('#layout_center_tabs').tabs('select', title);
            },

            getSelectTab: function () {
                return $('#layout_center_tabs').tabs('getSelected').panel('options');
            },

            refreshTab: function (title) {
                $('#layout_center_tabs').tabs('getTab', title).panel('refresh');
            },

            closeTab: function () {
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
            },

            getIconPath: function (icoCls) {
                if (!icoCls || icoCls.indexOf('-') == -1) {
                    return null;
                }
                return '/manage/assert/style/' + icoCls.split('-').join('/') + '.png';
            }
        }
    });
})(jQuery);
