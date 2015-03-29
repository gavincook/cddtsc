<%@ taglib prefix="m" uri="/moon" %>
<%@ page contentType="text/html;charset=utf-8" language="java" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="../common/header.jsp" %>
    <m:require src="jquery,handlebars,common,noty,font,ev,webuploader,dialog,bootstrap,{cddtsc/login}"></m:require>
    <m:require src="css/pages/cddtsc/common.css" type="css"></m:require>
    <title>成都通道蔬菜</title>
</head>

<body>
<div class="content">
<%@ include file="../common/frontHeader.jsp"%>

<div class="login-container container">
    <form id="loginForm" action="" class="login-form">
        <h3 class="title">登录</h3>
        <div class="input-group">
            <div class="input-group-addon"><i class="fa fa-user input-icon"></i></div>
            <input type="text" name="userName" autocomplete="off" class="form-control"validate="validate[minsize(6),maxsize(15)]" errmsg="用户名为6~15个字符">
        </div>
        <div class="input-group">
            <div class="input-group-addon"><i class="fa fa-lock input-icon"></i></div>
            <input name="password" type="password" autocomplete="off" class="form-control" validate="validate[minsize(6)]" errmsg="密码须6位以上">
        </div>
        <div class="user-regist-bottom">
            <button class="btn btn-primary btn-user-login" type="button">登&nbsp;录</button>
        </div>
    </form>
</div>
</div>
<%@ include file="../common/footer.jsp"%>
</body>
</html>