<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <m:require src="jquery,common,handlebars,bootstrap,table,dialog,noty,font,{cddtsc/confirm}"></m:require>
    <title>付款</title>

</head>
<body class="confirm">

    <div class="address-container">
        <h4>付款<a href="${pageContext.request.contextPath}/user/my" target="_blank" class="manage-address">(管理收货地址)</a></h4>
    </div>


</body>
</html>