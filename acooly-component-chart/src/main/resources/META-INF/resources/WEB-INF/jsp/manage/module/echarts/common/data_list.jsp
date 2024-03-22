<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" src="/manage/assert/plugin/jquery/3.4.1/jquery.min.js" charset="utf-8"></script>
<script type="text/javascript" src="/plugin/echarts/javascript/jquery.table2excel.js" charset="utf-8"></script>
<script type="text/javascript" src="/plugin/echarts/javascript/download.js" charset="utf-8"></script>

<html>
<%------示例----%>
<%--<div class="data-table min" style="margin: 0 20px;margin-bottom: 30px;height: 260px;">--%>
<%--    <div class="table-header">--%>
<%--        <div class="thead-rows">--%>
<%--            <span class="thead-td no-border" style="flex: .7;">&nbsp;</span>--%>
<%--            <span class="thead-td td-bg">亚洲销售部</span>--%>
<%--            <span class="thead-td td-bg">特种车销售部</span>--%>
<%--            <span class="thead-td td-bg">电摩欧销售部</span>--%>
<%--            <span class="thead-td td-bg">非洲销售部</span>--%>
<%--            <span class="thead-td td-bg">南非销售部</span>--%>
<%--            <span class="thead-td td-bg">中北美销售部</span>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <div class="table-body">--%>
<%--        <div class="tbody-rows">--%>
<%--            <span class="tbody-td td-bg td-icon-14C9C9" style="flex: .7;">三轮</span>--%>
<%--            <span class="tbody-td">400001</span>--%>
<%--            <span class="tbody-td">10000</span>--%>
<%--            <span class="tbody-td">10000</span>--%>
<%--            <span class="tbody-td">400001</span>--%>
<%--            <span class="tbody-td">10000</span>--%>
<%--            <span class="tbody-td">10000</span>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>
<%----------------%>
<c:if test="${chartItem.isDataListShow=='YES'}">
    <%--    <button onclick="downloadToExcel('container_${chartItemId}_dataList_table')" class="btn btn-sm btn-primary"--%>
    <%--            style=" position: absolute;left:85%;margin-top:-25px; ">下载Excel--%>
    <%--    </button>--%>


    <div style="margin-left:1%; margin-top:30px;">
            <%--        <table id="container_${chartItemId}_dataList_table" class="data-table min" border="1" width="90%">--%>
            <%--        </table>--%>
        <div id="container_${chartItemId}_dataList_table" class="data-table min"
             style="margin-bottom: 50px; height: auto;">
        </div>
    </div>
</c:if>

