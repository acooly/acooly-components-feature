<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_appConfig_editform" action="${pageContext.request.contextPath}/manage/module/config/appConfig/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="appConfig" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th>配置项名称：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入配置项名称..." style="width:300px;" name="configName" class="easyui-validatebox" data-options="validType:['length[1,255]'],required:true"></textarea></td>
			</tr>					
			<tr>
				<th>配置值：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入配置值..." style="width:300px;" name="configValue" class="easyui-validatebox" data-options="validType:['length[1,2048]'],required:true"></textarea></td>
			</tr>					
			<tr>
				<th>配置描述：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入配置描述..." style="width:300px;" name="comments" class="easyui-validatebox" data-options="validType:['length[1,255]'],required:true"></textarea></td>
			</tr>					
			<tr>
				<th>本地缓存过期时间：</th>
				<td><input type="text" name="localCacheExpire" size="48" placeholder="单位毫秒,默认0" style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,10]']"/></td>
			</tr>					
			<tr>
				<th>redis缓存过期时间：</th>
				<td><input type="text" name="redisCacheExpire" size="48" placeholder="单位毫秒,默认600000" style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,10]']"/></td>
			</tr>					
        </table>
      </jodd:form>
    </form>
</div>
