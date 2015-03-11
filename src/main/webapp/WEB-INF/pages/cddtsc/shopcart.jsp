<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <m:require src="jquery,common,handlebars,bootstrap,table,dialog,noty,font,{cddtsc/shopcart}"></m:require>
    <title>购物车</title>

</head>
<body class="shopcart">

    <%--购物车表格--%>
    <table id="shopcartTable" class="table">
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


    <%--地址模板--%>
    <script type="text/html" id="shopcartTemplate">
        {{#each this}}
        <tr data-id="{{id}}">
            <td class="img-container">
                <img src="${pageContext.request.contextPath}file/get/{{url}}">
            </td>
            <td>{{name}}</td>
            <td>{{specification}}</td>
            <td>{{price}}</td>
            <td>{{number}}</td>
            <td>{{plus number price }}</td>

        </tr>
        {{/each}}
    </script>
</body>
</html>