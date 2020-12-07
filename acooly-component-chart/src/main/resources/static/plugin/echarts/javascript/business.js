//倒计时刷新
function refreshTimeValue(chartItemId,loopTime){
	if(loopTime>0){
		loopTime=loopTime-1;
		$("#container_"+chartItemId+"_loopTime").html("  刷新 "+ loopTime +" s");
		if(loopTime!=0){
			setTimeout("refreshTimeValue("+chartItemId+","+loopTime+")",1000);
		}
	}
}