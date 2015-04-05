<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="common/header.jsp" %>
    <m:require src="jquery,common,ev,font,bootstrap,noty,{login}"/>
    <script type="text/javascript">
        var from = "${from}";
    </script>
    <title>成都通道蔬菜</title>
</head>
<body class="login container">


<div class="logo">
    <img src="${pageContext.request.contextPath}/css/images/cddtsc/logo.png">
</div>

<div class="content">
    <form id="loginForm" class="login-form">
        <div class="title">登&nbsp;录</div>
        <div class="input-group">
            <div class="input-group-addon"><i class="fa fa-user input-icon"></i></div>
            <input type="text" name="userName" class="form-control" value="system_user" validate="validate[minsize(6),maxsize(15)]"
                   errMsg="用户名为6~15个字符"/>
        </div>
        <div class="input-group">
            <div class="input-group-addon"><i class="fa fa-lock input-icon"></i></div>
            <input name="password" type="password" class="form-control" validate="validate[minsize(6)]" errMsg="密码须6位以上"/>
        </div>

        <input type="button" id="submit" class="btn btn-info btn-block login-btn" value="登&nbsp;&nbsp;录"/>
    </form>

    <div class="login-banner">
        <img src="${pageContext.request.contextPath}/css/images/cddtsc/m4nxyu.jpg">
        <span class="helper"></span>
    </div>
</div>



<div class="system-info-container">
    <div class="system-info">用户名:system_user</div>
    <div class="system-info">密码：<m:systemUserPassword/></div>
</div>

<div class="footer">
    &copy;2015 成都通道蔬菜 版权所有
</div>
</body>
</html>