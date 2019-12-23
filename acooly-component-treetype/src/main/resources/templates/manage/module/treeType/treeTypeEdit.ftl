<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_treeType_editform${theme}" action="/manage/module/treeType/treeType/<#if action=='create'>saveJson<#else>updateJson</#if>.html" method="post">
        <@jodd.form bean="treeType" scope="request">
            <input name="id" type="hidden"/>
            <input name="theme" type="hidden" value="${theme}"/>
            <table class="tableForm" width="100%">
                <tr>
                    <th>父类型ID：</th>
                    <td>
                        <#if action == 'create'>
                            <#if parent??>${parent.name}<#else>顶级节点</#if>
                            <input type="hidden" name="parentId" value="<#if parent??>${parent.id}</#if>"/>
                        <#else>
                            <#if parent??>${parent.name}<#else>顶级节点</#if><input type="hidden" name="parentId"/>
                        </#if>
                    </td>
                </tr>
                <tr>
                    <th>类型编码：</th>
                    <td><input type="text" name="code" placeholder="请输入类型编码..." class="easyui-validatebox" data-options="validType:['length[1,64]']"/></td>
                </tr>
                <tr>
                    <th>类型名称：</th>
                    <td><input type="text" name="name" placeholder="请输入类型名称..." class="easyui-validatebox" data-options="validType:['length[1,32]'],required:true"/></td>
                </tr>
                <tr>
                    <th>备注：</th>
                    <td><textarea rows="3" cols="40" placeholder="请输入备注..." name="comments" class="easyui-validatebox" data-options="validType:['length[1,128]']"></textarea></td>
                </tr>
            </table>
        </@jodd.form>
    </form>
</div>
