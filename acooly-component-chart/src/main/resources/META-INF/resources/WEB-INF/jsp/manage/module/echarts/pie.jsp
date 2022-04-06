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
			url : "/manage/module/echarts/charData_pie_${chartItemId}.html",
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
//						console.log(xShaft);

					//y轴
					var yShaftJsonList = yShaftJsons[0];
					for ( var y in yShaftJsonList) {
						columnName.push(y);
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
				
					//动态数据列表					
					showPieChartDataList(${chartItemId},columnName,xShaft,yShafts);
					
				}
			}
		});
	}
	
	
	
	
	/**
	 * 显示数据格式
	 * @param chartItemId
	 * @param columnName
	 * @param xShaft
	 * @param yShafts
	 * @returns
	 */
	function  showPieChartDataList(chartItemId,columnName,xShaft,yShafts) {
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
		columnValueHtml=columnValueHtml+"<tr style='font-size: 16px; height: 30px;'>";
		for(var y in yShafts){
			columnValueHtml=columnValueHtml+"<th>"+yShafts[y].value+"</th>";
		}		
		columnValueHtml=columnValueHtml+"</tr>";

		//动态模板html
		tableHtml=tableHtml+columnNameHtml+columnValueHtml;
		
		$("#container_"+chartItemId+"_dataList_table").append(tableHtml);
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

