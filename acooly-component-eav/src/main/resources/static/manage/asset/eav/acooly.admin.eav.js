
/**
 * 属性实体查询
 */
function manage_eavAttribute_search(entity) {
    $('#manage_eavAttribute_search_schemeId').val(entity.id);
    $.acooly.framework.search('manage_eavAttribute_toolbar', 'manage_eavAttribute_datagrid');
}

function getZTree(){
    return $.fn.zTree.getZTreeObj("manage_eavScheme_menu");
}

function manage_eavAttribute_preTreeData(rows){
    for(var i=0;i<rows.length;i++){
        rows[i].icon=null;
        rows[i].iconSkin="fa fa-table";
    }
}


/**
 * 加载和构建分类目录
 * @param defaultNode
 * @param clear
 */
function manage_eavAttribute_loadTree(defaultNode, clear) {
    $.ajax({
        url : '/manage/module/eav/eavSchema/queryJson.html',
        success : function(result) {
            if (!result.success) {
                return;
            }
            var rows = result.rows;
            manage_eavAttribute_preTreeData(rows);
            $.fn.zTree.init($("#manage_eavScheme_menu"), {
                view : {
                    showLine : true,
                    showIcon : true,
                    showTitle : true,
                    addHoverDom : manage_eavAttribute_tree_addHoverDom,
                    removeHoverDom : manage_eavAttribute_tree_removeHoverDom,
                },
                edit : {
                    enable : true,
                    showRemoveBtn : false,
                    showRenameBtn : false
                },
                data: {
                    keep: {
                        parent: true
                    }
                },
                callback : {
                    onClick : function(event, treeId, treeNode, clickFlag) {
                        manage_eavAttribute_search(treeNode)
                    },
                    onDrop : function(event, treeId, treeNodes, targetNode, moveType) {
                        manage_eavAttribute_tree_moveup(treeNodes[0], targetNode, moveType);
                    }
                }
            }, rows);
            if(rows && rows.length > 0){
                manage_eavAttribute_search(rows[0]);
                var node = getZTree().getNodeByParam("id", rows[0].id, null);
                getZTree().selectNode(node, true);
            }
        }
    });
}

/**
 * 鼠标移动到节点动态添加添加和删除链接。
 */
function manage_eavAttribute_tree_addHoverDom(treeId, treeNode) {
    var aObj = $("#" + treeNode.tId + "_a");
    // 添加子
    // if ($("#manage_eavAttribute_add_Btn_" + treeNode.id).length == 0) {
    //     var html = "<a id='manage_eavAttribute_add_Btn_" + treeNode.id + "' href='javascript:;' onclick='manage_eavAttribute_tree_add(" + treeNode.id + ");return false;' style='margin:0 0 0 5px;'>添加</a>";
    //     aObj.append(html)
    // }
    // 编辑
    if ($("#manage_eavAttribute_edit_Btn_" + treeNode.id).length == 0) {
        var html = "<a id='manage_eavAttribute_edit_Btn_" + treeNode.id + "' href='javascript:;' onclick='manage_eavAttribute_tree_edit(" + treeNode.id + ");return false;' style='margin:0 0 0 5px;'>修改</a>";
        aObj.append(html)
    }
    // 删除
    if (!treeNode.children || treeNode.children.length == 0) {
        if ($("#manage_eavAttribute_delete_Btn_" + treeNode.id).length > 0)
            return;
        var html = "<a id='manage_eavAttribute_delete_Btn_" + treeNode.id + "' href='javascript:;' onclick='manage_eavAttribute_tree_delete(" + treeNode.id + ");return false;' style='margin:0 0 0 5px;'>删除</a>";
        aObj.append(html)
    }
}

/**
 * 鼠标移出节点处理
 */
function manage_eavAttribute_tree_removeHoverDom(treeId, treeNode) {
    // if ($("#manage_eavAttribute_add_Btn_" + treeNode.id).length > 0)
    //     $("#manage_eavAttribute_add_Btn_" + treeNode.id).unbind().remove();
    if ($("#manage_eavAttribute_edit_Btn_" + treeNode.id).length > 0)
        $("#manage_eavAttribute_edit_Btn_" + treeNode.id).unbind().remove();
    if ($("#manage_eavAttribute_delete_Btn_" + treeNode.id).length > 0)
        $("#manage_eavAttribute_delete_Btn_" + treeNode.id).unbind().remove();
}


/**
 * 添加子节点
 * @param parentId
 */
function manage_scheme_tree_add(parentId) {
    var url = '/manage/module/eav/eavSchema/create.html';
    $.acooly.framework.create({
        url : url,
        entity : 'eavScheme',
        width : 500,
        height : 400
        ,onSuccess : function(result) {
            var zTree = $.fn.zTree.getZTreeObj("manage_eavScheme_menu");
            var row = result.entity;
            row.icon=null;
            row.iconSkin="fa fa-folder";
            var newNode;
            if (parentId) {
                var parentNode = zTree.getNodeByParam("id", parentId, null);
                newNode = zTree.addNodes(parentNode, row, false);
            } else {
                newNode = zTree.addNodes(null, row, false);
            }
            zTree.selectNode(newNode);
        }
    });
}


/**
 * 修改节点
 * @param id
 */
function manage_eavAttribute_tree_edit(id) {
    $.acooly.framework.edit({
        url : '/manage/module/eav/eavSchema/edit.html',
        id : id,
        entity : 'eavScheme',
        width : 500,
        height : 400,
        reload : false,
        onSuccess : function(result) {
            console.info(result);
            var zTree = $.fn.zTree.getZTreeObj("manage_eavScheme_menu");
            var node = zTree.getNodeByParam("id", id, null);
            node.name = result.entity.name;
            zTree.updateNode(node, true);
        }
    })
}


/**
 * 删除子节点
 * @param id
 */
function manage_eavAttribute_tree_delete(id) {
    $.messager.confirm("确认", "你确认删除该类型？	", function(r) {
        if (!r)
            return;
        $.ajax({
            url : '/manage/module/eav/eavSchema/deleteJson.html?id=' + id,
            success : function(data, status) {
                if (data.success) {
                    var zTree = $.fn.zTree.getZTreeObj("manage_eavScheme_menu");
                    var node = zTree.getNodeByParam("id", id, null);
                    zTree.removeNode(node);
                }
                if (data.message)
                    $.messager.show({
                        title : '提示',
                        msg : data.message
                    });
            }
        });
    })
}

//移动
function manage_eavAttribute_tree_moveup(sourceNode, targetNode, moveType) {
    if (!moveType || moveType == null)
        return;
    $.ajax({
        url : '/manage/module/eav/eavSchema/move.html',
        data : {
            sourceId : sourceNode.id,
            targetId : targetNode.id,
            moveType : moveType
        },
        success : function(data, status) {
            if (data.success) {
                console.info("save move success!");
            }
        }
    });
}

//展开/收缩
var manage_eavAttribute_tree_expand_state = true;
function manage_eavAttribute_tree_toggle() {
    getZTree().expandAll(!manage_eavAttribute_tree_expand_state);
    manage_eavAttribute_tree_expand_state = !manage_eavAttribute_tree_expand_state;
}

/**
 * 添加属性
 */
function manage_eavAttribute_create() {
    var schemeId = $('#manage_eavAttribute_search_schemeId').val();
    if (!schemeId) {
        alert("请先选择方案");
        return;
    }
    $.acooly.framework.create({
        url:'/manage/module/eav/eavAttribute/create.html?schemeId='+schemeId,
        reload:true,
        entity:'eavAttribute',
        width:800,height:600
    });
}