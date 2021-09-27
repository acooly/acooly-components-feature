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

    function manage_role_load_tree() {
        var roleId = <#if role?? && role.id??>${role.id}<#else>
        "0"</#if>;
        $.ajax({
            url: "/manage/system/role/getAllResourceNode.html",
            data: {roleId: roleId},
            success: function (data, txtStatus) {
                $.fn.zTree.init($("#manage_role_resource_tree"), manage_role_resource_tree_setting, data.rows);
                var zTree = $.fn.zTree.getZTreeObj("manage_role_resource_tree");
                zTree.expandAll(true);
            }
        });
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
                    <label class="col-sm-3 col-form-label">角色：</label>
                    <div class="col-sm-9">
                        <input type="text" name="name" placeholder="请输入角色，建议以`ROLE_`开头..." class="easyui-validatebox form-control" data-options="validType:['length[1,16]']" required="true"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">说明：</label>
                    <div class="col-sm-9">
                        <input type="text" name="descn" placeholder="请输入角色说明，例如：系统管理..." class="easyui-validatebox form-control" data-options="validType:['length[1,16]']" required="true"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">权限/资源：</label>
                    <div class="col-sm-9">
<#--                        <div style="margin-bottom: 2px;">-->
<#--                            <div class="icheck-primary d-inline">-->
<#--                                <input type="checkbox" id="checkboxPrimary1" checked="">-->
<#--                                <label for="checkboxPrimary1">全选</label>-->
<#--                            </div>-->
<#--                            <button type="button" class="btn btn-primary btn-xs"><i class="fa fa-minus-circle"></i> 全部折叠</button>-->
<#--                        </div>-->
                        <div id="manage_role_resource_tree" class="ztree"></div>
                    </div>
                </div>
            </div>

        </@jodd.form>
    </form>
</div>


