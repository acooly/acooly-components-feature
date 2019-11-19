<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_chartItems_editform" action="${pageContext.request.contextPath}/manage/module/chart/chartItems/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="chartItems" scope="request">
        <input name="id" type="hidden" />
		  <input id="operationIndex"  type="hidden"/>
		  <input id="listNum"  type="hidden" value="0"/>
		  <input id="fieldMapped" name="fieldMapped"  type="hidden" />
		  <input id="xShaft" name="xShaft" type="hidden"/>
		  <input id="yShaft"  name="yShaft" type="hidden" />
		  <c:choose>
			  <c:when test="${action=='create'}">
				  <input  name="chartId" type="hidden" value="${chartId}"/>
			  </c:when>
			  <c:otherwise>
				  <input  name="chartId" type="hidden"/>
			  </c:otherwise>
		  </c:choose>

        <table class="tableForm" width="100%">
			<tr>
				<th>标题：</th>
				<td><input type="text" name="title" size="48" placeholder="请给图表取个名,例如访问量曲线图" class="easyui-validatebox text" data-options="validType:['length[1,64]'],required:true"/></td>
			</tr>					
			<tr>
				<th>图表类型：</th>
				<td><select id="type" name="type" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" data-options="required:true">
					<c:forEach items="${allTypes}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>状态：</th>
				<td><select name="status" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" data-options="required:true">
					<c:forEach items="${allStatuss}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>刷新时间：</th>
				<td>
                    <c:choose>
                        <c:when test="${action=='create'}">
                            <input type="text" name="loopTime" size="20" placeholder="请输入循环拉取数据时间(单位秒),为0时手动拉取数据" style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]'],required:true"/>
                        </c:when>
                        <c:otherwise>
                            <input type="text" name="loopTime" value="${chartItems.loopTime/1000}" size="20" placeholder="请输入循环拉取数据时间(单位秒),为0时手动拉取数据" style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]'],required:true"/>
                        </c:otherwise>
                    </c:choose>
					<br/>规则：&nbsp;&nbsp;&nbsp; 0：页面图形不自动刷新；  &nbsp;&nbsp;&nbsp;     （1-9）:页面图形10秒刷新一次;  &nbsp;&nbsp;&nbsp;     x>9:页面图形x秒刷新一次;
                </td>
			</tr>
			<tr>
				<th>高：</th>
				<td>
					<c:choose>
						<c:when test="${action=='create'}">
							<input value="50" type="number" name="height" size="20" placeholder="图表高度默认50" style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]']"/>   单位“%”
						</c:when>
						<c:otherwise>
							<input type="number" value="${chartItems.height}" name="height" size="20" placeholder="图表高度默认50" style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]']"/>
						</c:otherwise>
					</c:choose>

				</td>
			</tr>
			<tr>
				<th>宽：</th>
				<td>

					<c:choose>
						<c:when test="${action=='create'}">
							<input type="number" value="50" name="width" size="20" placeholder="图表宽度默认50" style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]']"/>   单位“%”
						</c:when>
						<c:otherwise>
							<input type="number" value="${chartItems.width}" name="width" size="20" placeholder="图表宽度默认50" style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]']"/>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			
			<tr>
				<th>是否显示数据值：</th>
				<td><select name="isShow" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" >
					<c:forEach items="${allChartItemsIsShows}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>

			<tr>
				<th>sql表达式：</th>
				<td>
					<%--<input  type="text" />--%>
					<textarea rows="10" cols="100" placeholder="请输入sql表达式..."  style="width:850px;" name="sqlData" class="easyui-validatebox" data-options="validType:['length[1,2048]'],required:true"></textarea>
				</td>
			</tr>
			<tr>
				<th>数据字段：</th>
				<td>
					<%--<textarea rows="3" cols="40" placeholder="请输入数据字段..." style="width:850px;" name="fieldMapped" class="easyui-validatebox" data-options="validType:['length[1,512]'],required:true"></textarea>--%>
						<div id="standard">

							<c:forEach items="${chartItems.fieldMappedMap}" var="item" varStatus="status">
								<div id="standards_${status.index+1}">
										sql字段:<input type='text' value="${item.key}"  id='sqlField_${status.index+1}' class='easyui-validatebox text' style='width: 100px' precision='2' placeholder='sql字段名' data-options='required:true'/>
									&emsp;sql中文:<input type='text' value="${item.value}"  id='chinese_${status.index+1}' class='easyui-validatebox text' style='width: 100px' precision='2' placeholder='字段中文名' data-options='required:true'/>
									<button type="button" onclick="deleteFieldMapping(${status.index+1})">删除</button>

								</div>
							</c:forEach>
						</div>
						<br>
						<button type="button" onclick="addFieldMapping()">添加sql字段映射</button>
				</td>
			</tr>

			<tr id="xShaftTr" style="display: none;">
				<th>x轴：</th>
				<td id="xShaftTd">
					<%--<input name="Fruit" type="checkbox" value="" />--%>
					<%--<input type="text" id="xShaft" name="xShaft" size="48" placeholder="举个栗子{account_period:日期} json格式..." class="easyui-validatebox text" data-options="validType:['length[1,128]'],required:true" />--%>
				</td>
			</tr>
			<tr id="yShaftTr" style="display: none;">
				<th>y轴：</th>
				<td id="yShaftTd">
					<%--<input type="text" id="yShaft" name="yShaft" size="48" placeholder="举个栗子{withdraw_account:提现金额} json格式..." class="easyui-validatebox text" data-options="validType:['length[1,128]'],required:true"/>--%>
				</td>
			</tr>

			<tr>
				<th>备注：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入备注..." style="width:300px;" name="comments" class="easyui-validatebox" data-options="validType:['length[1,255]']"></textarea></td>
			</tr>
		</table>
      </jodd:form>
    </form>
