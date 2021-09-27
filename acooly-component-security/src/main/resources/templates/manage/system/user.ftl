<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="overflow: hidden;" align="left">
        <form id="manage_user_searchform" class="form-inline ac-form-search" onsubmit="return false" style="padding-left: 5px;">
            <div class="form-group">
                <label class="col-form-label">登录名：</label>
                <input type="text" class="form-control form-control-sm" name="search_LIKE_username"/>
            </div>
            <div class="form-group">
                <label class="col-form-label">姓名：</label>
                <input type="text" class="form-control form-control-sm" name="search_LIKE_realName"/>
            </div>
            <div class="form-group">
                <label class="col-form-label">状态：</label>
                <select name="search_EQ_status" class="form-control input-sm select2bs4">
                    <option value="">所有</option><#list allStatuss as k,v>
                    <option value="${k}">${v}</option></#list>
                </select>
            </div>
            <div class="form-group">
                <label class="col-form-label">类型：</label>
                <select name="search_EQ_userType" class="form-control input-sm select2bs4">
                    <option value="">所有</option><#list allUserTypes as k,v>
                    <option value="${k}">${v}</option></#list>
                </select>
            </div>
            <div class="form-group">
                <label class="col-form-label">角色：</label>
                <select name="search_EQ_role" class="form-control input-sm select2bs4">
                    <option value="">所有</option><#list allRoles as e>
                    <option value="${e.id}">${e.name}</option></#list>
                </select>
            </div>
            <div class="form-group">
                <label class="col-form-label">所属机构：</label>
                <#--                <input type="text" id="orgId" class="easyui-combobox" name="search_EQ_orgId"/>-->
                <select name="search_EQ_orgId" style="width:200px;" id="manage_user_searchform_orgId" class="form-control input-sm select2bs4"></select>
            </div>
            <div class="form-group">
                <button class="btn btn-sm btn-primary" type="button" onclick="$.acooly.framework.search('manage_user_searchform','manage_user_datagrid');"><i class="fa fa-search fa-fw fa-col"></i> 查询</button>
            </div>
        </form>
    </div>
    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_user_datagrid" class="easyui-datagrid" url="${rc.getContextPath()}/manage/system/user/listUser.html"
               toolbar="#manage_user_toolbar" fit="true" fitColumns="false"
               pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc"
               checkOnSelect="true" selectOnCheck="true" singleSelect="false">
            <thead>
            <tr>
                <th field="userId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
                <th field="id" sortable="true">编号</th>
                <th field="username">登录名称</th>
                <th field="realName">姓名</th>
                <th field="email">E-mail</th>
                <th field="mobileNo">手机号码</th>
                <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
                <th field="unlockTime">解锁时间</th>
                <th field="roleName">角色</th>
                <th field="status" formatter="mappingFormatter">状态</th>
                <th field="userType" formatter="mappingFormatter">用户类型</th>
                <th field="orgName">所属机构</th>
                <th field="action"
                    data-options="formatter:function(value, row, index){return formatAction('manage_user_action',value,row)}">动作
                </th>
            </tr>
            </thead>
        </table>

        <div id="manage_user_action" style="display: none;">
            <a title="编辑" onclick="$.acooly.framework.edit({url:'/manage/system/user/edit.html',id:'{0}',entity:'user',height:500});"
               href="#"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
            <!--<a title="查看"  onclick="$.acooly.framework.show('/manage/system/user/show.html?id={0}',500,350);" href="#"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>-->
            <a title="重置密码" onclick="$.acooly.system.user.changePasswd('{0}');" href="#"><i class="fa fa-key fa-lg fa-fw fa-col"></i></a>
            <a title="删除" onclick="$.acooly.framework.remove('/manage/system/user/deleteJson.html','{0}','manage_user_datagrid');" href="#"><i
                        class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
        </div>

        <div id="manage_user_toolbar">
            <a href="#" class="easyui-linkbutton" data-options="size:'small'" plain="true" onclick="$.acooly.framework.create({url:'/manage/system/user/create.html',entity:'user',height:510})"><i class="fa fa-plus-circle fa-fw fa-col"></i>添加</a>
            <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/system/user/deleteJson.html','manage_user_datagrid')">
                <i class="fa fa-trash fa-fw fa-col"></i>批量删除
            </a>
            <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/system/user/importView.html',uploader:'manage_user_import_uploader_file',datagrid:'manage_user_datagrid'});"><i class="fa fa-cloud-upload fa-fw fa-col"></i>批量导入</a>
        </div>
    </div>
</div>
<script type="text/javascript">

    /**
     * 页面加载完成后执行
     */
    $(function () {
        //注册按键回车直接提交查询
        $.acooly.framework.initPage('manage_user_searchform', 'manage_user_datagrid');
        // 初始化组织结构下拉选择框
        $.acooly.system.user.orgTreeBoxInit("manage_user_searchform_orgId", true);
    });
</script>
