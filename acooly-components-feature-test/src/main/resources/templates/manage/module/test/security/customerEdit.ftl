<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_customer_editform" class="form-horizontal" action="/manage/module/test/security/customer/<#if action=='create'>saveJson<#else>updateJson</#if>.html" method="post">
        <@jodd.form bean="customer" scope="request">
        <input name="id" type="hidden"/>
        <div class="card-body">
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">用户名</label>
                <div class="col-sm-4">
                    <input type="text" name="username" placeholder="请输入用户名..." class="easyui-validatebox form-control" data-inputmask="'alias':'account'" data-mask data-options="validType:['account','length[1,32]'],required:true"/>
                </div>
                <label class="col-sm-2 col-form-label">中文姓名</label>
                <div class="col-sm-4">
                    <input type="text" name="realName" placeholder="请输入姓名..." class="form-control easyui-validatebox" data-mask data-options="validType:['chinese','length[1,16]'],required:true"/>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-2 col-form-label">生日</label>
                <div class="col-sm-4">
                    <input type="text" name="birthday" placeholder="请输入生日..." class="easyui-validatebox form-control" value="<#if customer.birthday??>${customer.birthday?string('yyyy-MM-dd')}</#if>" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
                </div>
                <label class="col-sm-2 col-form-label">年龄</label>
                <div class="col-sm-4">
                    <input type="text" name="age" placeholder="请输入年龄..." class="form-control" data-inputmask="'alias':'integer','min':1,'max':150" data-mask data-options="validType:['number[1,150]']"/>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-2 col-form-label">性别</label>
                <div class="col-sm-10">
                    <select name="gender" data- ="选择性别" class="form-control select2bs4">
                        <#list allGenders as k,v><option value="${k}">${v}</option></#list>
                    </select>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-2 col-form-label">银行卡号</label>
                <div class="col-sm-10">
                    <div class="input-group">
                        <input type="text" name="bankCard" placeholder="请输入银行卡号..." class="form-control easyui-validatebox" data-inputmask="'alias':'bankcard'" data-mask data-options="validType:['bankcard','length[1,16]']"/>
                        <div class="input-group-append"><span class="input-group-text"><i class="fa fa-credit-card" aria-hidden="true"></i></span></div>
                    </div>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-2 col-form-label">证件类型</label>
                <div class="col-sm-10">
                    <select name="idcardType" id="manage_customer_edit_idcardType" onchange="manage_customer_edit_idcardType_onchange();" class="form-control select2bs4" data-options="required:true">
                        <#list allIdcardTypes as k,v>
                            <option value="${k}">${v}</option></#list>
                    </select>
                </div>
            </div>
            <div class="form-group row">
                <label id="manage_customer_edit_idcardNo_label" class="col-sm-2 col-form-label">证件号码</label>
                <div class="col-sm-10">
                    <input type="text" name="idcardNo" placeholder="请输入证件号码..." class="form-control easyui-validatebox" data-inputmask="'alias':'idcard'" data-mask data-options="validType:'idcard',required:true"/>
                </div>
            </div>

            <div class="form-group row">
                <label id="manage_customer_edit_idcardNo_label" class="col-sm-2 col-form-label">网站地址</label>
                <div class="col-sm-10">
                    <input type="text" name="website" placeholder="请输入URL..." class="form-control easyui-validatebox" data-inputmask="'alias':'url'" data-mask data-options="validType:'url'"/>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-2 col-form-label">手机号码</label>
                <div class="col-sm-10">
                    <div class="input-group">
                        <input type="text" name="mobileNo" placeholder="请输入手机号码..." class="form-control easyui-validatebox" data-inputmask="'alias':'mobile'" data-mask data-options="validType:['mobile']"/>
                        <div class="input-group-append"><span class="input-group-text"><i class="fa fa-mobile-phone fa-lg" aria-hidden="true"></i></span></div>
                    </div>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">邮箱地址</label>
                <div class="col-sm-10">

                    <input type="text" name="mail" placeholder="请输入邮件..." class="form-control easyui-validatebox" data-inputmask="'alias':'email'" data-mask data-options="validType:['email','length[1,64]']"/>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">客户照片</label>
                <div class="col-sm-10">
                    <input type="text" name="photo" placeholder="请选择并上传照片..." class="form-control easyui-filebox" buttonText="选择照片" accept="image/*" data-options="validType:['length[1,64]']"  width="100%"/>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-2 col-form-label">客户简介</label>
                <div class="col-sm-10">
                    <input type="text" name="subject" placeholder="请输入简介..." class="form-control easyui-validatebox" data-options="validType:['length[1,64]']"/>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-2 col-form-label">客户类型</label>
                <div class="col-sm-10">
                    <select name="customerType" editable="false" panelHeight="auto" class="form-control select2bs4">
                        <#list allCustomerTypes as k,v>
                            <option value="${k}">${v}</option></#list>
                    </select>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-2 col-form-label">详细介绍</label>
                <div class="col-sm-10">
                    <textarea rows="3" cols="40" placeholder="请输入测试Text类型..." name="content" class="form-control easyui-validatebox" data-options="validType:['length[1,100000]']"></textarea>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-2 col-form-label">完成度</label>
                <div class="col-sm-10">
                    <div class="input-group">
                        <input type="text" name="fee" placeholder="请输入完成度百分比..." class="form-control easyui-validatebox" data-inputmask="'alias':'percent'" data-mask data-options="validType:['percent']"/>
                        <div class="input-group-append"><span class="input-group-text"><i class="fa fa-percent" aria-hidden="true"></i></span></div>
                    </div>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-2 col-form-label">手续费</label>
                <div class="col-sm-10">
                    <div class="input-group">
                    <input type="text" name="fee" placeholder="请输入手续费..." class="form-control easyui-validatebox" data-inputmask="'alias':'money','min':0.02,'max':1000" data-mask data-options="validType:['money','length[1,12]']"/>
                    <div class="input-group-append"><span class="input-group-text">元</span></div>
                    </div>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-2 col-form-label">薪水</label>
                <div class="col-sm-10">
                    <div class="input-group">
                        <input type="text" name="salary" placeholder="请输入薪水..." class="form-control easyui-validatebox" data-inputmask="'alias':'money','min':1,'max':1000" data-mask/>
                        <div class="input-group-append"><span class="input-group-text"><i class="fa fa-jpy" aria-hidden="true"></i></span></div>
                    </div>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-2 col-form-label">状态</label>
                <div class="col-sm-10">
                    <select name="status" editable="false" panelHeight="auto" class="form-control select2bs4" data-options="required:true">
                        <#list allStatuss as k,v>
                            <option value="${k}">${v}</option></#list>
                    </select>
                </div>
            </div>
            </@jodd.form>
    </form>

    <script>
        $(function () {
            // //Initialize Select2 and inputmask
            // $('.select2bs4').select2({theme: 'bootstrap4',});
            // $('[data-mask]').inputmask();

            // 初始化选择
            manage_customer_edit_idcardType_onchange();
        });


        function manage_customer_edit_idcardType_onchange() {
            var idcardTypes = {<#list allIdcardTypes as k,v><#if k?index gt 0>, </#if>"${k}":"${v}"</#list>};
            var idcardType = $('#manage_customer_edit_idcardType').val();
            $('#manage_customer_edit_idcardNo_label').html(idcardTypes[idcardType] + "号码");
        }
    </script>
</div>
