<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_contentType_editform" class="form-horizontal"
          action="${rc.contextPath}/manage/module/cms/contentType/<#if action == 'create'>save<#else>update</#if>Json.html" method="post">
        <@jodd.form bean="contentType" scope="request">
            <input name="id" type="hidden"/>
            <div class="card-body">
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">父节点</label>
                    <div class="col-sm-9 col-form-content">
                        <#if action == 'create'>
                            <#if parent??>${parent.name}<#else>顶级节点</#if>
                            <input type="hidden" name="parentId" value="<#if parent??>${parent.id}</#if>"/>
                        <#else>
                            <#if contentType.parent??>${contentType.parent.name}<#else>顶级节点</#if>
                            <input type="hidden" name="parentId" value="<#if contentType.parent??>${contentType.parent.id}</#if>"/>
                        </#if>
                    </div>
                </div>

                <div class="form-group row">
                    <label class="col-sm-3 col-form-label"><a title="使用常用的单词表示分类或模块标志" class="easyui-tooltip">类型编码 <i class="fa fa-info-circle" aria-hidden="true"></i></a></label>
                    <div class="col-sm-9">
                        <input name="code" type="text" placeholder="请输入类型编码..." class="easyui-validatebox form-control" data-options="validType:['length[1,64]']" required="true"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">类型名称</label>
                    <div class="col-sm-9">
                        <input name="name" type="text" placeholder="请输入类型名称..." class="easyui-validatebox form-control" data-options="validType:['length[1,32]']" required="true"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">备注</label>
                    <div class="col-sm-9">
                        <input name="comments" type="text" placeholder="请输入备注..." class="easyui-validatebox form-control" data-options="validType:['length[1,128]']"/>
                    </div>
                </div>
            </div>
        </@jodd.form>
    </form>
</div>
