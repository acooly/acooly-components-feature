<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_region_editform" action="/manage/module/eav/region/<#if action=='create'>saveJson<#else>updateJson</#if>.html" method="post">
		<@jodd.form bean="region" scope="request">
        <input name="id" type="hidden" />
        <table class="editForm" width="100%">
			<tr>
				<th width="25%">区域编码：</th>
				<td><input type="text" name="pid" placeholder="请输入区域编码..." class="easyui-numberbox" style="height: 30px;width: 260px;line-height: 1.3em;" data-options="validType:['length[1,19]'],required:true"/></td>
			</tr>					
			<tr>
				<th>区域名称：</th>
				<td><input type="text" name="name" placeholder="请输入区域名称..." class="easyui-validatebox" data-options="validType:['length[1,100]'],required:true"/></td>
			</tr>					
			<tr>
				<th>首字母拼音：</th>
				<td><input type="text" name="pinyin" placeholder="请输入首字母拼音..." class="easyui-validatebox" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>排序值：</th>
				<td><input type="text" name="sortTime" placeholder="请输入排序值..." class="easyui-numberbox" style="height: 30px;width: 260px;line-height: 1.3em;" data-options="validType:['length[1,19]']"/></td>
			</tr>					
			<tr>
				<th>备注：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入备注..." name="comments" class="easyui-validatebox" data-options="validType:['length[1,128]']"></textarea></td>
			</tr>					
        </table>
      </@jodd.form>
    </form>
</div>
