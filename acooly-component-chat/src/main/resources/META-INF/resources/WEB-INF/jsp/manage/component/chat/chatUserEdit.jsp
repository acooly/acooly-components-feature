<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<!-- <script type="text/javascript" src="/manage/assert/plugin/jquery-easyui/plugins/jquery.easyui.extend.js" charset="utf-8"></script> -->




<div>
    <form id="manage_chatUser_editform" action="${pageContext.request.contextPath}/manage/component/chat/chatUser/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="chatUser" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th width="25%">用户名：</th>
				<td>
				
				<c:if test="${chatUser.id==null}">
					<input type="text" name="userName" size="48" placeholder="用户名：6-16位字母或者数字组成" class="easyui-validatebox text" required="true" validType="commonRegExp['[\\w]{6,16}','6-16位字母或者数字组成']"  invalidMessage="请输入6-16位字母或者数字组成"/>
				</c:if>
				
				<c:if test="${chatUser.id!=null}">
					${chatUser.userName}				
				</c:if>
				
				</td>
			
			</tr>		
			<c:if test="${chatUser.id==null}">	
			<tr>
				<th>密码：</th>
				<td><input type="text" name="password" size="48" placeholder="请输入密码..." class="easyui-validatebox text" data-options="validType:['length[1,64]'],required:true"/></td>
			</tr>	
			</c:if>		
			<tr>
				<th>昵称：</th>
				<td><input type="text" name="nickName" size="48" placeholder="请输入昵称..." class="easyui-validatebox text" data-options="validType:['length[1,64]'],required:true"/></td>
			</tr>					
			<tr>
				<th>个性签名：</th>
				<td><input type="text" name="signature" size="48" placeholder="请输入个性签名..." class="easyui-validatebox text" data-options="validType:['length[1,128]']"/></td>
			</tr>					
			<tr>
				<th>生日：</th>
				<td><input type="text" name="birthday" size="15" value="<fmt:formatDate value="${chatUser.birthday}" pattern="yyyy-MM-dd 00:00:00"/>" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd 00:00:00'})" /></td>
			</tr>					
			<tr>
				<th>性别：</th>
				<td>
				<select name="gender" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" >
					<c:forEach items="${allGenders}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select>
				</td>
			</tr>					
								
			<tr>
				<th>客服消息id：</th>
				<td><input type="text" name="infoTempId" size="48" placeholder="请输入客服置顶消息模板id..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]']"/></td>
			</tr>					
			<tr>
				<th>备注：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入备注..." style="width:300px;" name="comments" class="easyui-validatebox" data-options="validType:['length[1,255]']"></textarea></td>
			</tr>					
        </table>
      </jodd:form>
    </form>
</div>
