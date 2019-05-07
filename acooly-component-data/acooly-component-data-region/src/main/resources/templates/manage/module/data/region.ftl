<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_region_searchform','manage_region_datagrid');
});

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_region_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
          	<div>
					父ID: <input type="text" class="text" size="15" name="search_EQ_pid"/>
					区域名称: <input type="text" class="text" size="15" name="search_LIKE_name"/>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false" onclick="$.acooly.framework.search('manage_region_searchform','manage_region_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
          	</div>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_region_datagrid" class="easyui-datagrid" url="/manage/module/data/region/listJson.html" toolbar="#manage_region_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="id" sortable="true" sum="true">主键ID</th>
			<th field="parentId" sortable="true" sum="true">父ID</th>
			<th field="name">区域名称</th>
			<th field="pinyin">首字母拼音</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_region_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>


    <!-- 每行的Action动作模板 -->
    <div id="manage_region_action" style="display: none;">
      <a onclick="$.acooly.framework.edit({url:'/manage/module/eav/region/edit.html',id:'{0}',entity:'region',width:500,height:500});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.show('/manage/module/eav/region/show.html?id={0}',500,500);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.remove('/manage/module/eav/region/deleteJson.html','{0}','manage_region_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_region_toolbar">
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/module/eav/region/create.html',entity:'region',width:500,height:500})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/module/eav/region/deleteJson.html','manage_region_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>
      <a href="#" class="easyui-menubutton" data-options="menu:'#manage_region_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>
      <div id="manage_region_exports_menu" style="width:150px;">
        <div onclick="$.acooly.framework.exports('/manage/module/eav/region/exportXls.html','manage_region_searchform','省市区编码表')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>
        <div onclick="$.acooly.framework.exports('/manage/module/eav/region/exportCsv.html','manage_region_searchform','省市区编码表')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>
      </div>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/module/eav/region/importView.html',uploader:'manage_region_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a>
    </div>
  </div>

</div>
