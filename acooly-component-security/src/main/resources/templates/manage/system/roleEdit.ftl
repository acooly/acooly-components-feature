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
        var roleId = <#if role != null>${role.id}<#else>0</#if>;
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
    <form id="manage_role_editform" action="${rc.contextPath}/manage/system/role/<#if action == 'create'>save<#else>update</#if>Json.html"
          method="post">
      <@jodd.form bean="role" scope="request">
          <input name="id" type="hidden"/>
          <input id="manage_role_editform_resourceIds" name="resourceIds" type="hidden"/>
          <table class="tableForm" width="98%" align="center">
              <tr>
                  <th style="width: 80px">角色名称：</th>
                  <td><input name="name" type="text" class="easyui-validatebox" data-options="required:true"/></td>
              </tr>
              <tr>
                  <th>备注</th>
                  <td><textarea name="descn" rows="3" cols="40" class="easyui-validatebox"></textarea></td>
              </tr>
          </table>
          <div id="manage_role_resource_tree" class="ztree"></div>
      </@jodd.form>
    </form>
</div>


