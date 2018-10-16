<%@ page contentType="text/html;charset=UTF-8"%>
<html style="height: 100%; width: 100%">
<body style="height: 100%; margin: 0">

	<div id="container_${chartItemId}" style="height: 100%"></div>

	<script type="text/javascript" src="//cdn.bootcss.com/jquery/1.9.1/jquery.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="/plugin/echarts/echarts.min.js"></script>


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
			
			jQuery.ajax({
				url : "/manage/module/echarts/charData_line_${chartItemId}.html",
				data : {dateTime:(new Date()).getTime()},
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
	
							var yShaftJson={
									name:y,
									type : 'line',
									data:yShaft
							};
							yShafts.push(yShaftJson);
						}
// 						console.log(yShafts);
						
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

