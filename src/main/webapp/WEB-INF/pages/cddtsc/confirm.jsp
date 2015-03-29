<%@ page language="java" contentType="text/html;charset=utf-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="../common/header.jsp" %>
    <m:require src="jquery,common,handlebars,bootstrap,table,dialog,noty,font,{cddtsc/confirm}"></m:require>
    <title>订单确认</title>

</head>
<body class="confirm">
<div class="content">
    <%@ include file="../common/frontHeader.jsp"%>
    <div class="container">

        <div class="address-container">
            <h4>请选择您的收货地址<a href="${pageContext.request.contextPath}/user/my" target="_blank" class="manage-address">(管理收货地址)</a></h4>
            <div class="address-items">
                <c:forEach items="${addresses}" var="address">
                <div class="address-item" data-id="${address.id}">
                    <div class="consignee">${address.consignee}</div>
                    <div class="address">${address.address}${address.phoneNumber} </div>
                    <i class="fa fa-check check-icon"></i>
                </div>
                </c:forEach>
            </div>
        </div>

        <h4>确认订单信息</h4>
        <%--购物车表格--%>
        <table id="shopcartTable" class="table table-responsive">
            <thead>
                <th colspan="2">物品</th>
                <th>规格</th>
                <th>单价</th>
                <th>数量</th>
                <th>金额</th>
            </thead>
            <tbody class="shopcart-items">

            </tbody>
        </table>
        <div class="horizontal-line"></div>

        <div class="bar">
            <span>实付款：</span>
            <span class="total">
                <i class="fa fa-rmb"></i>
                <span id="totalNum"></span>
            </span>
        </div>
        <div class="horizontal-line"></div>
        <div class="opts">

            <form action="${pageContext.request.contextPath}/order/submit" method="post">
                <a href="${pageContext.request.contextPath}/shopcart" class="btn btn-link btn-warning">返回购物车修改</a>
                <input name="addressId" id="addressId" type="hidden">
                <button class="btn btn-danger btn-lg submit-order" type="submit">提交订单</button>
            </form>
        </div>
    </div>
</div>
    <%--地址模板--%>
    <script type="text/html" id="shopcartTemplate">
        {{#each this}}
        <tr data-id="{{cartId}}" data-price="{{price}}">
            <td class="img-container">
                <img src="${pageContext.request.contextPath}/file/get/{{url}}">
            </td>
            <td>{{name}}</td>
            <td>{{specification}}</td>
            <td>{{price}}</td>
            <td>
                <div class="input-group number">
                    {{number}}
                </div>
            </td>
            <td class="subtotal">{{plus number price }}</td>
        </tr>
        {{/each}}
    </script>
<%@ include file="../common/footer.jsp"%>
</body>
</html>