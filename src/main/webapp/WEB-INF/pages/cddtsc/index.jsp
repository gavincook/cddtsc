<%@ taglib prefix="m" uri="/moon" %>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="../common/header.jsp" %>
    <m:require src="jquery,handlebars,common,noty,font,ev,webuploader,dialog,bootstrap,{cddtsc/index}"></m:require>
    <title>成都通道蔬菜</title>
</head>

<body>
<div class="content">
<%@ include file="../common/frontHeader.jsp"%>

<div class="goods-show container">
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

<div class="menu-bar container">
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
<div class="goods-list-container container">
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

</div>
<%@ include file="../common/footer.jsp"%>
</body>
</html>