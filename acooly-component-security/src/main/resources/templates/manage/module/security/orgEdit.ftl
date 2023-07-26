<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<script type="text/javascript" src="/manage/assert/plugin/area/area.js" charset="utf-8"></script>
<script type="text/javascript">
    _init_area();
    $(function () {
        setAreaValue();
    });

    function setAreaValue() {
        <#if org != null>
        $('#s_province').val('${org.province}').trigger("change");
        change(1);
        $('#s_city').val('${org.city}').trigger("change");
        change(2);
        $('#s_county').val('${org.county}');
        $('#parentId').val('${org.parentId}');
        </#if>
    }
</script>


<div>
    <form id="manage_org_editform" class="form-horizontal"
          action="/manage/module/security/org/<#if action == 'create'>save<#else>update</#if>Json.html" method="post">
        <@jodd.form bean="org" scope="request">
            <input name="id" type="hidden"/>
            <div class="card-body">
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">所属机构：</label>
                    <div class="col-sm-9">
                        <div class="input-group">
                            <select name="parentId" data-allow-clear="true" id="manage_user_searchform_parentId" class="form-control select2bs4"></select>
                            <span class="input-group-append"><button type="button" class="btn btn-default btn-sm btn-flat" onclick="$('#manage_user_searchform_parentId').val(null).trigger('change');">
                               <i class="fa fa-times-circle fa-lg fa-fw fa-col"></i></button></span>
                        </div>

                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">机构名称：</label>
                    <div class="col-sm-9">
                        <input type="text" name="name" placeholder="请输入机构名称..." class="easyui-validatebox form-control" data-options="validType:['length[1,32]']" required="true"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">机构负责人
                        <a title="该部门的管理员，第一负责人。" class="easyui-tooltip"><i class="fa fa-info-circle" aria-hidden="true"></i></a>
                    </label>
                    <div class="col-sm-9">
                        <select name="username" class="form-control select2bs4" required>
                            <option value="">请选择机构负责人</option>
                            <#list users as user>
                                <option value="${user.username}">${user.pinyin}:${user.realName}:${user.orgName}</option></#list>
                        </select>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">联系人</label>
                    <div class="col-sm-9">
                        <input type="text" name="contacts" placeholder="请输入联系人姓名..." class="easyui-validatebox form-control" data-options="validType:['length[1,24]']"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">地区：</label>
                    <div class="col-sm-9 row">
                        <div class="col-md-4"><select required="true" id="s_province" name="province" class="form-control select2bs4"></select></div>
                        <div class="col-md-4"><select required="true" id="s_city" name="city" class="form-control select2bs4"></select></div>
                        <div class="col-md-4"><select required="true" id="s_county" name="county" class="form-control select2bs4"></select></div>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">地址</label>
                    <div class="col-sm-9">
                        <input type="text" name="address" placeholder="请输入详细地址..." class="easyui-validatebox form-control" data-options="validType:['length[1,128]']"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">手机号码</label>
                    <div class="col-sm-9">
                        <input type="text" name="mobileNo" placeholder="请输入手机号码..." class="easyui-validatebox form-control" data-inputmask="'alias':'mobile'" data-mask data-options="validType:['mobile','length[1,11]']"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">邮件</label>
                    <div class="col-sm-9">
                        <input type="text" name="email" placeholder="请输入邮件..." class="easyui-validatebox form-control" data-inputmask="'alias':'email'" data-mask data-options="validType:['email','length[1,64]']"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">固定电话</label>
                    <div class="col-sm-9">
                        <input type="text" name="telephone" placeholder="请输入座机电话..." class="easyui-validatebox form-control" data-options="validType:['length[1,24]']"/>
                    </div>
                </div>

                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">状态：</label>
                    <div class="col-sm-9">
                        <select name="status" class="form-control select2bs4">
                            <#list allStatuss as k,v>
                                <option value="${k}">${v}</option></#list>
                        </select>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">备注</label>
                    <div class="col-sm-9">
                        <input type="text" name="memo" placeholder="请输入备注信息..." class="easyui-validatebox form-control" data-options="validType:['length[1,255]']"/>
                    </div>
                </div>
            </div>
        </@jodd.form>
    </form>
    <script>
        $(function () {
            // 初始化组织结构下拉选择框
            var parentId = '<#if action == 'create'>${parentId}<#else>${org.parentId}</#if>';
            $.acooly.system.user.orgTreeBoxInit("manage_user_searchform_parentId", false,parentId);
        });
    </script>
</div>
