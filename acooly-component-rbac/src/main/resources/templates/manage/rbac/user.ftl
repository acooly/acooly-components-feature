<#if ssoEnable><#include "/manage/common/ssoInclude.ftl"></#if>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_user_searchform" class="form-inline ac-form-search" onsubmit="return false">
                    <div class="form-group">
                        <label class="col-form-label">用户名：</label>
                        <input type="text" class="form-control form-control-sm" name="search_EQ_username"/>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">姓名：</label>
                        <input type="text" class="form-control form-control-sm" name="search_EQ_realName"/>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">姓名拼音：</label>
                        <input type="text" class="form-control form-control-sm" name="search_EQ_pinyin"/>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">密码加盐：</label>
                        <input type="text" class="form-control form-control-sm" name="search_EQ_salt"/>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">邮件：</label>
                        <input type="text" class="form-control form-control-sm" name="search_EQ_email"/>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">手机号码：</label>
                        <input type="text" class="form-control form-control-sm" name="search_EQ_mobileNo"/>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">密码过期时间：</label>
                        <input type="text" class="form-control form-control-sm" id="search_GTE_expireTime" name="search_GTE_expireTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
                        <span class="mr-1 ml-1">至</span> <input type="text" class="form-control form-control-sm" id="search_LTE_expireTime" name="search_LTE_expireTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">解锁时间：</label>
                        <input type="text" class="form-control form-control-sm" id="search_GTE_unlockTime" name="search_GTE_unlockTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
                        <span class="mr-1 ml-1">至</span> <input type="text" class="form-control form-control-sm" id="search_LTE_unlockTime" name="search_LTE_unlockTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">最后登录时间：</label>
                        <input type="text" class="form-control form-control-sm" id="search_GTE_loginTime" name="search_GTE_loginTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
                        <span class="mr-1 ml-1">至</span> <input type="text" class="form-control form-control-sm" id="search_LTE_loginTime" name="search_LTE_loginTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">状态：</label>
                        <select name="search_EQ_status" class="form-control input-sm select2bs4">
                            <option value="">所有</option><#list allStatuss as k,v><option value="${k}">${v}</option></#list>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">创建时间：</label>
                        <input type="text" class="form-control form-control-sm" id="search_GTE_createTime" name="search_GTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
                        <span class="mr-1 ml-1">至</span> <input type="text" class="form-control form-control-sm" id="search_LTE_createTime" name="search_LTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">最后修改时间：</label>
                        <input type="text" class="form-control form-control-sm" id="search_GTE_updateTime" name="search_GTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
                        <span class="mr-1 ml-1">至</span> <input type="text" class="form-control form-control-sm" id="search_LTE_updateTime" name="search_LTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
                    </div>
            <div class="form-group">
                <button class="btn btn-sm btn-primary" type="button" onclick="$.acooly.framework.search('manage_user_searchform','manage_user_datagrid');"><i class="fa fa-search fa-fw fa-col"></i> 查询</button>
            </div>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_user_datagrid" class="easyui-datagrid" url="/manage/rbac/rbacUser/listJson.html" toolbar="#manage_user_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" formatter="idFormatter">编号</th>
            <th field="id" sortable="true" >主键</th>
			<th field="username">用户名</th>
			<th field="realName">姓名</th>
			<th field="pinyin">姓名拼音</th>
			<th field="password" formatter="contentFormatter">登录密码</th>
			<th field="salt">密码加盐</th>
			<th field="userType" formatter="mappingFormatter">用户类型</th>
			<th field="email">邮件</th>
			<th field="mobileNo">手机号码</th>
            <th field="orgId" sortable="true" sum="true">组织ID</th>
			<th field="orgName" formatter="contentFormatter">组织名称</th>
		    <th field="expireTime" formatter="dateTimeFormatter">密码过期时间</th>
		    <th field="unlockTime" formatter="dateTimeFormatter">解锁时间</th>
            <th field="loginFailCount" sortable="true" sum="true">登录失败次数</th>
		    <th field="loginTime" formatter="dateTimeFormatter">最后登录时间</th>
			<th field="status" formatter="mappingFormatter">状态</th>
			<th field="memo" formatter="contentFormatter">描述</th>
		    <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
		    <th field="updateTime" formatter="dateTimeFormatter">最后修改时间</th>
        </tr>
      </thead>
      <thead frozen="true">
        <tr>
            <th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_user_action',value,row)}">动作</th>
        </tr>
      </thead>

    </table>

      <!-- 每行的Action动作模板 -->
      <div id="manage_user_action" style="display: none;">
          <div class="btn-group btn-group-xs">
              <button onclick="$.acooly.framework.show('/manage/rbac/rbacUser/show.html?id={0}',500,500);" class="btn btn-outline-primary btn-xs" type="button"><i class="fa fa-info fa-fw fa-col"></i>查看</button>
              <button onclick="$.acooly.framework.edit({url:'/manage/rbac/rbacUser/edit.html',id:'{0}',entity:'rbacUser',width:500,height:500});" class="btn btn-outline-primary btn-xs" type="button"><i class="fa fa-pencil fa-fw fa-col"></i>编辑</button>
              <button onclick="$.acooly.framework.remove('/manage/rbac/rbacUser/deleteJson.html','{0}','manage_user_datagrid');" class="btn btn-outline-primary btn-xs" type="button"><i class="fa fa-trash fa-fw fa-col"></i>删除</button>
          </div>
      </div>

      <!-- 表格的工具栏 -->
      <div id="manage_user_toolbar">
          <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/rbac/rbacUser/create.html',entity:'rbacUser',width:500,height:500})"><i class="fa fa-plus-circle fa-fw fa-col"></i>添加</a>
          <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/rbac/rbacUser/deleteJson.html','manage_user_datagrid')"><i class="fa fa-trash fa-fw fa-col"></i>批量删除</a>
          <a href="#" class="easyui-menubutton" data-options="menu:'#manage_user_exports_menu'"><i class="fa fa-cloud-download fa-fw fa-col"></i>批量导出</a>
          <div id="manage_user_exports_menu" style="width:150px;">
              <div onclick="$.acooly.framework.exports('/manage/rbac/rbacUser/exportXls.html','manage_user_searchform','用户表')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>
              <div onclick="$.acooly.framework.exports('/manage/rbac/rbacUser/exportCsv.html','manage_user_searchform','用户表')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>
          </div>
          <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/rbac/rbacUser/importView.html',uploader:'manage_user_import_uploader_file'});"><i class="fa fa-cloud-upload fa-fw fa-col"></i>批量导入</a>
      </div>
  </div>
    <script type="text/javascript">
        $(function () {
            $.acooly.framework.initPage('manage_user_searchform', 'manage_user_datagrid');
        });
    </script>
</div>
