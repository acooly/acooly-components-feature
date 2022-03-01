<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<script type="text/javascript">
    /**
     * Form表单提交前，前置处理
     * @returns {boolean|*|Window.jQuery}
     */
    function manage_user_editform_onSubmit() {
        <#if action == 'create'>
        var isValid = $(this).form('validate');
        if (!isValid) {
            return false;
        }
        //自定义检查合法性
        if ($('#manage_user_passwd').val() != $('#manage_user_confirmPasswd').val()) {
            $.messager.alert('提示', '两次密码输入不一致');
            return false;
        }
        return true;
        <#else>
        return $(this).form('validate');
        </#if>
    }
</script>
<div>
    <form id="manage_user_editform" class="form-horizontal" action="/manage/system/user/<#if action == 'create'>save<#else>update</#if>Json.html" method="post">
        <@jodd.form bean="user" scope="request">
        <input name="id" type="hidden"/>
        <div class="card-body">
            <div class="form-group row">
                <label class="col-sm-3 col-form-label">用户名</label>
                <div class="col-sm-9<#if action != 'create'> col-form-content</#if>">
                    <#if action == 'create'>
                        <input name="username" type="text" placeholder="请输入用户名..." class="easyui-validatebox form-control" data-inputmask="'alias':'account'" data-mask data-options="validType:['account','length[1,32]']" required="true"/>
                    <#else>${user.username}</#if>
                </div>
            </div>
            <#if action == 'create'>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">密码</label>
                    <div class="col-sm-9">
                        <input name="password" id="manage_user_passwd" type="password" placeholder="请输入密码..."
                               validType="commonRegExp['${PASSWORD_REGEX}','${PASSWORD_ERROR}']" class="easyui-validatebox form-control"
                               data-options="required:true"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">确认密码</label>
                    <div class="col-sm-9">
                        <input name="confirmPasswd" id="manage_user_confirmPasswd" validType="equals['#manage_user_passwd']"
                               type="password" class="easyui-validatebox form-control" data-options="required:true"/>
                    </div>
                </div>
            </#if>
            <div class="form-group row">
                <label class="col-sm-3 col-form-label">姓名</label>
                <div class="col-sm-9">
                    <input type="text" name="realName" placeholder="请输入姓名..." class="easyui-validatebox form-control" data-options="validType:['length[1,16]']" required="true"/>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-3 col-form-label">邮件</label>
                <div class="col-sm-9">
                    <input type="text" name="email" placeholder="请输入邮件..." class="easyui-validatebox form-control" data-inputmask="'alias':'email'" data-mask data-options="validType:['email','length[1,64]'],required:true"/>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-3 col-form-label">手机号码</label>
                <div class="col-sm-9">
                    <input type="text" name="mobileNo" placeholder="请输入手机号码..." class="easyui-validatebox form-control" data-inputmask="'alias':'mobile'" data-mask data-options="validType:['mobile','length[1,11]']"/>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-3 col-form-label">类型</label>
                <div class="col-sm-9">
                    <select id="userTypeTest" name="userType" class="form-control select2bs4">
                        <#list allUserTypes as k,v>
                            <option value="${k}">${v}</option></#list>
                    </select>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-3 col-form-label">状态</label>
                <div class="col-sm-9">
                    <select name="status" class="form-control select2bs4">
                        <#list allStatuss as k,v>
                            <option value="${k}">${v}</option></#list>
                    </select>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-3 col-form-label">角色</label>
                <div class="col-sm-9">
                    <select name="role" class="form-control input-sm select2bs4">
                        <#list allRoles as e><option value="${e.id}"<#if role == e.id> selected</#if>>${e.name}</option></#list>
                    </select>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-3 col-form-label">组织机构</label>
                <div class="col-sm-9">
                    <select name="orgId" id="manage_user_editform_orgId" class="form-control input-sm select2bs4"></select>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-3 col-form-label">备注</label>
                <div class="col-sm-9">
                    <textarea rows="3" cols="40" placeholder="请输入备注..." name="descn" class="easyui-validatebox form-control"></textarea>
                </div>
            </div>
            </@jodd.form>
    </form>
    <script>
        $(function () {
            $.acooly.system.user.orgTreeBoxInit("manage_user_editform_orgId", false);
        });
    </script>
</div>
