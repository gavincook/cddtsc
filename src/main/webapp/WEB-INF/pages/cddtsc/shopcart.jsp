<%@ page language="java" contentType="text/html;charset=utf-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="../common/header.jsp" %>
    <m:require src="jquery,common,handlebars,bootstrap,table,dialog,noty,font,{cddtsc/shopcart}"></m:require>
    <title>购物车</title>

</head>
<body class="shopcart">

<div class="content">
    <%@ include file="../common/frontHeader.jsp"%>
    <div class="container">
        <%--购物车表格--%>
        <table id="shopcartTable" class="table table-responsive">
            <thead>
                <th colspan="2">物品</th>
                <th>等级</th>
                <th>规格</th>
                <th>单价</th>
                <th>数量</th>
                <th>金额</th>
                <th></th>
            </thead>
            <tbody class="shopcart-items">

            </tbody>
        </table>
        <div class="horizontal-line"></div>

        <div class="bar">
            <span>合计：</span>
            <span class="total">
                <i class="fa fa-rmb"></i>
                <span id="totalNum"></span>
            </span>
            <button class="btn btn-warning btn-lg confirm">结算</button>
        </div>
        <div class="horizontal-line"></div>
    </div>
    <%--地址模板--%>
    <script type="text/html" id="shopcartTemplate">
        {{#each this}}
        <tr data-id="{{cartId}}" data-price="{{price}}">
            <td class="img-container">
                <img src="${pageContext.request.contextPath}file/get/{{url}}">
            </td>
            <td>{{name}}</td>
            <td>{{level}}</td>
            <td>{{specification}}</td>
            <td>{{price}}</td>
            <td>
                <div class="input-group number">
                    <span class="input-group-addon btn minus">-</span>
                    <input type="text" class="form-control number-box" value="{{number}}">
                    <span class="input-group-addon  btn plus">+</span>
                </div>
            </td>
            <td class="subtotal">{{plus number price }}</td>
            <td><a href="javascript:void(0)" class="btn-link remove">删除</a></td>
        </tr>
        {{/each}}
    </script>
</div>
<%@ include file="../common/footer.jsp"%>
</body>
</html>