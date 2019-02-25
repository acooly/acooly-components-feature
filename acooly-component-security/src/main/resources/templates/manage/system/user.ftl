<script type="text/javascript">
    /**
     * 修改密码
     */
    function manage_user_changePasswd(id) {
        $('#manage_user_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
        $('<div/>').dialog({
            href: '/manage/system/user/showChangePassword.html?id=' + id,
            width: 400,
            height: 200,
            modal: true,
            title: '修改用戶密碼',
            buttons: [{
                text: '修改',
                iconCls: 'icon-edit',
                handler: function () {
                    var d = $(this).closest('.window-body');
                    $('#manage_user_changePassword_form').form('submit', {
                        onSubmit: function () {
                            var isValid = $('#manage_user_changePassword_form').form('validate');
                            if (!isValid) {
                                return false;
                            }
                            return true;
                        },
                        success: function (data) {
                            try {
                                var result = $.parseJSON(data);
                                if (result.success) {
                                    d.dialog('destroy');
                                }
                                $.messager.show({
                                    title: '提示',
                                    msg: result.message
                                });
                            } catch (e) {
                                $.messager.alert('提示', result);
                            }
                        }
                    });
                }
            }],
            onClose: function () {
                $(this).dialog('destroy');
            }
        });
    }

    /**
     * 页面加载完成后执行
     */
    $(function () {
        //注册按键回车直接提交查询
        $.acooly.framework.registerKeydown('manage_user_searchform', 'manage_user_datagrid');

        //树初始化，设置默认值
        $('#orgId').combotree({
            url: '${pageContext.request.contextPath}/manage/module/security/org/listOrganize.html',
            required: false
        })
    });
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="overflow: hidden;" align="left">
        <form id="manage_user_searchform">
            <table class="tableForm" width="100%">
                <tr>
                    <td align="left">
                        登录名: <input type="text" class="text" size="10" name="search_LIKE_username"/>
                        姓名: <input type="text" class="text" size="10" name="search_LIKE_realName"/>
                        状态: <select id="search_EQ_status" name="search_EQ_status" editable="false" panelHeight="auto" class="easyui-combobox" style="width:100px;">
                            <option value="">所有</option><#list allStatus as k,v><option value="${k}">${v}</option></#list></select>
                        类型:<select id="search_EQ_userType" name="search_EQ_userType" panelHeight="auto" editable="false" class="easyui-combobox" style="width:150px;">
                        <option value="">所有</option>
                        <#list allUserTypes as k,v><option value="${k}">${v}</option></#list></select>
                        角色:<select id="search_EQ_role" name="search_EQ_role" panelHeight="auto" editable="false" class="easyui-combobox">
                        <option value="">所有</option>
                        <#list allRoles as e><option value="${e.id}">${e.name}</option></#list>
                        </select>
                        组织机构: <input type="text" id="orgId" class="easyui-combobox" name="search_EQ_orgId"/>
                        <a href="javascript:void(0);" style="width:70px;" class="easyui-linkbutton" data-options="plain:false"
                           onclick="$.acooly.framework.search('manage_user_searchform','manage_user_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i> 查询</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_user_datagrid" class="easyui-datagrid" url="${rc.getContextPath()}/manage/system/user/listUser.html"
               toolbar="#manage_user_toolbar" fit="true" fitColumns="false"
               pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc"
               checkOnSelect="true" selectOnCheck="true" singleSelect="true">
            <thead>
            <tr>
                <th field="userId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
                <th field="id" sortable="true">编号</th>
                <th field="username">登录名称</th>
                <th field="realName">姓名</th>
                <th field="email">E-mail</th>
                <th field="mobileNo">手机号码</th>
                <th field="createTime">创建时间</th>
                <th field="lastModifyTime">修改时间</th>
                <th field="unlockTime">解锁时间</th>
                <th field="roleName">角色</th>
                <th field="status"
                    data-options="formatter:function(value){ return formatRefrence('manage_user_datagrid','allStatus',value);} ">状态
                </th>
                <th field="userType"
                    data-options="formatter:function(value){ return formatRefrence('manage_user_datagrid','allUserTypes',value);} ">用户类型
                </th>
                <th field="orgName">所属机构</th>
                <th field="action"
                    data-options="formatter:function(value, row, index){return formatAction('manage_user_action',value,row)}">动作
                </th>
            </tr>
            </thead>
        </table>

        <div id="manage_user_action" style="display: none;">
            <a title="编辑" onclick="$.acooly.framework.edit({url:'/manage/system/user/edit.html',id:'{0}',entity:'user',height:450});"
               href="#"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
            <!--<a title="查看"  onclick="$.acooly.framework.show('/manage/system/user/show.html?id={0}',500,350);" href="#"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>-->
            <a title="修改密码" onclick="manage_user_changePasswd('{0}');" href="#"><i class="fa fa-key fa-lg fa-fw fa-col"></i></a>
            <a title="删除" onclick="$.acooly.framework.remove('/manage/system/user/deleteJson.html','{0}','manage_user_datagrid');" href="#"><i
                    class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
        </div>

        <div id="manage_user_toolbar">
            <a href="#" class="easyui-linkbutton" data-options="size:'small'" plain="true" onclick="$.acooly.framework.create({url:'/manage/system/user/create.html',entity:'user',height:510})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
            <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/system/user/importView.html',
            uploader:'manage_user_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a>
        </div>
    </div>

</div>
