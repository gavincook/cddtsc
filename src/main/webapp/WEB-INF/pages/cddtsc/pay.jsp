<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <m:require src="jquery,common,handlebars,bootstrap,table,dialog,noty,font,{cddtsc/pay}"></m:require>
    <title>付款</title>

</head>
<body class="pay">

    <div class="container">
        <h3>付款</h3>
        <div class="order-detail">
            <c:forEach items="${orders}" var="order">
                <c:forEach items="${order.orderDetail}" var="orderDetail">
                    <div class="order-detail-item">
                        <span class="goods-name">商品：${orderDetail.name}</span>
                        <span class="purchase-number">购买数量：${orderDetail.purchaseNumber}</span>
                    </div>
                </c:forEach>
            </c:forEach>
        </div>

        <div class="pay-item">
            <span>需要付款:</span>
            <span class="money"><i class="fa fa-rmb"></i> ${totalPrice}</span>
        </div>
        <div class="pay-item">
            <form action="${pageContext.request.contextPath}/order/pay" method="Post" id="payForm">
                <span>账号密码：</span>
                <input type="password" name="password">
                <input type="hidden" name="orderId">
                <button type="button" class="btn btn-danger pay-btn"> 付款 </button>
            </form>
        </div>
    </div>

</body>
</html>