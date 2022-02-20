<div>
    <form id="manage_appMessage_editform" action="${rc.contextPath}/manage/module/app/appMessage/customPush.html" method="post">
        <table class="tableForm" width="100%">
            
            <input type="hidden" name="contentType" value="business"/>
            <input type="hidden" name="online" value="true" />
            
            <tr>
                <th width="25%">标题：</th>
                <td><input type="text" name="title" class="easyui-validatebox" data-options="required:true" validType="byteLength[1,32]" style="width: 200px;"/>
                </td>
            </tr>
           
            <tr>
                <th>发送内容：</th>
                <td><textarea rows="4" cols="45" name="content" class="easyui-validatebox" data-options="required:true" validType="byteLength[1,1024]"></textarea></td>
            </tr>
            
            <#if tagsMap>
            <tr>
                <th>接收群体：</th>
                <td>
                	<input name="tags" type="checkbox" value="" />全部用户  &nbsp;&nbsp;
		                <#list tagsMap as k,v>
		               		 <input name="tags" type="checkbox" value="${v}" />${k} &nbsp;&nbsp; 	        
		                </#list>
	                
                </td>
            </tr>
            </#if>
            
            <tr>
                <th>接收人：</th>
                <td><textarea rows="5" cols="45" name="receivers" class="easyui-validatebox" validType="byteLength[1,1024]"></textarea>
                    <div style="color:red">多个逗号分隔。为空则表示全部用户群体</div>
                </td>
            </tr>
            
            <#if iosJumpMap>
            <tr>
                <th>苹果app<br/>跳转位置：</th>
                <td>
                	<select name="iosJump" editable="false" panelHeight="auto" class="easyui-combobox" style="width: 200px;">
	                    <option value="">无跳转</option>
	                    <#list iosJumpMap as k,v>
	                        <option value="${v}">${k}</option>
	                    </#list>
                    </select>
                </td>
            </tr>
            </#if>
            
            <#if androidJumpMap>
            <tr>
                <th>安卓app<br/>跳转位置：</th>
                <td>
                	<select name="androidJump" editable="false" panelHeight="auto" class="easyui-combobox" style="width: 200px;">
                		<option value="">无跳转</option>
	                    <#list androidJumpMap as k,v>
	                        <option value="${v}">${k}</option>
	                    </#list>
                    </select>
                </td>
            </tr>
            </#if>
        </table>
        
    </form>
</div>
