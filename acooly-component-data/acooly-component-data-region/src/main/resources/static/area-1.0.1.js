/*******************************************************************************
 * 省市县 四级联动
 * 
 * cuifuq
 * 
 * 可以直接看 CCCC
 * 
 * 
 * 使用方式
 * AAAA:
 * 服务器地址：/acooly/data/region/tree.html
 * 数据结构："id":110000000000,"parentId":100000,"name":"北京市","sortTime":0,"children"
 * 解析方式:  <option value=' 数据结构 id ' > 数据结构 name  </option>  
 *
 * BBBB:
 * 动态支持 省-市-县-街道 四级联动
 * 配置方式如下：可自定义id,可自定义初始化 文字
 * 		配置对应的 省-市-县-街道 id: 	var selectIds = [ "s_province", "s_city", "s_county", "s_street" ];
 * 		配置对应的 省-市-县-街道 name : 	var selectInitText = [ "请选择-省份", "请选择-市", "请选择-区、县级市", "请选择-街道" ]
 * 
 * CCCC:
 * 初始化赋值：
 * var initData=new Array('${org.province}', '${org.city}', '${org.county}', '${org.street}');
 * initAreaData(initData);
 * 
 * 
 * DDDD:
 * var selectInitText  数据可以为空 
 * var selectIds 至少一个参数，最多四个参数（省-市-县-街道）
 * 
 * 
 */

// 三个select的name
var selectIds = [ "s_province", "s_city", "s_county", "s_street" ];

// 初始值
//var selectInitText = [ "请选择-省份", "请选择-市", "请选择-区、县级市", "请选择-街道" ];
var selectInitText = [  ];

// code: 省市县-数据id
// name:省市县-数据名称
var saveType="code";



// 初始化函数
function initArea() {
	// 请求数据
	requestAreaData();

	// 初始化数据
	setDataList(selectIds[0], "", areaDataJson);
	areaSelset(1, "");

	// 动态绑定事件
	$("#" + selectIds[0]).change(function() {
		areaSelset(1, $("#" + selectIds[0]).val());
	});

	$("#" + selectIds[1]).change(function() {
		areaSelset(2, $("#" + selectIds[1]).val());
	});

	$("#" + selectIds[2]).change(function() {
		areaSelset(3, $("#" + selectIds[2]).val());
	});

	$("#" + selectIds[3]).change(function() {
		areaSelset(4, $("#" + selectIds[3]).val());
	});
}

// 初始化 数据结构
// var myCars=new Array("省份", "地级市", "市、县级市", "街道" );
function initAreaData(areaData) {

	// 初始化
	initArea();

	// 逐级赋值
	for (x = 0; x < areaData.length; x++) {
		// console.log("------"+x);
		var area = areaData[x];
		areaSelset((x + 1), area);
	}
}

// 服务器 数据集
var areaDataJson = new Object();

// 当前选中---序列
var province_index = 0;
var city_index = 0;
var county_index = 0;
var street_index = 0;

// 服务端 数据请求
function requestAreaData() {
	$.ajax({
		url : "/acooly/data/region/tree.html",
		async : false,
		success : function(data) {
			areaDataJson = data;
		}
	});
}

