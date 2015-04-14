<%@ taglib prefix="m" uri="/moon" %>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="../common/header.jsp" %>
    <m:require src="jquery,handlebars,common,noty,font,ev,webuploader,dialog,bootstrap,{cddtsc/categoryGoods}"></m:require>
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
    <script type="text/javascript">
        var categoryId = "${categoryId}";
    </script>
</head>

<body>
<div class="content">
    <%@ include file="../common/frontHeader.jsp" %>

    <%--商品列表--%>

    <div class="goods-list-container container">
        <div class="container-ul"></div>
    </div>
    <script type="text/html" id="goodsTemplate">
        {{#each this}}
        <div class="container-li" data-goods-id="{{id}}">
            <div>
                <div class="good-img">
                    <img src="${pageContext.request.contextPath}/file/get/{{cover}}">
                    <div class="helper"></div>
                </div>
                <div class="goods-content">
                    <div class="good-price"><i class="fa fa-rmb"></i><span class="price">{{price}}</span></div>
                    <div class="good-introduce">
                        <div class="title-main">{{name}}({{specification}})</div>
                        <%--<div class="title-assistant">{{specification}}</div>--%>
                    </div>
                </div>
            </div>
        </div>
        {{/each}}
    </script>
</div>
<%@ include file="../common/footer.jsp" %>
</body>
</html>