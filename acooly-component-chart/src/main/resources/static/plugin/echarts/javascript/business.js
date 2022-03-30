//倒计时刷新
function refreshTimeValue(chartItemId, loopTime) {
	if (loopTime >= 10) {
		refreshTimeValueShow(chartItemId, loopTime);
	}
}

// 倒计时刷新
function refreshTimeValueShow(chartItemId, loopTime) {
	if (loopTime > 0) {
		loopTime = loopTime - 1;
		$("#container_" + chartItemId + "_loopTime").html(
				"  刷新 " + loopTime + " s");
		if (loopTime != 0) {
			setTimeout("refreshTimeValueShow(" + chartItemId + "," + loopTime
					+ ")", 1000);
		}
	}
}

function downloadCsv(chartItemId) {
	if ($("#container_" + chartItemId + "_dataList_table").html() != '') {
		var csv_value = $("#container_" + chartItemId + "_dataList_table")
				.table2CSV({
					delivery : 'value'
				});
		$("#csv_text").val(csv_value);
	} else {
		alert("没有查到符合条件的数据");
	}
}
