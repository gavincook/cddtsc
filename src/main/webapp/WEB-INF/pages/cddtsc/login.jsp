<%@ taglib prefix="m" uri="/moon" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*"
         pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <m:require src="jquery,handlebars,common,noty,font,ev,webuploader,dialog,bootstrap,{cddtsc/login}"></m:require>
    <m:require src="css/pages/cddtsc/common.css" type="css"></m:require>
    <title>成都通道蔬菜</title>
</head>

<body>
<div class="page-navigation">
    <div class="navigationbar">
        <div class="header-container">
            <span class="floatl">品质食材，只要成都道通蔬菜！<span class="boldhead">免费配送</span>。</span>
            <span class="floatr">
                <a href="/user/login.html"><span class="boldhead">登陆</span></a>
                <a href="login.html">会员中心</a>
                <a href="javascript:void(0)" onclick="SetHome(this,window.location)" style="color:red;font-weight:bold;">
                    设为首页</a>|<a href="javascript:void(0)" onclick="AddFavorite(window.location,document.title)" style="color:red;font-weight:bold;">
                收藏本站</a>
                <a href="/aboutus.html?cid=7">帮助中心</a>
                <a href="/aboutus.html?cid=1">关于我们</a>
            </span>
        </div>
    </div>
    <div class="header clearfix">
        <div class="wrapper thead">
            <h1 class="logo">
                <a href="/"><img src="/css/images/cddtsc/logo.png" width="260" height="38" title="道通蔬菜"></a>
            </h1>
            <div class="telephone">
                <span class="topimg">
                  <img src="/css/images/cddtsc/tel.png" width="280" height="55">
                </span>
            </div>
        </div>
    </div>
</div>

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

<%--页面底部信息栏--%>
<div class="footer">
    <div class="wrapper">
        <div class="copyright">版权所有&nbsp;&nbsp;&nbsp;道通蔬菜专业合作社 Copyright www.cddtsc.com All Rights Reserved&nbsp;&nbsp;&nbsp;蜀ICP备13024935号-1&nbsp;&nbsp;&nbsp; 网站建设及推广服务：<a href="http://www.renrenbang.com" target="_blank" style="color:white;">人人帮</a></div>
    </div>
</div>

</body>
</html>