</div>
<script>
    fieldMapping = {};
    xShaft = {};
    yShaft = {};
    count = 0;
    
	var data = '${chartItems.fieldMappedJson}';
	if (data){
		data = JSON.parse(data);
        count = 0;
        for (var a in data){
            count++;
            var flag = {};
            flag[a] = data[a]
		   fieldMapping[count] = flag;
	   }

	}

    var xdata = '${chartItems.xShaft}'
	if (xdata){
        xdata = JSON.parse(xdata);
        for (var a in xdata){
            xShaft[a] = xdata[a]
        }
	}

    var ydata = '${chartItems.yShaft}'
    if (ydata){
        ydata = JSON.parse(ydata);
        for (var a in ydata){
            yShaft[a] = ydata[a]
        }
    }
    listNum = 0;
    if (count != 0){
        listNum = count
    }
    $("#listNum").val(listNum);

    operationIndex = 1;
    if (count != 0){
        for(var i=1;i<=count;i++){
            operationIndex = i+1
            addIncident();
            if (i==1){
                $('#sqlField_1').trigger("blur");
			}
		}

	}



    /*for (var b=1;b<=operationIndex;b++){


	}
    showYAndeX()*/

    function chartItems_editform_onSubmit() {
        
        if (jQuery.isEmptyObject(fieldMapping)){
            $.messager.alert("提示","请添加并填写sql字段映射！");
            return false;
		}
        var xShaft_value =[];
        $('input[name="xShaftData"]:checked').each(function(){
            xShaft_value.push($(this).val());
        });
        console.log("x======="+xShaft_value)
        var typeSelectData  = null;
        if ('${action}'=='create'){
            typeSelectData = $('#type').combobox('getValue');
        }else {
            typeSelectData = $('#type').val();
        }
        if ( xShaft_value.length==0 && typeSelectData != 'pie'){
            $.messager.alert("提示","请勾选x轴的值！");
            return false;
		}


        var yShaft_value = [];
        $('input[name="yShaftData"]:checked').each(function(){
            yShaft_value.push($(this).val());
        });
        if ( yShaft_value.length==0){
            $.messager.alert("提示","请勾选y轴的值！");
            return false;
        }
        console.log("y======="+yShaft_value)

        var  newfieldMapping = {};
        for (var key in fieldMapping){
            for (var objectKey in fieldMapping[key]){
                newfieldMapping[objectKey] =(fieldMapping[key])[objectKey];
			}
		}
		console.log(newfieldMapping);
        xShaft = {};
		for (var key in xShaft_value ){
            
           for (var objectKey in fieldMapping[xShaft_value[key]]){
               
               
               xShaft[objectKey] = (fieldMapping[xShaft_value[key]])[objectKey]
		   }
		}
        console.log(xShaft);
        yShaft ={};
        for (var key in yShaft_value ){
            
            for (var objectKey in fieldMapping[yShaft_value[key]]){
                
                yShaft[objectKey] = (fieldMapping[yShaft_value[key]])[objectKey]
            }
        }
        console.log(yShaft);
		$("#fieldMapped").val(JSON.stringify(newfieldMapping));
        $("#xShaft").val(JSON.stringify(xShaft));
        $("#yShaft").val(JSON.stringify(yShaft));
        return true;
    }
    function isJSON(str) {
        if (typeof str == 'string') {
            try {
                var obj=JSON.parse(str);
                if(typeof obj == 'object' && obj ){
                    return true;
                }else{
                    return false;
                }

            } catch(e) {
                console.log('error：'+str+'!!!'+e);
                return false;
            }
        }
        console.log('It is not a string!')
    }


	function  addFieldMapping() {
        
		var FieldMappingHtml = "<div id='standards_"+operationIndex+"'>" +
			"  sql字段:" +"<input type='text'  id='sqlField_"+operationIndex+"'"+" class='easyui-validatebox text' style='width: 100px' precision='2' placeholder='sql字段名' data-options='required:true'/>" +
			"  &emsp;sql中文:"+"<input type='text'  id='chinese_"+operationIndex+"'"+" class='easyui-validatebox text' style='width: 100px' precision='2' placeholder='字段中文名' data-options='required:true'/>" +
			"  <button type=\"button\" onclick=\"deleteFieldMapping("+operationIndex+")\"> 删除</button></div>"
			" </div>";
		$("#standard").append(FieldMappingHtml);
        operationIndex++;
        listNum ++;
        $("#listNum").val(listNum);
        $.parser.parse($("#standard"));
        addIncident()
    }

    function deleteFieldMapping(index) {
        var hideId="standards_"+index;
        var sqlFieldId="sqlField_"+index;
        var chineseId="chinese_"+index;
        $("#"+sqlFieldId).attr("disabled",true);
        $("#"+chineseId).attr("disabled",true);
        $("#"+hideId).hide();
        listNum --;
        $("#listNum").val(listNum);
        delete  fieldMapping[index];
        showYAndeX()
        $.parser.parse($("#standard"));
    }
    
   function combinationData(sqlFieldId,chineseId,index) {
	    var field = {};
	   var listNum =  $("#listNum").val();
	   if (listNum==0 || operationIndex ==0){return ;}
	   var sqlField =  $("#"+sqlFieldId).val();
       var chinese = $("#"+chineseId).val();
       if (!sqlField || !chinese){
           return ;
	   }
       sqlField = $.trim(sqlField);
       chinese = $.trim(chinese);
       field[sqlField] = chinese;
       fieldMapping[index] = field;
       showYAndeX()
   }
   
   function  addIncident() {
       if (listNum==0 || operationIndex ==0){return ;}
       var index = operationIndex -1;
       var sqlFieldId="sqlField_"+index;
       var chineseId="chinese_"+index;
       $("#"+sqlFieldId).blur(function () {
           combinationData(sqlFieldId,chineseId,index);
       })
       $("#"+chineseId).blur(function () {
           combinationData(sqlFieldId,chineseId,index);
       })

   }

   function  showYAndeX() {
	    if (jQuery.isEmptyObject(fieldMapping)){
            $("#yShaftTr").hide();
            $("#xShaftTr").hide();
	        return ;
		}
       var typeSelectData  = null;
       if ('${action}'=='create'){
            typeSelectData = $('#type').combobox('getValue');
		}else {
           typeSelectData = $('#type').val();
		}

       if (typeSelectData != "pie"){
           $("#xShaftTr").show();
           appendXAndYHtml(false)
	   }else {
           appendXAndYHtml(true)
	   }
	  $("#yShaftTr").show();
   }

   function appendXAndYHtml(xExcept) {
       $("#xShaftTd").html("");
       $("#yShaftTd").html("");
       if (!jQuery.isEmptyObject(fieldMapping)){
           
               for(var key in fieldMapping){
                   for (var objectKey in fieldMapping[key]){
                       if (!xExcept) {
                           if (!jQuery.isEmptyObject(xShaft)){
                               if ((fieldMapping[key])[objectKey] == xShaft[objectKey] ){
                                   $("#xShaftTd").append("<input name='xShaftData' type='checkbox' value=" + key + " checked />"+objectKey+"&emsp;"  )
							   }else {
                                   $("#xShaftTd").append("<input name='xShaftData' type='checkbox' value=" + key + " />"+objectKey +"&emsp;" )
							   }
						   }else {
                               $("#xShaftTd").append("<input name='xShaftData' type='checkbox' value=" + key + " />"+objectKey +"&emsp;" )
						   }
                       }

                       if (!jQuery.isEmptyObject(yShaft)){
                           if ((fieldMapping[key])[objectKey] == yShaft[objectKey] ){
                               $("#yShaftTd").append("<input name='yShaftData' type='checkbox' value=" + key +  " checked />"+objectKey +"&emsp;")
                           }else {
                               $("#yShaftTd").append("<input name='yShaftData' type='checkbox' value=" + key + " />"+objectKey+"&emsp;" )
                           }
                       }else {
                           $("#yShaftTd").append("<input name='yShaftData' type='checkbox' value=" + key + " />"+objectKey +"&emsp;" )
                       }

                   }
               }

	   }

   }


</script>