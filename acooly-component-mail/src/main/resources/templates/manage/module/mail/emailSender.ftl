<#if ssoEnable><#include "/manage/common/ssoInclude.ftl"></#if>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;">
        <form id="manage_emailSender_searchform" class="form-inline ac-form-search" onsubmit="return false">
            <div class="form-group">
                <label class="col-form-label">boss用户ID：</label>
                <input type="text" class="form-control form-control-sm" name="search_EQ_sysUserId"/>
            </div>
            <div class="form-group">
                <label class="col-form-label">boss登录名：</label>
                <input type="text" class="form-control form-control-sm" name="search_EQ_sysUserName"/>
            </div>
<#--            <div class="form-group">-->
<#--                <label class="col-form-label">boss真实姓名：</label>-->
<#--                <input type="text" class="form-control form-control-sm" name="search_EQ_sysRealName"/>-->
<#--            </div>-->
            <div class="form-group">
                <label class="col-form-label">邮箱用户名：</label>
                <input type="text" class="form-control form-control-sm" name="search_EQ_emailName"/>
            </div>
<#--            <div class="form-group">-->
<#--                <label class="col-form-label">boss真实姓名：</label>-->
<#--                <input type="text" class="form-control form-control-sm" name="search_EQ_sysRealName"/>-->
<#--            </div>-->
            <div class="form-group">
                <label class="col-form-label">启动tls：</label>
                <select name="search_EQ_startTls" class="form-control input-sm select2bs4">
                    <option value="">所有</option><#list allStartTlss as k,v><option value="${k}">${v}</option></#list>
                </select>
            </div>
            <div class="form-group">
                <label class="col-form-label">使用SSL连接：</label>
                <select name="search_EQ_sslConnect" class="form-control input-sm select2bs4">
                    <option value="">所有</option><#list allSslConnects as k,v><option value="${k}">${v}</option></#list>
                </select>
            </div>
            <div class="form-group">
                <label class="col-form-label">创建时间：</label>
                <input type="text" class="form-control form-control-sm" id="search_GTE_createTime" name="search_GTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
                <span class="mr-1 ml-1">至</span> <input type="text" class="form-control form-control-sm" id="search_LTE_createTime" name="search_LTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
            </div>
<#--            <div class="form-group">-->
<#--                <label class="col-form-label">修改时间：</label>-->
<#--                <input type="text" class="form-control form-control-sm" id="search_GTE_updateTime" name="search_GTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />-->
<#--                <span class="mr-1 ml-1">至</span> <input type="text" class="form-control form-control-sm" id="search_LTE_updateTime" name="search_LTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />-->
<#--            </div>-->
            <div class="form-group">
                <button class="btn btn-sm btn-primary" type="button" onclick="$.acooly.framework.search('manage_emailSender_searchform','manage_emailSender_datagrid');"><i class="fa fa-search fa-fw fa-col"></i> 查询</button>
            </div>
    </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_emailSender_datagrid" class="easyui-datagrid" url="/manage/module/mail/emailSender/listJson.html" toolbar="#manage_emailSender_toolbar" fit="true" border="false" fitColumns="false"
                pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" formatter="idFormatter">编号</th>
                <th field="id" sortable="true" >id</th>
                <th field="fromAddress" >发件人邮箱</th>
                <th field="fromName" >发件人名称</th>

                <th field="emailName">邮箱用户名</th>
                <th field="hostName" >SMTP服务器地址</th>
                <th field="hostPort" sortable="true" sum="true">SMTP服务器端口</th>
<#--                <th field="emailPassword" formatter="contentFormatter">邮箱密码</th>-->


                <th field="sslPort" sortable="true" sum="true">SSL链接的端口</th>
                <th field="startTls" formatter="mappingFormatter">启动tls</th>
                <th field="sslConnect" formatter="mappingFormatter">使用SSL连接</th>

                <th field="sysUserId" sortable="true" sum="true">boss用户id</th>
                <th field="sysUserName">boss登录名</th>
                <th field="sysRealName">boss真实姓名</th>
                <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
                <th field="updateTime" formatter="dateTimeFormatter">修改时间</th>
            </tr>
            </thead>
            <thead frozen="true">
            <tr>
                <th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_emailSender_action',value,row)}">动作</th>
            </tr>
            </thead>
        </table>
        <!-- 每行的Action动作模板 -->
        <div id="manage_emailSender_action" style="display: none;">
            <div class="btn-group btn-group-xs">
<#--              <button onclick="$.acooly.framework.show('/manage/module/mail/emailSender/show.html?id={0}',500,500);" class="btn btn-outline-primary btn-xs" type="button"><i class="fa fa-info fa-fw fa-col"></i>查看</button>-->
              <button onclick="$.acooly.framework.edit({url:'/manage/module/mail/emailSender/edit.html',id:'{0}',entity:'emailSender',width:500,height:500});" class="btn btn-outline-primary btn-xs" type="button"><i class="fa fa-pencil fa-fw fa-col"></i>编辑</button>
              <button onclick="$.acooly.framework.remove('/manage/module/mail/emailSender/deleteJson.html','{0}','manage_emailSender_datagrid');" class="btn btn-outline-primary btn-xs" type="button"><i class="fa fa-trash fa-fw fa-col"></i>删除</button>
          </div>
        </div>
        <!-- 表格的工具栏 -->
        <div id="manage_emailSender_toolbar">
            <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/module/mail/emailSender/create.html',entity:'emailSender',width:500,height:500})"><i class="fa fa-plus-circle fa-fw fa-col"></i>添加</a>
<#--            <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/module/mail/emailSender/deleteJson.html','manage_emailSender_datagrid')"><i class="fa fa-trash fa-fw fa-col"></i>批量删除</a>-->
<#--            <a href="#" class="easyui-menubutton" data-options="menu:'#manage_emailSender_exports_menu'"><i class="fa fa-cloud-download fa-fw fa-col"></i>批量导出</a>-->
<#--            <div id="manage_emailSender_exports_menu" style="width:150px;">-->
<#--              <div onclick="$.acooly.framework.exports('/manage/module/mail/emailSender/exportXls.html','manage_emailSender_searchform','短信发件人')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>-->
<#--              <div onclick="$.acooly.framework.exports('/manage/module/mail/emailSender/exportCsv.html','manage_emailSender_searchform','短信发件人')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>-->
<#--            </div>-->
<#--            <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/module/mail/emailSender/importView.html',uploader:'manage_emailSender_import_uploader_file'});"><i class="fa fa-cloud-upload fa-fw fa-col"></i>批量导入</a>-->
        </div>
    </div>
    <script type="text/javascript">
        $(function () {
            $.acooly.framework.initPage('manage_emailSender_searchform', 'manage_emailSender_datagrid');
        });
    </script>
</div>