// 省市县--选中事件
function areaSelset(level, selectKey) {
	console.log("level:" + level + "---" + "selectKey:" + selectKey);

	var provinceKey = $("#" + selectIds[0]).val();
	var cityKey = $("#" + selectIds[1]).val();
	var countyKey = $("#" + selectIds[2]).val();
	var streetKey = $("#" + selectIds[3]).val();

	// 省---变更
	if (level == 1) {
		if (selectKey != "" || selectKey != null) {
			provinceKey = selectKey;
		}
		for (i = 0; i < areaDataJson.length; i++) {
			if (saveType == 'code') {
				if (provinceKey == areaDataJson[i].id) {
					province_index = i;
					break;
				}
			} else {
				if (provinceKey == areaDataJson[i].name) {
					province_index = i;
					break;
				}
			}
		}
		
		// 省---赋值
		setDataList(selectIds[0], selectKey, areaDataJson);

		setDataList(selectIds[1], "", areaDataJson[province_index].children);

		if (areaDataJson[province_index].children[0].children != null) {
			setDataList(selectIds[2], "",areaDataJson[province_index].children[0].children);
		}
		if (areaDataJson[province_index].children[0].children[0].children != null) {
			setDataList(selectIds[3],"",areaDataJson[province_index].children[0].children[0].children);
		}
	}

	// 市---变更
	if (level == 2) {
		if (selectKey != "" || selectKey != null) {
			cityKey = selectKey;
		}
		var cityJson = areaDataJson[province_index].children;
		for (i = 0; i < cityJson.length; i++) {
			if (saveType == 'code') {
				if (cityKey == cityJson[i].id) {
					city_index = i;
					break;
				}
			} else {
				if (cityKey == cityJson[i].name) {
					city_index = i;
					break;
				}
			}
		}

		// 市---赋值
		setDataList(selectIds[1], selectKey, cityJson);

		setDataList(selectIds[2], "", cityJson[city_index].children);

		if (cityJson[city_index].children[0].children != null) {
			setDataList(selectIds[3], "",cityJson[city_index].children[0].children);
		}
	}

	// 县/区---变更
	if (level == 3) {
		if (selectKey != "" || selectKey != null) {
			countyKey = selectKey;
		}
		var countyJson = areaDataJson[province_index].children[city_index].children;
		for (i = 0; i < countyJson.length; i++) {
			if (saveType == 'code') {
				if (countyKey == countyJson[i].id) {
					county_index = i;
					break;
				}
			} else {
				if (countyKey == countyJson[i].name) {
					county_index = i;
					break;
				}
			}
		}
		// 县/区---赋值
		setDataList(selectIds[2], selectKey, countyJson);

		setDataList(selectIds[3], "", countyJson[county_index].children);
	}

	// 街道---变更
	if (level == 4) {
		if (selectKey != "" || selectKey != null) {
			countyKey = selectKey;
		}
		var streetJson = areaDataJson[province_index].children[city_index].children[county_index].children;
		for (i = 0; i < streetJson.length; i++) {
			if (saveType == 'code') {
				if (streetJson == streetJson[i].id) {
					street_index = i;
					break;
				}
			} else {
				if (streetJson == streetJson[i].name) {
					street_index = i;
					break;
				}
			}
		}
		// 街道---赋值
		setDataList(selectIds[3], selectKey, streetJson);
	}
	return;
}

// 仅仅 下拉表 赋值
function setDataList(key, selectKey, dataJson) {
	$("#" + key).empty();

	if(dataJson==null){
		return ;
	}
	// if(selectKey==""||selectKey==null){
		for (y = 0; y < selectIds.length; y++) {
			if(selectIds[y]==key){
				if(selectInitText[y]!=undefined){
					$("#" + key).append("<option value=''>" + selectInitText[y] + "</option>");
				}
			}
		}
	// }
	
	var z=0;
	for (i = 0; i < dataJson.length; i++) {
		var selected = null;
		if (saveType == 'code') {
			selected = keySelected(dataJson[i].id, selectKey);
			$("#" + key).append("<option value='" + dataJson[i].id + "' " + selected + ">" + dataJson[i].name + "</option>");
		} else {
			selected = keySelected(dataJson[i].name, selectKey);
			$("#" + key).append("<option value='" + dataJson[i].name + "' " + selected + ">" + dataJson[i].name + "</option>");
		}
		if(selected!=""){
			z=i;
		}
	}
	 $("#" + key+"_text").val(dataJson[z].name);
}

// 是否选中
function keySelected(valueKey, selectedKey) {
	var selected = " selected='selected' ";
	if (valueKey == selectedKey) {
		return selected;
	}
	return "";
}