<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript" src="/manage/assert/plugin/jquery/3.4.1/jquery.min.js" charset="utf-8"></script>
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
	//合计
	var columnTotalValueHtml="";
	var totalValue = new Array();

	
	//列名--组装
	columnNameHtml="<tr style='font-size: 18px; height: 30px; background:SkyBlue;'>";
	for ( var i in columnName) {
		columnNameHtml=columnNameHtml+"<th style='padding-left:10px;'>"+columnName[i]+"</th>";
	}
	columnNameHtml=columnNameHtml+"</tr>";
	
	//列数据--组装
	var z=0;
	//小数位（位数）
	var fixedNum=0;
	for ( var x in xShaft) {
		columnValueHtml=columnValueHtml+"<tr style='font-size: 16px; height: 30px;'>";
		columnValueHtml=columnValueHtml+"<th style='padding-left:10px;'>"+xShaft[x]+"</th>";
		for(var y in yShafts){
			//数字类型			
			var yShaftsValueNumber=0;
			//行数据（z:行；y:列）
			var yShaftsValue=yShafts[y].data[z];
			
			// 测试小数
			//yShaftsValue=yShaftsValue+".01231";
			
			//小数判断（最大小数位数）
			if(yShaftsValue.indexOf(".")!=-1){
				var fixedNumLength=yShaftsValue.split(".")[1].length;
				if(fixedNumLength>fixedNum){
					fixedNum=fixedNumLength;
				}
			}
			
			//倍数
			var multiple=multipleCount(fixedNum);
			//数据类型转换
			yShaftsValueNumber=(Math.round(yShaftsValue*multiple)/multiple);
			
			columnValueHtml=columnValueHtml+"<th style='padding-left:10px;'>"+yShaftsValueNumber+"</th>";
			
			//合计-行数据
			if(z==0){
				totalValue.push(yShaftsValueNumber);
			}else{
				var currentValue=Math.round(totalValue[y]*multiple)/multiple ;
				totalValue[y]=(currentValue + yShaftsValueNumber).toFixed(fixedNum);
				//console.log("----累加值:"+totalValue[y]);
			}
		}
		columnValueHtml=columnValueHtml+"</tr>";
		z=z+1;
	}
	
	//合计
	columnTotalValueHtml=columnTotalValueHtml+"<tr style='font-size: 18px; height: 30px;'><th style='padding-left:20px;'>合计：</th>";
	for ( var t in totalValue) {
		columnTotalValueHtml=columnTotalValueHtml+"<th style='padding-left:10px;'>"+totalValue[t]+"</th>";
	}
	columnTotalValueHtml=columnTotalValueHtml+"</tr>";

	
	//动态模板html
	tableHtml=tableHtml+columnNameHtml+columnValueHtml+columnTotalValueHtml;
	
	$("#container_"+chartItemId+"_dataList_table").append(tableHtml);
}



/**
 * 计算倍数
 */
function multipleCount(num){
	var multiple=1;
	if(num>0){
		multiple=Math.pow(10,num);
	}
	return multiple;
}

</script>	
	
</html>