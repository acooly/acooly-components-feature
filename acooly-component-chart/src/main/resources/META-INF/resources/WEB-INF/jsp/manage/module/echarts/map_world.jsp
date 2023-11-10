<%@ page contentType="text/html;charset=UTF-8" %>
<html style="display: flex;justify-content: center;">

<body style="height: ${chartHeight}%; width: ${chartWidth}%;margin-top: 20px;">


<div id="container_${chartItemId}_title"></div>

<div id="container_${chartItemId}_loopTime"
     style="font-size: 8px; color: #D3D3D3; position: absolute; left: 90%; margin-top: 35px;"></div>

<div id="container_${chartItemId}"
     style="height: 98%; margin: 20px auto; margin-left:25px; border: 0px solid #ddd;"></div>

<script type="text/javascript" src="/manage/assert/plugin/jquery/3.4.1/jquery.min.js" charset="utf-8"></script>
<script type="text/javascript" src="/plugin/echarts/echarts.min.js" charset="utf-8"></script>
<script type="text/javascript" src="/plugin/echarts/ext/map/echarts-map-worldZH.js" charset="utf-8"></script>
<script type="text/javascript" src="/plugin/echarts/ext/echarts-auto-tooltip.js" charset="utf-8"></script>
<script type="text/javascript" src="/plugin/echarts/javascript/business.js" charset="utf-8"></script>


