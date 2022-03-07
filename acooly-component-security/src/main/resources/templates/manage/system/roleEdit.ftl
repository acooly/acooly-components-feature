<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<script language="JavaScript">
    $(function () {
        manage_role_load_tree();
    });

    var manage_role_resource_tree_setting = {
        check: {
            enable: true,
            chkboxType: {"Y": "ps", "N": "s"}
        },
        callback: {
            onClick: function (event, treeId, treeNode, clickFlag) {
            }
        }
    };

    var ztreeExpand = true;

    function manage_role_load_tree() {
        var roleId = "<#if role?? && role.id??>${role.id}<#else>0</#if>";
        $.ajax({
            url: "/manage/system/role/getAllResourceNode.html",
            data: {roleId: roleId},
            success: function (data, txtStatus) {
                $.fn.zTree.init($("#manage_role_resource_tree"), manage_role_resource_tree_setting, data.rows);
                var zTree = $.fn.zTree.getZTreeObj("manage_role_resource_tree");
                zTree.expandAll(ztreeExpand);
                var checkAll = true;
                $.each(data.rows, function (i, e) {
                    if (!e.checked) {
                        // 非全选，全选checkbox为false
                        checkAll = false;
                        return false;
                    }
                });
                $('#manage_role_editform_selectAll').prop("checked", checkAll);
            }
        });
    }

    function manage_role_checkAll() {
        var checked = $("#manage_role_editform_selectAll").prop("checked");
        var zTree = $.fn.zTree.getZTreeObj("manage_role_resource_tree");
        zTree.checkAllNodes(checked);
    }

    function manage_role_expandAll(obj) {
        var zTree = $.fn.zTree.getZTreeObj("manage_role_resource_tree");
        ztreeExpand = !ztreeExpand;
        var i = $(obj).children('i');
        if (ztreeExpand) {
            $(obj).html('<i class="fa fa-minus-circle"></i> 全部折叠');
        } else {
            $(obj).html('<i class="fa fa-plus-circle"></i> 全部展开');
        }
        zTree.expandAll(ztreeExpand);
    }

    /**
     * 提交前，获取所有选中的资源组装后请求服务器
     */
    function manage_role_editform_beforeSubmit() {
        var zTree = $.fn.zTree.getZTreeObj("manage_role_resource_tree");
        var nodes = zTree.getCheckedNodes(true);
        var resourceIds = [];
        $.each(nodes, function (index, e) {
            resourceIds.push(e.id);
        });
        $('#manage_role_editform_resourceIds').val(resourceIds.join(','));
    }
</script>


<div>
    <form id="manage_role_editform" class="form-horizontal" action="/manage/system/role/<#if action == 'create'>save<#else>update</#if>Json.html" method="post">
        <@jodd.form bean="role" scope="request">
            <input name="id" type="hidden"/>
            <input id="manage_role_editform_resourceIds" name="resourceIds" type="hidden"/>
            <div class="card-body">
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">
                        角色
                        <a title="注意：角色名称必须以`ROLE_`开头的大写英文组成" class="easyui-tooltip"><i class="fa fa-info-circle" aria-hidden="true"></i></a>
                    </label>
                    <div class="col-sm-9">
                        <input type="text" name="name" placeholder="角色名称必须以`ROLE_`开头的大写英文组成..." class="easyui-validatebox form-control" data-options="validType:['length[1,32]','roleName']" required="true"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">说明</label>
                    <div class="col-sm-9">
                        <input type="text" name="descn" placeholder="请输入角色说明，例如：系统管理..." class="easyui-validatebox form-control" data-options="validType:['length[1,32]']" required="true"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">权限/资源：</label>
                    <div class="col-sm-9">
                        <div style="margin: 0 0 2px 5px;">
                            <button type="button" onclick="manage_role_expandAll(this);" class="btn btn-primary btn-xs"><i class="fa fa-minus-circle"></i> 全部折叠</button>
                            <div class="icheck-primary d-inline">
                                <input type="checkbox" id="manage_role_editform_selectAll" onclick="manage_role_checkAll()">
                                <label for="manage_role_editform_selectAll">全选</label>
                            </div>
                        </div>
                        <div id="manage_role_resource_tree" class="ztree"></div>
                    </div>
                </div>
            </div>

        </@jodd.form>
    </form>
</div>


