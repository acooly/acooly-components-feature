<%@ page contentType="text/html;charset=UTF-8"%>
<html style="height: 95%; width: 95%">
<body style="height: 100%; margin: 0">

	<div id="container_${chartItemId}_title" style="font-size: 14px; position: absolute; left: 90%; margin-top: 10px;"></div>
		
	<div id="container_${chartItemId}_loopTime" style="font-size: 8px; color: #D3D3D3; position: absolute; left: 90%; margin-top: 35px;"></div>
	
	<div id="container_${chartItemId}" style="height: 98%; margin: 20px auto; margin-left:25px; border: 0px solid #ddd;"></div>
		
	<script type="text/javascript" src="//cdn.bootcss.com/jquery/1.9.1/jquery.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="/plugin/echarts/echarts.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="/plugin/echarts/javascript/business.js" charset="utf-8"></script>
	<script type="text/javascript" src="/plugin/echarts/ext/map/echarts-map-China.js" charset="utf-8"></script>
	<script type="text/javascript" src="/plugin/echarts/ext/echarts-auto-tooltip.js" charset="utf-8"></script>	
		
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
		var yShaft = new Array();
		var yShafts = new Array();	
		
		//数据值区间
		var maxNum=0;
		var minNum=0;
		
		jQuery.ajax({
			url : "/manage/module/echarts/charData_map_China_${chartItemId}.html",
			data : {dateTime:(new Date()).getTime()},
			cache : false,
			success : function(data) {
				if (data.success) {
					title=data.data.shaftData.title;
					var xShaftJson = data.data.shaftData.xShaft;
					var yShaftJsons = data.data.shaftData.yShafts;

					var mapData = new Array();	

					//x轴
					for ( var x in xShaftJson) {						
						xShaft = xShaftJson[x].split(",");
					}

					//y轴
					var yShaftJsonList = yShaftJsons[0];
					for ( var y in yShaftJsonList) {
						yShaft = yShaftJsonList[y].split(",");
					}
					
					for (var i=0; i<xShaft.length; i++)
					{ 
						var name=xShaft[i];
						var num=parseFloat(yShaft[i]);
						if(i==0){
							maxNum=num;
							minNum=num;
						}

						if(num>maxNum){
							maxNum=num;	
						}
						if(num<minNum){
							minNum=num;	
						}
						
						//特殊处理省市
						if(name.indexOf("省") != -1 ){
							name=name.replace('省','');		
						}
						if(name.indexOf("市") != -1 ){
							name=name.replace('市','');		
						}
						if(name.indexOf("自治区") != -1 ){
							name=name.replace('自治区','');		
						}
						if(name.indexOf("壮族") != -1 ){
							name=name.replace('壮族','');		
						}
						if(name.indexOf("回族") != -1 ){
							name=name.replace('回族','');		
						}
						if(name.indexOf("维吾尔") != -1 ){
							name=name.replace('维吾尔','');	
						}
						if(name.indexOf("特别行政区") != -1 ){
							name=name.replace('特别行政区','');
						}
						
						var jsonstr='{"name":"'+name+'", "value":"'+num+'"}';
						var jsonObject= jQuery.parseJSON(jsonstr)
						mapData.push(jsonObject);
					}

					// console.log(maxNum+"==========="+minNum);
// 					console.log(mapData);
					
					var	chartItemsParams=data.data.chartItemsParams;

					//倒计时刷新
					refreshTimeValue(${chartItemId},${loopTime}/1000);

					//动态数据解决					
					mapChartDraw(title,maxNum,minNum,mapData,chartItemsParams);
				}
			}
		});
	}
	
	//动态数据解决
	function mapChartDraw(title,maxNum,minNum,mapData,chartItemsParams) {
		$("#container_"+${chartItemId}+"_title").html(title);
		var dom = document.getElementById("container_"+${chartItemId});
		var myChart = echarts.init(dom);
		var app = {};
		option = null;
		option = {
	            tooltip: {
	            	  backgroundColor: "rgba(50,50,50,0.8)", //设置背景图片 rgba格式
	            	  color: "black",
	            	  borderWidth: "1", //边框宽度设置1
	            	  borderColor: "gray", //设置边框颜色
	            	  textStyle: {
	            		    color: "white" //设置文字颜色
	            		  },
	                  formatter:function(params, ticket, callback){
	                    	 if(!isNaN(params.value)){
	 	                        return params.name+' ：'+params.value;
                             }
	                    }
	                },
	            visualMap: {
	                min: minNum,
	                max: maxNum,
// 	                type: 'piecewise',
// 	                splitNumber:5, 
// 					formatter: function (value) {                //标签的格式化工具。
// 	                    return value;                   // 范围标签显示内容。
// 	                },
	                left: 'left',
	                top: 'bottom',
	                text: ['高','低'],
	                inRange: {
	                    color: ['#e0ffff', '#006edd'],
	                },
	                show:true
	            },
	            geo: {
	                map: 'china',
	                roam: false,
	                zoom:1.23,
	                label: {
	                    normal: {
	                        show: true,
	                        fontSize:'10',
	                        color: 'rgba(0,0,0,0.7)'
	                    }
	                },
	                itemStyle: {
	                    normal:{
	                        borderColor: 'rgba(0, 0, 0, 0.2)'
	                    },
	                    emphasis:{
	                        areaColor: '#F3B329',
	                        shadowOffsetX: 0,
	                        shadowOffsetY: 0,
	                        shadowBlur: 20,
	                        borderWidth: 0,
	                        shadowColor: 'rgba(0, 0, 0, 0.5)'
	                    }
	                }
	            },
	            series : [
	                {
	                    name: '',
	                    type: 'map',
	                    geoIndex: 0,
	                    data:mapData
	                }
	            ]
	        };

		if (option && typeof option === "object") {
			myChart.setOption(option, true);
			if(chartItemsParams.isShow=='YES'){
				//数据轮播
				tools.loopShowTooltip(myChart, option, {loopSeries: true}); 
			};
		}
	}
	</script>
</body>
</html>

