/**
 * acooly portal framework system module
 *
 * zhangpu
 */

/**
 * 会员管理
 * @type {{}}
 */
let system_user_class = {

    orgTreeBoxInit: function (treeboxId, isSearch) {
        $.ajax({
            url: '/manage/module/security/org/listJson.html',
            method: 'post',
            success: function (result) {
                let nodes = result.rows;
                if (isSearch) {
                    nodes.push({id: 0, parentId: -1, name: '所有'});
                }
                $('#' + treeboxId).select2ztree({
                    theme: 'bootstrap4',
                    textField: 'name',
                    valueField: 'id',
                    ztree: {
                        setting: {
                            data: {
                                simpleData: {
                                    enable: true,
                                    pIdKey: 'parentId',
                                    rootPId: 0
                                }
                            }
                        },
                        zNodes: nodes
                    }
                });
            }
        });
    },

    /**
     * 修改指定用户密码
     * @param id
     */
    changePasswd: function (id) {
        $('#manage_user_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
        var d = $('<div/>').dialog({
            href: '/manage/system/user/showChangePassword.html?id=' + id,
            width: 450,
            height: 350,
            modal: true,
            title: ' <i class="fa fa-lock fa-lg"></i> 重置用户密码',
            buttons: [{
                text: '<i class="fa fa-check fa-col"></i> 提 交',
                handler: function () {
                    $('#manage_user_changePassword_form').ajaxSubmit({
                        beforeSubmit: function () {
                            return $('#manage_user_changePassword_form').form('validate');
                        },
                        success: function (result) {
                            $.acooly.messager('修改密码', result.message, result.success ? "success" : "danger");
                            if (result.success) {
                                d.dialog('destroy');
                            }
                        },
                        error: function (XmlHttpRequest, textStatus, errorThrown) {
                            $.acooly.messager('错误', errorThrown, 'danger');
                        }
                    });
                }
            }],
            onClose: function () {
                $(this).dialog('destroy');
            }
        });
    }
}

/**
 *
 * $.acooly.system
 * @type {{init: system_class.init, getCSRFToken: (function(): *), globalSetting: system_class.globalSetting, loadSecurityConfig: system_class.loadSecurityConfig, config: {}}}
 */
let system_class = {
    user: system_user_class,
    config: {},
    /** init */
    init: function () {
        //console.info("acooly framework init...")
        // 全局基本配置
        this.globalSetting();
        // 加载全局配置
        this.loadSecurityConfig();
        //console.info("load security config:",$.acooly.system.config);
    },

    loadSecurityConfig: function () {
        var That = this;
        $.ajax({
            url: contextPath + "/security/config/index.html",
            success: function (data, textStatus) {
                if (typeof (data) == 'string') {
                    data = eval('(' + data + ')');
                }
                That.config = data;
                var acoolyTheme = $.acooly.admin.theme.getTheme($.acooly.admin.theme.acoolyThemeKey);
                if (acoolyTheme && acoolyTheme.indexOf('acooly') != -1) {
                    $.acooly.admin.init();
                }
            }
        });
    },

    /** 全局配置 */
    globalSetting: function () {
        // 注册全局ajax错误处理：Session过期
        $(document).ajaxComplete(function (event, xhr, settings) {
            var sessionState = xhr.getResponseHeader("SessionState");
            if (sessionState && sessionState == '1') {
                $.messager.alert('提示', '会话过期，请重新登录', 'info', function () {
                    window.location.href = '/manage/logout.html';
                });
            }
        });
        var token = $("meta[name='X-CSRF-TOKEN']").attr("content");// 从meta中获取token
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader("X-CSRF-TOKEN", token);// 每次ajax提交请求都会加入此token
        });
    },

    /** 获取当前的csrf-token */
    getCSRFToken: function () {
        return $("meta[name='X-CSRF-TOKEN']").attr("content");
    }

    // ~~~ end
};


/***
 * JQuery静态类前缀处理
 */
(function ($) {
    if (!$.acooly) {
        $.acooly = {};
    }
    if (!$.acooly.system) {
        $.extend($.acooly, {system: system_class});
    }
})(jQuery);


