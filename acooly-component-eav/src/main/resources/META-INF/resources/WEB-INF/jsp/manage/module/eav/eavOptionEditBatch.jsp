<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div>
    <style>
        .table-list {
        }

        .table-list .header {
            background-color: #eee;
        }

        .table-list td {
            padding: 5px;
            border: 1px solid #ddd;
        }
    </style>
    <form id="manage_eavOption_editform"
          action="/manage/module/eav/eavOption/saveBatch.html"
          method="post">
        <input name="parentId" type="hidden" value="${parentId}"/>

        <div style="margin: 15px;">
            <span style="margin-right: 15px;">选项名称: ${parent.name}</span>
            <a onclick="$.manage.eav.option.append()" class="easyui-linkbutton" href="#" title="添加"><i
                    class="fa fa-plus-circle fa-lg fa-fw fa-col"></i> 添加选项</a>
        </div>
        <table id="manage_eavOption_formlist" class="table-list tableForm" width="100%">
            <tr class="header">
                <td>编码</td>
                <td>名称</td>
                <td>操作</td>
            </tr>
            <c:forEach items="${eavOptions}" var="e">
                <tr>
                    <td><input type="text" name="code_${e.id}" value="${e.code}" style="width: 200px"
                               placeholder="选项编码..."
                               class="easyui-validatebox text" data-options="validType:['length[1,64]'],required:true"/>
                    </td>
                    <td><input type="text" name="name_${e.id}" value="${e.name}" style="width: 200px"
                               placeholder="选项名称..."
                               class="easyui-validatebox text" data-options="validType:['length[1,64]'],required:true"/>
                    </td>
                    <td>
                        <a onclick="$.manage.eav.option.remove(this,${e.id});" href="#" title="删除"><i
                                class="fa fa-times-circle fa-lg fa-fw fa-col"></i></a>
                        <%--<a onclick="" href="#" title="上移"><i class="fa fa-arrow-up fa-lg fa-fw fa-col"></i></a>--%>
                        <%--<a onclick="" href="#" title="置顶"><i class="fa fa-step-backward fa-rotate-90 fa-lg fa-fw fa-col"></i></a>--%>
                        <input type="hidden" name="id_${e.id}" value="${e.id}">
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form>

    <script id="manage_eavOption_row_template" type="text/html">
        <tr>
            <td><input type="text" name="code_{%=id%}" style="width: 200px" placeholder="选项编码..."
                       class="easyui-validatebox text" data-options="validType:['length[1,64]'],required:true"/></td>
            <td><input type="text" name="name_{%=id%}" style="width: 200px" placeholder="选项名称..."
                       class="easyui-validatebox text" data-options="validType:['length[1,64]'],required:true"/></td>
            <td>
                <a onclick="$.manage.eav.option.remove(this,'{%=id%}')" href="#" title="删除"><i
                        class="fa fa-times-circle fa-lg fa-fw fa-col"></i></a>
                <%--<a onclick="" href="#" title="上移"><i class="fa fa-arrow-up fa-lg fa-fw fa-col"></i></a>--%>
                <%--<a onclick="" href="#" title="置顶"><i class="fa fa-step-backward fa-rotate-90 fa-lg fa-fw fa-col"></i></a>--%>
            </td>
        </tr>
    </script>
    <script>

        var eavOptionManage = {

            append: function () {
                var That = this;
                $('#manage_eavOption_formlist').append($.acooly.template.render(
                    $('#manage_eavOption_row_template').html(),
                    {id: That.randomId()},
                    null,
                    {"LEFT_DELIMITER": "'{%'", "RIGHT_DELIMITER": "'%}'"}
                    )
                );
            },

            remove: function (obj, id) {

                var item = $(obj).parent().parent();
                var id = item.find('input[name=id_'+id+']').val();
                if (id) {
                    $.acooly.framework.remove('/manage/module/eav/eavOption/deleteJson.html', id, 'manage_eavOption_datagrid', "删除选项", "是否要删除该选项", function () {
                        item.remove();
                    });
                } else {
                    var confirm = false;
                    item.find('input').each(function () {
                        if (!confirm && $(this).val() != '') {
                            confirm = true;
                        }
                    });
                    if (confirm) {
                        $.messager.confirm("删除选项", "本选项已填入了数据，确认要删除?", function (r) {
                            if (r) {
                                item.remove();
                            }
                        });
                    } else {
                        item.remove();
                    }
                }
            }

            randomId: function () {
                return (Math.random() * 10000000).toString(16).substr(0, 4) + '-' + (new Date()).getTime() + '-' + Math.random().toString().substr(2, 5);
            }

        }


        $(function () {
            if (!$.manage) $.manage = {eav: {}};
            $.extend($.manage.eav, {option: eavOptionManage});
        });


    </script>

</div>
