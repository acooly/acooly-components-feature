<!DOCTYPE html>
<html>
<head>
    <title>${content.title}</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
	<script type="text/javascript" src="//cdn.bootcss.com/jquery/1.9.1/jquery.min.js" charset="utf-8"></script>
</head>
	<body style="height: 100%; margin: 0">
		<#if (content.contentBody)??>
			${content.contentBody.body}
		</#if>
	</body>
</html>

