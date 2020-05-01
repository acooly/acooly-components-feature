/**
 * 使用方式：
 *
 * 1：单个页面添加
 * //统计
 * $('#manage_trade_datagrid').datagrid({
 *		showFooter:true,
 *		onLoadSuccess : function() {
 *        	$('#manage_trade_datagrid').datagrid('statistics','gid,tradeOrderNo,createTime,updateTime');
 *     	}
 * 	});
 *    $('#manage_trade_datagrid').datagrid('statistics','xxx');
 *    重叠显示"undefined"：xxx=createTime,updateTime
 *    <th field="dateTime" width="150px;" data-options="formatter:function(value,row){ return row.createTime+'<br/>'+row.updateTime}">创建时间<br/>更新时间</th>
 *
 *
 * 2：<th field="amount"  sum="true" >交易金额</th>
 * 添加 sum="true"
 *
 */
$.extend($.fn.datagrid.methods, {
    statistics: function (jq, columns) {
        var opt = $(jq).datagrid('options').columns;
        var rows = $(jq).datagrid("getRows");

        var sumShow = false;
        var avgShow = false;
        var maxShow = false;
        var minShow = false;

        var footerObj = new Array();
        var footer = new Array();
        footer['sum'] = "";
        footer['avg'] = "";
        footer['max'] = "";
        footer['min'] = "";

        if (undefined != columns) {
            var resultV = columns.split(",");
            for (var i = 0; i < resultV.length; i++) {
                footer['sum'] = footer['sum'] + '"' + resultV[i] + '":"",';
                footer['avg'] = footer['avg'] + '"' + resultV[i] + '":"",';
                footer['max'] = footer['max'] + '"' + resultV[i] + '":"",';
                footer['min'] = footer['min'] + '"' + resultV[i] + '":"",';
            }
        }

        for (var i = 0; i < opt[0].length; i++) {
            var fieldName = opt[0][i].field;
            if (isStatics(fieldName,"sum")) {
                sumShow = true;
                footer['sum'] = footer['sum'] + sum(fieldName) + ',';
            } else {
                footer['sum'] = footer['sum'] + '"' + fieldName + '":"",';
            }
            if (isStatics(fieldName,"avg")) {
                avgShow = true;
                footer['avg'] = footer['avg'] + avg(opt[0][i].field) + ',';
            } else {
                footer['avg'] = footer['avg'] + '"' + fieldName + '":"",';
            }

            if (isStatics(fieldName,"max")) {
                maxShow = true;
                footer['max'] = footer['max'] + max(opt[0][i].field) + ',';
            } else {
                footer['max'] = footer['max'] + '"' + fieldName + '":"",';
            }

            if (isStatics(fieldName,"min")) {
                minShow = true;
                footer['min'] = footer['min'] + min(opt[0][i].field) + ',';
            } else {
                footer['min'] = footer['min'] + '"' + fieldName + '":"",';
            }

//			console.log(footer['sum']);
        }


        if (footer['sum'] != "") {
            var tmp = '{' + footer['sum'].substring(0, footer['sum'].length - 1) + "}";
            var obj = eval('(' + tmp + ')');
            if (obj[opt[0][0].field] == undefined) {
                footer['sum'] += '"' + opt[0][1].field + '":"<b>当页合计:</b>"';
                obj = eval('({' + footer['sum'] + '})');
            } else {
                obj[opt[0][1].field] = "<b>当页合计:</b>" + obj[opt[0][0].field];
            }
            if (sumShow) {
                footerObj.push(obj);
            }
        }

        if (footer['avg'] != "") {
            var tmp = '{' + footer['avg'].substring(0, footer['avg'].length - 1) + "}";
            var obj = eval('(' + tmp + ')');
            if (obj[opt[0][0].field] == undefined) {
                footer['avg'] += '"' + opt[0][1].field + '":"<b>当页均值:</b>"';
                obj = eval('({' + footer['avg'] + '})');
            } else {
                obj[opt[0][1].field] = "<b>当页均值:</b>" + obj[opt[0][0].field];
            }
            if (avgShow) {
                footerObj.push(obj);
            }
        }

        if (footer['max'] != "") {
            var tmp = '{' + footer['max'].substring(0, footer['max'].length - 1) + "}";
            var obj = eval('(' + tmp + ')');
            if (obj[opt[0][0].field] == undefined) {
                footer['max'] += '"' + opt[0][1].field + '":"<b>当页最大值:</b>"';
                obj = eval('({' + footer['max'] + '})');
            } else {
                obj[opt[0][1].field] = "<b>当页最大值:</b>" + obj[opt[0][0].field];
            }
            if (maxShow) {
                footerObj.push(obj);
            }
        }


        if (footer['min'] != "") {
            var tmp = '{' + footer['min'].substring(0, footer['min'].length - 1) + "}";
            var obj = eval('(' + tmp + ')');
            if (obj[opt[0][0].field] == undefined) {
                footer['min'] += '"' + opt[0][1].field + '":"<b>当页最小值:</b>"';
                obj = eval('({' + footer['min'] + '})');
            } else {
                obj[opt[0][1].field] = "<b>当页最小值:</b>" + obj[opt[0][0].field];
            }
            if (minShow) {
                footerObj.push(obj);
            }
        }


        if (footerObj.length > 0) {
            $(jq).datagrid('reloadFooter', footerObj);
        }

        function isStatics(fieldName,statics) {
            return $(jq).find("th[field='"+fieldName+"']").attr(statics);
        }

        function sum(filed) {
            var sumNum = 0;
            for (var i = 0; i < rows.length; i++) {
                sumNum += Number(rows[i][filed]);
            }
            return '"' + filed + '":"' + sumNum.toFixed(2) + '"';
        };

        function avg(filed) {
            var sumNum = 0;
            for (var i = 0; i < rows.length; i++) {
                sumNum += Number(rows[i][filed]);
            }
            return '"' + filed + '":"' + (sumNum / rows.length).toFixed(2) + '"';
        }

        function max(filed) {
            var max = 0;
            for (var i = 0; i < rows.length; i++) {
                if (i == 0) {
                    max = Number(rows[i][filed]);
                } else {
                    max = Math.max(max, Number(rows[i][filed]));
                }
            }
            return '"' + filed + '":"' + max + '"';
        }

        function min(filed) {
            var min = 0;
            for (var i = 0; i < rows.length; i++) {
                if (i == 0) {
                    min = Number(rows[i][filed]);
                } else {
                    min = Math.min(min, Number(rows[i][filed]));
                }
            }
            return '"' + filed + '":"' + min + '"';
        }
    }
});
