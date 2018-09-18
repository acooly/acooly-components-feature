<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_contentType_editform"
          action="${rc.contextPath}/manage/module/cms/contentType/<#if action == 'create'>save<#else>update</#if>Json.html"
          method="post">
        <@jodd.form bean="contentType" scope="request">
            <input name="id" type="hidden"/>
            <table class="tableForm" width="100%">
                <tr>
                    <th width="20%">父节点：</th>
                    <td>
                <#if action == 'create'>
                    <#if parent??>${parent.name}<#else>顶级节点</#if>
                    <input type="hidden" name="parentId" value="<#if parent??>${parent.id}</#if>"/>
                <#else>
                    <#if contentType.parent??>${contentType.parent.name}<#else>顶级节点</#if>
                    <input type="hidden" name="parentId" value="<#if contentType.parent??>${contentType.parent.id}</#if>"/>
                </#if>
                    </td>
                </tr>
                <tr>
                    <th width="20%">类型编码：</th>
                    <td>
                        <input type="text" name="code" class="easyui-validatebox" validType="byteLength[1,64]"/>
                    </td>
                </tr>
                <tr>
                    <th width="20%">类型名称：</th>
                    <td>
                        <input type="text" name="name" class="easyui-validatebox" data-options="required:true"
                               validType="byteLength[1,32]"/>
                    </td>
                </tr>
                <tr>
                    <th width="20%">备注：</th>
                    <td><textarea rows="3" cols="40" name="comments" class="easyui-validatebox"
                                  validType="byteLength[1,128]"></textarea></td>
                </tr>
            </table>
        </@jodd.form>
    </form>
</div>
