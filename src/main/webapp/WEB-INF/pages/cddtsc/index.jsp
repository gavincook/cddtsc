<%@ taglib prefix="m" uri="/moon" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*"
         pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <m:require src="jquery,handlebars,common,noty,font,ev,webuploader,dialog,bootstrap,{cddtsc/index}"></m:require>
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
                <a href="/index">会员中心</a>
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
                <a href="/index.html"><img src="css/images/cddtsc/logo.png" width="260" height="38" title="道通蔬菜"></a>
            </h1>
            <div class="telephone">
                <span class="topimg">
                  <img src="css/images/cddtsc/tel.png" width="280" height="55">
                </span>
            </div>
        </div>
    </div>
</div>

<div class="goods-show wrapper">
    <div class="clearfix">
        <div class="goods-picture-container">
            <div class="banner">
                <a href="/index.html"><img src="css/images/cddtsc/m4nxyu.jpg"></a>
            </div>
        </div>
        <div class="goods-scare-buying-container">
            <div class="countdown">
                <h2 class="tita"><a href="snapgoods.html">更多抢购</a>限时抢购</h2>
                <div class="bor">
                    <div class="snapgoods" currid="NaN">
                        <div class="time">暂无抢购</div>
                        <div class=""><img src="css/images/cddtsc/snap.png" width="215" height="215"></div>
                    </div>
                    <div class="snapgoods" currid="NaN">
                        <span class="goodslfet">
                            <span class="snapleft"></span>
                        </span>
                        <span class="goodsright">
                            <span class="snapright"></span>
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="menu-bar wrapper">
    <h2 class="tita1">
        <ul class="arrondi-tit">
            <li class="licur"><a href="product.html" target="_blank">所有新品</a></li>
            <li><a href="product.html?cid=2" target="_blank">绿色专区</a></li>
            <li><a href="product.html?cid=3" target="_blank">有机专区</a></li>
            <li><a href="product.html?cid=4" target="_blank">无公害专区</a></li>
        </ul>
        <span>新品推荐</span>
    </h2>
</div>
<%--商品列表--%>
<div class="goods-list-container wrapper">
    <ul class="container-ul">

    </ul>
</div>
    <script type="text/html" id="goodsTemplate">
        {{#each this}}
        <li class="container-li" data-goods-id="{{id}}">
            <div>
                <div class="good-img"><img src="${pageContext.request.contextPath}/file/get/{{cover}}"></div>
                <div class="goods-content">
                    <div class="good-price"><i>￥</i><span class="price">{{price}}</span></div>
                    <div class="good-introduce">
                        <div class="title-main">{{name}}</div>
                        <div class="title-assistant">{{specification}}</div>
                    </div>
                </div>
            </div>
        </li>
        {{/each}}
    </script>
<%--页面底部信息栏--%>
<%--<div class="service-list wrapper">--%>
    <%--<dl class="svrfore-0">--%>
        <%--<dt>--%>
            <%--<b></b>--%>
            <%--<strong>关于我们</strong>--%>
        <%--</dt>--%>
        <%--<dd>--%>
            <%--<div><a href="newslist.html?cid=43">产品介绍</a></div>--%>
            <%--<div><a href="aboutus.html?cid=8">公司简介</a></div>--%>
            <%--<div><a href="aboutus.html?cid=7">联系我们</a></div>--%>
            <%--<div><a href="aboutus.html?cid=20">人才招聘</a></div>--%>
            <%--<div><a href="aboutus.html?cid=47">我要当会员</a></div>--%>
        <%--</dd>--%>
    <%--</dl>--%>
    <%--<dl class="svrfore-2">--%>
        <%--<dt>--%>
            <%--<b></b>--%>
            <%--<strong>配送方式</strong>--%>
        <%--</dt>--%>
        <%--<dd>--%>
            <%--<div><a href="aboutus.html?cid=23">上门自提</a></div>--%>
            <%--<div><a href="aboutus.html?cid=25">配送服务查询</a></div>--%>
            <%--<div><a href="aboutus.html?cid=26">配送收费标准</a></div>--%>
            <%--<div><a href="0?cid=46">本社连锁店配送到小区</a></div>--%>
        <%--</dd>--%>
    <%--</dl>--%>
    <%--<dl class="svrfore-3">--%>
        <%--<dt>--%>
            <%--<b></b>--%>
            <%--<strong>支付方式 </strong>--%>
        <%--</dt>--%>
        <%--<dd>--%>
            <%--<div><a href="aboutus.html?cid=29">在线支付</a></div>--%>
            <%--<div><a href="aboutus.html?cid=32">公司转账</a></div>--%>
            <%--<div><a href="0?cid=44">到连锁店现金充值</a></div>--%>
            <%--<div><a href="aboutus.html?cid=45">到连锁店刷信用卡或借记卡</a></div>--%>
        <%--</dd>--%>
    <%--</dl>--%>
    <%--<dl class="svrfore-4">--%>
        <%--<dt>--%>
            <%--<b></b>--%>
            <%--<strong>售后服务</strong>--%>
        <%--</dt>--%>
        <%--<dd>--%>
            <%--<div><a href="aboutus.html?cid=34">售后政策</a></div>--%>
            <%--<div><a href="aboutus.html?cid=35">价格保护</a></div>--%>
            <%--<div><a href="aboutus.html?cid=36">会员权利</a></div>--%>
        <%--</dd>--%>
    <%--</dl>--%>
    <%--<div class="callctn">--%>
        <%--<div>售前咨询</div>--%>
        <%--<div class="call-serve">400 8822 883</div>--%>
        <%--<div>售前咨询</div>--%>
        <%--<div class="call-serve">400 8822 883</div>--%>
        <%--<div>服务时间：9:00-21:00</div>--%>
    <%--</div>--%>
<%--</div>--%>

<div class="footer">
    <div class="wrapper">
        <div class="copyright">版权所有&nbsp;&nbsp;&nbsp;道通蔬菜专业合作社 Copyright www.cddtsc.com All Rights Reserved&nbsp;&nbsp;&nbsp;蜀ICP备13024935号-1&nbsp;&nbsp;&nbsp; 网站建设及推广服务：<a href="http://www.renrenbang.com" target="_blank" style="color:white;">人人帮</a></div>
    </div>
</div>

</body>
</html>