<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
        <form id="manage_customer_searchform" class="form-inline ac-form-search" onsubmit="return false">
            <div class="form-group">
                <label class="col-form-label">用户名：</label>
                <input type="text" class="form-control form-control-sm" name="search_LIKE_username"/>
            </div>
            <div class="form-group">
                <label class="col-form-label">姓名：</label>
                <input type="text" class="form-control form-control-sm" name="search_LIKE_realName"/>
            </div>
            <div class="form-group">
                <label class="col-form-label">性别：</label>
                <select name="search_EQ_gender" class="form-control input-sm select2bs4">
                    <option value="">所有</option><#list allGenders as k,v>
                    <option value="${k}">${v}</option></#list>
                </select>
            </div>
            <div class="form-group">
                <label class="col-form-label">手机号码：</label>
                <input type="text" class="form-control form-control-sm" name="search_LIKE_mobileNo"/>
            </div>
            <div class="form-group">
                <label class="col-form-label">类型：</label>
                <select name="search_EQ_customerType" class="form-control select2bs4">
                    <option value="">所有</option><#list allCustomerTypes as k,v>
                    <option value="${k}">${v}</option></#list>
                </select>
            </div>
            <#--            <div class="form-group mr-2">-->
            <#--                <label class="col-form-label">创建时间：</label>-->
            <#--                <input type="text" class="form-control form-control-sm" id="search_GTE_createTime" name="search_GTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>-->
            <#--                <span class="mr-1 ml-1">至</span> <input type="text" class="form-control form-control-sm" id="search_LTE_createTime" name="search_LTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>-->
            <#--            </div>-->
            <div class="form-group mr-2">
                <label class="col-form-label">创建日期：</label>
                <button type="button" class="btn btn-sm btn-default float-right acooly-daterangepicker" style="width: 275px;"
                        data-start-name="search_GTE_createTime" data-end-name="search_LTE_createTime">
                    <i class="fa fa-calendar"></i> <span>选择日期段 </span> <i class="fa fa-caret-down"></i>
                </button>
            </div>
            <div class="form-group">
                <button class="btn btn-sm btn-primary" type="button" onclick="$.acooly.framework.search('manage_customer_searchform','manage_customer_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i> 查询</button>
            </div>
        </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_customer_datagrid" class="easyui-datagrid icheck-primary" url="/manage/module/test/security/customer/listJson.html" toolbar="#manage_customer_toolbar" fit="true" border="false" fitColumns="false"
               pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true"
               data-options="showFooter:true, onLoadSuccess: function (data) {$('#manage_customer_datagrid').datagrid('statistics','salary');}"
        >
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" formatter="idFormatter">编号</th>
                <th field="id" sortable="true">ID</th>
                <th field="username">用户名</th>
                <th field="birthday" formatter="dateFormatter">生日</th>
                <th field="gender" formatter="mappingFormatter">性别</th>
                <th field="idcardType" formatter="mappingFormatter">证件类型</th>
                <th field="idcardNo">身份证号码</th>
                <th field="photo" formatter="contentFormatter">照片</th>
                <th field="photoThum" formatter="contentFormatter">照片缩略图</th>
                <th field="content" formatter="contentFormatter">内容</th>
                <th field="customerType" formatter="mappingFormatter">客户类型</th>
                <th field="fee" sum="true">手续费</th>
                <th field="salary" sortable="true" sum="true">薪水</th>
                <th field="status" formatter="mappingFormatter">状态</th>
                <th field="subject">摘要</th>
                <th field="comments">备注</th>
                <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
                <th field="updateTime" formatter="dateTimeFormatter">更新时间</th>
            </tr>
            </thead>
            <thead frozen="true">
                <tr>
                    <th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_customer_action',value,row)}">动作</th>
                </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_customer_action" style="display: none;">
            <div class="btn-group btn-group-xs">
                <a onclick="$.acooly.framework.show('/manage/module/test/security/customer/show.html?id={0}',500,500);" class="btn btn-outline-primary btn-xs" href="#" title="查看"><i class="fa fa-file-o"></i> 查看</a>
                <a onclick="$.acooly.framework.edit({url:'/manage/module/test/security/customer/edit.html',id:'{0}',entity:'customer',width:500,height:500});" class="btn btn-outline-primary btn-xs" href="#" title="编辑"><i class="fa fa-pencil"></i> 编辑</a>
                <a onclick="$.acooly.framework.remove('/manage/module/test/security/customer/deleteJson.html','{0}','manage_customer_datagrid');" class="btn btn-outline-primary btn-xs" href="#" title="删除"><i class="fa fa-trash-o"></i> 删除</a>
            </div>
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_customer_toolbar">
            <@shiro.hasPermission name="customer:create">
                <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/module/test/security/customer/create.html',entity:'customer',width:500,height:500})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
            </@shiro.hasPermission>
            <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/module/test/security/customer/deleteJson.html','manage_customer_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>
            <a href="#" class="easyui-menubutton" data-options="menu:'#manage_customer_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>
            <div id="manage_customer_exports_menu" style="width:150px;">
                <div onclick="$.acooly.framework.exports('/manage/module/test/security/customer/exportXls.html','manage_customer_searchform','acoolycoder测试')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>
                <div onclick="$.acooly.framework.exports('/manage/module/test/security/customer/exportCsv.html','manage_customer_searchform','acoolycoder测试')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>
            </div>
            <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/module/test/security/customer/importView.html',uploader:'manage_customer_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a>
        </div>
    </div>
    <script type="text/javascript">
        $(function () {
            $.acooly.framework.initPage('manage_customer_searchform', 'manage_customer_datagrid');
        });
    </script>
</div>
