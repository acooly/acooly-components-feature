<#if ssoEnable><#include "/manage/common/ssoInclude.ftl"></#if>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_emailRecord_searchform" class="form-inline ac-form-search" onsubmit="return false">
                    <div class="form-group">
                        <label class="col-form-label">模板名称：</label>
                        <input type="text" class="form-control form-control-sm" name="search_EQ_templateName"/>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">模板标题：</label>
                        <input type="text" class="form-control form-control-sm" name="search_EQ_templateTitle"/>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">发送人地址：</label>
                        <input type="text" class="form-control form-control-sm" name="search_EQ_fromAddress"/>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">发送人：</label>
                        <input type="text" class="form-control form-control-sm" name="search_EQ_from"/>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">创建时间：</label>
                        <input type="text" class="form-control form-control-sm" id="search_GTE_createTime" name="search_GTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
                        <span class="mr-1 ml-1">至</span> <input type="text" class="form-control form-control-sm" id="search_LTE_createTime" name="search_LTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">修改时间：</label>
                        <input type="text" class="form-control form-control-sm" id="search_GTE_updateTime" name="search_GTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
                        <span class="mr-1 ml-1">至</span> <input type="text" class="form-control form-control-sm" id="search_LTE_updateTime" name="search_LTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
                    </div>
            <div class="form-group">
                <button class="btn btn-sm btn-primary" type="button" onclick="$.acooly.framework.search('manage_emailRecord_searchform','manage_emailRecord_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i> 查询</button>
            </div>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_emailRecord_datagrid" class="easyui-datagrid" url="/manage/module/mail/emailRecord/listJson.html" toolbar="#manage_emailRecord_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" formatter="idFormatter">编号</th>
            <th field="id" sortable="true" >id</th>
			<th field="templateName">模板名称</th>
			<th field="templateTitle">模板标题</th>
			<th field="title" formatter="contentFormatter">标题</th>
			<th field="subject" formatter="contentFormatter">主题</th>
			<th field="content" formatter="contentFormatter">内容</th>
			<th field="fromAddress">发送人地址</th>
			<th field="from">发送人</th>
			<th field="toAddressList" formatter="contentFormatter">收件人地址列表</th>
		    <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
		    <th field="updateTime" formatter="dateTimeFormatter">修改时间</th>
			<th field="comments" formatter="contentFormatter">备注</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_emailRecord_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_emailRecord_action" style="display: none;">
      <a onclick="$.acooly.framework.edit({url:'/manage/module/mail/emailRecord/edit.html',id:'{0}',entity:'emailRecord',width:500,height:500});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.show('/manage/module/mail/emailRecord/show.html?id={0}',500,500);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.remove('/manage/module/mail/emailRecord/deleteJson.html','{0}','manage_emailRecord_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_emailRecord_toolbar">
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/module/mail/emailRecord/create.html',entity:'emailRecord',width:500,height:500})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/module/mail/emailRecord/deleteJson.html','manage_emailRecord_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>
      <a href="#" class="easyui-menubutton" data-options="menu:'#manage_emailRecord_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>
      <div id="manage_emailRecord_exports_menu" style="width:150px;">
        <div onclick="$.acooly.framework.exports('/manage/module/mail/emailRecord/exportXls.html','manage_emailRecord_searchform','邮件发送记录')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>
        <div onclick="$.acooly.framework.exports('/manage/module/mail/emailRecord/exportCsv.html','manage_emailRecord_searchform','邮件发送记录')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>
      </div>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/module/mail/emailRecord/importView.html',uploader:'manage_emailRecord_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a>
    </div>
  </div>
    <script type="text/javascript">
        $(function () {
            $.acooly.framework.initPage('manage_emailRecord_searchform', 'manage_emailRecord_datagrid');
        });
    </script>
</div>
