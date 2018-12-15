<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传文件测试1</title>
</head>
<body>
<h3>上传文件测试2</h3>

<form action="/ofile/upload.html?csrf=${requestScope['org.springframework.security.web.csrf.CsrfToken'].token}" enctype="multipart/form-data" method="post">
<div>
<label>文件1：</label><input type="file" name="file1">
</div>
<div>
<label>文件2：</label><input type="file" name="file2">
</div>
<div>
<label>文件3：</label><input type="file" name="file3">
</div>
<div>
<label>文件4：</label><input type="file" name="file4">
</div>
<div>
<label></label><input type="submit" value="上传">
</div>
</form>

<h3>下载测试</h3>
<img alt="缩略图" src="http://localhost:8080/ofile/thumbnail/21.html">
<a href="http://localhost:8080/ofile/download/21.html" target="_blank">下载</a>
</body>
</html>