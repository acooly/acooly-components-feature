<#if ssoEnable>
    <#include "*/include.ftl">
</#if>
<script type="text/javascript">
    $(function () {
        $.acooly.framework.registerKeydown('manage_appWelcome_searchform', 'manage_appWelcome_datagrid');
    });
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding: 5px; overflow: hidden;" align="left">
        <form id="manage_appWelcome_searchform" onsubmit="return false">
            <table class="tableForm" width="100%">
                <tr>
                    <td align="left">
                        设置时间:<input size="15" id="search_GTE_createTime" name="search_GTE_createTime" size="15"
                                    onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/> 至<input size="15"
                                                                                                                   id="search_LTE_createTime"
                                                                                                                   name="search_LTE_createTime"
                                                                                                                   size="15"
                                                                                                                   onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                        状态:<input type="text" size="15" name="search_EQ_status"/>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false"
                           onclick="$.acooly.framework.search('manage_appWelcome_searchform','manage_appWelcome_datagrid');">查询</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_appWelcome_datagrid" class="easyui-datagrid" url="/manage/module/app/appWelcome/listJson.html"
               toolbar="#manage_appWelcome_toolbar" fit="true" border="false" fitColumns="false" pagination="true" idField="id"
               pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true"
               singleSelect="true">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
                <th field="id">主键</th>
                <th field="imageDefault" formatter="formatContent">默认图片</th>
                <th field="imageIphone4" formatter="formatContent">IPHONE4</th>
                <th field="imageIphone6" formatter="formatContent">iphone5/6: 1242*2208</th>
                <th field="imageAndroid" formatter="formatContent">android: 1080 * 1920</th>
                <th field="createTime" formatter="formatDate">创建时间</th>
                <th field="status">状态</th>
                <th field="rowActions"
                    data-options="formatter:function(value, row, index){return formatAction('manage_appWelcome_action',value,row)}">动作
                </th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_appWelcome_action" style="display: none;">
            <a class="line-action icon-show" onclick="$.acooly.framework.show('/manage/module/app/appWelcome/show.html?id={0}',500,400);"
               href="#" title="查看"></a> <a class="line-action icon-edit"
                                           onclick="$.acooly.framework.edit({url:'/manage/module/app/appWelcome/edit.html',id:'{0}',entity:'appWelcome',width:500,height:400});"
                                           href="#" title="编辑"></a> <a class="line-action icon-delete"
                                                                       onclick="$.acooly.framework.remove('/manage/module/app/appWelcome/deleteJson.html','{0}','manage_appWelcome_datagrid');"
                                                                       href="#" title="删除"></a>
            <a onclick="moveTop('{0}')" href="#" title="置顶">
                <i class="fa fa-arrow-circle-up fa-lg fa-fw fa-col"></i>
            </a>
            <a onclick="moveUp('{0}')" href="#" title="上移"><i class="fa fa-arrow-up fa-lg fa-fw fa-col"></i></a>
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_appWelcome_toolbar">
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
               onclick="$.acooly.framework.create({url:'/manage/module/app/appWelcome/create.html',entity:'appWelcome',width:500,height:400})">添加</a>
        </div>
    </div>

</div>
<script type="text/javascript">

    function moveUp(id) {
        var path = contextPath + '/manage/module/app/appWelcome/moveUp.html?id='+id+"&"+new Date();
        move(path);
    }

    function moveTop(id) {
        var path = contextPath + '/manage/module/app/appWelcome/moveTop.html?id='+id+"&"+new Date();
        move(path);
    }


    function move(path) {
        $.ajax({
            url:path,
            success : function(result) {
                $.acooly.framework.search('manage_appWelcome_searchform','manage_appWelcome_datagrid');
                $.messager.show({title:'提示', msg:result.message, timeout:1000});
            },
            error:function(XmlHttpRequest){
                var e = XmlHttpRequest.responseText;
                var json = eval('(' + e + ')');
                console.log(json);
                $.messager.alert('提示', json.message);
            }
        });
    }
</script>