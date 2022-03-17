<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_appConfig_editform" class="form-horizontal" action="${pageContext.request.contextPath}/manage/module/config/appConfig/<#if action == 'create'>save<#else>update</#if>Json.html" method="post">
        <@jodd.form bean="appConfig" scope="request">
            <input name="id" type="hidden"/>
            <div class="card-body">
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">配置标题</label>
                    <div class="col-sm-9">
                        <input name="title" type="text" placeholder="配置参数名称或简称..." class="easyui-validatebox form-control" data-options="validType:['length[1,32]']" required="true"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">配置名称</label>
                    <div class="col-sm-9">
                        <input name="configName" type="text" placeholder="建议英文字母，多个下划线分割，注意唯一..." class="easyui-validatebox form-control" data-options="validType:['length[1,32]']" required="true"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">类型</label>
                    <div class="col-sm-9">
                        <select name="configType" class="form-control select2bs4">
                            <#list allConfigTypes as k,v>
                                <option value="${k}">${v}</option></#list>
                        </select>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">配置值</label>
                    <div class="col-sm-9" id="manage_appConfig_editform_configValue_container">
                        <textarea id="manage_appConfig_editform_configValue" rows="4" cols="40" placeholder="配置参数值..." name="configValue" class="easyui-validatebox form-control form-words" data-words="2048" data-options="validType:['length[1,2048]']" required="true"></textarea>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">本地缓存过期时间</label>
                    <div class="col-sm-9">
                        <input name="localCacheExpire" type="text" placeholder="单位毫秒,默认0..." class="easyui-validatebox form-control" data-options="validType:['length[1,10]']" required="true"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">Redis缓存过期时间</label>
                    <div class="col-sm-9">
                        <input name="redisCacheExpire" type="text" placeholder="单位毫秒,默认600000..." class="easyui-validatebox form-control" data-options="validType:['length[1,10]']" required="true"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">备注</label>
                    <div class="col-sm-9">
                        <input name="comments" type="text" placeholder="请输入配置描述" class="easyui-validatebox form-control" data-options="validType:['length[1,255]']"/>
                    </div>
                </div>
            </div>
        </@jodd.form>
    </form>
    <script>
        $(function () {
            $.acooly.formObj("manage_appConfig_editform", "configType").on('select2:select', function (e) {
                let data = e.params.data;
                console.log(data);
                if (data.id == "text") {
                    $('#manage_appConfig_editform_configValue').validatebox('options').validType = ['length[1,2048]'];
                } else {
                    $('#manage_appConfig_editform_configValue').validatebox('options').validType = ['json', 'length[1,2048]'];
                }
            });
        });
    </script>
</div>