<script type="text/javascript">
    //数据初始化
    ajaxRequest();

    //定时器
    if (${loopTime}>=
    10000
    )
    {
        setInterval("ajaxRequest()", ${loopTime});
    }

    //ajax 数据请求
    function ajaxRequest() {
        var title;
        var legendData = new Array();
        var xShaft = new Array();
        var yShaft = new Array();
        var yShafts = new Array();

        //数据值区间
        var maxNum = 0;
        var minNum = 0;

        jQuery.ajax({
            url: "/manage/module/echarts/charData_map_world_${chartItemId}.html",
            data: {dateTime: (new Date()).getTime()},
            cache: false,
            success: function (data) {
                if (data.success) {
                    title = data.data.shaftData.title;
                    var xShaftJson = data.data.shaftData.xShaft;
                    var yShaftJsons = data.data.shaftData.yShafts;

                    var mapData = new Array();

                    //x轴
                    for (var x in xShaftJson) {
                        xShaft = xShaftJson[x].split(",");
                    }

                    //y轴
                    var yShaftJsonList = yShaftJsons[0];
                    for (var y in yShaftJsonList) {
                        yShaft = yShaftJsonList[y].split(",");
                    }

                    for (var i = 0; i < xShaft.length; i++) {
                        var name = xShaft[i];
                        var num = parseFloat(yShaft[i]);
                        if (i == 0) {
                            maxNum = num;
                            minNum = num;
                        }

                        if (num > maxNum) {
                            maxNum = num;
                        }
                        if (num < minNum) {
                            minNum = num;
                        }
                        var jsonstr = '{"name":"' + name + '", "value":"' + num + '"}';
                        var jsonObject = jQuery.parseJSON(jsonstr)
                        mapData.push(jsonObject);
                    }

                    // console.log(maxNum+"==========="+minNum);
// 					console.log(mapData);

                    var chartItemsParams = data.data.chartItemsParams;

                    //倒计时刷新
                    refreshTimeValue(${chartItemId}, ${loopTime}/1000);

                    //动态数据解决
                    mapWorldChartDraw(title, maxNum, minNum, mapData, chartItemsParams);
                }
            }
        });
    }

    //动态数据解决
    function mapWorldChartDraw(title, maxNum, minNum, mapData, chartItemsParams) {
        console.log("-----" + mapData);
        var isShow = chartItemsParams.isShow;
        if (isShow == 'YES') {
            isShow = true;
        } else {
            isShow = false;
        }
        var titleShow = chartItemsParams.titleShow;
        var titleColor = chartItemsParams.titleColor;
        var titleAlign = chartItemsParams.titleAlign;
        var legendTop = chartItemsParams.legendTop;
        var legendLabelShow = chartItemsParams.legendLabelShow;
        if (legendTop == 'top') {
            legendTop = '5%';
        }
        var legendAlign = chartItemsParams.legendAlign;
        var legendLabelShow = chartItemsParams.legendLabelShow;

        //上：'top', 中：'middle',下： 'bottom'
        //左：'left', 中：'center', 下：'right'
        //标题
        var titleDiv = $("#container_"+${chartItemId}+
        "_title"
    )
        ;
        if (titleShow) {
            if (titleAlign == 'left') {
                titleDiv.attr("style", "font-size: 16px; position: absolute; left: 5%; margin-top: 5px; font-weight:bold;color:" + titleColor);
            } else if (titleAlign == 'center') {
                titleDiv.attr("style", "font-size: 16px; position: absolute; left: 50%; margin-top: 5px; font-weight:bold;color:" + titleColor);
            } else if (titleAlign == 'right') {
                titleDiv.attr("style", "font-size: 16px; position: absolute; left: 90%; margin-top: 5px; font-weight:bold;color:" + titleColor);
            }
            titleDiv.html(title);
        }

        var dom = document.getElementById("container_" +${chartItemId});
        var myChart = echarts.init(dom);
        option = null;
        option = {

            // tooltip: {
            //     triggerOn: 'mousemove', // 触发方式为鼠标移动到地图上时显示 tooltip
            //     position: 'top-left', // tooltip 的位置在地图的左上角
            //     formatter: function (params) { // tooltip 的数据格式化函数
            //         var data = params.data || {}; // 获取当前鼠标悬停的数据项
            //         return data.name + ' : ' + data.value; // 在 tooltip 中显示数据项的名称和值
            //     },
            //     axisPointer: { // 设置轴指示器的样式和行为
            //         type: 'shadow' // 使用阴影类型指示器
            //     }
            // },

            //悬浮
            tooltip: {
                show: true,
                backgroundColor: "rgba(50,50,50,0.8)", //设置背景图片 rgba格式
                color: "black",
                // position: ['50%', '50%'],
                // position: function (pos, params, dom, rect, size) {
                //     // 鼠标在左侧时 tooltip 显示到右侧，鼠标在右侧时 tooltip 显示到左侧。
                //     var obj = {top: 60};
                //     obj[['left', 'right'][+(pos[0] < size.viewSize[0] / 2)]] = 5;
                //     return obj;
                // },
                position:function(p){   //其中p为当前鼠标的位置
                    return [p[0] + 15, p[1] - 60];
                },
                borderWidth: "1", //边框宽度设置1
                borderColor: "gray", //设置边框颜色
                textStyle: {
                    color: "white" //设置文字颜色
                },
                formatter: function (params, ticket, callback) {
                    if (!isNaN(params.value)) {
                        return params.name + ' ：' + params.value;
                    } else {
                        return params.name + ' ：' + 0;
                    }
                }
            },
            visualMap: {
                show: legendLabelShow,
                min: minNum,
                max: maxNum,
                // type: 'piecewise',
                // splitNumber: 5,
                // formatter: function (value) {                //标签的格式化工具。
                //     return value;                   // 范围标签显示内容。
                // },
                // left: legendAlign,
                // top: legendTop,
                text: ['高', '低'],
                inRange: {
                    color: ['#e0ffff', '#006edd'],
                }
                // inRange: {
                //     color: [
                //         "#313695",
                //         "#4575b4",
                //         "#74add1",
                //         "#abd9e9",
                //         "#e0f3f8",
                //         "#ffffbf",
                //         "#fee090",
                //         "#fdae61",
                //         "#f46d43",
                //         "#d73027",
                //         "#a50026",
                //     ],
                // }
            },
            // roam: false,
            // zoom: 5.50,
            // // 去除各个国家上的小红点
            // showLegendSymbol: false,
            geo: {
                left: '5',
                right: '5',
                // center: [117.98561551896913, 31.205000490896193],
                zoom: 0.90,// 改变这个值的大小就可以了
                map: 'world',
                roam: false,
                label: {
                    normal: {
                        show: isShow,
                        fontSize: '10',
                        color: 'rgba(0,0,0,0.7)'
                    }
                },
                itemStyle: {
                    normal: {
                        //区域背景色
                        borderColor: 'rgba(0, 0, 0.2, 0.5)'
                    },
                    emphasis: {
                        areaColor: '#F3B329',
                        shadowOffsetX: 0,
                        shadowOffsetY: 0,
                        shadowBlur: 20,
                        borderWidth: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            },
            series: [
                {
                    name: '',
                    type: 'map',
                    mapType: "world",
                    geoIndex: 0,
                    label: {
                        show: true,
                        // position: "top",
                        // margin: 8,
                        normal: {
                            show: false,
                            fontSize: '10',
                            color: 'rgba(0,0,0,0.7)'
                        }
                    },

                    data: mapData
                }
            ]
        };

        if (option && typeof option === "object") {
            myChart.setOption(option, true);
            // if (chartItemsParams.isShow == 'YES') {
                //数据轮播
                tools.loopShowTooltip(myChart, option, {loopSeries: true});
            // };
        }
    }
</script>
</body>
</html>

