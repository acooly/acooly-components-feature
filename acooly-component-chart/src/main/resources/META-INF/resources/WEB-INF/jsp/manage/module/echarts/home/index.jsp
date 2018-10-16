<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<html>

<body>
	<c:forEach var="e" items="${chartItemsList}">
		<iframe src="/manage/module/echarts/chartItem_${e.type.code}_${e.id}.html" height="50%" width="50%"></iframe>
	</c:forEach>
</body>

</html>

