<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>
<c:if test="${initParam['ssoEnable']=='true'}">
    <%@ include file="/WEB-INF/jsp/manage/common/ssoInclude.jsp" %>
</c:if>
<script src="//cdn.bootcss.com/mustache.js/2.3.0/mustache.min.js"></script>
<script src="asset/eav/unserialize.jquery.1.0.3.js"></script>
<script src="asset/eav/eav.js"></script>
<script src="/acooly/eav/acooly.eav.js"></script>
<script type="text/javascript">

    if (!window.data) {
        window.data = {}
    }

    function manage_eavEntity_searchform_beforeSubmit(arguments) {
        window.data.eavSchemaId = $("#search_EQ_schemaId").val();
        constructColumns(window.data.eavSchemaId);
        return true;
    }


    function constructColumns(eavSchemaId) {
        $.ajax({
            url: "/eav/getEavSchema?id=" + eavSchemaId,
            async: false,
            success: function (result) {
                var attrs = result.data.attributes;
                var columns = [];
                columns.push({
                    "field": "showCheckboxWithId",
                    "title": "编号",
                    "width": 5,
                    "checkbox": true,
                    "formatter": function (value, row, index) {
                        return row.id;
                    }
                });
                columns.push({
                    "field": "id",
                    "title": "id"
                });
                for (var key in attrs) {
                    var attr = attrs[key]
                    var column = {
                        "field": attr.name,
                        "title": attr.displayName
                    }
                    columns.push(column);
                }
                columns.push({
                    "field": "entityCreateTime",
                    "title": "创建时间",
                });
                columns.push({
                    "field": "entityUpdateTime",
                    "title": "修改时间",
                });
                columns.push({
                    "field": "rowActions",
                    "title": "动作",
                    "formatter": function (value, row, index) {
                        return formatAction('manage_eavEntity_action', value, row)
                    }
                });
                c = []
                c.push(columns)
                $('#manage_eavEntity_datagrid').datagrid({
                    columns: c
                });
            }
        });
    }

    // //加载方案下拉菜单
    // $.ajax({
    //     url: "/eav/getEavSchemas",
    //     async: false,
    //     success: function (result) {
    //         var data = [];
    //         var first = true;
    //         // $("#search_EQ_schemaId").append("<option value='0'>请选择</option>");
    //         $.each(result.data, function (i, val) {
    //             data.push({"text": val.name, "id": val.id, "selected": first});
    //             if (first) {
    //                 window.data.eavSchemaId = val.id;
    //                 loadExtraQueryCondition(window.data.eavSchemaId);
    //             }
    //             first = false
    //             $("#search_EQ_schemaId").append("<option value='" + val.id + "'>" + val.name + "</option>");
    //         })
    //         window.data.eavSchemas = data;
    //     }
    // });
    // $("#search_EQ_schemaId").change(function () {
    //     window.data.eavSchemaId = $(this).val();
    //     loadExtraQueryCondition(window.data.eavSchemaId);
    // });
    //
    // function loadExtraQueryCondition(eavSchemaId) {
    //     $.ajax({
    //         url: "/eav/getEavSchema?id=" + eavSchemaId,
    //         async: false,
    //         success: function (result) {
    //             var attrs = result.data.attributes;
    //             var idx = 1;
    //             var html = "";
    //             var template = '{{attr.displayName}}:{{{input}}}';
    //
    //             for (var key in attrs) {
    //                 var attr = attrs[key];
    //                 attr.nullable=true;
    //                 attr.forQueryCondition=true;
    //                 html += appendTable(template, attr) + "&nbsp;&nbsp;&nbsp;&nbsp;";
    //                 if (idx % 7 == 0) {
    //                     html += "<br/>"
    //                 }
    //                 idx++;
    //             }
    //             $('#extraQueryCondition').html(html);
    //
    //         }
    //     });
    // }

    $(function () {
        $.acooly.framework.registerKeydown('manage_eavEntity_searchform', 'manage_eavEntity_datagrid');
    });

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding:5px; overflow: auto;height: 70px;" align="left">
        <form id="manage_eavEntity_searchform" onsubmit="return false">
            <table class="tableForm" width="100%">
                <tr>
                    <td align="left">
                        <div>
                            方案:
                            <%--<input type="text" class="text" size="15" name="search_EQ_schemaId"--%>
                            <%--id="search_EQ_schemaId"/>--%>
                            <select name="search_EQ_schemaId" style="width:100px;"
                                    id="search_EQ_schemaId">
                            </select>
                            <span id="extraQueryCondition"></span>
                            <%--内容: <input type="text" class="text" size="15" name="search_LIKE_value"/>--%>

                            <%--创建时间: <input size="12" class="text easyui-textbox" id="search_GTE_createTime"--%>
                                         <%--name="search_GTE_createTime"--%>
                                         <%--onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"--%>
                                         <%--style="height: 20px"/>--%>
                            <%--至<input size="12" class="text easyui-textbox" id="search_LTE_createTime"--%>
                                    <%--name="search_LTE_createTime"--%>
                                    <%--onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" style="height: 20px"/>--%>
                            <%--修改时间: <input size="10" class="text" id="search_GTE_updateTime" name="search_GTE_updateTime"--%>
                                         <%--onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"--%>
                                         <%--style="height: 20px"/>--%>
                            <%--至<input size="12" class="text easyui-textbox" id="search_LTE_updateTime"--%>
                                    <%--name="search_LTE_updateTime"--%>
                                    <%--onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" style="height: 20px"/>--%>
                            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false"
                               onclick="$.acooly.framework.search('manage_eavEntity_searchform','manage_eavEntity_datagrid');"><i
                                    class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_eavEntity_datagrid" class="easyui-datagrid"
               url="${pageContext.request.contextPath}/manage/module/eav/eavEntity/listJson.html?"
               toolbar="#manage_eavEntity_toolbar" fit="true" border="false" fitColumns="false"
               pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id"
               sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_eavEntity_action" style="display: none;">
            <a onclick="$.acooly.framework.edit({url:'/manage/module/eav/eavEntity/edit.html',id:'{0}',entity:'eavEntity',width:500,height:400});"
               href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.show('/manage/module/eav/eavEntity/show.html?id={0}',500,400);" href="#"
               title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.remove('/manage/module/eav/eavEntity/deleteJson.html','{0}','manage_eavEntity_datagrid');"
               href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_eavEntity_toolbar">
            <a href="#" class="easyui-linkbutton" plain="true"
               onclick="$.acooly.framework.create({url:'/manage/module/eav/eavEntity/create.html',entity:'eavEntity',width:500,height:400})"><i
                    class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
            <a href="#" class="easyui-linkbutton" plain="true"
               onclick="$.acooly.framework.removes('/manage/module/eav/eavEntity/deleteJson.html','manage_eavEntity_datagrid')"><i
                    class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>
            <a href="#" class="easyui-menubutton" data-options="menu:'#manage_eavEntity_exports_menu'"><i
                    class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>
            <div id="manage_eavEntity_exports_menu" style="width:150px;">
                <div onclick="$.acooly.framework.exports('/manage/module/eav/eavEntity/exportXls.html','manage_eavEntity_searchform','eav_entity')">
                    <i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel
                </div>
                <div onclick="$.acooly.framework.exports('/manage/module/eav/eavEntity/exportCsv.html','manage_eavEntity_searchform','eav_entity')">
                    <i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV
                </div>
            </div>
            <a href="#" class="easyui-linkbutton" plain="true"
               onclick="$.acooly.framework.imports({url:'/manage/module/eav/eavEntity/importView.html',uploader:'manage_eavEntity_import_uploader_file'});"><i
                    class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a>
        </div>
    </div>
    <script>
        $(function () {
            $.acooly.eav.schemeId = '${schemeId}';
            $.acooly.eav.loadAllSchemes();
        });
    </script>
</div>
