<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<title>${sessionScope.securityConfig.title }</title>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="acooly">
<meta http-equiv="description" content="spring+jpa+hibernate+easyui+springmvc+jstl/freemarker">
<meta name="X-CSRF-TOKEN" content="${requestScope["org.springframework.security.web.csrf.CsrfToken"].token}"/>