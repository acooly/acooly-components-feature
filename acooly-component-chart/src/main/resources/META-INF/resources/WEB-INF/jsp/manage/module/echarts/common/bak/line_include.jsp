<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html style="height: 95%; width: 95%">
<link rel="stylesheet" href="/manage/assert/plugin/jquery-easyui/themes/acooly/basic.css" type="text/css"/>

<body style="height: 100%; margin: 0">

	<!-- 查询条件  -->
	<jsp:include page="/manage/module/echarts/chartSearch_${chartItemId}.html" flush="true"/>
	
	<div id="container_${chartItemId}_loopTime" style="font-size:8px;color:	#D3D3D3;position: absolute;left:90%;margin-top:35px; "></div>
	<div id="container_${chartItemId}" style="height: 100%"></div>

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
		function submitSearch(){
			
		}
		
		
		
		//ajax 数据请求
		function ajaxRequest(){
// 			alert();
			var title;
			var legendData = new Array();
			var xShaft = new Array();
			var yShafts = new Array();	
			
			jQuery.ajax({
				url : "/manage/module/echarts/charData_line_${chartItemId}.html",
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
							xShaft = xShaftJson[x].split(",");
						}
// 						console.log(xShaft);
	
						//y轴
						var yShaftJsonList = yShaftJsons[0];
						for ( var y in yShaftJsonList) {
							legendData.push(y);
							var yShaft = new Array();
							yShaft = yShaftJsonList[y].split(",");

							//是否显示 数值
							var isShow={};
							if(data.data.chartItemsParams.isShow=='YES'){
								isShow={normal: {
													show: true,
													position: 'top'
												}
								};
							};
							
							var yShaftJson={
									name:y,
									type : 'line',
									data:yShaft,
									label:isShow,//是否显示 数值
									smooth: true
							};

							yShafts.push(yShaftJson);
						}
// 						console.log(yShafts);
						
						//倒计时刷新
						refreshTimeValue(${chartItemId},${loopTime}/1000);

						//动态数据解决
						lineChartDraw(title,legendData,xShaft,yShafts);
					}
				}
			});
		}

		//动态数据解决
		function lineChartDraw(title,legendData,xShaft,yShafts) {
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
				grid : {
					left : '3%',
					right : '4%',
					bottom : '3%',
					containLabel : true
				},
				toolbox : {
					feature : {
// 						dataView : {
// 							show : true,
// 							readOnly : false
// 						},
						magicType : {
							show : true,
							type : [ 'line', 'bar' ]
						},
// 						restore : {
// 							show : true
// 						},
						saveAsImage : {
							show : true
						}
					}
				},
				xAxis : {
					type : 'category',
					boundaryGap : false,
					data :xShaft
				},
				yAxis : {
					type : 'value'
				},
				series : yShafts
			};

			if (option && typeof option === "object") {
				myChart.setOption(option, true);
			}

		}
	</script>
</body>
</html>

