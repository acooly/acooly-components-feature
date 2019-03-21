<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_eavScheme_editform" action="/manage/module/eav/eavScheme/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="eavScheme" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm">
			<tr>
				<th width="25%">方案名称：</th>
				<td><input type="text" name="name" size="48" placeholder="唯一编码(建议表名).." class="easyui-validatebox text" data-options="validType:['length[1,128]'],required:true"/></td>
			</tr>
			<tr>
				<th>方案标题：</th>
				<td><input type="text" name="title" size="48" placeholder="请输入名称(中文名称)..." class="easyui-validatebox text" data-options="validType:['length[1,128]'],required:true"/></td>
			</tr>
			<tr>
				<th>弹出框宽：</th>
				<td><input type="text" style="height: 30px; line-height: 30px;" name="panelWidth" size="48" min="100" max="1024" placeholder="编辑/查看弹出框宽度" class="easyui-numberbox text" /></td>
			</tr>
			<tr>
				<th>弹出框高：</th>
				<td><input type="text" style="height: 30px; line-height: 30px;" name="panelHeight" size="48" min="100" max="1024" placeholder="编辑/查看弹出框高度" class="easyui-numberbox text" /></td>
			</tr>
			<tr>
				<th>显示权限：</th>
				<td>
					<c:forEach items="${allPermissions}" var="e">
						<input type="checkbox" name="permission" checked value="${e.key}"> ${e.value}
					</c:forEach>
				</td>
			</tr>
			<tr>
				<th>备注：</th>
				<td><input type="text" name="memo" size="48" placeholder="请输入备注..." class="easyui-validatebox text" data-options="validType:['length[1,128]']"/></td>
			</tr>					
        </table>
      </jodd:form>
    </form>
	<script>

        function initEavSchemePermission(){
            var showType = "${eavScheme.permission}";
            if(showType == ''){
                return;
            }
            var perms = parseInt(showType);
            $('#manage_eavScheme_editform :input[name=permission]').each(function(index){
                var v = parseInt($(this).val());
                if((perms & v) == v){
                    $(this).attr("checked","true");
                }else{
                    $(this).removeAttr("checked");
                }
            });
        }

        $(function(){
            initEavSchemePermission();
		});

	</script>
</div>
