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

<div class="login-container wrapper">
    <form id="login-form" action="">
        <div class="control-group">
            <span class="content left">会员名称:</span>
            <span class="right">
                <input type="text"  name="userName" validate="validate[required,number,call(checkPhoneNumber),call(isUserNameExists)]" placeholder="请输入您的手机号" errMsg="请输入有效的电话号码!"/>
            </span>
            <span class="errer-messae"></span>
        </div>
        <div class="control-group">
            <span class="content left">密&nbsp;&nbsp;&nbsp;&nbsp;码:</span>
            <span class="right">
                <input type="password" class="password" name="password" validate="validate[required,minsize(6)]" placeholder="请输入不少于6个字符" errMsg="请输入不少于6个字符的密码!"/>
            </span>
            <span class="errer-messae"></span>
        </div>
        <div class="user-regist-bottom">
            <span><button class="btn btn-primary btn-user-login" type="button">登陆</button></span>
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