<script type="text/javascript">

    /**
     * 显示数据格式
     * @param chartItemId
     * @param columnName
     * @param xShaft
     * @param yShafts
     * @returns
     */
    function showChartDataList(chartItemId, columnName, xShaft, yShafts) {
        // console.log("x轴：" + xShaft);
        // console.log("y轴：" + yShafts);

        $("#container_" + chartItemId + "_dataList_table").empty();
        //表HTML
        var tableHtml = "";
        //列名
        var columnNameHtml = "";
        //列数据
        var columnValueHtml = " ";
        //合计
        var columnTotalValueHtml = "";
        var totalValue = new Array();

        var dataListShowXY = '${chartItem.dataListShowXY}';

        //y轴 为表头
        if (dataListShowXY == 'X') {
            //列名--组装----表头
            columnNameHtml = "<div class='table-header'> <div class='thead-rows'>";
            for (var i in xShaft) {
                if (i == 0) {
                    columnNameHtml = columnNameHtml + "<span class='thead-td td-bg'></span>";
                }
                var xShaftHeadName = xShaft[i];
                xShaftHeadName = xShaftHeadName.replace("\n", "<br/>");
                columnNameHtml = columnNameHtml + "<span class='thead-td td-bg'>" + xShaftHeadName + "</span>";
            }
            columnNameHtml = columnNameHtml + " </div> </div>";

            var z = 0;
            for (var y in yShafts) {

                //小数位（位数）
                var fixedNum = 0;

                var yShaftsName = yShafts[y].name;
                yShaftsName = yShaftsName.replace("\n", "<br/>");

                columnValueHtml = columnValueHtml + "<div class='table-body'><div class='tbody-rows'>";
                columnValueHtml = columnValueHtml + "<span class='tbody-td td-bg' style='font-weight: bold'>" + yShaftsName + "</span>";
                for (var yy in yShafts[y].data) {
                    //数字类型
                    var yShaftsValueNumber = 0;
                    //行数据（z:行；y:列）
                    var yShaftsValue = yShafts[y].data[yy];
                    yShaftsValue = yShaftsValue.replace("\n", "<br/>");
                    columnValueHtml = columnValueHtml + "<span class='tbody-td '>" + yShaftsValue + "</span>";

                    //小数判断（最大小数位数）
                    if (yShaftsValue.indexOf(".") != -1) {
                        var fixedNumLength = yShaftsValue.split(".")[1].length;
                        if (fixedNumLength > fixedNum) {
                            fixedNum = fixedNumLength;
                        }
                    }

                    //倍数
                    var multiple = multipleCount(fixedNum);
                    //数据类型转换
                    yShaftsValueNumber = (Math.round(yShaftsValue * multiple) / multiple);

                    if (z == 0) {
                        totalValue.push(yShaftsValue);
                    } else {
                        var currentValue = Math.round(totalValue[yy] * multiple) / multiple;
                        totalValue[yy] = (currentValue + yShaftsValueNumber).toFixed(fixedNum);
                    }
                    // console.log("totalValue：" + totalValue);
                }
                z = z + 1;
                columnValueHtml = columnValueHtml + " </div> ";
            }
            // columnValueHtml = columnValueHtml + " </div> ";

        } else {
            //列名--组装----表头
            columnNameHtml = "<div class='table-header'> <div class='thead-rows'>";
            for (var i in columnName) {
                var columnHeadName = columnName[i];
                columnHeadName = columnHeadName.replace("\n", "<br/>");
                columnNameHtml = columnNameHtml + "<span class='thead-td td-bg'>" + columnHeadName + "</span>";
            }
            columnNameHtml = columnNameHtml + " </div> </div>";

            // console.log("表头---:" + columnNameHtml);

            //列数据--组装
            var z = 0;
            //小数位（位数）
            var fixedNum = 0;
            for (var x in xShaft) {
                var xShaftNameTd = xShaft[x];
                xShaftNameTd = xShaftNameTd.replace("\n", "<br/>");
                columnValueHtml = columnValueHtml + "<div class='table-body'><div class='tbody-rows'>";
                columnValueHtml = columnValueHtml + "<span class='tbody-td td-bg' style='font-weight: bold'>" + xShaftNameTd + "</span>";
                for (var y in yShafts) {
                    //数字类型
                    var yShaftsValueNumber = 0;
                    //行数据（z:行；y:列）
                    var yShaftsValue = yShafts[y].data[z];

                    // 测试小数
                    //yShaftsValue=yShaftsValue+".01231";

                    //小数判断（最大小数位数）
                    if (yShaftsValue.indexOf(".") != -1) {
                        var fixedNumLength = yShaftsValue.split(".")[1].length;
                        if (fixedNumLength > fixedNum) {
                            fixedNum = fixedNumLength;
                        }
                    }

                    //倍数
                    var multiple = multipleCount(fixedNum);
                    //数据类型转换
                    yShaftsValueNumber = (Math.round(yShaftsValue * multiple) / multiple);

                    columnValueHtml = columnValueHtml + "<span class='tbody-td'>" + yShaftsValueNumber + "</span>";

                    //合计-行数据
                    if (z == 0) {
                        totalValue.push(yShaftsValueNumber);
                    } else {
                        var currentValue = Math.round(totalValue[y] * multiple) / multiple;
                        totalValue[y] = (currentValue + yShaftsValueNumber).toFixed(fixedNum);
                        //console.log("----累加值:"+totalValue[y]);
                    }
                }
                columnValueHtml = columnValueHtml + "</div>";
                z = z + 1;
            }
            // console.log("行数据(无最后一行)---:" + columnValueHtml);
        }
        //合计
        if (totalValue.length > 0) {
            columnTotalValueHtml = columnTotalValueHtml + "<div class='tbody-rows'><span class='tbody-td td-bg' style='font-size:15px;font-weight: bold;'>合计：</span>";

        }
        for (var t in totalValue) {
            columnTotalValueHtml = columnTotalValueHtml + "<span class='tbody-td' style='font-size:15px;font-weight: bold;'>" + totalValue[t] + "</span>";
        }
        columnTotalValueHtml = columnTotalValueHtml + "</div>";

        columnTotalValueHtml = columnTotalValueHtml + "<div style='padding-bottom: 50px;'></div>";
        columnTotalValueHtml = columnTotalValueHtml + "</div>";

        // console.log("合计数据---:" + columnTotalValueHtml);


        //动态模板html
        tableHtml = tableHtml + columnNameHtml + columnValueHtml + columnTotalValueHtml;

        // console.log("动态模板html---:" + tableHtml);

        $("#container_" + chartItemId + "_dataList_table").append(tableHtml);
    }


    /**
     * 计算倍数
     */
    function multipleCount(num) {
        var multiple = 1;
        if (num > 0) {
            multiple = Math.pow(5, num);
        }
        return multiple;
    }

