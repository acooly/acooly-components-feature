<%@ page contentType="text/html;charset=UTF-8"%>
<html style="height: 95%; width: 95%">
<body style="height: 100%; margin: 0">

	<div id="container_${chartItemId}_loopTime" style="font-size:8px;color:	#D3D3D3;position: absolute;left:90%;margin-top:35px; "></div>
	<div id="container_${chartItemId}" style="height: 100%"></div>

	<script type="text/javascript" src="//cdn.bootcss.com/jquery/1.9.1/jquery.min.js" charset="utf-8"></script>
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
					
					var	chartItemsParams=data.data.chartItemsParams;
					
					//倒计时刷新
					refreshTimeValue(${chartItemId},${loopTime}/1000);

					//动态数据解决					
					pieChartDraw(title,legendData,xShaft,yShafts,chartItemsParams);
				}
			}
		});
	}
	
	//动态数据解决
	function pieChartDraw(title,legendData,xShaft,yShafts,chartItemsParams) {

		//是否显示 数值
		var isShow={};
		if(chartItemsParams.isShow=='YES'){
			isShow={normal: {
                formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ',
                backgroundColor: '#eee',
                borderColor: '#aaa',
                borderWidth: 1,
                borderRadius: 4,
                rich: {
                    a: {
                        color: '#999',
                        lineHeight: 22,
                        align: 'center'
                    },
                    hr: {
                        borderColor: '#aaa',
                        width: '100%',
                        borderWidth: 0.5,
                        height: 0
                    },
                    b: {
                        fontSize: 16,
                        lineHeight: 33
                    },
                    per: {
                        color: '#eee',
                        backgroundColor: '#334455',
                        padding: [2, 4],
                        borderRadius: 2
                    }
                }
            }
		};
	};


		
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
				name : '数据值',
				type : 'pie',
				radius : '70%',
				center : [ '50%', '60%' ],
				label: isShow,
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

