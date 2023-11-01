<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Awesome-pyecharts</title>
    <!-- 下面两行的意思是，浏览器从网络上获取echart包，而不需要你配置任何编程环境  -->
    <script type="text/javascript" src="/manage/assert/plugin/jquery/3.4.1/jquery.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="/plugin/echarts/echarts.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="/plugin/echarts/ext/map/echarts-map-worldZH.js" charset="utf-8"></script>


</head>
<body>
<!-- div标签表示一个块，它是我们的图片容器。所以属性style里 宽度和高度是可以自定义的，1800px代表1800像素，块的宽度和高度于生成图片的尺寸相关，在下面的饼状图中则同理。-->
<div id="container" class="chart-container" style="width:1800px; height:1200px;"></div>
<script>
    //  所有的脚本代码都写在script标签里
    var chart_container = echarts.init(
        document.getElementById('container'), 'white', {renderer: 'canvas'});
    // var option代表定义一个名为option的变量，后面花括号里的代表我们需要作图的参数设置
    var option_container = {
        // 默认的颜色数组 （如果不明确设置每个数据项的颜色，则会采用默认的数组
        // 此处的颜色为十六进制表示，也可以使用rgb来表示。简单地理解就是一串字符就代表一个颜色，挑选喜欢的颜色可以自行搜索颜色
        "color": ["#ac6767","#1d953f","#6950a1","#918597"],
        "series": [
            {
                // 图标类型为 地图
                "type": "map",
                "name": "test",
                "label": {
                    "show": false,
                    "position": "top",
                    "margin": 8
                },
                "mapType": "world",  // 地图类型为 世界地图
                // data里的每一个{}中，是一项数据整体，标明了该项数据的名称，值，以及颜色等参数。注意：此处的字符串需要加双引号""(输入法切换至英文状态)
                "data": [
                    {
                        // ItemStyle 中设置每一个数据项的颜色
                        "name": "China", "value": 43,
                        'itemStyle':{'color':"#c23531"}
                    },
                    {
                        // ItemStyle 中设置每一个数据项的颜色
                        "name": "United States", "value": 43,
                        'itemStyle':{'color':"#c23531"}
                    },
                    {
                        "name": "Japan","value": 17, // 注意项与项之间的逗号
                        'itemStyle':{'color':"#CD8500"}
                    },
                    {
                        "name": "France","value": 7,
                        'itemStyle':{'color':"#EE2C2C"}
                    },
                    {
                        "name": "Italy","value": 6,
                        'itemStyle':{'color':"#918597"}
                    },
                    {
                        "name": "Canada","value": 5,
                        'itemStyle':{'color':"#bda29a"}
                    },
                    {
                        "name": "United Kingdom","value": 4,
                        'itemStyle':{'color':"#6950a1"}
                    },
                    {
                        "name": "Spain","value": 4,
                        'itemStyle':{'color':"#1d953f"}
                    },
                    {
                        "name": "Holland","value": 4,
                        'itemStyle':{'color':"#2f4554"}
                    },
                    {
                        "name": "Belgium","value": 3,
                        'itemStyle':{'color':"#EEAD0E"}
                    }
                    ,                    {
                        "name": "Germany","value": 2,
                        'itemStyle':{'color':"#607B8B"}
                    },
                    {
                        "name": "Austria","value": 2,
                        'itemStyle':{'color':"#5CACEE"}
                    },
                    {
                        "name": "Switzerland","value": 1,
                        'itemStyle':{'color':"#4EEE94"}
                    },
                    {
                        "name": "Poland","value": 1,
                        'itemStyle':{'color':"#8DEEEE"}
                    },
                    {
                        "name": "Chile","value": 1,
                        'itemStyle':{'color':"#B23AEE"}
                    },
                ],
                "roam": true,
                "zoom": 1,
                // 去除各个国家上的小红点
                "showLegendSymbol": false,
            }
        ],

        // 鼠标悬浮，单击产生的效果（在网页上可以动态显示）
        "tooltip": {
            "show": true,
            "trigger": "item",
            "triggerOn": "mousemove|click",
            "axisPointer": {
                "type": "line"
            },
            "textStyle": {
                "fontSize": 14
            },
            "borderWidth": 0
        }
    };
    chart_container.setOption(option_container);
</script>
</body>
</html>