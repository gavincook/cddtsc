<%@ taglib prefix="m" uri="/moon" %>
<%@ page contentType="text/html;charset=utf-8" language="java" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="../common/header.jsp" %>
    <m:require src="jquery,handlebars,common,noty,font,bootstrap,jqzoom,{cddtsc/goodsDetail}"></m:require>
    <m:require src="css/pages/cddtsc/common.css" type="css"></m:require>
    <script type="text/javascript">
        var user = "${user.userName}";
        var inventory = "${goods.inventory}";
    </script>
    <title>成都通道蔬菜</title>
</head>

<body>
<div class="content">
<%@ include file="../common/frontHeader.jsp"%>

<div class="goods-show container">
    <div class="title"><span>${goods.name}</span></div>
</div>

<%--商品详情--%>
<div class="goods-container container" data-goods-id="${goods.userGoodsId}">
    <span class="goods-right">
        <div class="goods-name">商品名称：&nbsp;${goods.name}</div>
        <div class="goods-price">价格：&nbsp;<i class="fa fa-rmb"></i> ${goods.price}</div>
        <div class="goods-price">等级：&nbsp; ${goods.level}</div>
        <div class="goods-price">等级描述：&nbsp; ${goods.levelDescription}</div>
        <div class="goods-price">计量单位：&nbsp; ${goods.unit}</div>
        <div class="goods-price">规格：&nbsp;
            <c:forEach items="${specs}" var="spec">
                <span data-url="${spec.userGoodsId}_${spec.goodsId}" class="spec <c:if test='${spec.userGoodsId==goods.userGoodsId}'> selected</c:if>">${spec.specification}</span>
            </c:forEach>
        </div>
        <div class="goods-buy">
            <span>购买数量：&nbsp;</span>
            <div class="number">
                <div class="input-group">
                    <span class="input-group-addon btn minus">-</span>
                    <input type="text" class="form-control number-box" value="1" id="goodsNumber">
                    <span class="input-group-addon  btn plus">+</span>
                </div>
            </div>
            <span>(库存：${goods.inventory} ${goods.unit})</span>
        </div>
        <div class="goods-add">
            <%--<button class="btn btn-danger buy">立即购买</button>--%>
            <button class="btn btn-danger add-shopcar">添加到购物车</button>
        </div>

    </span>
    
    <span class="goods-left">
        <div class="goods-img">
            <a href="${pageContext.request.contextPath}/file/get/${images[0].url}" class="jqzoom" rel='gal1'  title="商品大图" >
                <img src="${pageContext.request.contextPath}/file/get/${images[0].url}"  title="大图" class="middle-img"  style="border: 4px solid #666;">
            </a>
        </div>
        <div class="goods-img-list">
            <span class="left-opt"><i class="fa fa-angle-left"></i> </span>

            <span class="right-opt"><i class="fa fa-angle-right"></i> </span>
            <div class="small-images">
                <c:forEach items="${images}" var="image" varStatus="status">
                    <span class="image">
                        <a class='<c:if test="${status.index ==0}">zoomThumbActive</c:if>' href='javascript:void(0);'
                           rel="{gallery: 'gal1', smallimage: '${pageContext.request.contextPath}/file/get/${image.url}',largeimage: '${pageContext.request.contextPath}/file/get/${image.url}'}">
                            <img src='${pageContext.request.contextPath}/file/get/${image.url}'>
                        </a>
                    </span>
                </c:forEach>
            </div>
        </div>
        <div class="clearfix"></div>
    </span>


</div>

    <div class="container">
        <div class="description-title">商品详情</div>
        <div class="description-content">
            ${goods.description}
        </div>
    </div>

    <div class="container">
        <div class="comment-title">评论详情(${comments.size()})</div>
        <c:forEach items="${comments}" var="comment" varStatus="status">
            <div class="comment-content <c:if test="${status.index < comments.size()-1}">has-border</c:if>">
                <div class="comment"><span>${comment.content}</span></div>
                <div class="realname"><span>${comment.realName}</span></div>
                <div class="time"><span>${comment.time}</span></div>
            </div>
        </c:forEach>
    </div>
</div>

<%@ include file="../common/footer.jsp"%>

</body>
</html>