<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html style="display: flex;justify-content: center;">

<body style="height: ${chartHeight}%; width: ${chartWidth}%;margin-top: 5px;">

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
		function submitSearch(){
			
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
							columnName.push(x);
							xShaft = xShaftJson[x].split(",");
						}
// 						console.log(xShaft);
	
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

						var chartItemsParams = data.data.chartItemsParams;

						//倒计时刷新
						refreshTimeValue(${chartItemId},${loopTime}/1000);

						//动态数据解决
						lineChartDraw(title,legendData,xShaft,yShafts,chartItemsParams);
						
						//动态数据列表					
						showChartDataList(${chartItemId},columnName,xShaft,yShafts);
					}
				}
			});
		}

		//动态数据解决
		function lineChartDraw(title,legendData,xShaft,yShafts,chartItemsParams) {
			var titleShow = chartItemsParams.titleShow;
			var titleColor = chartItemsParams.titleColor;
			var titleAlign = chartItemsParams.titleAlign;
			var legendTop = chartItemsParams.legendTop;
			if(legendTop=='top'){
				legendTop='5%';
			}
			var legendAlign = chartItemsParams.legendAlign;

			var dom = document.getElementById("container_"+${chartItemId});
			var myChart = echarts.init(dom);
			var app = {};
			option = null;
			option = {
				title: {
					text: title,
					show: titleShow,
					x: titleAlign,
					textStyle:{
						color:titleColor
					}
				},
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					//上：'top', 中：'middle',下： 'bottom'
					top: legendTop,
					//左：'left', 中：'center', 下：'right'
					left: legendAlign,
					data : legendData
				},
				grid : {
					// top : '12%',
					// left : '4%',
					// right : '3%',
					// bottom : '10%',
					containLabel : true
				},
				toolbox : {
					show : false,
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

