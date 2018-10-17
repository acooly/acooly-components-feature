<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<c:if test="${initParam['ssoEnable']=='true'}">
    <%@ include file="/WEB-INF/jsp/manage/common/ssoInclude.jsp" %>
</c:if>
<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_chart_searchform','manage_chart_datagrid');
});

function manage_chart_datagrid_onClickRow() {
    var tab = $('#manage_chartInfo_datagrid_sub_tab').tabs('getSelected');
    var index = $('#manage_chartInfo_datagrid_sub_tab').tabs('getTabIndex', tab);
    manage_chartInfo_selectTab(index);
}

function manage_chartInfo_selectTab(index) {
    
    var row = $.acooly.framework.getSelectedRow("manage_chart_datagrid");
    if(!row) return;
    if (index == 0) {
        console.info("load chart datagrid",index);
        load_manage_chartItems_datagrid();
    }
}

function load_manage_chartItems_datagrid() {
    $.acooly.framework.fireSelectRow('manage_chart_datagrid', function (row) {
        $.acooly.framework.loadGrid({
            gridId: "manage_chartItems_datagrid",
            url: '/manage/module/chart/chartItems/listJson.html',
            ajaxData: {"search_EQ_chartId": row.id}
        });
    }, '请先选择操作客户数据行');
}

function  manage_chartInfo_sub_tab_onSelect(title,index) {
    manage_chartInfo_selectTab(index)
}

function manage_chartItems_create() {
    $.acooly.framework.fireSelectRow('manage_chart_datagrid', function (row) {
        $.acooly.framework.create({
            url: '/manage/module/chart/chartItems/create.html',
            entity: 'chartItems',
            width: 1000, height: 700,
            ajaxData: {'chartId': row.id},
            hideSaveBtn:true,
            buttons:[{
                id:'manage_chartItems_btn_create',
                text:'<i class=\'fa fa-plus-circle fa-lg fa-fw fa-col\'></i>增加',
                handler:function(){
                    chartItems_editform_onSubmit();

                    $.acooly.framework.ajaxSubmitHandler('create', $(this), 'manage_chartItems_editform', 'manage_chartItems_datagrid');
                }
            }]
        });
    }, '请先选择操作客户数据行');
}

function  moveUp(id) {
    $.ajax({
        url:'/manage/module/chart/chartItems/moveUp.html',
        data : {'id':id},
        success : function(result) {
            if(!result.success)return;
            var  chartId = result.data.chartId;
            $.ajax({
                url:'/manage/module/chart/chartItems/queryJson.html',
                data : {'search_EQ_chartId':chartId},
                success : function(result) {
                    ;
                    if(!result.success)return;
                    $('#manage_chartItems_datagrid').datagrid('loadData',result);
                }
            });
        }
    });
}

function manage_chartData_create(itemsId) {
    $.acooly.framework.fireSelectRow('manage_chart_datagrid', function (row) {
        $.ajax({
            type: "POST",
            url: "/manage/module/chart/chartData/checkSaveOrEdit.html",
            data: {"itemsId":itemsId},
            success: function(result){
                if (result.success){
                    if (result.data.isSave){
                        $.acooly.framework.create({
                            url: '/manage/module/chart/chartData/create.html',
                            entity: 'chartData',
                            width: 1000, height: 700,
                            ajaxData: {'chartId': row.id,"itemsId":itemsId}
                        });
                    }else {
                        $.acooly.framework.create({
                            url: '/manage/module/chart/chartData/edit.html',
                            entity: 'chartData',
                            width: 1000, height: 700,
                            ajaxData: {'chartId': row.id,"itemsId":itemsId},
                            title:"编辑",
                            addButton:"修改"
                        });
                    }
                }
            }
        });
    }, '请先选择操作客户数据行');
}



