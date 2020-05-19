<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_smsTemplateProvider_editform" class="form-horizontal" action="/manage/smsend/smsTemplateProvider/<#if action=='create'>saveJson<#else>updateJson</#if>.html" method="post">
        <@jodd.form bean="smsTemplateProvider" scope="request">
            <input name="id" type="hidden"/>
            <div class="card-body">
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">模板</label>
                    <div class="col-sm-9">
                        <#if action=='create'>${RequestParameters.templateCode}<#else>${smsTemplateProvider.templateCode}</#if> : <#if action=='create'>${RequestParameters.templateName}<#else>${smsTemplateProvider.templateName}</#if>
                        <input type="hidden" name="templateCode" <#if RequestParameters.templateCode??>value="${RequestParameters.templateCode}"</#if> />
                        <input type="hidden" name="templateName" <#if RequestParameters.templateName??>value="${RequestParameters.templateName}"</#if> />
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">渠道</label>
                    <div class="col-sm-9">
                        <select name="provider" class="form-control select2bs4" data-options="required:true">
                            <#list allProviders as k,v><option value="${k}">${v}</option></#list>
                        </select>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">渠道模板编码</label>
                    <div class="col-sm-9">
                        <input type="text" name="providerTemplateCode" placeholder="请输入渠道模板编码..." class="easyui-validatebox form-control" data-options="validType:['text','length[1,64]']" required="true"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">备注</label>
                    <div class="col-sm-9">
                        <input type="text" name="comments" placeholder="请输入备注..." class="easyui-validatebox form-control" data-options="validType:['text','length[1,127]']"/>
                    </div>
                </div>
            </div>
        </@jodd.form>
    </form>
</div>
