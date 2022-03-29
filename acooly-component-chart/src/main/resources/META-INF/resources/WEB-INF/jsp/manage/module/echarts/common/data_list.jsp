<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<c:if test="${chartItem.isDataListShow=='YES'}">
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
	columnNameHtml="<tr style='font-size: 18px; height: 30px;'>";
	for ( var i in columnName) {
		columnNameHtml=columnNameHtml+"<th>"+columnName[i]+"</th>";
	}
	columnNameHtml=columnNameHtml+"</tr>";
	
	//列数据--组装
	var z=0;
	for ( var x in xShaft) {
		columnValueHtml=columnValueHtml+"<tr style='font-size: 16px; height: 30px;'>";
		columnValueHtml=columnValueHtml+"<th>"+xShaft[x]+"</th>";
		for(var y in yShafts){
			columnValueHtml=columnValueHtml+"<th>"+yShafts[y].data[z]+"</th>";
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