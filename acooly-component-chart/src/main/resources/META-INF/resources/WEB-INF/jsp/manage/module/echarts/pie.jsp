<%@ page contentType="text/html;charset=UTF-8"%>
<html style="height: 100%; width: 100%">
<body style="height: 100%; margin: 0">

	<div id="container_${chartItemId}" style="height: 100%"></div>

	<script type="text/javascript"
		src="//cdn.bootcss.com/jquery/1.9.1/jquery.min.js" charset="utf-8"></script>
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
			url : "/manage/module/echarts/charData_pie_${chartItemId}.html",
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
//						console.log(xShaft);

					//y轴
					var yShaftJsonList = yShaftJsons[0];
					for ( var y in yShaftJsonList) {
						legendData.push(y);
						var yShaft = new Array();
						yShaft = yShaftJsonList[y].split(",")[0];

						var yShaftJson={
								name:y,
								value:yShaft
						};
						yShafts.push(yShaftJson);
					}
// 						console.log(yShafts);
					
					pieChartDraw(title,legendData,xShaft,yShafts);
				}
			}
		});
	}
	
	//动态数据解决
	function pieChartDraw(title,legendData,xShaft,yShafts) {
		var dom = document.getElementById("container_"+${chartItemId});
		var myChart = echarts.init(dom);
		var app = {};
		option = null;
		option = {
			title : {
				text : title,
				x : 'center'
			},
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			legend : {
				orient : 'vertical',
				left : 'left',
				data : legendData
			},
			series : [ {
				name : '访问来源',
				type : 'pie',
				radius : '75%',
				center : [ '50%', '60%' ],
				data : yShafts,
				itemStyle : {
					emphasis : {
						shadowBlur : 10,
						shadowOffsetX : 0,
						shadowColor : 'rgba(0, 0, 0, 0.5)'
					}
				}
			} ]
		};
		;
		if (option && typeof option === "object") {
			myChart.setOption(option, true);
		}
	}
	</script>
</body>
</html>