var manage_resource_tree_setting = {
    view: {
        showLine: true,
        showIcon: true,
        addHoverDom: manage_resource_tree_addHoverDom,
        removeHoverDom: manage_resource_tree_removeHoverDom,
        fontCss: function (treeId, treeNode) {
            return treeNode.showState == 1 ? {
                color: "gray"
            } : {
                color: 'black'
            };
        }
    },
    edit: {
        enable: true,
        showRemoveBtn: false,
        showRenameBtn: false
    },
    callback: {
        onClick: function (event, treeId, treeNode, clickFlag) {
            manage_resource_form_bind(treeNode)
        },
        onDrop: function (event, treeId, treeNodes, targetNode, moveType) {
            manage_resource_form_moveup(treeNodes[0], targetNode, moveType);
        }

    }
};

function manage_resource_tree_addHoverDom(treeId, treeNode) {
    var aObj = $("#" + treeNode.tId + "_a");
    // 添加子
    if ($("#manage_resource_add_Btn_" + treeNode.id).length == 0) {
        var html = "<a id='manage_resource_add_Btn_" + treeNode.id + "' href='javascript:;' onclick='manage_resource_form_add();return false;' style='margin:0 0 0 5px;'>添加</a>"
        aObj.after(html)
    }
    // 删除
    if (!treeNode.children || treeNode.children.length == 0) {
        if ($("#manage_resource_delete_Btn_" + treeNode.id).length > 0)
            return;
        var html = "<a id='manage_resource_delete_Btn_" + treeNode.id + "' href='javascript:;' onclick='manage_resource_tree_delete(" + treeNode.id + ");return false;' style='margin:0 0 0 5px;'>删除</a>"
        aObj.append(html)
    }

}

function manage_resource_tree_removeHoverDom(treeId, treeNode) {
    if ($("#manage_resource_add_Btn_" + treeNode.id).length > 0)
        $("#manage_resource_add_Btn_" + treeNode.id).unbind().remove();
    if (treeNode.children && treeNode.children.length == 0)
        $("#manage_resource_delete_Btn_" + treeNode.id).unbind().remove();
}

function manage_resource_form_moveup(sourceNode, targetNode, moveType) {
    if (!moveType || moveType == null)
        return;
    //console.info("sourceNode:" + sourceNode.name + ", targetNode:" + targetNode.name + ",moveType:" + moveType);
    $.ajax({
        url: '/manage/system/resource/move.html',
        data: {
            sourceId: sourceNode.id,
            targetId: targetNode.id,
            moveType: moveType
        },
        success: function (data, status) {
            if (data.success) {
                console.info("save move success!");
            }
        }
    });
}

/**
 * 加载树
 *
 * @param defaultNode
 * @param clear
 */
function manage_resource_loadTree(defaultNode, clear) {
    $.ajax({
        url: '/manage/system/resource/listAllJson.html',
        success: function (data, status) {
            if (typeof (data) == 'string') {
                data = eval('(' + data + ')');
            }
            if (data.success) {
                $.fn.zTree.init($("#manage_resource_tree"), manage_resource_tree_setting, data.rows);
                var zTree = $.fn.zTree.getZTreeObj("manage_resource_tree");
                zTree.expandAll(true);
                if (defaultNode) {
                    zTree.selectNode(defaultNode, true);
                } else {
                    manage_resource_form_add(true);
                }
            }
        }
    });
}


var manage_resource_tree_expand_state = true;

function manage_resource_tree_toggle() {
    var zTree = $.fn.zTree.getZTreeObj("manage_resource_tree");
    zTree.expandAll(!manage_resource_tree_expand_state);
    manage_resource_tree_expand_state = !manage_resource_tree_expand_state;
}

function manage_resource_tree_getSelectedNode() {
    var node;
    var zTree = $.fn.zTree.getZTreeObj("manage_resource_tree");
    var nodes = zTree.getSelectedNodes();
    if (nodes && nodes.length > 0)
        node = nodes[0];
    return node;
}


function manage_resource_tree_reload() {
    var zTree = $.fn.zTree.getZTreeObj("manage_resource_tree");
    zTree.refresh();
}


/**
 * 添加界面
 */