</script>

<style>
    .data-table {
        position: relative;
        height: 100%;
    }

    .data-table.min .table-header {
        height: 35px;
    }

    .data-table.min .table-header .thead-rows .thead-td {
        font-size: 14px;
        font-weight: bold;
        color: #333;
    }

    .data-table.min .table-body {
        top: 35px;
    }

    .data-table.min .table-body .tbody-rows {
        height: 35px;
    }

    .data-table.min .table-body .tbody-rows .tbody-td {
        font-size: 14px;
        letter-spacing: 0.06em;
        color: #666;
    }

    .data-table .td-bg {
        background: #F4F9FF;
        letter-spacing: 0.06em;
    }

    .data-table .td-icon-14C9C9::before {
        content: '';
        display: inline-block;
        width: 10px;
        height: 10px;
        background: #14C9C9;
        border-radius: 100%;
        margin-right: 5px;
    }

    .data-table .td-icon-165DFF::before {
        content: '';
        display: inline-block;
        width: 10px;
        height: 10px;
        background: #165DFF;
        border-radius: 100%;
        margin-right: 5px;
    }

    .data-table .td-icon-E4C203::before {
        content: '';
        display: inline-block;
        width: 10px;
        height: 10px;
        background: #E4C203;
        border-radius: 100%;
        margin-right: 5px;
    }

    .data-table .td-icon-7A3AD4::before {
        content: '';
        display: inline-block;
        width: 10px;
        height: 10px;
        background: #7A3AD4;
        border-radius: 100%;
        margin-right: 5px;
    }

    .data-table .table-header {
        height: 75px;
        display: flex;
    }

    .data-table .table-header .thead-rows {
        flex: 1;
        display: flex;
    }

    .data-table .table-header .thead-rows .thead-td {
        flex: 1;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 16px;
        color: #3D3D3D;
        border: 1px solid #E4E4E4;
        border-bottom: 0;
        border-right: 0;
    }

    .data-table .table-header .thead-rows .thead-td .icon-thead {
        width: 32px;
        height: 32px;
        margin-right: 14px;
    }

    .data-table .table-header .thead-rows .thead-td.no-border {
        border: none;
    }

    .data-table .table-header .thead-rows .thead-td:last-child {
        border-right: 1px solid #E4E4E4;
    }

    .data-table .table-body {
        position: absolute;
        left: 0;
        right: 0px;
        top: 75px;
        bottom: 0;
        /* overflow: hidden; */
        /* overflow-y: auto; */
    }

    .data-table .table-body .tbody-rows {
        display: flex;
        height: 30px;
        background: #FFF;
    }

    .data-table .table-body .tbody-rows .tbody-td {
        flex: 1;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 24px;
        color: #3D3D3D;
        border: 1px solid #E4E4E4;
        border-bottom: 0;
        border-right: 0;
    }

    .data-table .table-body .tbody-rows .tbody-td:last-child {
        border-right: 1px solid #E4E4E4;
    }

    .data-table .table-body .tbody-rows:last-child {
        border-bottom: 1px solid #E4E4E4;
    }

    .data-table .table-body.fc-grey {
        overflow: hidden;
        overflow-y: auto;
    }

    .data-table .table-body.fc-grey .tbody-rows .tbody-td {
        color: #999999;
    }
</style>
</html>