<script type="text/javascript">
$(function() {
	$.acooly.framework.createUploadify({
				/** 上传导入的URL */
				url:'/manage/rbac/rbacRoleResc/importJson.html?_csrf=${_csrf.token}&splitKey=v',
				/** 导入操作消息容器 */
				messager:'manage_rbacRoleResc_import_uploader_message',
				/** 上传导入文件表单ID */
				uploader:'manage_rbacRoleResc_import_uploader_file',
				jsessionid:'${Session.id}'
	});	
});
</script>
<div align="center">
<table class="tableForm" width="100%">
  <tr>
    <th width="30%">文件类型：</th>
    <td>
    根据文件扩展名自动适配导入文件类型，目前支持的格式包括：Excel各版本(*.xls,*.xlsx)和CSV(*.csv)。 请<a href="javascript:alert('请开发人员根据业务需求定义模板。')">下载模板文件</a>。
    </td>
  </tr>	
  <tr>
    <th width="30%" height="15"></th>
    <td><div id="manage_rbacRoleResc_import_uploader_message" style="color: red;"></div></td>
  </tr>          				
  <tr>
    <th width="30%">文件：</th>
    <td>
    <input type="file" name="manage_rbacRoleResc_import_uploader_file" id="manage_rbacRoleResc_import_uploader_file" />
    </td>
  </tr>		
</table>
</div>
