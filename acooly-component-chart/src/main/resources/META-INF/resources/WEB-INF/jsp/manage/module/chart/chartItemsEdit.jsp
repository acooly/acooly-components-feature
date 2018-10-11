<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_chartItems_editform" action="${pageContext.request.contextPath}/manage/module/chart/chartItems/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="chartItems" scope="request">
        <input name="id" type="hidden" />
		  <c:choose>
			  <c:when test="${action=='create'}">
				  <input  name="chartId" type="hidden" value="${chartId}"/>
			  </c:when>
			  <c:otherwise>
				  <input  name="chartId" type="hidden"/>
			  </c:otherwise>
		  </c:choose>

        <table class="tableForm" width="100%">
			<tr>
				<th>标题：</th>
				<td><input type="text" name="title" size="48" placeholder="请给图表取个名,例如访问量曲线图" class="easyui-validatebox text" data-options="validType:['length[1,64]'],required:true"/></td>
			</tr>					
			<tr>
				<th>图表类型：</th>
				<td><select name="type" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" data-options="required:true">
					<c:forEach items="${allTypes}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>状态：</th>
				<td><select name="status" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" data-options="required:true">
					<c:forEach items="${allStatuss}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>循环时间：</th>
				<td><input type="text" name="loopTime" size="48" placeholder="请输入循环拉取数据时间(单位秒),为0时手动拉取数据" style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]'],required:true"/></td>
			</tr>					
			<tr>
				<th>x轴：</th>
				<td><input type="text" name="xShaft" size="48" placeholder="请输入x轴..." class="easyui-validatebox text" data-options="validType:['length[1,128]'],required:true"/></td>
			</tr>					
			<tr>
				<th>y轴：</th>
				<td><input type="text" name="yShaft" size="48" placeholder="请输入y轴..." class="easyui-validatebox text" data-options="validType:['length[1,128]'],required:true"/></td>
			</tr>
			<tr>
				<th>备注：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入备注..." style="width:300px;" name="comments" class="easyui-validatebox" data-options="validType:['length[1,255]']"></textarea></td>
			</tr>					
        </table>
      </jodd:form>
    </form>
</div>
