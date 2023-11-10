<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Awesome-pyecharts</title>
    <!-- 下面两行的意思是，浏览器从网络上获取echart包，而不需要你配置任何编程环境  -->
    <script type="text/javascript" src="/manage/assert/plugin/jquery/3.4.1/jquery.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="/plugin/echarts/echarts.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="/plugin/echarts/ext/map/echarts-map-worldZH.js" charset="utf-8"></script>
    <script type="text/javascript" src="/plugin/echarts/ext/echarts-auto-tooltip.js" charset="utf-8"></script>


</head>
<body>
<!-- div标签表示一个块，它是我们的图片容器。所以属性style里 宽度和高度是可以自定义的，1800px代表1800像素，块的宽度和高度于生成图片的尺寸相关，在下面的饼状图中则同理。-->
<div id="container" style="width:100%; height:100%;"></div>
<script>

    var dom = document.getElementById("container");
    var myChart = echarts.init(dom);
    option = null;
    option = {
        //悬浮
        tooltip: {
            show: true,
            backgroundColor: "rgba(50,50,50,0.8)", //设置背景图片 rgba格式
            color: "black",
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
            show: true,
            min: 0,
            max: 1000,
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
            // itemStyle: {
            //     normal: {
            //         areaColor: '#B9C6D5FF',
            //         borderColor: '#5C78AAFF',
            //         //    fontSize:18
            //     },
            //     emphasis: {
            //         areaColor: '#0A74AD7F'
            //     }
            // }
        },
        series: [
            {
                name: '',
                type: 'map',
                mapType: "world",
                geoIndex: 0,
                label: {
                    show: false,
                    // position: "top",
                    // margin: 8,
                    normal: {
                        show: false,
                        fontSize: '10',
                        color: 'rgba(0,0,0,0.7)'
                    }
                },

                data: [
                    {
                        // ItemStyle 中设置每一个数据项的颜色
                        "name": "中国", "value": 999
                    },
                    {
                        // ItemStyle 中设置每一个数据项的颜色
                        "name": "美国", "value": 800
                    },
                    {
                        "name": "日本", "value": 10
                    },
                    {
                        "name": "法国", "value": 700
                    },
                    {
                        "name": "意大利", "value": 60
                    },
                    {
                        "name": "加拿大", "value": 500
                    },
                    {
                        "name": "United Kingdom", "value": 4
                    },
                    {
                        "name": "Spain", "value": 4
                    },
                    {
                        "name": "智利", "value": 4
                    },
                    {
                        "name": "阿根廷", "value": 123
                    }
                    , {
                        "name": "德国", "value": 220
                    },
                    {
                        "name": "澳大利亚", "value": 200
                    },
                    {
                        "name": "瑞士", "value": 1000
                    },
                    {
                        "name": "俄罗斯", "value": 888
                    },
                    {
                        "name": "Chile", "value": 1
                    },
                ]
            }
        ]
    };

    if (option && typeof option === "object") {
        myChart.setOption(option, true);
        // if (chartItemsParams.isShow == 'YES') {
        //数据轮播
        // tools.closeTooltip();
        tools.loopShowTooltip(myChart, option, {loopSeries: true});
        // }
        ;
    }



</script>
</body>
</html>