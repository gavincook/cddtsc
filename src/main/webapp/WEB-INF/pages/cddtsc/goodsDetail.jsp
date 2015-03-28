<%@ taglib prefix="m" uri="/moon" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*"
         pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <m:require src="jquery,handlebars,common,noty,font,ev,webuploader,dialog,bootstrap,{cddtsc/goodsDetail}"></m:require>
    <m:require src="css/pages/cddtsc/common.css" type="css"></m:require>
    <title>成都通道蔬菜</title>
</head>

<body>
<div class="page-navigation">
    <div class="navigationbar">
        <div class="header-container">
            <span class="floatl">品质食材，只要成都道通蔬菜！<span class="boldhead">免费配送</span>。</span>
            <span class="floatr">
                <a href="/user/regist.html"><span class="boldhead">注册</span></a>
                <a href="/user/login.html"><span class="boldhead">登陆</span></a>
                <a href="login.html">会员中心</a>
                <a href="javascript:void(0)" onclick="SetHome(this,window.location)" style="color:red;font-weight:bold;">
                    设为首页</a>|<a href="javascript:void(0)" onclick="AddFavorite(window.location,document.title)" style="color:red;font-weight:bold;">
                收藏本站</a>
                <a href="/index.html">帮助中心</a>
                <a href="/index.html">关于我们</a>
            </span>
        </div>
    </div>
    <div class="header clearfix">
        <div class="wrapper thead">
            <h1 class="logo">
                <a href="/index.html"><img src="/css/images/cddtsc/logo.png" width="260" height="38" title="道通蔬菜"></a>
            </h1>
            <div class="telephone">
                <span class="topimg">
                  <img src="/css/images/cddtsc/tel.png" width="280" height="55">
                </span>
            </div>
        </div>
    </div>
</div>

<div class="goods-show wrapper">
    <div><span>${goods.name}</span></div>
</div>

<%--商品详情--%>
<div class="goods-container wrapper"  data-user-name="${user.userName}" data-goods-id="${goods.id}">
    <span class="goods-left">
        <div class="goods-img">
            <img src="${pageContext.request.contextPath}/file/get/${images[0].url}">
        </div>
        <div class="goods-img-list">
            <c:forEach items="${images}" var="image">
                <span class="image"><img src="${pageContext.request.contextPath}/file/get/${image.url}"></span>
            </c:forEach>
        </div>
    </span>
    <span class="goods-right">
        <div class="goods-name">商品名称：&nbsp;${goods.name}</div>
        <div class="goods-price">价格：&nbsp;${goods.price}元</div>
        <div class="goods-buy">
            <span>购买数量：&nbsp;</span><span><input type="text" class="form-control" placeholder="请输入您要购买的数量" id="goodsNumber"></span>
        </div>
        <div class="goods-add"><span class="add-shopcar">添加到购物车</span></div>
    </span>
</div>


<div class="footer">
    <div class="wrapper">
        <div class="copyright">版权所有&nbsp;&nbsp;&nbsp;道通蔬菜专业合作社 Copyright www.cddtsc.com All Rights Reserved&nbsp;&nbsp;&nbsp;蜀ICP备13024935号-1&nbsp;&nbsp;&nbsp; 网站建设及推广服务：<a href="http://www.renrenbang.com" target="_blank" style="color:white;">人人帮</a></div>
    </div>
</div>

</body>
</html>