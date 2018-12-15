<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>


<script type="text/javascript">
    $(function () {
        $.acooly.framework.registerKeydown('manage_app_searchform', 'manage_app_datagrid');
    });

    function manage_app_add() {
        $.acooly.framework.create({
            url: '/manage/module/app/app/create.html', entity: 'app', width: 500, height: 400, hideSaveBtn: true, buttons: [
                {
                    id: "manage_app_btn_ok",
                    text: "<i class=\"fa fa-book fa-lg fa-fw fa-col\"></i>审批通过",
                    handler: function () {
                        // 获取按钮当前ID
                        console.info($(this).attr('id'));
                        $.messager.alert('提示', "这个审批按钮，这里可以通过JS操作表单的值，然后再调用ajaxSubmitHander提交表单，行为与原来的提交表单一致");

                        // do something. eg: change action url; change form-field value.
                        // ...

                        // 提交表单（create or edit）,前4个参数必选。
                        $.acooly.framework.ajaxSubmitHandler("create", $(this), "manage_app_editform", "manage_app_datagrid", true,
                            function () {
                                // onSubmit: before submit
                                console.info("onsubmit");
                                return true;
                            },
                            function () {
                                // onSuccess
                                console.info("onSuccess");
                            },
                            function () {
                                // onFailure
                                console.info("onFailure");
                            }
                        );

                    }
                }, {
                    id: "manage_app_btn_no",
                    text: "<i class=\"fa fa-book fa-lg fa-fw fa-col\"></i>审批驳回",
                    handler: function () {
                        $.acooly.framework.ajaxSubmitHandler("create", $(this), "manage_app_editform", "manage_app_datagrid");
                    }
                }
            ]
        });
    }

    function manage_app_edit(id) {
        $.acooly.framework.edit({
            url: '/manage/module/app/app/edit.html',
            id: id,
            entity: 'app',
            reload: true,
            width: 500,
            height: 400,
            buttons: [{
                text: "<i class=\"fa fa-book fa-lg fa-fw fa-col\"></i>审批通过",
                handler: function () {
                    console.info($(this).attr('id'));
                    $.messager.alert('提示', "这个审批按钮");
                }
            }]
        });
    }


</script>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
        <form id="manage_app_searchform" onsubmit="return false">
            <table class="tableForm" width="100%">
                <tr>
                    <td align="left">
                        <div>
                            parent_app_id: <input type="text" class="text" size="15" name="search_EQ_parentAppId"/>
                            user_id: <input type="text" class="text" size="15" name="search_EQ_userId"/>
                            创建时间: <input size="15" class="text" id="search_GTE_rawAddTime" name="search_GTE_rawAddTime"
                                         onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
                            至<input size="15" class="text" id="search_LTE_rawAddTime" name="search_LTE_rawAddTime"
                                    onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
                            修改时间: <input size="15" class="text" id="search_GTE_rawUpdateTime" name="search_GTE_rawUpdateTime"
                                         onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
                            至<input size="15" class="text" id="search_LTE_rawUpdateTime" name="search_LTE_rawUpdateTime"
                                    onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
                            parent_id: <input type="text" class="text" size="15" name="search_EQ_parentId"/>
                            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false"
                               onclick="$.acooly.framework.search('manage_app_searchform','manage_app_datagrid');"><i
                                    class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_app_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/module/app/app/listJson.html"
               toolbar="#manage_app_toolbar" fit="true" border="false" fitColumns="false"
               pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc"
               checkOnSelect="true" selectOnCheck="true" singleSelect="true">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
                <th field="id">id</th>
                <th field="displayName">display_name</th>
                <th field="name">name</th>
                <th field="parentAppId">parent_app_id</th>
                <th field="type">type</th>
                <th field="userId">user_id</th>
                <th field="rawAddTime" formatter="formatDate">创建时间</th>
                <th field="rawUpdateTime" formatter="formatDate">修改时间</th>
                <th field="parentId">parent_id</th>
                <th field="rowActions"
                    data-options="formatter:function(value, row, index){return formatAction('manage_app_action',value,row)}">动作
                </th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_app_action" style="display: none;">
            <a onclick="manage_app_edit('{0}');" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.show('/manage/module/app/app/show.html?id={0}',500,400);" href="#" title="查看"><i
                    class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.remove('/manage/module/app/app/deleteJson.html','{0}','manage_app_datagrid');" href="#"
               title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_app_toolbar">
            <a href="#" class="easyui-linkbutton" plain="true" onclick="manage_app_add()"><i
                    class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
            <a href="#" class="easyui-linkbutton" plain="true"
               onclick="$.acooly.framework.removes('/manage/module/app/app/deleteJson.html','manage_app_datagrid')"><i
                    class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>
            <a href="#" class="easyui-menubutton" data-options="menu:'#manage_app_exports_menu'"><i
                    class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>
            <div id="manage_app_exports_menu" style="width:150px;">
                <div onclick="$.acooly.framework.exports('/manage/module/app/app/exportXls.html','manage_app_searchform','app')"><i
                        class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel
                </div>
                <div onclick="$.acooly.framework.exports('/manage/module/app/app/exportCsv.html','manage_app_searchform','app')"><i
                        class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV
                </div>
            </div>
            <a href="#" class="easyui-linkbutton" plain="true"
               onclick="$.acooly.framework.imports({url:'/manage/module/app/app/importView.html',uploader:'manage_app_import_uploader_file'});"><i
                    class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a>
        </div>
    </div>

</div>
