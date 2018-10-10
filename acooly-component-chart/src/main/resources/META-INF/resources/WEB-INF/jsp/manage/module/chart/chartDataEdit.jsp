<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_chartData_editform" action="${pageContext.request.contextPath}/manage/module/chart/chartData/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="chartData" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th width="25%">主题id：</th>
				<td><input type="text" name="chartId" size="48" placeholder="请输入主题id..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]'],required:true"/></td>
			</tr>					
			<tr>
				<th>图表选项id：</th>
				<td><input type="text" name="itemsId" size="48" placeholder="请输入图表选项id..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]'],required:true"/></td>
			</tr>					
			<tr>
				<th>sql表达式：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入sql表达式..." style="width:300px;" name="sqlData" class="easyui-validatebox" data-options="validType:['length[1,2048]'],required:true"></textarea></td>
			</tr>					
			<tr>
				<th>数据字段：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入数据字段..." style="width:300px;" name="fieldMapped" class="easyui-validatebox" data-options="validType:['length[1,512]'],required:true"></textarea></td>
			</tr>					
			<tr>
				<th>备注：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入备注..." style="width:300px;" name="comments" class="easyui-validatebox" data-options="validType:['length[1,255]']"></textarea></td>
			</tr>					
        </table>
      </jodd:form>
    </form>
</div>
