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
    <%@ include file="../common/frontHeader.jsp" %>

    <%--商品列表--%>
    <div class="goods-list-container container">
        <ul class="container-ul">
            <c:forEach items="${goodsList.items}" var="goods">
                <li class="container-li" data-goods-id="${goods.id}">
                    <div>
                        <div class="good-img"><img src="${pageContext.request.contextPath}/file/get/${goods.cover}">
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
    </div>
</div>
<%@ include file="../common/footer.jsp" %>
</body>
</html>