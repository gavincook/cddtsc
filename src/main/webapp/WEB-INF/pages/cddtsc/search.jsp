<%@ taglib prefix="m" uri="/moon" %>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="../common/header.jsp" %>
    <m:require src="jquery,handlebars,common,noty,font,ev,webuploader,dialog,bootstrap,{cddtsc/index}"></m:require>
    <title>成都通道蔬菜</title>
    <style type="text/css">
        .h{
            color:#D71616;
        }

        .info{
            text-align: center;
            margin-top: 100px;
        }

        .title {
            background: #59A111;
            height: 44px;
            line-height: 44px;
            font-size: 20px;
            padding-left: 20px;
            border-radius: 4px;
            margin-bottom: 20px;
        }
    </style>
</head>

<body>
<div class="content">
    <%@ include file="../common/frontHeader.jsp" %>

    <%--商品列表--%>
    <div class="goods-list-container container">
        <c:if test="${goodsList.totalItemsCount==0}">
            <div class="info">
            <img src="${pageContext.request.contextPath}/css/images/sorry.png" alt="">
            没有找到"
            <span class="h">${keyword}</span>
            "相关的菜品
            </div>
        </c:if>
        <c:if test="${goodsList.totalItemsCount>0}">
            <div class="title">
                为您找到 <span class="h"> ${goodsList.totalItemsCount}</span> 件在售菜品：
            </div>
            <ul class="container-ul">
                <c:forEach items="${goodsList.items}" var="goods">
                    <li class="container-li" data-goods-id="${goods.goodsId}">
                        <div>
                            <div class="good-img">
                                <img src="${pageContext.request.contextPath}/file/get/${goods.cover}">
                                <div class="helper"></div>
                            </div>
                            <div class="goods-content">
                                <div class="good-price"><i class="fa fa-rmb"></i> <span class="price">${goods.price}</span>
                                </div>
                                <div class="good-introduce">
                                    <div class="title-main">${goods.name}</div>
                                    <div class="title-assistant">${goods.specification}</div>
                                </div>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
    </div>
</div>
<%@ include file="../common/footer.jsp" %>
</body>
</html>