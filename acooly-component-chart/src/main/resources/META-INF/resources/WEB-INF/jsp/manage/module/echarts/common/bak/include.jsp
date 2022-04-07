<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html style="height: 95%; width: 95%">
<link rel="stylesheet" href="/manage/assert/plugin/jquery-easyui/themes/acooly/basic.css" type="text/css"/>

	<body style="height: 100%; margin: 0">
		<!-- 查询条件 -->
		<c:if test="${whereData!='' && whereData!=null}">
			<div class="easyui-layout" style="margin-left:5%;margin-top:5px; ">
			  <!-- 查询条件 -->
			    <form id="container_${chartItemId}_searchform" onsubmit="return false">
			      <table class="tableForm" width="100%">
			        <tr>
			          <td align="left">
			          	<div>
			          	<c:forEach var="whereDataParam" items="${whereData}" varStatus="name">
			          		<c:if test="${whereDataParam.dataType eq 'text'}">
			          			${whereDataParam.name} <input type="text" class="text" size="10" name="${whereDataParam.conditParam}" value="${whereDataParam.defaultValue}"/> &nbsp;
			          		</c:if>
			          		
			          		<c:if test="${whereDataParam.dataType eq 'date_time'}">
			          			${whereDataParam.name}<input size="10" class="text"  name="${whereDataParam.conditParam}" value="${whereDataParam.defaultValue}" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>&nbsp;
			          		</c:if>
			          		
			          		<c:if test="${whereDataParam.dataType eq 'pull_down'}">
			          			${whereDataParam.name}
								<select style="width:100px;height:27px;" name="${whereDataParam.conditParam}" editable="false" panelHeight="auto" class="easyui-combobox">
									<option value="">所有</option>
									<c:forEach var="pullDownDefaultValue" items="${whereDataParam.pullDownMap}" varStatus="name" >
										<option value="${pullDownDefaultValue.value}">${pullDownDefaultValue.key}</option>
									</c:forEach>
								</select>
								&nbsp;
			          		</c:if>
			          	</c:forEach> 
			          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false" onclick="ajaxRequest()"><i class="fa fa-search fa-lg fa-fw fa-col"></i>查询（include）</a>
			          	</div>
			          </td>
			        </tr>
			      </table>
			    </form>
			</div>
		</c:if>
</body>

	<script type="text/javascript" src="/manage/assert/plugin/My97DatePicker/WdatePicker.js"></script>

</html>