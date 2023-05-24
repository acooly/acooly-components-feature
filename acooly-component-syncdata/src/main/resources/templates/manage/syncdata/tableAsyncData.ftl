<#if ssoEnable><#include "/manage/common/ssoInclude.ftl"></#if>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_tableAsyncData_searchform" class="form-inline ac-form-search" onsubmit="return false">
                    <div class="form-group">
                        <label class="col-form-label">业务类型：</label>
                        <select name="search_EQ_type" class="form-control input-sm select2bs4">
                            <option value="">所有</option><#list allTypes as k,v><option value="${k}">${v}</option></#list>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">表名：</label>
                        <input type="text" class="form-control form-control-sm" name="search_EQ_tableName"/>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">查询类型：</label>
                        <select name="search_EQ_queryType" class="form-control input-sm select2bs4">
                            <option value="">所有</option><#list allQueryTypes as k,v><option value="${k}">${v}</option></#list>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">字段类型：</label>
                        <select name="search_EQ_queryColumnType" class="form-control input-sm select2bs4">
                            <option value="">所有</option><#list allQueryColumnTypes as k,v><option value="${k}">${v}</option></#list>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">字段名称：</label>
                        <input type="text" class="form-control form-control-sm" name="search_EQ_queryColumnName"/>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">字段值：</label>
                        <input type="text" class="form-control form-control-sm" name="search_EQ_queryColumnValue"/>
                    </div>

                    <div class="form-group">
                        <label class="col-form-label">创建时间：</label>
                        <input type="text" class="form-control form-control-sm" id="search_GTE_createTime" name="search_GTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
                        <span class="mr-1 ml-1">至</span> <input type="text" class="form-control form-control-sm" id="search_LTE_createTime" name="search_LTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
                    </div>

            <div class="form-group">
                <button class="btn btn-sm btn-primary" type="button" onclick="$.acooly.framework.search('manage_tableAsyncData_searchform','manage_tableAsyncData_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i> 查询</button>
            </div>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_tableAsyncData_datagrid" class="easyui-datagrid" url="/manage/syncdata/tableAsyncData/listJson.html" toolbar="#manage_tableAsyncData_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" formatter="idFormatter">编号</th>
            <th field="id" sortable="true" >ID</th>
			<th field="type" sortable="true" formatter="mappingFormatter">业务类型</th>
			<th field="tableName">表名</th>
			<th field="queryType" formatter="mappingFormatter">查询类型</th>
			<th field="queryColumnType" formatter="mappingFormatter">字段类型</th>
			<th field="queryColumnName">字段名称</th>
			<th field="queryColumnValue">最后字段值</th>
            <th field="queryRows" sortable="true" sum="true">查询条数</th>
<#--		    <th field="sortTime" formatter="dateTimeFormatter">排序时间</th>-->
		    <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
		    <th field="updateTime" formatter="dateTimeFormatter">更新时间</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_tableAsyncData_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_tableAsyncData_action" style="display: none;">
      <a onclick="$.acooly.framework.edit({url:'/manage/syncdata/tableAsyncData/edit.html',id:'{0}',entity:'tableAsyncData',width:500,height:500});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
<#--      <a onclick="$.acooly.framework.show('/manage/syncdata/tableAsyncData/show.html?id={0}',500,500);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>-->


        <a onclick="$.acooly.framework.move('/manage/syncdata/tableAsyncData/moveTop.html','{0}','manage_tableAsyncData_datagrid');" href="#">
                <span class="fa-stack fa-fw fa-col fa-lg">
                  <i class="fa fa-circle fa-stack-1x"></i>
                  <i class="fa fa-angle-double-up fa-stack-1x fa-inverse" style="font-size: 10px;"></i>
                </span>
        </a>
        <a onclick="$.acooly.framework.move('/manage/syncdata/tableAsyncData/moveUp.html','{0}','manage_tableAsyncData_datagrid');" href="#">
            <i class="fa fa-arrow-circle-up fa-fw fa-col fa-lg" aria-hidden="true"></i>
        </a>

      <a onclick="$.acooly.framework.remove('/manage/syncdata/tableAsyncData/deleteJson.html','{0}','manage_tableAsyncData_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_tableAsyncData_toolbar">
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/syncdata/tableAsyncData/create.html',entity:'tableAsyncData',width:500,height:500})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/syncdata/tableAsyncData/deleteJson.html','manage_tableAsyncData_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>
<#--      <a href="#" class="easyui-menubutton" data-options="menu:'#manage_tableAsyncData_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>-->
<#--      <div id="manage_tableAsyncData_exports_menu" style="width:150px;">-->
<#--        <div onclick="$.acooly.framework.exports('/manage/syncdata/tableAsyncData/exportXls.html','manage_tableAsyncData_searchform','同步表数据信息')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>-->
<#--        <div onclick="$.acooly.framework.exports('/manage/syncdata/tableAsyncData/exportCsv.html','manage_tableAsyncData_searchform','同步表数据信息')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>-->
<#--      </div>-->
<#--      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/syncdata/tableAsyncData/importView.html',uploader:'manage_tableAsyncData_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a>-->
    </div>
  </div>
    <script type="text/javascript">
        $(function () {
            $.acooly.framework.initPage('manage_tableAsyncData_searchform', 'manage_tableAsyncData_datagrid');
        });
    </script>
</div>