</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <%--<div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">--%>
    <form id="manage_chart_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
          	<div>
					标题: <input type="text" class="text" size="15" name="search_LIKE_title"/>
				状态: <select style="width:80px;height:27px;" name="search_EQ_status" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allStatuss}"><option value="${e.key}" ${param.search_EQ_status == e.key?'selected':''}>${e.value}</option></c:forEach></select>
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false" onclick="$.acooly.framework.search('manage_chart_searchform','manage_chart_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
          	</div>
          </td>
        </tr>
      </table>
    </form>
  <%--</div>--%>

  <!-- 列表和工具栏 -->
  <div data-options="region:'north',border:false " style="height:55%;"> <%--修改--%>
    <table id="manage_chart_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/module/chart/chart/listJson.html" toolbar="#manage_chart_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true" data-options= "onClickRow:manage_chart_datagrid_onClickRow">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="id" sum="true">id</th>
			<th field="title">标题</th>
			<th field="status" formatter="mappingFormatter">状态</th>
		    <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
		    <th field="updateTime" formatter="dateTimeFormatter">更新时间</th>
			<th field="comments">备注</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_chart_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_chart_action" style="display: none;">
      <a onclick="$.acooly.framework.edit({url:'/manage/module/chart/chart/edit.html',id:'{0}',entity:'chart',width:500,height:400});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.show('/manage/module/chart/chart/show.html?id={0}',500,400);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.remove('/manage/module/chart/chart/deleteJson.html','{0}','manage_chart_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_chart_toolbar">
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/module/chart/chart/create.html',entity:'chart',width:500,height:400})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/module/chart/chart/deleteJson.html','manage_chart_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>
      <a href="#" class="easyui-menubutton" data-options="menu:'#manage_chart_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>
      <div id="manage_chart_exports_menu" style="width:150px;">
        <div onclick="$.acooly.framework.exports('/manage/module/chart/chart/exportXls.html','manage_chart_searchform','图表-主题')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>
        <div onclick="$.acooly.framework.exports('/manage/module/chart/chart/exportCsv.html','manage_chart_searchform','图表-主题')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>
      </div>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/module/chart/chart/importView.html',uploader:'manage_chart_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a>
    </div>
  </div>


    <div data-options="region:'center',border:false" style="height:40%;">
        <div id="manage_chartInfo_datagrid_sub_tab" class="easyui-tabs" fit="true" data-options="onSelect:manage_chartInfo_sub_tab_onSelect" > <%--修改--%>

            <!-- tab1 -->

            <div title="图表" style="margin-left: 0px;">
                <table id="manage_chartItems_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/module/chart/chartItems/listJson.html" toolbar="#manage_chartItems_toolbar" fit="true" border="false" fitColumns="false"
                       pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
                    <thead>
                    <tr>
                        <th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
                        <th field="id" sum="true">id</th>
                        <th field="chartId" sum="true">主题id</th>
                        <th field="title">标题</th>
                        <th field="type" formatter="mappingFormatter">图表类型</th>
                        <th field="status" formatter="mappingFormatter">状态</th>
                        <th field="loopTime" sum="true">循环时间</th>
                        <th field="xShaft">x轴</th>
                        <th field="yShaft">y轴</th>
                        <th field="orderTime" formatter="dateTimeFormatter">排序</th>
                        <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
                        <th field="updateTime" formatter="dateTimeFormatter">更新时间</th>
                        <th field="comments">备注</th>
                        <th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_chartItems_action',value,row)}">动作</th>
                    </tr>
                    </thead>
                </table>

                <!-- 每行的Action动作模板 -->
                <div id="manage_chartItems_action" style="display: none;">
                    <a href="#" class="easyui-linkbutton" plain="true" onclick="manage_chartData_create('{0}')" title="添加/修改sql"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i></a>
                    <a onclick="$.acooly.framework.edit({url:'/manage/module/chart/chartItems/edit.html',id:'{0}',entity:'chartItems',width:1000,height:700,hideSaveBtn:true,
             buttons:[{
             id:'manage_chartItems_btn_create',
                    text:'<i class=\'fa fa-plus-circle fa-lg fa-fw fa-col\'></i>修改',
                    handler:function(){
                      chartItems_editform_onSubmit();

                     $.acooly.framework.ajaxSubmitHandler('edit', $(this), 'manage_chartItems_editform', 'manage_chartItems_datagrid');
                    }
                    }]});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
                    <a onclick="$.acooly.framework.show('/manage/module/chart/chartItems/show.html?id={0}',500,400);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
                    <a onclick="$.acooly.framework.remove('/manage/module/chart/chartItems/deleteJson.html','{0}','manage_chartItems_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
                    <a onclick="moveUp('{0}')" href="#" title="上移"><i class="<%--line-action icon-movetop--%>line-action icon-moveup"></i></a>
                </div>

                <!-- 表格的工具栏 -->
                <div id="manage_chartItems_toolbar">
                    <a href="#" class="easyui-linkbutton" plain="true"  onclick="manage_chartItems_create()"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
                </div>
            </div>

        </div>
    </div>
</div>
