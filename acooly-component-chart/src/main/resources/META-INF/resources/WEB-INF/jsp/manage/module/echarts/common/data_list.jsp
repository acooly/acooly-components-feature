<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript" src="/plugin/echarts/javascript/jquery.table2excel.js" charset="utf-8"></script>
<script type="text/javascript" src="/plugin/echarts/javascript/download.js" charset="utf-8"></script>

<html>


	<c:if test="${chartItem.isDataListShow=='YES'}">
		<button onclick="downloadToExcel('container_${chartItemId}_dataList_table')"  class="btn btn-sm btn-primary" style=" position: absolute;left:85%;margin-top:-25px; ">下载Excel</button>

		<div  style="margin-left:8%; margin-top:10px; padding-bottom: 50px; ">
		    <table id="container_${chartItemId}_dataList_table"  class="easyui-datagrid" border="1" width="90%">
		    </table>
		</div>
	</c:if>
	
	
<script type="text/javascript">
	
/**
 * 显示数据格式
 * @param chartItemId
 * @param columnName
 * @param xShaft
 * @param yShafts
 * @returns
 */
function  showChartDataList(chartItemId,columnName,xShaft,yShafts) {
	$("#container_"+chartItemId+"_dataList_table").empty();
	//表HTML
	var tableHtml="";
	//列名
	var columnNameHtml = "";
	//列数据
	var columnValueHtml = "";
	
	//列名--组装
	columnNameHtml="<tr style='font-size: 18px; height: 30px; background:SkyBlue;'>";
	for ( var i in columnName) {
		columnNameHtml=columnNameHtml+"<th style='padding-left:10px;'>"+columnName[i]+"</th>";
	}
	columnNameHtml=columnNameHtml+"</tr>";
	
	//列数据--组装
	var z=0;
	for ( var x in xShaft) {
		columnValueHtml=columnValueHtml+"<tr style='font-size: 16px; height: 30px;'>";
		columnValueHtml=columnValueHtml+"<th style='padding-left:10px;'>"+xShaft[x]+"</th>";
		for(var y in yShafts){
			columnValueHtml=columnValueHtml+"<th style='padding-left:10px;'>"+yShafts[y].data[z]+"</th>";
		}
		columnValueHtml=columnValueHtml+"</tr>";
		z=z+1;
	}
	
	//动态模板html
	tableHtml=tableHtml+columnNameHtml+columnValueHtml;
	
	$("#container_"+chartItemId+"_dataList_table").append(tableHtml);
}
</script>	
	
</html>