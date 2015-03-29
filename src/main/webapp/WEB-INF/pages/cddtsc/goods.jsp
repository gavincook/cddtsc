<%@ taglib prefix="m" uri="/moon" %>
<%@ page contentType="text/html;charset=utf-8" language="java" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="../common/header.jsp" %>
    <m:require src="jquery,handlebars,common,noty,font,ev,webuploader,dialog,bootstrap,{cddtsc/goods}"></m:require>
    <m:require src="css/pages/cddtsc/common.css" type="css"></m:require>
    <title>成都通道蔬菜</title>
</head>

<body>
<div class="content">
<%@ include file="../common/frontHeader.jsp"%>

<div class="goods-show wrapper">
    <div>今天一共有如下商铺销售<span>${goods.name}</span></div>
</div>

<%--商品列表--%>
<div class="goods-list-container wrapper">
    <ul class="container-ul">
        <c:forEach items="${shops.items}" var="shop">
            <li class="container-li" data-goods-id="${shop.goodsId}">
                <div>
                    <div class="good-img"><img src="${pageContext.request.contextPath}/file/get/${goods.cover}"></div>
                    <div class="goods-content">
                        <div class="goods-seller">店铺: <span>${shop.shopName}</span></div>
                    </div>
                </div>
            </li>
        </c:forEach>
    </ul>
</div>
</div>
<%--页面底部信息栏--%>


<%@ include file="../common/footer.jsp"%>
</body>
</html>