function manage_resource_form_add(isRoot) {
    $('#manage_resource_editform').form('reset');
    var node = manage_resource_tree_getSelectedNode();
    if (isRoot || !node) {
        var zTree = $.fn.zTree.getZTreeObj("manage_resource_tree");
        zTree.cancelSelectedNode();
        $('#manage_resource_node_parentId').val('');
        $('#manage_resource_node_parentName').html("根节点");
    } else {
        $('#manage_resource_node_parentName').html(node.name);
        $('#manage_resource_node_parentId').val(node.id);
    }
    $('#manage_resource_node_id').val('');
}

/**
 * 修改界面
 *
 * @param resource
 */
function manage_resource_form_bind(resource) {
    // 该死的IE8，不兼容raido的load
    try {
        $('#manage_resource_editform').form('load', resource);
    } catch (e) {
    }
    $("#manage_resource_form_showState option[id='" + resource.showState + "']").attr("selected", true);
    $("input", $('#iconContainer')).each(function () {
        if (this.value == resource.icon || this.value == resource.iconSkin) {
            $(this).attr('checked', 'true');
            return;
        }
    });
    var zTree = $.fn.zTree.getZTreeObj("manage_resource_tree");
    if (resource.parentId) {
        var node = zTree.getNodeByParam("id", resource.parentId, null);
        $('#manage_resource_node_parentName').html(node.name);
    } else {
        $('#manage_resource_node_parentName').html('根节点');
    }

}

/**
 * save/update
 */
function manage_resource_form_submit() {
    $('#manage_resource_editform').ajaxSubmit({
        beforeSubmit: function (formData, jqForm, options) {
            var result = $('#manage_resource_editform').form('validate');
            if (!result) {
                return result;
            }
            var type = null;
            var value = null;
            for (var i; i < formData.length; i++) {
                var e = formData[i];
                if (e.name == 'type') {
                    type = e.value;
                }
                if (e.name == 'value') {
                    value = e.value;
                }
            }
            if (type != 'MENU' && value == '') {
                $.acooly.msgrb("非菜单资源，资源值不能为空", false);
                return false;
            }
            return result;
        },
        success: function (result, statusText) {
            if (typeof (result) == 'string') {
                result = eval('(' + result + ')');
            }
            if (result.success) {
                var zTree = $.fn.zTree.getZTreeObj("manage_resource_tree");
                var nodeId = $('#manage_resource_node_id').val();
                if (nodeId && nodeId != '') {
                    // 编辑
                    var node = zTree.getNodeByParam("id", result.entity.id, null);
                    if (result.entity.icon) {
                        if (result.entity.icon.startsWith("icon")) {
                            node.icon = result.entity.icon;
                            node.iconSkin = null;
                        } else {
                            node.icon = null;
                            node.iconSkin = result.entity.icon;
                        }
                    }
                    if (result.entity.name) {
                        node.name = result.entity.name;
                    }
                    if (result.entity.showState) {
                        node.showState = result.entity.showState;
                    }
                    zTree.updateNode(node);
                } else {
                    // 新增
                    var pNode = manage_resource_tree_getSelectedNode();
                    var nNode = result.entity;
                    if (nNode.icon) {
                        if (nNode.icon.startsWith("icon")) {
                            nNode = nNode.icon;
                            nNode.iconSkin = null;
                        } else {
                            nNode.iconSkin = nNode.icon;
                            nNode.icon = null;

                        }
                    }
                    zTree.addNodes(pNode, nNode);
//					 if(pNode.children && pNode.children.length > 1){
//						 zTree.moveNode(pNode.children[0],nNode,'prev');
//					 }
                    $('#manage_resource_node_id').val(result.entity.id);
                }
            }
            if (result.message)
                $.messager.show({
                    title: '提示',
                    msg: result.message
                });
        }
    });
}

/**
 * 删除节点
 */
function manage_resource_tree_delete(id) {
    $.messager.confirm("确认", "你确认删除该资源？	", function (r) {
        if (!r)
            return;
        $.ajax({
            url: '/manage/system/resource/deleteJson.html?id=' + id,
            success: function (result, status) {
                if (typeof (result) == 'string') {
                    result = eval('(' + result + ')');
                }
                if (result.success) {
                    var zTree = $.fn.zTree.getZTreeObj("manage_resource_tree");
                    var node = zTree.getNodeByParam("id", id, null);
                    zTree.removeNode(node);
                }
                if (result.message)
                    $.messager.show({
                        title: '提示',
                        msg: result.message
                    });
            }
        });
    })
}
