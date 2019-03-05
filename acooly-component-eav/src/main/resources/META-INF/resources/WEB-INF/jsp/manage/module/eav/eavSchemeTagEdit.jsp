<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_eavSchemeTag_editform" action="${pageContext.request.contextPath}/manage/module/eav/eavSchemeTag/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="eavSchemeTag" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th width="25%">方案ID：</th>
				<td><input type="text" name="schemeid" size="48" placeholder="请输入方案ID..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]'],required:true"/></td>
			</tr>					
			<tr>
				<th>标签：</th>
				<td><input type="text" name="tag" size="48" placeholder="请输入标签..." class="easyui-validatebox text" data-options="validType:['length[1,64]'],required:true"/></td>
			</tr>					
			<tr>
				<th>备注：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入备注..." style="width:300px;" name="memo" class="easyui-validatebox" data-options="validType:['length[1,255]']"></textarea></td>
			</tr>					
        </table>
      </jodd:form>
    </form>
</div>
