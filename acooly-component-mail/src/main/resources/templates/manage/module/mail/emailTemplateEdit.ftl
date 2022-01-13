<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_emailTemplate_editform" class="form-horizontal" action="${pageContext.request.contextPath}/manage/module/mail/emailTemplate/<#if action=='create'>saveJson<#else>updateJson</#if>.html" method="post">
        <@jodd.form bean="emailTemplate" scope="request">
            <input name="id" type="hidden"/>
            <div class="card-body">
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">
                        模版名称 <a title="模板唯一标志，不可重复，一般采用英文编码" class="easyui-tooltip"><i class="fa fa-info-circle" aria-hidden="true"></i></a>
                    </label>
                    <div class="col-sm-10">
                        <input type="text" name="name" placeholder="请输入模板名称,例如：USER_REGISTRY_SUCCESS..." class="easyui-validatebox form-control" data-options="validType:['text','length[1,32]']" required="true"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">模版标题 <a title="模板的中文名称，一般可用于模板类型，邮件类型的定义，对应邮件发送记录列表里面的`模板类型`" class="easyui-tooltip"><i class="fa fa-info-circle" aria-hidden="true"></i></a></label>
                    <div class="col-sm-10">
                        <input type="text" name="title" placeholder="请输入模板标题,例如：注册成功通知..." class="easyui-validatebox form-control" data-options="validType:['text','length[1,32]']" required="true"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">默认主题 <a title="默认主题是在发送时未指定主题(`标题`)时，邮件的默认标题" class="easyui-tooltip"><i class="fa fa-info-circle" aria-hidden="true"></i></a></label>
                    <div class="col-sm-10">
                        <textarea rows="3" cols="40" placeholder="请输入备注..." name="subject" class="easyui-validatebox form-control form-words" data-words="255" data-options="validType:['text','length[1,255]']"></textarea>
                    </div>
                </div>

                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">模板内容
                        <a title="邮件模板定义，支持语法的参数定义" class="easyui-tooltip"><i class="fa fa-info-circle" aria-hidden="true"></i></a>
                    </label>
                    <div class="col-sm-10">
                        <textarea required="true" rows="28" cols="40" placeholder="请输入邮件模板内容..." name="content" class="easyui-validatebox form-control form-words" data-words="102400" data-options="validType:['text','length[1,102400]']"></textarea>
                    </div>
                </div>
            </div>
        </@jodd.form>
    </form>
</div>
