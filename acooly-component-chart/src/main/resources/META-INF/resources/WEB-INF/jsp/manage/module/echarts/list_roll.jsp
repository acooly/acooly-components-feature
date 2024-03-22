<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html style="display: flex;justify-content: center;">

<body style="height: ${chartHeight}%; width: ${chartWidth}%;margin-top: 5px;">
<!-- 查询条件 -->
<%@ include file="/WEB-INF/jsp/manage/module/echarts/common/where_search.jsp" %>

<div id="container_${chartItemId}_loopTime"
     style="font-size:8px;color:	#D3D3D3;position: absolute;left:90%;margin-top:35px; "></div>
<div id="container_${chartItemId}" style="height: 100%;width: 100%">
    <div class="board-panel mb-10 flex-direction-column">
        <div class="board-panel-title">各客户发运订单</div>
        <div class="border-panel-cont" style="padding: 10px;padding-top: 0;">
            <div class="order-table">
                <div class="table-header">
                    <div class="thead-rows">
                        <span class="thead-td">客户编号</span>
                        <span class="thead-td">订单数量</span>
                    </div>
                </div>
                <div class="table-body overflow-hidden" id="j-table-body-dispatch1">
                    <div class="table-body-wrap" id="j-table-body-dispatch1-wrap">
                        <#list customerOrderList as customerOrder >
                        <div class="tbody-rows">
                            <span class="tbody-td">${customerOrder.customerCode?substring(4)}</span>
                            <span class="tbody-td">${customerOrder.nums}</span>
                        </div>
                    </
                    #list>
                    <div style="padding-bottom: 110px;">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</div>

<!-- 数据列表 -->
<%@ include file="/WEB-INF/jsp/manage/module/echarts/common/data_list.jsp" %>

<script type="text/javascript" src="/manage/assert/plugin/jquery/3.4.1/jquery.min.js" charset="utf-8"></script>
<script type="text/javascript" src="/plugin/echarts/echarts.min.js"></script>
<script type="text/javascript" src="/plugin/echarts/javascript/business.js"></script>

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
        var yShafts = new Array();
        var yShaftSum = new Array();

        //列名
        var columnName = new Array();

        jQuery.ajax({
            url: "/manage/module/echarts/charData_list_roll_${chartItemId}.html",
            data: $("#container_${chartItemId}_searchform").serialize(),
            cache: false,
            success: function (data) {
                console.log(data);
                if (data.success) {
                    listRollShow(${chartItemId});
                }
            }
        });
    }


    //动态数据解决
    function barChartDraw(title, legendData, xShaft, yShafts, chartItemsParams) {
    }


    //显示列表数据
    function listRollShow(chartItemId) {
        var tableBody = document.getElementById('j-table-body-'+chartItemId)
        var tableBodyWrap = document.getElementById('j-table-body-'+chartItemId+'-wrap')
        var tableBodyHeight = tableBody.offsetHeight
        var tableBodyWrapHeight = tableBodyWrap.offsetHeight;

        if (tableBodyWrapHeight > tableBodyHeight) {
            var childNodes = tableBodyWrap.childNodes;
            var childHtml = tableBodyWrap.innerHTML
            var childLen = 0
            for (var i = 0, len = childNodes.length; i < len; i++) {
                if (childNodes[i].nodeName !== '#text' && childNodes[i].nodeName !== '#comment') {
                    childHtml += childNodes[i].outerHTML
                    childLen += 1
                }
            }
            var maxTop = tableBodyWrapHeight
            if (childLen % 2 !== 0) {
                for (var i = 0, len = childNodes.length; i < len; i++) {
                    if (childNodes[i].nodeName !== '#text' && childNodes[i].nodeName !== '#comment') {
                        childHtml += childNodes[i].outerHTML
                    }
                }
                maxTop = tableBodyWrapHeight * 2
            }
            tableBodyWrap.innerHTML = childHtml
            var tableBodyWrapTop = 0
            setInterval(function () {
                tableBodyWrapTop = tableBodyWrapTop + 1
                tableBodyWrap.style.transform = 'translateY(-' + tableBodyWrapTop + 'px)'
                if (tableBodyWrapTop >= maxTop) {
                    tableBodyWrapTop = 0
                    tableBodyWrap.style.transform = 'translateY(-' + tableBodyWrapTop + 'px)'
                }
            }, 45)
        }
    }

</script>
</body>
</html>

