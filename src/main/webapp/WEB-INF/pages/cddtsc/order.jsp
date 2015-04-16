<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="../common/header.jsp" %>
    <m:require src="jquery,common,handlebars,bootstrap,table,dialog,noty,font,{cddtsc/order}"></m:require>
    <title>订单</title>

</head>
<body class="order">
<div class="wrapper">
    <%@ include file="../common/nav.jsp" %>
    <div class="content-section">
        <h3 class="header-title">${currentMenu.menuName}</h3>
<%--订单表格--%>
<table id="orderTable" class="table table-responsive">
    <thead>
    <th colspan="2">物品</th>
    <th>规格</th>
    <th>单价</th>
    <th>数量</th>
    <th>金额</th>
    <th></th>
    </thead>

</table>

<%--订单模板--%>
<script type="text/html" id="orderTemplate">
    {{#each this}}
    <tbody data-id="{{id}}">
    <tr class="sep-row"></tr>
    <tr class="order-summarize">
        <td colspan="7">
            <span>订单时间：{{time}}</span>
            <span>总价格：<i class="fa fa-rmb"></i> {{totalPrice}}</span>
            <span>
                {{#if serverOpt}}
                     <button type="button" class="btn btn-default opt" data-action="{{action}}">{{orderBtnText}}</button>
                {{else}}
                    {{currentStatus}}
                {{/if}}

            </span>
            <a href="javascript:void(0)" class="btn-link remove">删除</a>
        </td>
    </tr>
    {{#each orderDetail}}
    <tr data-price="{{price}}">
        <td class="img-container">
            <img src="${pageContext.request.contextPath}/file/get/{{url}}">
        </td>
        <td>{{name}}</td>
        <td>{{specification}}</td>
        <td>{{price}}</td>
        <td>
            <div class="input-group number">
                {{purchaseNumber}}
            </div>
        </td>
        <td class="subtotal">{{plus purchaseNumber price }}</td>
        <td></td>
    </tr>
    {{/each}}
    </tbody>
    {{/each}}
</script>
        </div>
    </div>
</body>
</html>