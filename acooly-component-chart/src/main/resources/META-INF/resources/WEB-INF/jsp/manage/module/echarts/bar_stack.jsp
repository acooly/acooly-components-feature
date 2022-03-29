<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html style="height: 95%; width: 95%">

<body style="height: 100%; margin: 0">
	<!-- 查询条件 -->
	<%@ include file="/WEB-INF/jsp/manage/module/echarts/common/where_search.jsp" %>

	<div id="container_${chartItemId}_loopTime" style="font-size:8px;color:	#D3D3D3;position: absolute;left:90%;margin-top:35px; "></div>
	<div id="container_${chartItemId}" style="height: 100%"></div>

	<!-- 数据列表 -->
	<%@ include file="/WEB-INF/jsp/manage/module/echarts/common/data_list.jsp" %>

	<script type="text/javascript" src="/manage/assert/plugin/jquery/3.4.1/jquery.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="/plugin/echarts/echarts.min.js"></script>
	<script type="text/javascript" src="/plugin/echarts/javascript/business.js"></script>

	<script type="text/javascript">
	//数据初始化
	ajaxRequest();
	
	//定时器
	if(${loopTime}>=10000){
		setInterval("ajaxRequest()",${loopTime});
	}
	
	//ajax 数据请求
	function ajaxRequest(){
		var title;
		var legendData = new Array();
		var xShaft = new Array();
		var yShafts = new Array();	
		
		//列名
		var columnName = new Array();	
		
		jQuery.ajax({
			url : "/manage/module/echarts/charData_bar_stack_${chartItemId}.html",
			data : $("#container_${chartItemId}_searchform").serialize(),
			cache : false,
			success : function(data) {
				console.log(data);
				if (data.success) {

					title=data.data.shaftData.title;
					var xShaftJson = data.data.shaftData.xShaft;
					var yShaftJsons = data.data.shaftData.yShafts;

					//x轴
					for ( var x in xShaftJson) {
						columnName.push(x);
						xShaft = xShaftJson[x].split(",");
					}
						console.log(xShaft);

					//y轴
					var yShaftJsonList = yShaftJsons[0];
					for ( var y in yShaftJsonList) {
						columnName.push(y);
						legendData.push(y);
						var yShaft = new Array();
						yShaft = yShaftJsonList[y].split(",");

						//是否显示 数值
						var isShow={};
						if(data.data.chartItemsParams.isShow=='YES'){
							isShow={normal: {
												show: true,
												position: 'insideRight'
											}
							};
						};
						
						var yShaftJson={
								name:y,
								type : 'bar',
								stack: 'bar_stack',
								label:isShow,//是否显示 数值
								data:yShaft
						};
						yShafts.push(yShaftJson);
					}
					console.log(yShafts);
						
					//倒计时刷新
					refreshTimeValue(${chartItemId},${loopTime}/1000);

					//动态数据解决
					barChartDraw(title,legendData,xShaft,yShafts);
					
					//动态数据列表					
					showChartDataList(${chartItemId},columnName,xShaft,yShafts);
				}
			}
		});
	}
	
	//动态数据解决
	function barChartDraw(title,legendData,xShaft,yShafts) {
		var dom = document.getElementById("container_"+${chartItemId});
		var myChart = echarts.init(dom);
		var app = {};
		option = null;
		option = {
			title : {
				text : title
			},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : legendData
			},
			toolbox : {
				show : true,
				feature : {
// 					dataView : {
// 						show : true,
// 						readOnly : false
// 					},
					magicType : {
						show : true,
						type : [ 'line', 'bar' ]
					},
// 					restore : {
// 						show : true
// 					},
					saveAsImage : {
						show : true
					}
				}
			},
			calculable : true,
			xAxis : [ {
				type : 'category',
				data : xShaft
			} ],
			yAxis : [ {
				type : 'value'
			} ],
			series :yShafts
		};

		;
		if (option && typeof option === "object") {
			myChart.setOption(option, true);
		}}
	</script>
</body>
</html>

